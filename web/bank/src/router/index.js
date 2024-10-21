/*
 * @Author: jiansheng
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store' // 导入 Vuex Store

if (!window.VueRouter) Vue.use(VueRouter)

/**
 * meta 中的属性
 *
 * @param {Boolean} hideFooter 是否需要隐藏底部，默认为 false, 需要隐藏的话 设置为 true
 * @param {Boolean} hideHeader 是否需要隐藏头部，默认为 false, 需要隐藏的话 设置为 true
 * @param {Boolean} auth       是否需要验证登录权限，需要的话 设置为 true
 */

const routes = [
  {
    path: '/',
    redirect: '/oauth'
  },
  {
    path: '/home',
    meta: { title: '首页', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "home" */ '@/views/home/index.vue'),
  },
  {
    path: '/oauth',
    meta: { title: '认证', hideHeader: true, hideFooter: true },
    component: () => import(/* webpackChunkName: "oauth" */ '@/views/oauth/index.vue')
  },
  {
    path: '/bankCard',
    meta: { title: '银行卡', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "bankCard" */ '@/views/bankcard/index.vue')
  },
  {
    path: '/addCard',
    meta: { title: '添加银行卡', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "bankCard" */ '@/views/addCard/index.vue')
  },
  {
    path: '/personalCenter',
    meta: { title: '我的', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "images" */ '@/views/personalCenter/index.vue')
  },
  {
    path: '/middle',
    meta: { title: '中间', hideHeader: true, hideFooter: true },
    component: () => import(/* webpackChunkName: "middle" */ '@/views/middle/index.vue')
  },
  {
    path: '/income',
    meta: { title: '收支明细', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "income" */ '@/views/income/index.vue')
  },
  {
    path: '/account',
    meta: { title: '记账', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "account" */ '@/views/account/index.vue')
  },
  {
    path: '/classfiy',
    meta: { title: '分类', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "classfiy" */ '@/views/classfiy/index.vue')
  },
  {
    path: '/classfiyDrtail',
    meta: { title: '分类详情', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "classfiy" */ '@/views/classfiy/components/index.vue')
  },
  {
    path: '/personalMessage',
    meta: { title: '个人中心', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "classfiy" */ '@/views/personalMessage/index.vue')
  },
  {
    path: '/inAndexRecord',
    meta: { title: '收支记录', hideHeader: true, hideFooter: true, auth: true }, // 需要权限
    component: () => import(/* webpackChunkName: "classfiy" */ '@/views/inAndexRecord/index.vue')
  }
]

// 解决重复导航时报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  mode: 'hash',
  routes
})

// 路由拦截
router.beforeEach((to, from, next) => {
  // 检查目标路由是否需要认证
  if (to.meta.auth && !localStorage.getItem('token')) {
    // 如果需要权限且未登录，重定向到 OAuth 页面
    next({ path: '/oauth' })
  } else {
    next() // 继续路由
  }
})

router.afterEach((to, from) => {
  let a = document.querySelector('#app')
  if (a) a.scrollTop = 0
})

export default router
