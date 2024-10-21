/*
 * @Descripttion: 绑定微信
 * @Author: jiansheng
 */

import HR from './axios'
// 获取公众号appId
export const getAppid = () => HR('get', `/wxApi/getAppid`)
// 根据openid检测用户有没有被绑定
export const checkUserHasBindByOpenid = openid => HR('get', `/wxApi/getHasOpenId?openid=${openid}`)
// 获取OpenId
export const getOpenid = params => HR('get', `/wxApi/getOpenId`, params)
// 绑定微信
export const bindAccountWx = data => HR('post', `/wxApi/boundOpenId`, data)
