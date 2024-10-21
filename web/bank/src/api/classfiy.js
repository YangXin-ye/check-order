// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";


export const list = () => {
    return axios.get("/classify/select")
}

export const save = (data) => {
    return axios.post("/classify/insert",data)
}
export const deleteData = (data)=>{
    return axios.post("/classify/delete",data)
}


export const update = (data) => {
    return axios.post("/classify/update",data)
}
