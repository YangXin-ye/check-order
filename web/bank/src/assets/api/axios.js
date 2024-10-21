/*
 * @Descripttion: axios的相关配置
 * @Author: jiansheng
 */
import axios from 'axios'
import router from '../../router'
import { getToken } from '@/utils/auth'
import { Toast } from 'vant'

let msgCenterAPI = ''

switch (process.env.VUE_APP_ENV) {
  case 'production':
    msgCenterAPI = ''
    break
  case 'test':
    msgCenterAPI = 'https://wxh5.sdyxdata.com/stage-api'
    break
  case 'development':
    msgCenterAPI = 'https://wxh5.sdyxdata.com/stage-api'
    break
}

export const BASEURL = process.env.VUE_APP_BASE_API
const service = axios.create({
  baseURL: BASEURL,
  timeout: 20000,
  withCredentials: false
})

service.interceptors.request.use(
  config => {
    const needToken = config.method === 'get' ? config.params.needToken : true
    if (getToken()) config.headers['Authorization'] = 'Bearer ' + getToken()

    // get请求映射params参数 - ruoyi
    if (config.method === 'get' && config.params) {
      delete config.params.needToken

      let url = config.url + '?'
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName]
        var part = encodeURIComponent(propName) + '='
        if (value !== null && typeof value !== 'undefined') {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              let params = propName + '[' + key + ']'
              var subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[key]) + '&'
            }
          } else {
            url += part + encodeURIComponent(value) + '&'
          }
        }
      }
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }

    return config
  },
  error => {
    Promise.reject(error)
  }
)

service.interceptors.response.use(
  res => {
    const code = res.data.code || 200
    const msg = res.data.msg

    if (code == 500) {
      Toast(msg)
      return Promise.reject({ error: { axiosUrl: res.config.url, ...res.data }, data: null })
    } else if (code != 200) {
      Toast(msg)
      return Promise.reject({ error: { axiosUrl: res.config.url, ...res.data }, data: null })
    } else {
      return Promise.resolve({ data: res.data ? res.data : res, error: null })
    }
  },
  error => {
    return Promise.reject({ error, data: null })
  }
)

/**
 * @description: 请求方法
 * @param {String} type       请求类型
 * @param {String} url        api接口地址
 * @param {any}    data       参数
 */
const HR = (type, url, data = null) => {
  type = type ? type.toUpperCase() : 'GET'
  if (!url) return

  if (type == 'GET') return service.get(url, { params: { ...data, needToken } })
  if (type == 'POST') return service.post(url, data)
  if (type == 'PUT') return service.put(url, data)
  if (type == 'DELETE') return service.delete(url, { params: data })
}

export default HR
