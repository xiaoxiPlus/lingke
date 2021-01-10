import request from '../../utils/request';

export function queryResourceCategory(query) {
    return request({
        url: '/wow-api/umsResource/ums-resource-category/data',
        method: 'get',
        params: query
    });
};

export function saveOrUpdateResourceCategory(category) {
    return request({
        url: '/wow-api/umsResource/ums-resource-category',
        method: 'post',
        params: category
    });
};

export function getResourceCategoryById(id) {
    return request({
        url: '/wow-api/umsResource/ums-resource-category/' + id,
        method: 'get',
    });
};

export function deleteResource(id) {
    return request({
        url: '/wow-api/umsResource/ums-resource-category',
        method: 'delete',
        params: {
            id: id
        }
    });
};


