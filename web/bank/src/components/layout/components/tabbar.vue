<template>
  <div class="footer_container">
    <!-- foot bar -->
    <div class="foot-bar">
      <ul>
        <li @click="changeTabs('/home')">
          <i :class="[active == 1 ? 'imageIcon' : '']"></i>
          <span>首页</span>
        </li>
        <li @click="changeTabs('/bankCard')">
          <i :class="[active == 2 ? 'cardIcon' : '']"></i>
          <span>银行卡</span>
        </li>

        <li class="middle" @click="changeTabs('/middle')"><i></i></li>
        <li @click="changeTabs('/income')">
          <i :class="[active == 4 ? 'monyIcon' : '']"></i>
          <span>收支明细</span>
        </li>
        <li @click="changeTabs('/personalCenter')">
          <i :class="[active == 5 ? 'propleIcon' : '']"></i>
          <span>我的</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 1,
      icon: {}
    }
  },
  watch: {
    $route: {
      handler(val) {
        if (val.path == '/home') {
          this.active = 1
        } else if (val.path == '/bankCard') {
          this.active = 2
        } else if (val.path == '/personalCenter' || val.path == '/personalMessage') {
          this.active = 5
        } else if (val.path == '/middle') {
          this.active = 3
        } else if (
          val.path == '/income' ||
          val.path == '/account' ||
          val.path == '/classfiy' ||
          val.path == '/classfiyDrtail'
        ) {
          this.active = 4
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    changeTabs(val) {
      this.$router.push(val)
    }
  }
}
</script>

<style lang="less" scoped>
.footer_container {
  width: 100%;
  height: 100%;
  display: table;
  /* 尾部导航 */
  .holder {
    width: 100%;
    height: 1.65rem;
  }
  .foot-bar {
    width: 100%;
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 99;
    border-top: 0.01rem solid #535870;
    background: linear-gradient(to right, #2c324d, #3f4561);
  }
  .foot-bar ul {
    width: 100%;
    display: flex;
    justify-content: space-between;
    font-size: 0.2rem;
    color: #fff;
    letter-spacing: 0.03rem;
  }
  .foot-bar ul li {
    width: 100%;
    text-align: center;
    height: 1.1rem;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
  }
  .foot-bar ul li i {
    flex-shrink: 0;
    display: inline-block;
    width: 100%;
    height: 0.5rem;
    margin-top: 0.08rem;
  }
  .foot-bar ul li span {
    padding-top: 0.15rem;
  }
  .foot-bar ul li.middle {
    flex-shrink: 0;
    width: 1.2rem;
    height: 1.2rem;
    border-radius: 100%;
    background: linear-gradient(#fe5e81, #fe4475);
    margin-top: -0.35rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .foot-bar ul li.middle i {
    width: 0.5rem;
    height: 0.5rem;
    background: url(../../../assets/images/tap-bar-middle.png) center center no-repeat;
    background-size: 0.5rem auto;
    margin-top: 0;
  }
  .foot-bar ul li:nth-child(2) i {
    background: url(../../../assets/images/card.png) center center no-repeat;
    background-size: 0.5rem auto;
  }
  .cardIcon {
    background: url(../../../assets/images/card_active.png) center center no-repeat !important;
    background-size: 0.5rem auto !important;
  }

  .foot-bar ul li:nth-child(1) i {
    background: url(../../../assets/images/images.png) center center no-repeat;
    background-size: 0.5rem auto;
  }
  .imageIcon {
    background: url(../../../assets/images/images_active.png) center center no-repeat !important;
    background-size: 0.5rem auto !important;
  }
  .foot-bar ul li:nth-child(4) i {
    background: url(../../../assets/images/mony.png) center center no-repeat;
    background-size: 0.5rem auto;
  }
  .monyIcon {
    background: url(../../../assets/images/mony_active.png) center center no-repeat !important;
    background-size: 0.5rem auto !important;
  }
  .foot-bar ul li:nth-child(5) i {
    background: url(../../../assets/images/prople.png) center center no-repeat;
    background-size: 0.5rem auto;
  }
  .propleIcon {
    background: url(../../../assets/images/prople_active.png) center center no-repeat !important;
    background-size: 0.5rem auto !important;
  }
  .foot-bar ul li.active span {
    font-weight: bold;
  }
}
</style>
