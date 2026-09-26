/**
 * chatbot.js
 * Frontend logic for the Veritas Chambers Website Chatbot
 */

document.addEventListener('DOMContentLoaded', () => {
    const launcher = document.getElementById('vc-chatbot-launcher');
    const panel = document.getElementById('vc-chatbot-panel');
    const closeBtn = document.getElementById('vc-chatbot-close');
    const form = document.getElementById('vc-chatbot-form');
    const input = document.getElementById('vc-chatbot-input');
    const sendBtn = document.getElementById('vc-chatbot-send');
    const messagesArea = document.getElementById('vc-chatbot-messages');
    const disclaimer = document.getElementById('vc-chatbot-disclaimer');

    if (!launcher || !panel || !form) return;

    // State
    const MAX_MESSAGES = 10;
    const conversation = [];
    let isRequestPending = false;

    // Security: Read CSRF token for API requests
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    // Setup toggling
    const toggleChat = () => {
        const isOpen = panel.classList.contains('is-open');
        if (isOpen) {
            panel.classList.remove('is-open');
            panel.setAttribute('aria-hidden', 'true');
            launcher.setAttribute('aria-expanded', 'false');
            launcher.focus(); // Return focus to launcher
        } else {
            panel.classList.add('is-open');
            panel.setAttribute('aria-hidden', 'false');
            launcher.setAttribute('aria-expanded', 'true');
            input.focus(); // Put focus on input
            scrollToBottom();
        }
    };

    launcher.addEventListener('click', toggleChat);
    closeBtn.addEventListener('click', toggleChat);

    // Auto-resize textarea
    input.addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
        
        // Validation check
        const val = this.value.trim();
        sendBtn.disabled = val.length === 0 || isRequestPending;
        
        if (val.length > 1000) {
            this.value = val.substring(0, 1000); // hard cap
        }
    });

    // Handle Enter key (Shift+Enter for new line)
    input.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            form.dispatchEvent(new Event('submit', { cancelable: true }));
        }
    });

    // Submission handler
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        if (isRequestPending) return;

        const content = input.value.trim();
        if (!content) return;

        if (conversation.length >= MAX_MESSAGES) {
            appendAlert('Conversation limit reached. Please reload the page to start a new chat.');
            return;
        }

        // Reset UI state
        input.value = '';
        input.style.height = 'auto';
        sendBtn.disabled = true;
        isRequestPending = true;

        // Hide disclaimer for new queries
        disclaimer.style.display = 'none';

        // 1. Add user message
        const userMsg = { role: 'user', content: content };
        conversation.push(userMsg);
        appendMessageUI(userMsg);

        // 2. Show loading
        const loadingId = appendLoadingUI();

        // 3. API Call
        try {
            const headers = {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            };
            if (csrfHeader && csrfToken) {
                headers[csrfHeader] = csrfToken;
            }

            const response = await fetch('/api/v1/public/chat', {
                method: 'POST',
                headers: headers,
                body: JSON.stringify({ messages: conversation })
            });

            removeLoadingUI(loadingId);

            if (!response.ok) {
                handleApiError(response.status);
                return;
            }

            const data = await response.json();

            // Handle successful response
            const assistantMsg = { role: 'assistant', content: data.reply };
            conversation.push(assistantMsg);
            appendMessageUI(assistantMsg);

            // Show disclaimer if requested by backend
            if (data.requiresDisclaimer) {
                disclaimer.style.display = 'block';
            }

        } catch (error) {
            removeLoadingUI(loadingId);
            appendAlert('A network error occurred. Please check your connection.');
            // Remove user message from local state so they can retry
            conversation.pop(); 
        } finally {
            isRequestPending = false;
            sendBtn.disabled = input.value.trim().length === 0;
            if (panel.classList.contains('is-open')) {
                input.focus();
            }
        }
    });

    // UI Helpers
    function appendMessageUI(msg) {
        const div = document.createElement('div');
        div.className = `vc-chat-msg ${msg.role}`;
        
        // Prevent XSS: use textContent instead of innerHTML
        // Convert newlines to <br> safely
        const lines = msg.content.split('\n');
        lines.forEach((line, index) => {
            if (index > 0) div.appendChild(document.createElement('br'));
            div.appendChild(document.createTextNode(line));
        });

        messagesArea.appendChild(div);
        scrollToBottom();
    }

    function appendAlert(text) {
        const div = document.createElement('div');
        div.className = 'vc-chat-alert vc-chat-alert-error';
        div.textContent = text;
        messagesArea.appendChild(div);
        scrollToBottom();
    }

    function appendLoadingUI() {
        const div = document.createElement('div');
        const id = 'loading-' + Date.now();
        div.id = id;
        div.className = 'vc-chat-loading';
        div.innerHTML = `
            <div class="vc-chat-loading-dot"></div>
            <div class="vc-chat-loading-dot"></div>
            <div class="vc-chat-loading-dot"></div>
        `;
        messagesArea.appendChild(div);
        scrollToBottom();
        return id;
    }

    function removeLoadingUI(id) {
        const el = document.getElementById(id);
        if (el) el.remove();
    }

    function scrollToBottom() {
        // Request animation frame ensures DOM is updated before scrolling
        requestAnimationFrame(() => {
            messagesArea.scrollTop = messagesArea.scrollHeight;
        });
    }

    function handleApiError(status) {
        // Remove the failed user message from the state since the backend rejected it
        conversation.pop();

        if (status === 400) {
            appendAlert('Message could not be sent due to invalid formatting or length limit.');
        } else if (status === 429) {
            appendAlert('Too many requests. Please wait a moment before trying again.');
        } else if (status === 503) {
            appendAlert('The assistant is temporarily unavailable. Please contact Veritas Chambers directly for assistance.');
        } else {
            appendAlert('An unexpected error occurred. Please try again later.');
        }
    }
});
