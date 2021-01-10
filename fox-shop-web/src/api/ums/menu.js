import request from '../../utils/request';

export function queryMenu(query) {
    return request({
        url: '/wow-api/umsMenu/ums-menu',
        method: 'get',
        params: query
    });
};

export function updateMenuStatus(updateStatus) {
    return request({
        url: '/wow-api/umsMenu/ums-menu',
        method: 'put',
        params: updateStatus
    });
};

export function saveOrUpdateMenu(Menu) {
    return request({
        url: '/wow-api/umsMenu/ums-menu',
        method: 'post',
        params: Menu
    });
};

export function getMenuById(id) {
    return request({
        url: '/wow-api/umsMenu/ums-menu/' + id,
        method: 'get',
    });
};

export function deleteMenu(id) {
    return request({
        url: '/wow-api/umsMenu/ums-menu',
        method: 'delete',
        params: {
            id: id
        }
    });
};

export function queryLevelMenu(query) {
    return request({
        url: '/wow-api/umsMenu/ums-menu/level',
        method: 'get',
        params: query
    });
};

export function queryMenuTree() {
    return request({
        url: '/wow-api/umsMenu/ums-menu/queryMenuTree',
        method: 'get',
    });
};

export function queryMenuChecked(id) {
    return request({
        url: '/wow-api/umsMenu/ums-menu/queryMenuChecked/' + id,
        method: 'get',
    });
};

export function saveOrUpdateMenuTree(menu) {
    return request({
        url: '/wow-api/umsRole/ums-role-menu-relation',
        method: 'post',
        data: menu
    });
};

export function queryErectMenuTree(username) {
    return request({
        url: '/wow-api/umsMenu/ums-menu/queryErectMenuTree/'+username,
        method: 'get',
    });
};

