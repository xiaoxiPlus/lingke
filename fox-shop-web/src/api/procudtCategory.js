import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: '/wow-api/loginController/login',
        method: 'post',
        params: query
    });
};

export const productTable = query => {
    return request({
        url: '/wow-api/productCategory/pms-product-category',
        method: 'get',
        params: query
    });
};

export const updateNavstatus = query => {
    return request({
        url: '/wow-api/productCategory/pms-product-category/navStatus',
        method: 'put',
        params: query
    });
};

export const updateStatus = query => {
    return request({
        url: '/wow-api/productCategory/pms-product-category/status',
        method: 'put',
        params: query
    });
};

export function addProduct (result){
    return request({
        url: '/wow-api/productCategory/pms-product-category/addProduct',
        method: 'post',
        params: result
    });
};

export function queryProductOptions (){
    return request({
        url: '/wow-api/productCategory/pms-product-category/queryProductOptions',
        method: 'get',
    });
};

export function queryProduct (result){
    return request({
        url: '/wow-api/productCategory/pms-product-category/queryProduct',
        method: 'get',
        params:result
    });
};

export function editProduct (result){
    return request({
        url: '/wow-api/productCategory/pms-product-category/editProduct',
        method: 'put',
        params:result
    });
};


export function queryProcudtCategoryOptions (){
    return request({
        url: '/wow-api/productCategory/pms-product-category/queryCategoryOptions',
        method: 'get',
    });
};
