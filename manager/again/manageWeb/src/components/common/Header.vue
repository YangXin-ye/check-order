<template>
  <div class="header-container">
    <div class="l-context">
      <el-button icon="el-icon-menu" size="medium" @click="handleMenu"></el-button>
<!--      <el-breadcrumb separator="/" style="padding-left: 15px;">-->
<!--        <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="{ path: item.path }">-->
<!--          {{ item.label }}-->
<!--        </el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->
    </div>
    <div class="r-context">
      <el-dropdown>
        <span class="el-dropdown-link" style="color: #ffffff">
          欢迎您，{{username}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="upInfo">个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="handleClick">注销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import {getHomeData} from "../../api/user";

export default {
  name: 'Header',
  data() {
    return {
      username:""
    }
  },
  mounted() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      getHomeData().then(res => {
        this.username = res.data.username
      })
    },
    handleClick() {
      this.$message({
        message: '退出登陆成功',
        type: 'success'
      })
      localStorage.removeItem('token')
      // 跳转到首页
      this.$router.push('/login')
    },
    handleMenu() {
      this.$store.commit('collapseChange')
    },
    upInfo(){
      this.$router.push('/upInfo')
    }
  },
  computed: {
    ...mapState({
      tags: state => state.tab.tabsList
    })
  }
}
</script>

<style lang="less" scoped>
.el-dropdown-link:hover{
  cursor: pointer;
}
.header-container {
  padding: 0 20px;
  background-color: #242f42;
  height: 70px;
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .firstPage {
    color: #fff;
    font-size: 16px;
    margin-left: 10px;
  }

  .r-context {
    padding-right: 20px;

    .user_img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }

  .l-context {
    display: flex;
    align-items: center;

    /deep/.el-breadcrumb__item {
      .el-breadcrumb__inner {
        font-weight: normal;

        &.is-link {
          color: #f8d173;
        }

        &:hover {
          color: aqua;
          transition: color 1s;
        }
      }

      &:last-child {
        .el-breadcrumb__inner {
          color: #fff;
        }

        .el-breadcrumb__inner:hover {
          color: aqua;
          transition: color 1s;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
