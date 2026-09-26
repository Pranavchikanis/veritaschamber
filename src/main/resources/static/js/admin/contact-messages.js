document.addEventListener('DOMContentLoaded', () => {
    
    const loading = document.getElementById('loading');
    const content = document.getElementById('content');
    const tbody = document.getElementById('data-table-body');
    const paginationContainer = document.getElementById('pagination-container');
    const paginationUl = document.getElementById('pagination-ul');
    
    const itemModal = new bootstrap.Modal(document.getElementById('itemModal'));
    const statusForm = document.getElementById('status-form');
    
    let currentPage = 0;
    const pageSize = 15;
    
    let itemsMap = {};

    async function loadData(page = 0) {
        try {
            const response = await window.ApiService.get(`/api/v1/admin/contact-messages?page=${page}&size=${pageSize}&sort=createdAt,desc`);
            if (response.success && response.data) {
                renderTable(response.data.content);
                renderPagination(response.data);
                currentPage = page;
            }
        } catch (error) {
            console.error('Failed to load data', error);
            tbody.innerHTML = '<tr><td colspan="7" class="text-center text-danger">Error loading data.</td></tr>';
        } finally {
            loading.classList.add('d-none');
            content.classList.remove('d-none');
        }
    }

    function renderTable(items) {
        itemsMap = {};
        if (!items || items.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted">No records found.</td></tr>';
            return;
        }
        
        tbody.innerHTML = items.map(row => {
            itemsMap[row.id] = row;
            return `
                <tr>
                    <td>#${row.id}</td>
                    <td>${formatDate(row.createdAt)}</td>
                    <td class="fw-bold">${escapeHtml(row.name)}</td>
                    <td>
                        <div class="small">${escapeHtml(row.email)}</div>
                        <div class="small text-muted">${escapeHtml(row.phone)}</div>
                    </td>
                    <td><span class="d-inline-block text-truncate" style="max-width: 200px;">${escapeHtml(row.subject)}</span></td>
                    <td><span class="badge ${getStatusBadge(row.status)}">${escapeHtml(row.status)}</span></td>
                    <td class="text-end">
                        <button class="btn btn-sm btn-outline-primary view-btn" data-id="${row.id}">View / Edit</button>
                    </td>
                </tr>
            `;
        }).join('');

        document.querySelectorAll('.view-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.currentTarget.getAttribute('data-id');
                openModal(itemsMap[id]);
            });
        });
    }

    function renderPagination(pageData) {
        if (pageData.totalPages <= 1) {
            paginationContainer.classList.add('d-none');
            return;
        }
        paginationContainer.classList.remove('d-none');
        
        let html = '';
        
        html += `<li class="page-item ${pageData.page === 0 ? 'disabled' : ''}">
            <button class="page-link" onclick="window.changePage(${pageData.page - 1})">Previous</button>
        </li>`;
        
        for (let i = 0; i < pageData.totalPages; i++) {
            html += `<li class="page-item ${i === pageData.page ? 'active' : ''}">
                <button class="page-link" onclick="window.changePage(${i})">${i + 1}</button>
            </li>`;
        }
        
        html += `<li class="page-item ${pageData.page === pageData.totalPages - 1 ? 'disabled' : ''}">
            <button class="page-link" onclick="window.changePage(${pageData.page + 1})">Next</button>
        </li>`;
        
        paginationUl.innerHTML = html;
    }

    window.changePage = function(page) {
        loadData(page);
    };

    function openModal(item) {
        document.getElementById('modal-id').value = item.id;
        document.getElementById('modal-name').textContent = item.name;
        document.getElementById('modal-date').textContent = formatDate(item.createdAt);
        document.getElementById('modal-email').textContent = item.email;
        document.getElementById('modal-email').href = 'mailto:' + item.email;
        document.getElementById('modal-phone').textContent = item.phone || 'N/A';
        document.getElementById('modal-subject').textContent = item.subject || 'No Subject';
        document.getElementById('modal-message').textContent = item.message;
        document.getElementById('modal-status').value = item.status;
        
        itemModal.show();
    }

    statusForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = document.getElementById('save-status-btn');
        const id = document.getElementById('modal-id').value;
        const status = document.getElementById('modal-status').value;
        
        btn.disabled = true;
        btn.textContent = 'Saving...';
        
        try {
            const response = await window.ApiService.patch(`/api/v1/admin/contact-messages/${id}`, { status });
            if (response.success) {
                itemModal.hide();
                loadData(currentPage);
            }
        } catch (error) {
            alert('Failed to update status.');
        } finally {
            btn.disabled = false;
            btn.textContent = 'Save Changes';
        }
    });

    function getStatusBadge(status) {
        if (status === 'NEW') return 'bg-primary';
        if (status === 'READ') return 'bg-warning text-dark';
        if (status === 'RESOLVED') return 'bg-secondary';
        return 'bg-info';
    }

    loadData(0);
});
