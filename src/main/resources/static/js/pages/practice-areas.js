document.addEventListener('DOMContentLoaded', () => {
    
    const paContainer = document.getElementById('practice-areas-container');
    const paLoading = document.getElementById('practice-areas-loading');
    const paError = document.getElementById('practice-areas-error');
    const paEmpty = document.getElementById('practice-areas-empty');

    async function loadPracticeAreas() {
        try {
            const response = await window.ApiService.get('/api/v1/public/practice-areas');
            if (response.success && response.data) {
                renderPracticeAreas(response.data);
            } else {
                showPaError();
            }
        } catch (error) {
            console.error('Failed to load practice areas', error);
            showPaError();
        }
    }

    function renderPracticeAreas(areas) {
        paLoading.classList.add('d-none');
        
        if (!areas || areas.length === 0) {
            paEmpty.classList.remove('d-none');
            return;
        }

        const itemsHtml = areas.map(pa => `
            <div class="col-md-6 col-lg-4">
                <div class="vc-card h-100 p-4 d-flex flex-column" style="transition: var(--vc-transition);">
                    <h2 class="h4 mb-3" style="font-family: var(--vc-font-serif);">${escapeHtml(pa.title)}</h2>
                    <p class="text-muted flex-grow-1">${escapeHtml(pa.shortDescription)}</p>
                    <a href="/practice-areas/${escapeHtml(pa.slug)}" class="text-decoration-none fw-bold mt-3" style="color: var(--vc-primary);">Learn More &rarr;</a>
                </div>
            </div>
        `).join('');
        
        paContainer.innerHTML = itemsHtml;
    }

    function showPaError() {
        paLoading.classList.add('d-none');
        paError.classList.remove('d-none');
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
    loadPracticeAreas();
});
