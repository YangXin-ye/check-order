// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";


export const save = (data) => {
    return axios.post("/budget/insert",data)
}
