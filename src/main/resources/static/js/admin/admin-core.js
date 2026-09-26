document.addEventListener('DOMContentLoaded', () => {
    
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                await window.ApiService.post('/api/v1/auth/logout', {});
            } catch (error) {
                console.error("Logout error", error);
            } finally {
                window.location.href = '/admin/login';
            }
        });
    }

    // Helper for basic HTML escaping in admin tables
    window.escapeHtml = function(unsafe) {
        if (!unsafe) return '';
        return unsafe
             .toString()
             .replace(/&/g, "&amp;")
             .replace(/</g, "&lt;")
             .replace(/>/g, "&gt;")
             .replace(/"/g, "&quot;")
             .replace(/'/g, "&#039;");
    };

    window.formatDate = function(dateStr) {
        if (!dateStr) return '';
        const d = new Date(dateStr);
        return d.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });
    };
});
