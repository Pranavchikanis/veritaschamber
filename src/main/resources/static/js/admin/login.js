document.addEventListener('DOMContentLoaded', () => {
    
    const form = document.getElementById('login-form');
    const errorAlert = document.getElementById('login-error');
    const submitBtn = document.getElementById('login-submit');

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            errorAlert.classList.add('d-none');
            
            // basic val
            if (!email || !password) return;

            submitBtn.disabled = true;
            submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Signing in...';

            try {
                const response = await window.ApiService.post('/api/v1/auth/login', {
                    email,
                    password
                });

                if (response.success) {
                    // Redirect to dashboard on success
                    window.location.href = '/admin/dashboard';
                }
            } catch (error) {
                errorAlert.classList.remove('d-none');
                errorAlert.textContent = error.status === 401 ? 'Invalid email or password.' : 'An error occurred during login.';
            } finally {
                submitBtn.disabled = false;
                submitBtn.textContent = 'Sign in';
            }
        });
    }
});
