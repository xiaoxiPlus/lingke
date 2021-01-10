import request from '../../utils/request';

export function queryAdmin(query) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin',
        method: 'get',
        params:query
    });
};

export function updateAdminStatus(updateStatus) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin',
        method: 'put',
        params:updateStatus
    });
};

export function saveOrUpdateAdmin(admin) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin',
        method: 'post',
        params:admin
    });
};

export function getAdminById(id) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin/'+id,
        method: 'get',
    });
};

export function deleteAdmin(id) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin/',
        method: 'delete',
        params:{
            id:id
        }
    });
};

export function queryAdminByUsername(username) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin/queryAdminByUsername/'+username,
        method: 'get',
    });
};

export function adminLoginOut(username) {
    return request({
        url: '/wow-api/loginController/adminLoginOut/'+username,
        method: 'get',
    });
};

export function checkToken(token) {
    return request({
        url: '/wow-api/umsAdmin/ums-admin/checkToken/'+token,
        method: 'get',
    });
}

