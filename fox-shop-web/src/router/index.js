import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: {title: '系统首页'}
                },
                {
                    path: '/icon',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Icon.vue'),
                    meta: {title: '自定义图标'}
                },
                {
                    path: '/ProductClassify',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/ProductClassify.vue'),
                    meta: {title: '商品分类'}
                },
                {
                    path: '/Product',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/product/Product.vue'),
                    meta: {title: '商品管理'}
                },
                {
                    path: '/brandCount',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/brandEcharts.vue'),
                    meta: {title: '商品管理'}
                },
                {
                    path: '/ProductAdd',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/product/ProductAdd.vue'),
                    meta: {title: '商品维护'}
                },
                {
                    path: '/ProductUpdate',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/product/ProductUpdate.vue'),
                    meta: {title: '商品修改'}
                },
                {
                    path: '/ProductAttribute',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/ProductAttribute.vue'),
                    meta: {title: '商品类型'},
                },
                {
                    path: '/ProductBrand',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/pms/ProductBrand.vue'),
                    meta: {title: '商品类型'},
                },
                {
                    path: '/AttributeList',
                    component: () => import('../components/page/pms/AttributeList.vue'),
                    meta: {title: '属性列表'},
                },
                {
                    path: '/admin',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/Admin.vue'),
                    meta: {title: '用户列表'}
                },
                {
                    path: '/role',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/Role.vue'),
                    meta: {title: '角色列表'}
                },
                {
                    path: '/menu',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/Menu.vue'),
                    meta: {title: '菜单列表'}
                },
                {
                    path: '/resource',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/Resource.vue'),
                    meta: {title: '资源列表'}
                },
                {
                    path: '/resourceCategory',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/ResourceCategory.vue'),
                    meta: {title: '资源列表'}
                },
                {
                    path: '/menuTree',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/MenuTree.vue'),
                    meta: {title: '资源列表'}
                },
                {
                    path: '/resourceCategoryTree',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ums/ResourceCategoryTree.vue'),
                    meta: {title: '资源列表'}
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: {title: '404'}
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: {title: '403'}
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: {title: '登录'}
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
