document.addEventListener('DOMContentLoaded', () => {
    
    const slug = document.body.getAttribute('data-slug');
    if (!slug) return;

    const paLoading = document.getElementById('pa-loading');
    const paError = document.getElementById('pa-error');
    const paContent = document.getElementById('pa-content');
    
    const titleEl = document.getElementById('pa-title');
    const breadcrumbEl = document.getElementById('breadcrumb-title');
    const shortDescEl = document.getElementById('pa-short-desc');
    const longDescEl = document.getElementById('pa-long-desc');

    async function loadPracticeArea() {
        try {
            const response = await window.ApiService.get(`/api/v1/public/practice-areas/${encodeURIComponent(slug)}`);
            if (response.success && response.data) {
                renderPracticeArea(response.data);
            } else {
                showError();
            }
        } catch (error) {
            console.error('Failed to load practice area detail', error);
            showError();
        }
    }

    function renderPracticeArea(pa) {
        paLoading.classList.add('d-none');
        paContent.classList.remove('d-none');
        
        document.title = `${pa.title} | Veritas Chambers`;
        titleEl.textContent = pa.title;
        breadcrumbEl.textContent = pa.title;
        shortDescEl.textContent = pa.shortDescription;
        
        // Description is rich HTML from the backend, so we use innerHTML
        // In a real prod environment, ensure the backend sanitizes this HTML 
        // before saving to the DB or before sending via API.
        longDescEl.innerHTML = pa.description || '';
    }

    function showError() {
        paLoading.classList.add('d-none');
        paError.classList.remove('d-none');
    }

    // Init
    loadPracticeArea();
});
