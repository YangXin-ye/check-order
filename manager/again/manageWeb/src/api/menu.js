import http  from '../utils/request'

// 获取所有menu
export const getMenuList = () => {
  return http.get('/menu/list')
}


// 修改菜单数据
export const updateMenu = (data) => {
  return http.post('/menu/update', data)
}

// 新增菜单
export const addMenu = (data) => {
  return http.post('/menu/add', data)
}

// 根据id删除菜单
export const deleteMenu = (id) => {
  return http.post('/menu/delete/' + id )
}

//获取子级菜单
export const getChildrenList = (id) => {
  return http.get('/menu/getChildrenList?id='+id)
}


