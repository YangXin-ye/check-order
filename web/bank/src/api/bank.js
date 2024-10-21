// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";


export const list = () => {
    return axios.get("/bank/select")
}

export const save = (data) => {
    return axios.post("/bank/insert",data)
}
export const deleteData = (data)=>{
    return axios.post("/bank/delete",data)
}


export const update = (data) => {
    return axios.post("/bank/update",data)
}
