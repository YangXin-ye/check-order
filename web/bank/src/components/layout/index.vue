<template>
  <div class="layout">
    <div class="header" v-if="tabFlag">
      <a href="javascript:;" @click="onClickLeft"></a>
      <span>{{ title }}</span>
      <i></i>
    </div>

    <div class="content">
      <router-view />
    </div>
    <div class="footer" v-if="tabFlag">
      <tabbar />
    </div>
  </div>
</template>

<script>
import tabbar from './components/tabbar.vue'

export default {
  name: 'layout',
  components: {
    tabbar
  },
  data() {
    return {
      title: '首页',
      tabFlag: false,
      silder: false
    }
  },
  mounted() {
    this.broadcastContent()
  },
  watch: {
    $route: {
      handler(val) {
        console.log(val)
        if (val.path == '/oauth') {
          this.tabFlag = false
        } else {
          this.tabFlag = true
        }
        if (val.path == '/home') {
          this.title = '首页'
        } else if (val.path == '/personalCenter') {
          this.title = '我的'
        } else if (val.path == '/middle') {
          this.title = '统计报表'
        } else if (val.path == '/bankCard' || val.path == '/addCard') {
          this.title = '银行卡'
        } else if (val.path == '/income') {
          this.title = '收支明细'
        } else if (val.path == '/account') {
          this.title = '记账'
        } else if (val.path == '/classfiy') {
          this.title = '分类'
        } else if (val.path == '/personalMessage') {
          this.title = '个人中心'
        } else if (val.path == '/inAndexRecord') {
          this.title = '收支记录'
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    onClickLeft() {
      this.$router.back()
    },
    broadcastContent() {
      //获取后端的公告数据
    },
    setNoticeData(notice) {
      if (this.noticeData != null && this.noticeData.length !== 0) {
        this.noticeData[0] = notice
      } else {
        this.noticeData = []
        this.noticeData.push(notice)
      }
    }
  }
}
</script>

<style lang="less" scoped>
.layout {
  width: 100%;
  height: 100vh;
}
.content {
  margin-top: 0.88rem;
}
.header {
  width: 100%;
  height: 0.88rem;
  line-height: 0.88rem;
  text-align: center;
  font-size: 0.34rem;
  color: #fff;
  border-bottom: 0.01rem solid #474c65;
  position: fixed;
  top: 0;
  z-index: 2;
  background: #474c65;
}
.header a {
  width: 0.5rem;
  height: 0.88rem;
  position: absolute;
  top: 0;
  left: 0.3rem;
  background: url(../../assets/images/icon-back.png) left center no-repeat;
  background-size: 0.23rem auto;
  z-index: 2;
}
.header i {
  width: 0.5rem;
  height: 0.88rem;
  position: absolute;
  top: 0;
  right: 0.3rem;
  background: url(../../assets/images/icon-more.png) right center no-repeat;
  background-size: 0.41rem auto;
  z-index: 2;
}
.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  z-index: 2;
  // height: 1.5rem;
  background: #f1f3f6;
}
</style>
