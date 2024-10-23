import axios from 'axios';
import { Message } from 'element-ui';


// 创建 Axios 实例
const service = axios.create({
    baseURL: "http://localhost:8089/express", // 根据你的配置设置基础 URL
    timeout: 5000, // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 在请求发送之前可以处理 token 等信息
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `${token}`;
        }
        return config;
    },
    (error) => {
        // 请求错误处理
        console.error('Request Error:', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        // 如果响应包含业务逻辑错误，例如非 200 状态
        const res = response.data;
        console.log("asdas213132");
        console.log(res);
        if (res.code !== 0) {
            Message.error(res.msg || 'Error');
            return Promise.reject(new Error(res.msg || 'Error'));
        } else {
            console.log("sadoooooo");
            return res;
        }
    },
    (error) => {
        if(error.response.status==500){
            Message({
                message: error.response.data.msg.length>20?'服务器响应异常':error.response.data.msg,
                type: 'error', // 类型可选：success, warning, info, error
                duration: 3000 // 持续时间（毫秒）
            });
        }else {
            Message({
                message: '网络连接异常',
                type: 'error', // 类型可选：success, warning, info, error
                duration: 3000 // 持续时间（毫秒）
            });
        }
        return Promise.reject(error);
    }
);

// 封装 GET 请求
export function get(url, params) {
    return service.get(url, { params });
}

// 封装 POST 请求
export function post(url, data) {
    return service.post(url, data);
}

// 封装 PUT 请求
export function put(url, data) {
    return service.put(url, data);
}

// 封装 DELETE 请求
export function del(url, params) {
    return service.delete(url, { params });
}

export default service;
