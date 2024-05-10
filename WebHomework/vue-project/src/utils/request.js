import axios from 'axios'
import router from "@/router";

// 创建一个新的 axios 实例
const request = axios.create({
    baseURL: "http://localhost:8080", // 后端的接口地址
    timeout: 30000,
});

// 请求拦截器
request.interceptors.request.use(
    config => {
        console.log('Request:', config);
        let token = localStorage.getItem('local-user');
        if (token) {
            config.headers['token'] = token;
        } else {
            console.log('用户未登录, 直接跳转至登录页面');
            // 只有在当前不是登录页面时才进行重定向
            if (router.currentRoute.path !== '/login') {
                router.push('/login');
            }
        }
        return config;
    },
    error => {
        console.error('request error: ' + error); // for debug
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }
        // 如果响应的 code 为 0，则跳转至登录页面
        if (res.code === 0) {
            // 只有在当前不是登录页面时才进行重定向
            if (router.currentRoute.path !== '/login') {
                router.push('/login');
            }
        }
        return res;
    },
    error => {
        console.error('response error: ' + error); // for debug
        return Promise.reject(error);
    }
);

export default request;