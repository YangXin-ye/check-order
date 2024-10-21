import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/common/Home'
import CustomerList from '../components/pages/customer/CustomerList.vue'
import CommonCustomer from '../components/pages/user/UserList.vue'
import FristPage from '../components/pages/FristPage.vue'
import Login from '../components/pages/loginAndRegister/Login.vue'
import MenuList from '../components/pages/menu/MenuList.vue'
import UpInfo from '../components/pages/upInfo/upInfo.vue'
import ClassifyList from '../components/pages/classifyList/ClassifyList.vue'
import DataList from '../components/pages/dataList/DataList.vue'
import DataRestore from '../components/pages/dataList/DataRestore.vue'



Vue.use(Router)

export default new Router({
  routes: [
    {
      // 修改这里的路由规则
      path: '/',
      name: 'Home',
      redirect:'FristPage',
      component: Home,
      // 在这书写子路由规则，这样页面就可以嵌套进主路由当中
      children: [
        {
          path: '/FristPage',
          name: 'FristPage',
          component: FristPage
        },
        {
          path: '/CustomerList',
          name: 'customerList',
          component: CustomerList
        },
        {
          path: '/CommonCustomer',
          name: 'commonCustomer',
          component: CommonCustomer
        },
        {
          path: '/menuList',
          name: 'menuList',
          component: MenuList
        },
        {
          path: '/menuList',
          name: 'menuList',
          component: MenuList
        },
        {
          path: '/upInfo',
          name: 'upInfo',
          component: UpInfo
        },
        {
          path: '/classifyList',
          name: 'classifyList',
          component: ClassifyList
        },
        {
          path: '/dataList',
          name: 'dataList',
          component: DataList
        },
        {
          path: '/dataRestore',
          name: 'dataRestore',
          component: DataRestore
        }
      ]
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    }
  ]
})
