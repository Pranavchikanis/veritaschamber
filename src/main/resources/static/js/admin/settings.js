document.addEventListener('DOMContentLoaded', () => {
    
    const loading = document.getElementById('loading');
    const content = document.getElementById('content');
    const form = document.getElementById('settings-form');
    
    const fields = {
        contactEmail: document.getElementById('contactEmail'),
        contactPhone: document.getElementById('contactPhone'),
        officeAddress: document.getElementById('officeAddress'),
        officeHours: document.getElementById('officeHours'),
        linkedinUrl: document.getElementById('linkedinUrl'),
        twitterUrl: document.getElementById('twitterUrl')
    };

    async function loadData() {
        try {
            const response = await window.ApiService.get('/api/v1/admin/settings');
            if (response.success && response.data) {
                const data = response.data;
                fields.contactEmail.value = data.contactEmail || '';
                fields.contactPhone.value = data.contactPhone || '';
                fields.officeAddress.value = data.officeAddress || '';
                fields.officeHours.value = data.officeHours || '';
                fields.linkedinUrl.value = data.linkedinUrl || '';
                fields.twitterUrl.value = data.twitterUrl || '';
            }
        } catch (error) {
            console.error('Failed to load settings', error);
            alert('Failed to load settings.');
        } finally {
            loading.classList.add('d-none');
            content.classList.remove('d-none');
        }
    }

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = document.getElementById('save-settings-btn');
        
        const payload = {
            contactEmail: fields.contactEmail.value,
            contactPhone: fields.contactPhone.value,
            officeAddress: fields.officeAddress.value,
            officeHours: fields.officeHours.value,
            linkedinUrl: fields.linkedinUrl.value,
            twitterUrl: fields.twitterUrl.value
        };
        
        btn.disabled = true;
        btn.textContent = 'Saving...';
        
        try {
            // "global" is used as a dummy path variable to satisfy the /{settingKey} endpoint requirement
            await window.ApiService.put('/api/v1/admin/settings/global', payload);
            
            // Show brief success feedback
            const originalText = btn.textContent;
            btn.textContent = 'Saved!';
            btn.classList.replace('btn-primary', 'btn-success');
            
            setTimeout(() => {
                btn.textContent = 'Save Settings';
                btn.classList.replace('btn-success', 'btn-primary');
                btn.disabled = false;
            }, 2000);
            
        } catch (error) {
            console.error(error);
            alert('Failed to save settings.');
            btn.disabled = false;
            btn.textContent = 'Save Settings';
        }
    });

    loadData();
});
