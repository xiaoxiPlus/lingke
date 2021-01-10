import request from '../../utils/request';

export function queryRole(query) {
    return request({
        url: '/wow-api/umsRole/ums-role',
        method: 'get',
        params:query
    });
};

export function queryAdminByOptions() {
    return request({
        url: '/wow-api/umsRole/ums-role/queryAdminByOptions',
        method: 'get',
    });
};

export function updateRoleStatus(updateStatus) {
    return request({
        url: '/wow-api/umsRole/ums-role',
        method: 'put',
        params:updateStatus
    });
};

export function saveOrUpdateRole(role) {
    return request({
        url: '/wow-api/umsRole/ums-role',
        method: 'post',
        params:role
    });
};

export function getRoleById(id) {
    return request({
        url: '/wow-api/umsRole/ums-role/'+id,
        method: 'get',
    });
};

export function deleteRole(id) {
    return request({
        url: '/wow-api/umsRole/ums-role/',
        method: 'delete',
        params:{
            id:id
        }
    });
};

export function getRoleByAdminId(adminId) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin-role-relation/'+adminId,
        method: 'get',
    });
};


export function saveOrUpdateRoleRelation(roleRelation) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin-role-relation',
        method: 'post',
        data:roleRelation
    });
};
