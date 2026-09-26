document.addEventListener('DOMContentLoaded', () => {

    const loading = document.getElementById('dashboard-loading');
    const content = document.getElementById('dashboard-content');

    const mConsultations = document.getElementById('metric-consultations');
    const mMessages = document.getElementById('metric-messages');
    const mArticles = document.getElementById('metric-articles');

    const tConsultations = document.getElementById('recent-consultations-body');
    const tMessages = document.getElementById('recent-messages-body');

    async function loadDashboard() {
        try {
            // Fetch first page of consultations, messages, articles
            const [consultationsRes, messagesRes, articlesRes] = await Promise.all([
                window.ApiService.get('/api/v1/admin/consultations?page=0&size=5'),
                window.ApiService.get('/api/v1/admin/contact-messages?page=0&size=5'),
                window.ApiService.get('/api/v1/admin/articles?page=0&size=1')
            ]);

            // Update metrics
            if (consultationsRes.success) mConsultations.textContent = consultationsRes.data.totalElements || 0;
            if (messagesRes.success) mMessages.textContent = messagesRes.data.totalElements || 0;
            if (articlesRes.success) mArticles.textContent = articlesRes.data.totalElements || 0;

            // Update tables
            if (consultationsRes.success && consultationsRes.data.content) {
                renderTable(tConsultations, consultationsRes.data.content, row => `
                    <tr>
                        <td>${escapeHtml(row.name)}</td>
                        <td>${formatDate(row.createdAt)}</td>
                        <td><span class="badge ${getStatusBadge(row.status)}">${escapeHtml(row.status)}</span></td>
                    </tr>
                `);
            }

            if (messagesRes.success && messagesRes.data.content) {
                renderTable(tMessages, messagesRes.data.content, row => `
                    <tr>
                        <td>${escapeHtml(row.name)}</td>
                        <td>${formatDate(row.createdAt)}</td>
                        <td><span class="badge ${getStatusBadge(row.status)}">${escapeHtml(row.status)}</span></td>
                    </tr>
                `);
            }

        } catch (error) {
            console.error('Error loading dashboard', error);
        } finally {
            loading.classList.add('d-none');
            content.classList.remove('d-none');
        }
    }

    function renderTable(tbody, items, rowFn) {
        if (!items || items.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3" class="text-center text-muted">No records found.</td></tr>';
            return;
        }
        tbody.innerHTML = items.map(rowFn).join('');
    }

    function getStatusBadge(status) {
        if (status === 'NEW') return 'bg-primary';
        if (status === 'IN_PROGRESS') return 'bg-warning text-dark';
        if (status === 'CLOSED' || status === 'RESOLVED') return 'bg-secondary';
        return 'bg-info';
    }

    loadDashboard();
});
