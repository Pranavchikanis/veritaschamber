/**
 * api.js
 * Frontend utility for REST API communication.
 */

const ApiService = (function() {
    
    // Helper to get CSRF token from meta tags
    function getCsrfToken() {
        const tokenElement = document.querySelector('meta[name="_csrf"]');
        const headerElement = document.querySelector('meta[name="_csrf_header"]');
        
        if (tokenElement && headerElement) {
            return {
                header: headerElement.getAttribute('content'),
                token: tokenElement.getAttribute('content')
            };
        }
        return null;
    }

    /**
     * Internal fetch wrapper
     */
    async function doFetch(url, options = {}) {
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            ...(options.headers || {})
        };

        // Add CSRF if required (for POST, PUT, DELETE)
        if (options.method && ['POST', 'PUT', 'DELETE', 'PATCH'].includes(options.method.toUpperCase())) {
            const csrf = getCsrfToken();
            if (csrf) {
                headers[csrf.header] = csrf.token;
            }
        }

        const config = {
            ...options,
            headers
        };

        try {
            const response = await fetch(url, config);
            
            // Handle 204 No Content
            if (response.status === 204) {
                return { success: true };
            }
            
            const data = await response.json().catch(() => null);
            
            if (!response.ok) {
                if (response.status === 401 && !url.includes('/api/v1/auth/login')) {
                    // Global intercept for unauthorized (except login itself)
                    window.location.href = '/admin/login';
                }
                throw {
                    status: response.status,
                    message: data?.message || 'An unexpected error occurred.',
                    errors: data?.fieldErrors || data?.errors || []
                };
            }
            
            return { success: true, data };
        } catch (error) {
            // Re-throw standardized error object
            if (error.status) {
                throw error;
            }
            throw {
                status: 0,
                message: 'Network error or server is unreachable.',
                errors: []
            };
        }
    }

    return {
        get: (url) => doFetch(url, { method: 'GET' }),
        post: (url, body) => doFetch(url, { method: 'POST', body: JSON.stringify(body) }),
        put: (url, body) => doFetch(url, { method: 'PUT', body: JSON.stringify(body) }),
        del: (url) => doFetch(url, { method: 'DELETE' })
    };
})();

window.ApiService = ApiService;
