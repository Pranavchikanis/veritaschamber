document.addEventListener('DOMContentLoaded', () => {
    
    const container = document.getElementById('articles-container');
    const loading = document.getElementById('articles-loading');
    const errorMsg = document.getElementById('articles-error');
    const emptyMsg = document.getElementById('articles-empty');
    
    const paginationNav = document.getElementById('articles-pagination');
    const btnPrev = document.getElementById('btn-prev-page');
    const btnNext = document.getElementById('btn-next-page');

    let currentPage = 0;

    async function loadArticles(page = 0) {
        // Reset states
        container.innerHTML = '';
        container.appendChild(loading);
        loading.classList.remove('d-none');
        errorMsg.classList.add('d-none');
        emptyMsg.classList.add('d-none');
        paginationNav.classList.add('d-none');

        try {
            const response = await window.ApiService.get(`/api/v1/public/articles?page=${page}&size=10`);
            if (response.success && response.data) {
                renderArticles(response.data);
            } else {
                showError();
            }
        } catch (error) {
            console.error('Failed to load articles', error);
            showError();
        }
    }

    function renderArticles(pageData) {
        loading.classList.add('d-none');
        
        if (!pageData.content || pageData.content.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        }

        const itemsHtml = pageData.content.map(article => {
            const date = article.publishedAt ? new Date(article.publishedAt).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' }) : '';
            const categoryName = article.category ? article.category.name : 'General';
            
            return `
                <div class="col-md-6">
                    <div class="vc-card h-100 p-4 d-flex flex-column" style="transition: var(--vc-transition);">
                        <div class="mb-2 text-muted small">
                            <span>${escapeHtml(date)}</span> 
                            &bull; <span>${escapeHtml(categoryName)}</span>
                        </div>
                        <h2 class="h4 mb-3" style="font-family: var(--vc-font-serif);">
                            <a href="/legal-insights/${escapeHtml(article.slug)}" class="text-decoration-none" style="color: var(--vc-text-main);">${escapeHtml(article.title)}</a>
                        </h2>
                        <p class="text-muted flex-grow-1">${escapeHtml(article.excerpt || '')}</p>
                        <a href="/legal-insights/${escapeHtml(article.slug)}" class="text-decoration-none fw-bold mt-3" style="color: var(--vc-primary);">Read Article &rarr;</a>
                    </div>
                </div>
            `;
        }).join('');
        
        container.innerHTML = itemsHtml;
        
        // Pagination logic
        if (pageData.totalPages > 1) {
            paginationNav.classList.remove('d-none');
            
            const prevBtn = btnPrev.querySelector('button');
            const nextBtn = btnNext.querySelector('button');
            
            if (pageData.first) {
                btnPrev.classList.add('disabled');
                prevBtn.setAttribute('tabindex', '-1');
                prevBtn.onclick = null;
            } else {
                btnPrev.classList.remove('disabled');
                prevBtn.removeAttribute('tabindex');
                prevBtn.onclick = (e) => {
                    e.preventDefault();
                    currentPage--;
                    loadArticles(currentPage);
                };
            }
            
            if (pageData.last) {
                btnNext.classList.add('disabled');
                nextBtn.setAttribute('tabindex', '-1');
                nextBtn.onclick = null;
            } else {
                btnNext.classList.remove('disabled');
                nextBtn.removeAttribute('tabindex');
                nextBtn.onclick = (e) => {
                    e.preventDefault();
                    currentPage++;
                    loadArticles(currentPage);
                };
            }
        }
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
    const urlParams = new URLSearchParams(window.location.search);
    const initialPage = parseInt(urlParams.get('page')) || 0;
    currentPage = initialPage;
    loadArticles(currentPage);
});
