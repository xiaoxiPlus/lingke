import request from '../utils/request';

export function queryAttributeOptions(type) {
    return request({
        url: '/wow-api/productAttribute/pms-product-attribute/'+type,
        method: 'get',
    });
};

export function queryParamsData(ty) {
    return request({
        url: '/wow-api/productAttribute/pms-product-attribute/queryParamsOptions/'+ty,
        method: 'get',
    });
};


