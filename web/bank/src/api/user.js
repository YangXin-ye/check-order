// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";

// 登录接口
export function getLoginUser() {
    return axios.get('/user/getLoginUser')
}

export function updateUser(data) {
    return axios.post('/user/updateLoginUser',data)
}
export function uploadFile(formData){
    return axios.post('/file/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}
