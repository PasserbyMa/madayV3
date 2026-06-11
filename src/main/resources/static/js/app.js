/* ========== CSRF ========== */
const csrf = {
    token: document.querySelector('meta[name="_csrf"]')?.content,
    header: document.querySelector('meta[name="_csrf_header"]')?.content || 'X-CSRF-TOKEN'
};

function authHeaders(extra = {}) {
    const h = { 'Content-Type': 'application/json' };
    if (csrf.token) h[csrf.header] = csrf.token;
    return Object.assign(h, extra);
}

/* ========== SQL CONSOLE ========== */
async function runSql() {
    const sql = document.getElementById('sqlInput')?.value?.trim();
    if (!sql) return;
    const resultEl = document.getElementById('sqlResult');
    resultEl.innerHTML = '<span style="color:#8892a4">실행 중...</span>';

    try {
        const res = await fetch(contextPath + '/sql/execute', {
            method: 'POST',
            headers: authHeaders(),
            body: JSON.stringify({ sql })
        });
        const data = await res.json();
        if (!data.success) {
            resultEl.innerHTML = `<span style="color:#ef4444">오류: ${data.error}</span>`;
            return;
        }
        const result = data.result;
        if (Array.isArray(result)) {
            if (result.length === 0) { resultEl.innerHTML = '<span style="color:#8892a4">결과 없음</span>'; return; }
            const cols = Object.keys(result[0]);
            let html = '<table class="result-table"><thead><tr>' + cols.map(c => `<th>${c}</th>`).join('') + '</tr></thead><tbody>';
            result.forEach(row => {
                html += '<tr>' + cols.map(c => `<td>${row[c] ?? ''}</td>`).join('') + '</tr>';
            });
            html += '</tbody></table>';
            resultEl.innerHTML = html;
        } else {
            resultEl.innerHTML = `<span style="color:#2dd4a0">${result.message}</span>`;
        }
    } catch (e) {
        resultEl.innerHTML = `<span style="color:#ef4444">네트워크 오류</span>`;
    }
}

/* ========== MODAL HELPERS ========== */
function openModal(id) { document.getElementById(id)?.classList.add('open'); }
function closeModal(id) { document.getElementById(id)?.classList.remove('open'); }

/* close on overlay click */
document.querySelectorAll('.modal-overlay').forEach(el => {
    el.addEventListener('click', e => { if (e.target === el) el.classList.remove('open'); });
});

/* ========== MEMBER CRUD ========== */
const memberModal = {
    el: document.getElementById('memberModal'),
    isEdit: false,

    openAdd() {
        this.isEdit = false;
        document.getElementById('memberModalTitle').textContent = '고객 등록';
        document.getElementById('memberForm').reset();
        document.getElementById('memberIdField').value = '';
        document.getElementById('memberStatus').value = 'ACTIVE';
        openModal('memberModal');
    },

    async openView(id) {
        const res = await fetch(contextPath + '/members/' + id + '/data');
        const d = await res.json();
        this.isEdit = false;
        document.getElementById('memberModalTitle').textContent = '고객 상세';
        this._fill(d);
        document.querySelectorAll('#memberForm input, #memberForm select')
            .forEach(el => el.setAttribute('readonly', '') || (el.tagName === 'SELECT' && (el.disabled = true)));
        document.getElementById('memberIdField').value = d.id;
        document.getElementById('btnSaveMember').style.display = 'none';
        document.getElementById('btnEditMember').style.display = 'inline-flex';
        document.getElementById('btnDeleteMember').dataset.id = id;
        openModal('memberModal');
    },

    enableEdit() {
        this.isEdit = true;
        document.getElementById('memberModalTitle').textContent = '고객 수정';
        document.querySelectorAll('#memberForm input:not([name="id"]), #memberForm select')
            .forEach(el => { el.removeAttribute('readonly'); el.disabled = false; });
        document.getElementById('btnSaveMember').style.display = 'inline-flex';
        document.getElementById('btnEditMember').style.display = 'none';
    },

    _fill(d) {
        document.getElementById('fName').value    = d.name || '';
        document.getElementById('fEmail').value   = d.email || '';
        document.getElementById('fPhone').value   = d.phone || '';
        document.getElementById('fBirth').value   = d.birthDate || '';
        document.getElementById('fAddress').value = d.address || '';
        document.getElementById('memberStatus').value = d.status || 'ACTIVE';
    },

    async save() {
        const id = document.getElementById('memberIdField').value;
        const payload = {
            name:      document.getElementById('fName').value,
            email:     document.getElementById('fEmail').value,
            phone:     document.getElementById('fPhone').value,
            birthDate: document.getElementById('fBirth').value,
            address:   document.getElementById('fAddress').value,
            status:    document.getElementById('memberStatus').value
        };
        const url    = id ? contextPath + '/members/' + id : contextPath + '/members';
        const method = id ? 'PUT' : 'POST';
        const res = await fetch(url, { method, headers: authHeaders(), body: JSON.stringify(payload) });
        const data = await res.json();
        if (data.success) { closeModal('memberModal'); location.reload(); }
        else alert(data.message || '오류 발생');
    },

    async delete(id) {
        if (!confirm('삭제하시겠습니까?')) return;
        const res = await fetch(contextPath + '/members/' + id, { method: 'DELETE', headers: authHeaders() });
        const data = await res.json();
        if (data.success) { closeModal('memberModal'); location.reload(); }
        else alert(data.message || '오류 발생');
    }
};

