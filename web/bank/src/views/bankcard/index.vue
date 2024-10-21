a
<template>
  <div class="main">
    <div class="button_inner">
      <div class="on" @click="addCard">添加银行卡</div>
    </div>
    <ul class="box" id="box" :class="{ newBox: itemIndex !== null }" style="margin-top: 10px">
      <li
        v-for="(item, index) in list"
        :key="index"
        :class="{ newItem: itemIndex == item.index }"
        @click="itemClick(index)">
        <div class="itemMain" :style="{ background: item.bankBg }">
          <div class="top clearfix">
            <div class="del" @click="deleteD(item)">删除</div>
            <div class="detail" @click="detail(item)">查看</div>
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
import { list,deleteData } from './../../api/bank'
import { Message } from 'element-ui';
export default {
  name: 'bankCard',
  data() {
    return {
      bankList: [
        { itemUrl: zz, itemName: '去转帐' },
        { itemUrl: sx, itemName: '扣款顺序' },
        { itemUrl: zd, itemName: '账单明细' },
        { itemUrl: yh, itemName: '银行服务' },
        { itemUrl: ks, itemName: '余额' }
      ],
      list: [
       /* {
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
        }*/
      ],
      itemList: [],
      itemIndex: null,
      btnIndex: null
    }
  },
  created() {
    this.lista();
    setTimeout(() => {
      this.itemClick(0)
    },100);

  },
  mounted() {
    var oBox = document.getElementById('box')
    var aLi = oBox.getElementsByTagName('li')
    for (var i = 0; i < aLi.length; i++) {
      aLi[i].style.top = 60 * i + 'px'
      aLi[i].style.zIndex = i
    }
  },
  activated() {
    this.lista();
    setTimeout(() => {
      this.itemClick(0)
    },100);
  },
  methods: {
    deleteD(row){
      deleteData(row);
      Message({
        message: '删除成功',
        type: 'success', // 类型可选：success, warning, info, error
        duration: 3000 // 持续时间（毫秒）
      });
      this.$router.go(0); // 刷新当前路由
    },
    async lista(){
      let res = await list();
      console.log("asasddsada");
      console.log(res);
      res.data.forEach(item=>{
        item.index = parseInt(item.id)-1;
      })
      this.list = res.data;
    },
    addCard() {
      this.$router.push('/addCard')
    },
    detail(row) {
      this.$router.push({ path: '/addCard', query: { id: row.id ,name:row.name,bankNum:row.bankNum} });

    },
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
      console.log(index);
      var oBox = document.getElementById('box')
      var aLi = oBox.getElementsByTagName('li')
      var oContent = document.getElementById('itemContent')
      console.log(this.list[index]);
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
          aLi[this.itemList[i]].style.top = 60 * i + 'px'
          aLi[this.itemList[i]].style.zIndex = i
        }
      } else {
        this.itemIndex = null
        for (var i = 0; i < aLi.length; i++) {
          aLi[i].style.top = 60 * i + 'px'
          aLi[i].style.zIndex = i
        }
      }
    }
  }
}
</script>

<style scoped lang="less">
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
.button_inner {
  width: 100%;
  height: 30px;
  position: fixed;
  right: 10px;
  bottom: 10%;
  .on {
    float: right;
    width: 60px;
    height: 25px;
    font-size: 0.28rem;
    color: #ff7100;
    padding: 0.1rem;
    background: #fff1e5;
    border-radius: 0.25rem;
    list-style: none;
    text-align: center;
    line-height: 25px;
  }
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
  position: relative;
}
.del {
  position: absolute;
  bottom: 10px;
  left: 50px;
  border: 1px solid #fff;
  border-radius: 5px;
  padding: 2px 5px;
  color: #fff;
  font-size: 10px;
}
.detail {
  position: absolute;
  bottom: 10px;
  left: 10px;
  border: 1px solid #fff;
  border-radius: 5px;
  padding: 2px 5px;
  color: #fff;
  font-size: 10px;
}
.box .newItem {
  transition: top linear 0.2s;
  top: -320px !important;
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
  background: #f0f0f085;
  font-size: 12px;
  position: absolute;
  top: 132px;
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
  bottom: -340px;
  left: 50%;
  margin-left: -25px;
  width: 50px;
  height: 14px;
  text-align: center;
  border: 1px solid #bfbfbf3b;
  border-bottom: none;
  border-radius: 20px 20px 0px 0px;
}
.itemBtn img {
  width: 16px;
  height: 16px;
  vertical-align: top;
}
</style>
