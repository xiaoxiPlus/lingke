import request from '../../utils/request';

export function queryResource(query) {
    return request({
        url: '/wow-api/umsResource/ums-resource',
        method: 'get',
        params:query
    });
};

export function saveOrUpdateResource(resource) {
    return request({
        url: '/wow-api/umsResource/ums-resource',
        method: 'post',
        params:resource
    });
};

export function getResourceById(id) {
    return request({
        url: '/wow-api/umsResource/ums-resource/'+id,
        method: 'get',
    });
};

export function deleteResource(id) {
    return request({
        url: '/wow-api/umsResource/ums-resource/',
        method: 'delete',
        params:{
            id:id
        }
    });
};

export function queryResourceCategory() {
    return request({
        url: '/wow-api/umsResource/ums-resource-category',
        method: 'get',
    });
};

export function queryResourceData() {
    return request({
        url: '/wow-api/umsResource/ums-resource/data',
        method: 'get',
    });
};
