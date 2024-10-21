<template>
  <div class="asideContainer">
    <el-menu default-active="1-4-1"
             class="el-menu-vertical-demo"
             @open="handleOpen"
             @close="handleClose"
             :collapse="isCollapse"
             background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b">
      <h3>{{ isCollapse ? '后台管理系统' : '后台管理系统' }}</h3>
      <el-menu-item index="1"
                    v-for="item in noChildren"
                    :key="item.name"
                    :index="item.name"
                    @click="clickMenu(item)">
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>

      <el-submenu v-for="item in hasChildren"
                  :key="item.name"
                  :index="item.name">
        <template slot="title">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{ item.label }}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item v-for="subItem in item.children"
                        :key="subItem.name"
                        :index="subItem.name"
                        @click="clickMenu(subItem)">
            {{ subItem.label }}
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import {getMenuList} from '@/api/menu'
export default {
  name:'Aside',
  data() {
    return {
      menuData: [
        {
          path: '/',
          name: 'main',
          label: '首页',
          icon: 's-home',
          url: 'Home/Home'
        },
        {
          label: '用户管理',
          icon: 'user',
          name: 'customer',
          children: [
            {
              path: '/CustomerList',
              name: 'customerList',
              label: '客户列表',
              icon: 'setting',
              url: 'customer/customerList'
            },
            {
              path: '/CommonCustomer',
              name: 'commonCustomer',
              label: '公海客户',
              icon: 'setting',
              url: 'customer/commonCustomer'
            }
          ]
        },
        {
          label: '商品管理',
          icon: 's-data',
          name: 'product',
          children: [
            {
              path:'/Product',
              name: 'Product',
              label: '商品列表',
              url: 'product/Product'
            },
            {
              path:'/Categories',
              name: 'Categories',
              label: '分类列表',
              url: 'product/Categories'
            }
          ]
        },
        {
          label: '数据管理',
          icon: 's-fold',
          name: 'data',
          children: [
            {
              path:'/AllData',
              name: 'AllData',
              label: '数据处理',
              url: 'data/AllData'
            }
          ]
        },
        {
          label: 'ES数据',
          icon: 's-fold',
          name: 'es',
          children: [
            {
              path:'/esData',
              name: 'EsData',
              label: 'ES数据',
              url: 'es/AllData'
            }
          ]
        },
        {
          label: 'Echarts图表',
          icon: 's-opportunity',
          name: 'echarts',
          children: [
            {
              path:'/Echarts',
              name: 'Echarts',
              label: 'Echarts图表',
              url: 'echarts/Echarts'
            }
          ]
        },
        {
          label: '订单管理',
          icon: 's-order',
          name: 'order',
          children: [
            {
              path: '/AllOrder',
              name: 'allOrder',
              label: '全部订单',
              icon: 'setting',
              url: 'order/allOrder'
            },
            {
              path: '/MyOrder',
              name: 'myOrder',
              label: '我的订单',
              icon: 'setting',
              url: 'order/myOrder'
            }
          ]
        }
      ]
    };
  },
  mounted() {
    this.getMenuListData();
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    clickMenu(item) {
      // console.log(item)
      if (this.$route.path !== item.path && !(this.$route.path === '/FristPage' && item.path === '/')) {
        this.$router.push(item.path)
      }
      // 触发面包屑
      this.$store.commit("selectMenu",item);
    },
    getMenuListData(){
      //获取数据库的menu
      getMenuList().then(res => {
        this.menuData=res.data;
        console.log(res.data);
      })
    }
  },
  computed: {
    // 获取只有一级菜单的数据
    noChildren() {
      return this.menuData.filter(item => !item.children)
    }
    // 获取只有二级菜单的数据
    , hasChildren() {
      return this.menuData.filter(item => item.children)
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse
    }
  }
}
</script>


<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  min-height: 400px;
}
.el-menu {
  height: 120vh;
  border-right: none;

  h3 {
    color: #fff;
    text-align: center;
    line-height: 48px;
    font-size: 16px;
    font-weight: 400;
  }
}
</style>
