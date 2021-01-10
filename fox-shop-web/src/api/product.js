import request from '../utils/request';

export function queryProductData(query) {
    return request({
        url: '/wow-api/product/pms-product',
        method: 'get',
        params: query
    });
};

export function saveOrUpdateProduct(product) {
    return request({
        url: '/wow-api/product/pms-product',
        method: 'post',
        data: product
    });
};

export function queryProductById(productId) {
    return request({
        url: '/wow-api/product/pms-product/'+productId,
        method: 'get',
    });
};

export function productCount() {
    return request({
        url: '/wow-api/product/pms-product/productCount',
        method: 'get',
    });
};


