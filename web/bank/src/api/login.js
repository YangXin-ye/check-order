// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";

// 登录接口
export function login(data) {
    return axios.post('/user/login', data)
}

export function sendVerificationCode(data) {
    return axios.get('/user/sendVerificationCode?phone='+data)
}