/* ========== SUBSCRIPTION CRUD ========== */
const subModal = {
    async openAdd() {
        document.getElementById('subModalTitle').textContent = '가입 등록';
        document.getElementById('subForm').reset();
        document.getElementById('subIdField').value = '';
        document.getElementById('btnSaveSub').style.display = 'inline-flex';
        document.getElementById('btnEditSub').style.display = 'none';
        document.querySelectorAll('#subForm select, #subForm input')
            .forEach(el => { el.removeAttribute('readonly'); el.disabled = false; });
        openModal('subModal');
    },

    async openView(id) {
        const res = await fetch(contextPath + '/subscriptions/' + id + '/data');
        const d = await res.json();
        document.getElementById('subModalTitle').textContent = '가입 상세';
        document.getElementById('subIdField').value = d.id;
        document.getElementById('fSubMember').value = d.memberId;
        document.getElementById('fSubPlan').value   = d.planId;
        document.getElementById('fSubStart').value  = d.startDate || '';
        document.getElementById('fSubEnd').value    = d.endDate || '';
        document.getElementById('fSubStatus').value = d.status;
        document.querySelectorAll('#subForm select, #subForm input')
            .forEach(el => { el.setAttribute('readonly', ''); el.disabled = true; });
        document.getElementById('btnSaveSub').style.display = 'none';
        document.getElementById('btnEditSub').style.display = 'inline-flex';
        document.getElementById('btnDeleteSub').dataset.id = id;
        openModal('subModal');
    },

    enableEdit() {
        document.getElementById('subModalTitle').textContent = '가입 수정';
        document.querySelectorAll('#subForm select, #subForm input')
            .forEach(el => { el.removeAttribute('readonly'); el.disabled = false; });
        document.getElementById('btnSaveSub').style.display = 'inline-flex';
        document.getElementById('btnEditSub').style.display = 'none';
    },

    async save() {
        const id = document.getElementById('subIdField').value;
        const payload = {
            memberId:  document.getElementById('fSubMember').value,
            planId:    document.getElementById('fSubPlan').value,
            startDate: document.getElementById('fSubStart').value,
            endDate:   document.getElementById('fSubEnd').value || null,
            status:    document.getElementById('fSubStatus').value
        };
        const url    = id ? contextPath + '/subscriptions/' + id : contextPath + '/subscriptions';
        const method = id ? 'PUT' : 'POST';
        const res = await fetch(url, { method, headers: authHeaders(), body: JSON.stringify(payload) });
        const data = await res.json();
        if (data.success) { closeModal('subModal'); location.reload(); }
        else alert(data.message || '오류 발생');
    },

    async delete(id) {
        if (!confirm('삭제하시겠습니까?')) return;
        const res = await fetch(contextPath + '/subscriptions/' + id, { method: 'DELETE', headers: authHeaders() });
        const data = await res.json();
        if (data.success) { closeModal('subModal'); location.reload(); }
        else alert(data.message || '오류 발생');
    }
};
