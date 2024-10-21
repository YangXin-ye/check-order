<template>
  <div class="main">
    <ul class="box" id="box" :class="{ newBox: itemIndex !== null }">
      <li
        v-for="(item, index) in list"
        :key="index"
        :class="[itemIndex == item.index ? 'newItem' : `newItem${index}`]"
        @click="itemClick(index)">
        <div class="itemMain" :style="{ background: item.bankBg }">
          <div class="top clearfix">
            <div class="bankLogo">
              <img :src="item.bankLogo" />
            </div>
            <div class="bankInfo">
              <p>{{ item.name }}</p>
              <p>{{ item.bankType }}</p>
            </div>
            <div class="bankNum">
              <p>**** {{ item.bankNum }}</p>
            </div>
          </div>
          <div class="bottom clearfix">
            <p>每日限额{{ item.limitNum }}万</p>
          </div>
        </div>
      </li>
    </ul>
    <dl class="itemContent" id="itemContent" v-show="itemIndex !== null ? true : false">
      <dd v-for="(items, index) in bankList" :key="index">
        <div class="clearfix">
          <img class="fl" :src="items.itemUrl" />
          <span class="fl">{{ items.itemName }}</span>
          <img class="fr" src="@/assets/images/jt.svg" />
        </div>
      </dd>
    </dl>
    <div class="itemBtn" v-show="itemIndex != null ? true : false" @click="btnClick">
      <img src="@/assets/images/xjt.svg" />
    </div>
  </div>
</template>
<script>
import zz from '@/assets/images/zz.svg'
import sx from '@/assets/images/sx.svg'
import zd from '@/assets/images/zd.svg'
import yh from '@/assets/images/yh.svg'
import ks from '@/assets/images/ks.svg'
import jianhang from '@/assets/images/jianhang.svg'
import gongshang from '@/assets/images/gongshang.svg'
import nonghang from '@/assets/images/nonghang.svg'
export default {
  name: 'bankCard',
  data() {
    return {
      bankList: [
        { itemUrl: zz, itemName: '去转帐' },
        { itemUrl: sx, itemName: '扣款顺序' },
        { itemUrl: zd, itemName: '账单明细' },
        { itemUrl: yh, itemName: '银行服务' },
        { itemUrl: ks, itemName: '其他' }
      ],
      list: [
        {
          index: 0,
          name: '建设银行',
          bankLogo: jianhang,
          bankBg: '#3973c4',
          bankType: '储蓄卡',
          bankNum: '0234',
          limitNum: '20'
        },
        {
          index: 1,
          name: '工商银行',
          bankLogo: gongshang,
          bankBg: '#fe6063',
          bankType: '储蓄卡',
          bankNum: '0158',
          limitNum: '50'
        },
        {
          index: 2,
          name: '农业银行',
          bankLogo: nonghang,
          bankBg: '#32977f',
          bankType: '储蓄卡',
          bankNum: '0353',
          limitNum: '40'
        }
      ],
      itemList: [],
      itemIndex: null,
      btnIndex: null
    }
  },
  created() {},
  mounted() {
    var oBox = document.getElementById('box')
    var aLi = oBox.getElementsByTagName('li')
    for (var i = 0; i < aLi.length; i++) {
      aLi[i].style.top = 60 * i + 'px'
      aLi[i].style.zIndex = i
    }
  },
  methods: {
    btnClick() {
      var oBox = document.getElementById('box')
      var aLi = oBox.getElementsByTagName('li')
      if (this.itemIndex == null) {
        this.itemIndex = this.btnIndex
      } else {
        this.itemIndex = null
        for (var i = 0; i < aLi.length; i++) {
          aLi[i].style.top = i * 60 + 'px'
          aLi[i].style.zIndex = i
        }
      }
    },
    itemClick(index) {
      var oBox = document.getElementById('box')
      var aLi = oBox.getElementsByTagName('li')
      var oBoxFistClild = oBox.firstElementChild
      var oContent = document.getElementById('itemContent')
      oContent.style.borderColor = this.list[index].bankBg
      this.itemList = []
      this.btnIndex = index
      if (this.itemIndex == null) {
        this.itemIndex = index
        for (var k = 0; k < aLi.length; k++) {
          this.itemList.push(k)
        }
        this.itemList.splice(index, 1)
        for (var i in this.itemList) {
          aLi[this.itemList[i]].style.top = 160 * i + 'px'
          aLi[this.itemList[i]].style.zIndex = i
        }
        oBoxFistClild.style.top = 80 * i + 'px'
      } else {
        this.itemIndex = null
        for (var i = 0; i < aLi.length; i++) {
          aLi[i].style.top = 60 * i + 'px'
          aLi[i].style.zIndex = i
        }
      }
      if (this.btnIndex == 0) {
        var oBoxFistSenc = oBox.children[1]
        oBoxFistSenc.style.top = 80 * i + 'px'
      }
    }
  }
}
</script>

<style scoped>
li {
  list-style: none;
}

.clearfix:after {
  display: block;
  clear: both;
  content: '';
}
.fl {
  float: left;
}
.fr {
  float: right;
}
.main {
  width: 250px;
  /* height: 550px; */
  margin: 0px auto;
  padding: 0px 15px;
  position: relative;
}
.box {
  position: relative;
  top: 20px;
  transition: all linear 0.2s;
}
.newBox {
  top: 340px !important;
}
.box li {
  font-size: 14px;
  width: 100%;
  position: absolute;
  z-index: 0;
  transition: all linear 0.2s;
}

.box li .itemMain {
  border-radius: 5px;
  padding: 15px;
}
.box .newItem {
  transition: top linear 0.2s;
  top: -300px !important;
}
.bankLogo {
  border-radius: 50%;
  background: #fff;
  text-align: center;
  width: 30px;
  height: 30px;
  float: left;
}

.bankLogo img {
  vertical-align: middle;
  width: 20px;
  height: 20px;
  margin: 5px;
}

.bankInfo {
  float: left;
  margin-left: 10px;
}

.bankInfo p {
  font-size: 12px;
  color: #ffffff9c;
}

.bankInfo p:first-child {
  font-size: 16px;
  color: #fff;
}

.bankNum {
  float: right;
  margin-top: 10px;
  color: #fff;
}

.bottom {
  margin-top: 30px;
}

.bottom p {
  float: right;
  color: #fff;
  font-size: 12px;
}
.itemContent {
  background: #fff;
  font-size: 12px;
  position: absolute;
  top: 208px;
  left: 15px;
  right: 15px;
  border: 1px solid #257bf6;
  border-radius: 0px 0px 8px 8px;
  border-top: none;
}
.itemContent dd {
  position: relative;
  padding: 10px 15px;
  color: #666;
}
.itemContent dd:not(:last-child)::after {
  position: absolute;
  box-sizing: border-box;
  content: ' ';
  pointer-events: none;
  right: 10px;
  bottom: 0;
  left: 10px;
  border-bottom: 1px solid #257bf6;
  -webkit-transform: scaleY(0.1);
  transform: scaleY(0.1);
}
.itemContent dd img {
  vertical-align: sub;
  width: 16px;
  height: 16px;
}
.itemContent dd span {
  margin-left: 10px;
}
.itemBtn {
  position: absolute;
  bottom: -420px;
  left: 50%;
  margin-left: -25px;
  width: 50px;
  height: 14px;
  text-align: center;
  border: 1px solid #bfbfbf3b;
  border-bottom: none;
  border-radius: 20px 20px 0px 0px;
  background: #fff;
}
.itemBtn img {
  width: 16px;
  height: 16px;
  vertical-align: top;
}
</style>
