document.addEventListener('DOMContentLoaded', () => {
    
    const container = document.getElementById('faqAccordion');
    const loading = document.getElementById('faq-loading');
    const errorMsg = document.getElementById('faq-error');
    const emptyMsg = document.getElementById('faq-empty');

    async function loadFaqs() {
        try {
            const response = await window.ApiService.get('/api/v1/public/faqs');
            if (response.success && response.data) {
                renderFaqs(response.data);
            } else {
                showError();
            }
        } catch (error) {
            console.error('Failed to load faqs', error);
            showError();
        }
    }

    function renderFaqs(faqs) {
        loading.classList.add('d-none');
        
        if (!faqs || faqs.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        }

        const itemsHtml = faqs.map((faq, index) => {
            return `
                <div class="accordion-item mb-3 border-0 rounded shadow-sm">
                    <h2 class="accordion-header" id="heading${index}">
                        <button class="accordion-button collapsed bg-white fw-bold" style="color: var(--vc-text-main);" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${index}" aria-expanded="false" aria-controls="collapse${index}">
                            ${escapeHtml(faq.question)}
                        </button>
                    </h2>
                    <div id="collapse${index}" class="accordion-collapse collapse" aria-labelledby="heading${index}" data-bs-parent="#faqAccordion">
                        <div class="accordion-body text-muted bg-white pb-4 pt-2">
                            ${escapeHtml(faq.answer)}
                        </div>
                    </div>
                </div>
            `;
        }).join('');
        
        container.innerHTML = itemsHtml;
    }

    function showError() {
        loading.classList.add('d-none');
        errorMsg.classList.remove('d-none');
    }

    // Helper for basic HTML escaping
    function escapeHtml(unsafe) {
        if (!unsafe) return '';
        return unsafe
             .toString()
             .replace(/&/g, "&amp;")
             .replace(/</g, "&lt;")
             .replace(/>/g, "&gt;")
             .replace(/"/g, "&quot;")
             .replace(/'/g, "&#039;");
    }

    // Init
    loadFaqs();
});
