document.addEventListener('DOMContentLoaded', () => {
    
    // --- Lawyer Profile Snippet ---
    const profileContainer = document.getElementById('lawyer-profile-container');
    const profileLoading = document.getElementById('lawyer-profile-loading');
    const profileError = document.getElementById('lawyer-profile-error');

    async function loadLawyerProfile() {
        try {
            const response = await window.ApiService.get('/api/v1/public/lawyer-profile');
            if (response.success && response.data) {
                renderProfile(response.data);
            } else {
                showProfileError();
            }
        } catch (error) {
            console.error('Failed to load profile', error);
            showProfileError();
        }
    }

    function renderProfile(profile) {
        profileLoading.classList.add('d-none');
        
        const html = `
            <div class="col-lg-5 mb-4 mb-lg-0">
                <div class="bg-secondary rounded" style="aspect-ratio: 4/5; width: 100%; display: flex; align-items: center; justify-content: center; color: white;">
                    [PLACEHOLDER_IMG]
                </div>
            </div>
            <div class="col-lg-6 offset-lg-1">
                <h2 class="h2 mb-2">${escapeHtml(profile.name || 'Dhiraj Sawant')}</h2>
                <p class="h5 text-muted mb-4">${escapeHtml(profile.title || 'Advocate, High Court')}</p>
                <div class="mb-4">
                    <p class="lead text-muted">${escapeHtml(profile.biography || '')}</p>
                </div>
                <a href="/about" class="btn btn-outline-primary vc-btn-secondary" aria-label="Read full profile of ${escapeHtml(profile.name || 'Dhiraj Sawant')}">Read Full Profile</a>
            </div>
        `;
        profileContainer.innerHTML = html;
    }

    function showProfileError() {
        profileLoading.classList.add('d-none');
        profileError.classList.remove('d-none');
    }

    // --- Practice Areas Snippet ---
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
            <div class="col-md-4">
                <div class="vc-card h-100 p-4 d-flex flex-column">
                    <h3 class="h5 mb-3">${escapeHtml(pa.title)}</h3>
                    <p class="text-muted flex-grow-1">${escapeHtml(pa.shortDescription)}</p>
                    <a href="/practice-areas/${escapeHtml(pa.slug)}" class="text-decoration-none fw-bold mt-3" style="color: var(--vc-primary);" aria-label="Learn more about ${escapeHtml(pa.title)}">Learn More &rarr;</a>
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
    loadLawyerProfile();
    loadPracticeAreas();
});
