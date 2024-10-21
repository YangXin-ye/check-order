/*
 * @Descripttion: browser localstorage
 * @Author: jiansheng
 */
const storage = {}

/**
 * @description: 添加持久缓存
 */
storage.setLocal = (name = 'default', value = '') => {
  window.localStorage.setItem(name, value)
}

/**
 * @description: 获取某个持久缓存
 */
storage.getLocal = (name = 'default') => {
  return window.localStorage.getItem(name)
}

/**
 * @description: 删除某个缓存
 * @param {string | ary} name key ceshi' || ['key1', 'key2']
 */
storage.removeLocal = name => {
  if (Array.isArray(name)) {
    name.map(item => {
      window.localStorage.removeItem(item)
    })
  } else {
    window.localStorage.removeItem(name)
  }
}

/**
 * @description: 清除所有的持久缓存
 */
storage.clearLocal = () => {
  window.localStorage.clear()
}

/**
 * @description: 存储会话缓存
 * @param {string} name key
 * @param {string} value value
 */
storage.setSession = (name = 'default', value = '') => {
  window.sessionStorage.setItem(name, value)
}

storage.getSession = (name = 'default') => {
  return window.sessionStorage.getItem(name)
}

/**
 * @description: 删除某个会话缓存
 * @param {string | ary} name 'ceshi' || ['key1', 'key2']
 */
storage.removeSession = (name = 'default') => {
  if (Array.isArray(name)) {
    name.map(item => window.sessionStorage.removeItem(item))
  } else {
    window.sessionStorage.removeItem(name)
  }
}

storage.clearSession = () => {
  window.sessionStorage.clear()
}

export default storage
