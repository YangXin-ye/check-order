/*
 * @Descripttion: 部分功能方法
 * @Author: jiansheng
 */
/**
 * 是否是手机号码
 * @param {String} val 输入值
 */
export function isMobile(val) {
  return /^1(3|4|5|6|7|8|9)\d{9}$/.test(val)
}

/**
 * @description: 生成随机字符串
 * @param {number} len
 * @return {string}
 */
export const randomString = len => {
  len = len || 32
  var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
  var maxPos = chars.length
  var pwd = ''
  for (let i = 0; i < len; i++) {
    pwd += chars.charAt(Math.floor(Math.random() * maxPos))
  }
  return pwd
}

/**
 * 获取文件后缀名
 * @param filename 文件名称
 * @return {string}
 */
export const getSuffix = filename => {
  var pos = filename.lastIndexOf('.')
  var suffix = ''
  if (pos !== -1) {
    suffix = filename.substring(pos)
  }
  return suffix.toLocaleLowerCase()
}

/**
 * @description: 获取文件名
 * @param {*} uri
 * @return {string}
 */
export const getFileName = uri => {
  var pos = uri.lastIndexOf('/')
  return pos !== -1 ? uri.substring(pos + 1) : ''
}

/**
 * @description: 是否是微信环境
 * @return {boolean}
 */
export const isWeiXin = () => {
  var ua = window.navigator.userAgent.toLowerCase()
  if (ua.match(/MicroMessenger/i) == 'micromessenger') {
    return true
  } else {
    return false
  }
}

/**
 * @description: 是否是 ios
 * @return {boolean}
 */
export function isIos() {
  let u = navigator.userAgent,
    app = navigator.appVersion
  let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1 //android终端或者uc浏览器
  // let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
  return !isAndroid ? true : false
}

/**
 * @description: 是否是 pc
 * @return {boolean}
 */
export const isPC = () => {
  var userAgentInfo = navigator.userAgent
  var Agents = ['Android', 'iPhone', 'SymbianOS', 'Windows Phone', 'iPad', 'iPod']
  var flag = true
  for (var v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      flag = false
      break
    }
  }
  return flag
}

/**
 * [通过参数名获取url中的参数值]
 * 示例URL:http://htmlJsTest/getrequest.html?uid=admin&rid=1&fid=2&name=小明
 * @param  {string} queryName [参数名]
 * @return {string}           [参数值]
 */
export const GetQueryValue = queryName => {
  var query = decodeURI(window.location.search.substring(1))
  var vars = query.split('&')
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split('=')
    if (pair[0] == queryName) {
      return pair[1]
    }
  }
  return null
}
