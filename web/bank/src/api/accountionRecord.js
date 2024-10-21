// 项目中我们大多数时候都会把对应的接口请求封装成api来调用
import axios from "@/axios";

// 登录接口
export function totalPrice() {
    return axios.get('/accountion/record/totalPrice')
}

export const getList = (param) => {
    return axios.get("/accountion/record/list?remark="+param)
}

export const deleteData = (param) => {
    return axios.get("/accountion/record/delete?id="+param)
}
export const getMonthData = (param) =>{
    return axios.get("/accountion/record/selectByIndex?mongth="+param)
}

export const getYearData = (param) =>{
    return axios.get("/accountion/record/selectByIndex?year="+param)
}

export const getDataDetail = (param) =>{
    return axios.get("/accountion/record/selectByDetail?mongth="+param)
}
export const save = (param) =>{
    return axios.post("/accountion/record/insert",param)
}
export const selectByDay = ()=>{
    return axios.get("/accountion/record/selectByDay");
}
export const getBudgetExceed = ()=>{
    return axios.get("/accountion/record/getBudgetExceed");
}

