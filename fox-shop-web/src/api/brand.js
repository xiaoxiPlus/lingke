import request from '../utils/request';

export function queryBrandOptions() {
    return request({
        url: '/wow-api/brand/pms-brand/queryBrandOptions',
        method: 'get',
    });
};

export function queryBrandData(query) {
    return request({
        url: '/wow-api/brand/pms-brand/queryBrandData',
        method: 'get',
        params: query
    });
};

export function saveOrUpdateBrand(brand) {
    return request({
        url: '/wow-api/brand/pms-brand/saveOrUpdateBrand',
        method: 'post',
        params: brand
    });
};

export function queryBrandById(id) {
    return request({
        url: '/wow-api/brand/pms-brand/queryBrandById',
        method: 'get',
        params: {
            id:id
        }
    });
};

export function deleteBrand(id) {
    return request({
        url: '/wow-api/brand/pms-brand/deleteBrand',
        method: 'delete',
        params: {
            id:id
        }
    });
};

export function updateFactoryStatus(param) {
    return request({
        url: '/wow-api/brand/pms-brand/updateFactoryStatus',
        method: 'put',
        params: param
    });
};

