/**
 * navigation.js
 * Navigation logic for Veritas Chambers
 */

document.addEventListener('DOMContentLoaded', () => {
    const header = document.querySelector('.vc-header');
    
    // Sticky header shadow on scroll
    if (header) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 10) {
                header.classList.add('shadow-sm');
            } else {
                header.classList.remove('shadow-sm');
            }
        });
    }

    // Auto-close mobile menu when clicking outside (optional enhancement)
    const navbarCollapse = document.getElementById('vcMainNavigation');
    if (navbarCollapse) {
        document.addEventListener('click', (event) => {
            const isClickInside = header.contains(event.target);
            const isMenuOpen = navbarCollapse.classList.contains('show');
            
            if (!isClickInside && isMenuOpen) {
                const bsCollapse = bootstrap.Collapse.getInstance(navbarCollapse);
                if (bsCollapse) {
                    bsCollapse.hide();
                }
            }
        });
    }
});
