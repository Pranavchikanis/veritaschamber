document.addEventListener('DOMContentLoaded', () => {
    
    const profileContainer = document.getElementById('about-profile-container');
    const profileLoading = document.getElementById('about-profile-loading');
    const profileError = document.getElementById('about-profile-error');

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
            <div class="col-md-5 mb-4 mb-md-0">
                <div class="bg-secondary rounded w-100 h-100 d-flex align-items-center justify-content-center text-white" style="min-height: 400px; aspect-ratio: 3/4;">
                    [PLACEHOLDER_IMG]
                </div>
            </div>
            <div class="col-md-7 ps-md-5 d-flex flex-column justify-content-center">
                <h2 class="h1 mb-2" style="font-family: var(--vc-font-serif);">${escapeHtml(profile.name || 'Dhiraj Sawant')}</h2>
                <p class="h5 text-muted mb-4">${escapeHtml(profile.title || 'Advocate, High Court')}</p>
                
                <div class="mt-4">
                    <h3 class="h6 text-uppercase text-muted fw-bold mb-3" style="letter-spacing: 1px;">Biography</h3>
                    <p>${escapeHtml(profile.biography || '')}</p>
                </div>
                
                <div class="mt-5">
                    <h3 class="h6 text-uppercase text-muted fw-bold mb-3" style="letter-spacing: 1px;">Credentials</h3>
                    <p>${escapeHtml(profile.credentials || '')}</p>
                </div>
            </div>
        `;
        profileContainer.innerHTML = html;
    }

    function showProfileError() {
        profileLoading.classList.add('d-none');
        profileError.classList.remove('d-none');
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
});
