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
            const response = await window.ApiService.get(`/api/v1/admin/articles?page=${page}&size=${pageSize}`);
            if (response.success && response.data) {
                renderTable(response.data.content);
                renderPagination(response.data);
                currentPage = page;
            }
        } catch (error) {
            console.error('Failed to load data', error);
            tbody.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Error loading data.</td></tr>';
        } finally {
            loading.classList.add('d-none');
            content.classList.remove('d-none');
        }
    }

    function renderTable(items) {
        itemsMap = {};
        if (!items || items.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="text-center text-muted">No records found.</td></tr>';
            return;
        }
        
        tbody.innerHTML = items.map(row => {
            itemsMap[row.id] = row;
            return `
                <tr>
                    <td>#${row.id}</td>
                    <td class="fw-bold">${escapeHtml(row.title)}</td>
                    <td class="text-muted small">${row.category ? escapeHtml(row.category.name) : 'N/A'}</td>
                    <td class="text-muted small">${row.publishedAt ? formatDate(row.publishedAt) : 'Not published'}</td>
                    <td><span class="badge ${getStatusBadge(row.status)}">${escapeHtml(row.status || 'DRAFT')}</span></td>
                    <td class="text-end">
                        <button class="btn btn-sm btn-outline-primary edit-btn" data-id="${row.id}"><i class="fa-solid fa-pen"></i></button>
                        <button class="btn btn-sm btn-outline-danger delete-btn ms-1" data-id="${row.id}"><i class="fa-solid fa-trash"></i></button>
                    </td>
                </tr>
            `;
        }).join('');

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const id = e.currentTarget.getAttribute('data-id');
                // Fetch full details
                try {
                    const res = await window.ApiService.get(`/api/v1/admin/articles/${id}`);
                    if (res.success && res.data) {
                        openEditModal(res.data);
                    }
                } catch (err) {
                    alert('Error loading article details.');
                }
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

    document.getElementById('btn-add-new').addEventListener('click', () => {
        itemForm.reset();
        document.getElementById('modal-id').value = '';
        document.getElementById('itemModalLabel').textContent = 'Add Article';
        itemModal.show();
    });

    document.getElementById('modal-title').addEventListener('blur', (e) => {
        const slugInput = document.getElementById('modal-slug');
        if (!slugInput.value) {
            slugInput.value = e.target.value.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '');
        }
    });

    function openEditModal(item) {
        document.getElementById('modal-id').value = item.id;
        document.getElementById('modal-title').value = item.title;
        document.getElementById('modal-slug').value = item.slug;
        document.getElementById('modal-excerpt').value = item.excerpt || '';
        document.getElementById('modal-content').value = item.content || '';
        document.getElementById('modal-status').value = item.status;
        document.getElementById('modal-category').value = item.category ? item.category.id : 1;
        document.getElementById('itemModalLabel').textContent = 'Edit Article';
        itemModal.show();
    }

    itemForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = document.getElementById('save-item-btn');
        const id = document.getElementById('modal-id').value;
        
        const payload = {
            categoryId: parseInt(document.getElementById('modal-category').value) || 1,
            title: document.getElementById('modal-title').value,
            slug: document.getElementById('modal-slug').value,
            excerpt: document.getElementById('modal-excerpt').value,
            content: document.getElementById('modal-content').value,
            status: document.getElementById('modal-status').value
        };
        
        btn.disabled = true;
        btn.textContent = 'Saving...';
        
        try {
            if (id) {
                await window.ApiService.put(`/api/v1/admin/articles/${id}`, payload);
            } else {
                await window.ApiService.post('/api/v1/admin/articles', payload);
            }
            itemModal.hide();
            loadData(currentPage);
        } catch (error) {
            console.error(error);
            alert('Failed to save. Please check inputs or try again.');
        } finally {
            btn.disabled = false;
            btn.textContent = 'Save Article';
        }
    });

    document.getElementById('confirm-delete-btn').addEventListener('click', async (e) => {
        const id = document.getElementById('delete-id').value;
        const btn = e.currentTarget;
        btn.disabled = true;
        btn.textContent = 'Deleting...';
        
        try {
            await window.ApiService.del(`/api/v1/admin/articles/${id}`);
            deleteModal.hide();
            loadData(currentPage);
        } catch (error) {
            alert('Failed to delete.');
        } finally {
            btn.disabled = false;
            btn.textContent = 'Delete';
        }
    });

    function getStatusBadge(status) {
        if (status === 'DRAFT') return 'bg-secondary';
        if (status === 'PUBLISHED') return 'bg-success';
        return 'bg-warning text-dark';
    }

    loadData(0);
});
