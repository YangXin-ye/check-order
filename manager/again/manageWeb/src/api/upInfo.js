import http  from '../utils/request'


export const getLoginUserInfo = () => {
  return http.get('/users/userInfo')
}

export const updateUserInfo = (data) => {
  return http.post('/users/updateUser', data)
}

