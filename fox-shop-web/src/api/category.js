import request from '../utils/request';

export function fetchCategoryData(queryCategory) {
    return request({
        url: '/wow-api/pms_product_attribute/category',
        method: 'get',
        params:queryCategory
    });
};

export function deleteCategory(id) {
    return request({
        url: '/wow-api/pms_product_attribute/category',
        method: 'delete',
        params:id
    });
};

export function saveOrUpdateAttributeData(queryCategory) {
    return request({
        url: '/wow-api/pms_product_attribute/category',
        method: 'post',
        params:queryCategory
    });
};

export function attributeData(queryCategory) {
    return request({
        url: '/wow-api/productAttribute/pms-product-attribute',
        method: 'get',
        params:queryCategory
    });
};

export function editAttributeData(queryCategory) {
    return request({
        url: '/wow-api/productAttribute/pms-product-attribute',
        method: 'post',
        params:queryCategory
    });
};

export function deleteAttribute(id) {
    return request({
        url: '/wow-api/productAttribute/pms-product-attribute',
        method: 'delete',
        params:id
    });
};

export function queryCateOptions() {
    return request({
        url: '/wow-api/pms_product_attribute/category/queryCateOptions',
        method: 'get',
    });
};






