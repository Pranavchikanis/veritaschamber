/**
 * Contact Page Logic
 * Handles Consultation Form submission
 */
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('consultationForm');
    const submitBtn = document.getElementById('submitBtn');
    const submitText = document.getElementById('submitText');
    const submitSpinner = document.getElementById('submitSpinner');
    const alertSuccess = document.getElementById('formAlertSuccess');
    const alertError = document.getElementById('formAlertError');
    const alertErrorMessage = document.getElementById('formAlertErrorMessage');

    if (!form) return;

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Reset UI
        clearErrors();
        alertSuccess.classList.add('d-none');
        alertError.classList.add('d-none');
        
        // Set loading state
        submitBtn.disabled = true;
        submitText.textContent = 'Submitting...';
        submitSpinner.classList.remove('d-none');

        // Gather data
        const formData = new FormData(form);
        const payload = {
            name: formData.get('name'),
            phone: formData.get('phone'),
            email: formData.get('email'),
            preferredContactMethod: formData.get('preferredContactMethod'),
            subject: formData.get('subject') || 'Consultation Request',
            message: formData.get('message')
        };

        try {
            const response = await ApiService.post('/api/v1/public/consultations', payload);
            
            // Handle Success
            alertSuccess.classList.remove('d-none');
            form.reset();
            form.style.display = 'none'; // Hide form after success
            
        } catch (error) {
            console.error('Submission error:', error);
            
            // Validation errors from standard error response
            if (error.fieldErrors && Array.isArray(error.fieldErrors)) {
                error.fieldErrors.forEach(fieldError => {
                    const input = document.getElementById(fieldError.field);
                    if (input) {
                        input.classList.add('is-invalid');
                        const feedback = input.nextElementSibling;
                        if (feedback && feedback.classList.contains('invalid-feedback')) {
                            feedback.textContent = fieldError.message;
                        }
                    }
                });
                alertErrorMessage.textContent = "Please correct the errors in the form.";
            } else {
                alertErrorMessage.textContent = error.message || "Unable to submit request. Please try again later or call our office.";
            }
            
            alertError.classList.remove('d-none');
            
        } finally {
            // Remove loading state
            submitBtn.disabled = false;
            submitText.textContent = 'Submit Request';
            submitSpinner.classList.add('d-none');
        }
    });

    function clearErrors() {
        const inputs = form.querySelectorAll('.form-control');
        inputs.forEach(input => {
            input.classList.remove('is-invalid');
            const feedback = input.nextElementSibling;
            if (feedback && feedback.classList.contains('invalid-feedback')) {
                feedback.textContent = '';
            }
        });
    }
});
