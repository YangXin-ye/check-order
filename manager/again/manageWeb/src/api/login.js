import http  from '../utils/request'

// 登录
export const login = (data) => {
  return http.post('/login',data)
}
