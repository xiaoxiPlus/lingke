import axios from 'axios';
import messages from "element-ui";
import router from '../router/index';

const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
    timeout: 5000
});

service.interceptors.request.use(
    config => {
        //  进行安顿token是否为空
        config.headers.common['Authorization-token'] = localStorage.getItem("token");
        return config;
    }
);

service.interceptors.response.use(
    response => {
        //盘点传过来的code是不是对应错误的状态码  是的化跳转到登录页面

        if (response.data.code===5100){
            router.replace("/login");
            messages.Message({
                title: '警告',
                message: response.data.msg,
                type: 'warning'
            });
            /*只返回一次错误信息*/
            return Promise.reject("/error")
        }else {
            //不是的话 返回response
            return response
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);


export default service;
