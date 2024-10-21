import http  from '../utils/request'

// 获取首页数据
export const getHomeData = () => {
  return http.get('/users/userInfo')
}

// 获取用户的数据
export const getUserList = (params) => {
  return http.get('/user/list', params)
}

// 修改用户的数据
export const updateUser = (params) => {
  return http.post('/user/update', params)
}

// 删除用户的数据
export const deleteUser = (id) => {
  return http.get('/user/delete?id='+id)
}

// 新增用户的数据
export const insertUser = (data) => {
  return http.post('/user/insert', data)
}
