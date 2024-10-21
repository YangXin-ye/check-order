import axios from "axios";
import router from '../router/index'
import { Message } from 'element-ui'

console.log(process)
console.log(process.env)
const request = axios.create({
  // 同一请求前缀
  // baseURL: process.env.API_URL,
  baseURL: 'http://127.0.0.1:8888/',
  // 超时时间
  timeout: 200000,
});

// 请求拦截
request.interceptors.request.use(
  config => {
    // 请求头添加token
    const token = localStorage.getItem("token");
    if (token) {
      config.headers['token'] = token;
    }
    console.log(config);
    return config;
  }
)

// 响应拦截
request.interceptors.response.use(
  (response) => {
    // console.log(response.data.code);
    // 处理异常情况
    if (response.data.code == 500) {
      // do something
      // 跳转路由
      router.push('/Login')
      Message({
        message: '请先登录',
        type: 'error'
      })
      return Promise.reject(response.data);
    }else if(response.data.code == 401){
      // 跳转路由
      router.push('/Login')
      return Promise.reject(response.data);
    }

    return response.data;
  }
)

export default request;
