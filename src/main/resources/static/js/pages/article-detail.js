document.addEventListener('DOMContentLoaded', () => {
    
    const slug = document.body.getAttribute('data-slug');
    if (!slug) return;

    const loadingEl = document.getElementById('article-loading');
    const errorEl = document.getElementById('article-error');
    const contentWrapperEl = document.getElementById('article-content-wrapper');
    
    const breadcrumbCat = document.getElementById('breadcrumb-category');
    const titleEl = document.getElementById('article-title');
    const metaEl = document.getElementById('article-meta');
    const dateEl = document.getElementById('article-date');
    const categoryEl = document.getElementById('article-category');
    const excerptEl = document.getElementById('article-excerpt');
    const contentEl = document.getElementById('article-content');

    async function loadArticle() {
        try {
            const response = await window.ApiService.get(`/api/v1/public/articles/${encodeURIComponent(slug)}`);
            if (response.success && response.data) {
                renderArticle(response.data);
            } else {
                showError();
            }
        } catch (error) {
            console.error('Failed to load article detail', error);
            showError();
        }
    }

    function renderArticle(article) {
        loadingEl.classList.add('d-none');
        contentWrapperEl.classList.remove('d-none');
        metaEl.classList.remove('d-none');
        
        document.title = `${article.title} | Veritas Chambers`;
        
        const catName = article.category ? article.category.name : 'General';
        breadcrumbCat.textContent = catName;
        categoryEl.textContent = catName;
        
        titleEl.textContent = article.title;
        
        if (article.publishedAt) {
            dateEl.textContent = new Date(article.publishedAt).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
        } else {
            dateEl.textContent = 'Unpublished';
        }

        excerptEl.textContent = article.excerpt || '';
        contentEl.innerHTML = article.content || '';
    }

    function showError() {
        loadingEl.classList.add('d-none');
        errorEl.classList.remove('d-none');
    }

    // Init
    loadArticle();
});
