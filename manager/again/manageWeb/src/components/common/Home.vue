<template>
  <div class="hello">
    <el-container>
      <el-aside width="auto">
        <!-- 调用组件 -->
        <Aside />
      </el-aside>
      <el-container>
        <el-header>
          <!-- 调用组件 -->
          <Header />
        </el-header>
        <!-- 调用组件 -->
        <CommonTag />
        <!-- 删除 main 设置成路由出口 -->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// 引入组件
import Aside from './Aside.vue'
import Header from './Header.vue'
import CommonTag from './CommonTag.vue';

export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  // 声明组件
  components: {
    Aside,
    Header,
    CommonTag
  }
}
</script>

<style scoped>
.el-header {
  padding: 0;
}
</style>
