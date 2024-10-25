// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";

// 登录接口
export function login(data) {
    return axios.post('/user/login', data)
}

export function sendVerificationCode(data) {
    return axios.get('/user/sendVerificationCode?phone='+data)
}

export const getDataProvinceAndCityAndArea = (params) => {
    return axios.get('/district/listDistrict', params)
}

export function sendEmailCode(data) {
    return axios.get('/user/sendEmailCode?email='+data)
}

export function registerUser(data){
    return axios.post('/user/register',data)
}
