document.addEventListener('DOMContentLoaded', () => {
    
    const loading = document.getElementById('loading');
    const content = document.getElementById('content');
    const tbody = document.getElementById('data-table-body');
    const paginationContainer = document.getElementById('pagination-container');
    const paginationUl = document.getElementById('pagination-ul');
    
    const itemModal = new bootstrap.Modal(document.getElementById('itemModal'));
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    
    const itemForm = document.getElementById('item-form');
    
    let currentPage = 0;
    const pageSize = 15;
    let itemsMap = {};

    async function loadData(page = 0) {
        try {
            // FAQs are not paginated on the backend, it returns a List!
            // Wait, let's assume it returns a list because FAQs are usually small, 
            // but let's check what the API returns. The API_CONTRACTS says PUB-05 list FAQs, ADM-??
            // Let's use the fetch format and see if it's paginated. If it's a list, we handle it.
            const response = await window.ApiService.get(`/api/v1/admin/faqs`);
            if (response.success && response.data) {
                // Determine if it's paginated or array
                let items = Array.isArray(response.data) ? response.data : (response.data.content || []);
                renderTable(items);
                // fake pagination if it's an array
                if (Array.isArray(response.data)) {
                    paginationContainer.classList.add('d-none');
                } else {
                    renderPagination(response.data);
                }
            }
        } catch (error) {
            console.error('Failed to load data', error);
            tbody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error loading data.</td></tr>';
        } finally {
            loading.classList.add('d-none');
            content.classList.remove('d-none');
        }
    }

    function renderTable(items) {
        itemsMap = {};
        if (!items || items.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="text-center text-muted">No records found.</td></tr>';
            return;
        }
        
        tbody.innerHTML = items.map(row => {
            itemsMap[row.id] = row;
            return `
                <tr>
                    <td>${row.displayOrder || 0}</td>
                    <td class="fw-bold"><span class="d-inline-block text-truncate" style="max-width: 300px;">${escapeHtml(row.question)}</span></td>
                    <td><span class="badge ${row.isActive ? 'bg-success' : 'bg-secondary'}">${row.isActive ? 'Active' : 'Inactive'}</span></td>
                    <td class="text-end">
                        <button class="btn btn-sm btn-outline-primary edit-btn" data-id="${row.id}"><i class="fa-solid fa-pen"></i></button>
                        <button class="btn btn-sm btn-outline-danger delete-btn ms-1" data-id="${row.id}"><i class="fa-solid fa-trash"></i></button>
                    </td>
                </tr>
            `;
        }).join('');

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.currentTarget.getAttribute('data-id');
                openEditModal(itemsMap[id]);
            });
        });

        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const id = e.currentTarget.getAttribute('data-id');
                document.getElementById('delete-id').value = id;
                deleteModal.show();
            });
        });
    }

    function renderPagination(pageData) {
        if (!pageData.totalPages || pageData.totalPages <= 1) {
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

    document.getElementById('btn-add-new').addEventListener('click', () => {
        itemForm.reset();
        document.getElementById('modal-id').value = '';
        document.getElementById('itemModalLabel').textContent = 'Add FAQ';
        itemModal.show();
    });

    function openEditModal(item) {
        document.getElementById('modal-id').value = item.id;
        document.getElementById('modal-question').value = item.question;
        document.getElementById('modal-answer').value = item.answer;
        document.getElementById('modal-order').value = item.displayOrder || 0;
        document.getElementById('modal-status').value = item.isActive ? 'true' : 'false';
        document.getElementById('itemModalLabel').textContent = 'Edit FAQ';
        itemModal.show();
    }

    itemForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = document.getElementById('save-item-btn');
        const id = document.getElementById('modal-id').value;
        
        const payload = {
            question: document.getElementById('modal-question').value,
            answer: document.getElementById('modal-answer').value,
            displayOrder: parseInt(document.getElementById('modal-order').value) || 0,
            isActive: document.getElementById('modal-status').value === 'true'
        };
        
        btn.disabled = true;
        btn.textContent = 'Saving...';
        
        try {
            if (id) {
                await window.ApiService.put(`/api/v1/admin/faqs/${id}`, payload);
            } else {
                await window.ApiService.post('/api/v1/admin/faqs', payload);
            }
            itemModal.hide();
            loadData(currentPage);
        } catch (error) {
            console.error(error);
            alert('Failed to save. Please check inputs or try again.');
        } finally {
            btn.disabled = false;
            btn.textContent = 'Save FAQ';
        }
    });

    document.getElementById('confirm-delete-btn').addEventListener('click', async (e) => {
        const id = document.getElementById('delete-id').value;
        const btn = e.currentTarget;
        btn.disabled = true;
        btn.textContent = 'Deleting...';
        
        try {
            await window.ApiService.del(`/api/v1/admin/faqs/${id}`);
            deleteModal.hide();
            loadData(currentPage);
        } catch (error) {
            alert('Failed to delete.');
        } finally {
            btn.disabled = false;
            btn.textContent = 'Delete';
        }
    });

    loadData(0);
});
