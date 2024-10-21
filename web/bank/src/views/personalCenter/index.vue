<template>
  <div class="container">
    <div class="headers-middle">
      <Notice v-if="this.noticeData.length > 0" :noticeData="noticeData" />
    </div>
    <!-- 我的个人账户 -->
    <div class="account">
      <div class="l">
        <img :src="this.potho" alt="" />
        <div class="info">
          <p>我的个人账户</p>
          <span>{{this.nowTime}}PM</span>
        </div>
      </div>
      <div class="r" @click="goPersonal">
        <span>
          个人中心
          <i></i>
        </span>
      </div>
    </div>
    <!-- 消费占比图表 -->
    <div class="consumption-percent">
      <div class="title">
        <span class="label">消费占比</span>
      </div>
      <div class="chart" id="chart"></div>
    </div>
    <!-- 明细 -->
    <div class="detail">
      <div class="title">
        <h4>今天 周{{ this.dayInChinese }}</h4>
        <span>收入{{this.totalIncome}}，支出{{this.totalPay}}</span>
      </div>
      <div class="list">
        <ul>
          <li v-for="(item, index) in dataList" :key="index">
            <div class="l">
              <div class="info">
                <p>{{ item.remark }}</p>
                <span>{{ item.createTime }}</span>
              </div>
            </div>
            <div class="r">
              <span>{{ item.type == 2 ? '+' : '-' }}{{ Math.abs(item.price) }} 元</span>
            </div>
          </li>

        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import Notice from '../../components/notice/index.vue'
import {totalPrice,selectByDay,getBudgetExceed} from './../../api/accountionRecord'
import {getLoginUser} from './../../api/user'
export default {
  name: 'Index',
  components: {
    Notice
  },
  data() {
    return {
      chart: null, // 图表
      nowTime:'',
      dayInChinese:'',
      dataList:[],
      totalPay:'',
      totalIncome:'',
      potho:'',
      data: [
        // 数据
        { name: '餐饮', value: 42 },
        { name: '购物', value: 18 },
        { name: '日用', value: 26 },
        { name: '学习', value: 14 }
      ],
      increment: [
        // 增量
        { name: '餐饮', value: 5 },
        { name: '购物', value: 7 },
        { name: '日用', value: -13 },
        { name: '学习', value: 8 }
      ],
      noticeData: [
        // {
        //   title: '还款',
        //   text: '2024年11月30日,还中国银行300元'
        // },
        // {
        //   title: '还款',
        //   text: '2024年11月30日,还中国银行300元'
        // }
      ]
    }
  },
  created() {
    this.nowTime = this.getTime();
    this.getTotalPrice();
    this.dayInChinese = this.getDayInChinese();
    this.getDayData();
    this.getLoginUserFun();
    this.getBudgetExceedData();
  },
  mounted() {
    this.$nextTick(function () {
      this.initChart()
    })
    window.onresize = function () {
      this.chart.resize()
    }
  },
  methods: {
    async getBudgetExceedData(){
      let res = await getBudgetExceed();
      if(res.data != null && res.data != ''){
        this.noticeData.push({
          title:"超出预算",
          text: res.data
        })
        this.noticeData.push({
          title:"超出预算",
          text: res.data
        })
      }
    },
    async getLoginUserFun(){
      let res = await getLoginUser();
      this.potho = "http://localhost:8089/express/file/download?fileId="+res.user.photo;
    },
    async getTotalPrice() {
      let response = await totalPrice();
      this.data = response.data.classifyTotalPay;
      this.increment = response.data.classifyTotalPay;
      this.$nextTick(function () {
        this.initChart()
      })
      window.onresize = function () {
        this.chart.resize()
      }
    },
    async getDayData(){
      let response = await selectByDay();
      console.log(response);
      this.totalPay = response.data.totalPay;
      this.totalIncome = response.data.totalIncome;
      this.dataList = response.data.accountionRecordList;
    },
    goPersonal() {
      this.$router.push('/personalMessage')
    },
    getDayInChinese(){
      const daysInChinese = ['日', '一', '二', '三', '四', '五', '六'];
      const today = new Date().getDay(); // 获取今天是周几，0 表示周日，1 表示周一，以此类推
      return daysInChinese[today];
    },
    getTime(){
      const now = new Date();
      let hours = now.getHours().toString().padStart(2, '0');
      let minutes = now.getMinutes().toString().padStart(2, '0');
      return `${hours}:${minutes}`;
    },
    initChart() {
      let that = this
      // 初始化图表
      this.chart = echarts.init(document.getElementById('chart'))
      // 配置项
      var chartOptions = {
        title: {
          textStyle: {
            fontSize: 17,
            color: '#ffffff',
            lineHeight: 20
          },
          subtextStyle: {
            fontSize: 12,
            color: '#ffffff'
          },
          textAlign: 'center',
          left: '23%',
          top: '41%'
        },
        tooltip: {
          formatter: function (params) {
            return params.data.name + ': ' + params.data.value
          }
        },
        legend: {
          type: 'scroll',
          orient: 'vertical',
          right: '0%',
          top: 'center',
          itemGap: 15,
          selectedMode: false,
          itemWidth: 12,
          itemHeight: 12,
          icon: 'diamond',
          textStyle: {
            color: '#77899c',
            fontSize: 12,
            rich: {
              a: {
                color: '#0bf4fd',
                width: 40,
                align: 'right'
              },
              b: {
                color: '#fd5b80',
                width: 40,
                align: 'right'
              }
            }
          },
          formatter: function (name) {
            return (
              name
            )
          }
        },
        color: ['#fecf4b', '#fe6a6c', '#4599fc', '#02c4a9 '],
        series: [
          {
            type: 'pie',
            radius: ['55%', '85%'],
            center: ['25%', '55%'],
            label: {
              show: true,
              position: 'inner',
              textStyle: {
                color: '#fff',
                fontSize: 10
              },
              formatter(params) {
                return params.data.value
              }
            },
            labelLine: {
              show: false
            },
            data: this.data
          }
        ]
      }
      // 填充配置
      this.chart.setOption(chartOptions)
    },
    getValue: function (array, key) {
      for (var i = 0; i < array.length; i++) {
        if (array[i].name == key) {
          return array[i].value
        }
      }
    }
  }
}
</script>
<style scoped lang="less">
.container {
  height: 100%;
  width: 95%;
  margin: 0 auto;
}
img {
  vertical-align: middle;
  max-width: 100%;
}

/* 我的个人账户 */
.account {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.35rem 0 0 0;
}
.account .l {
  display: flex;
}
.account .l img {
  width: 0.65rem;
  height: 0.65rem;
}
.account .l .info {
  display: flex;
  flex-direction: column;
  padding-left: 0.2rem;
}
.account .l .info p {
  font-size: 0.25rem;
  color: #fff;
}
.account .l .info span {
  font-size: 0.2rem;
  color: #999ba6;
}
.account .r {
  font-size: 0.2rem;
  color: #999ba6;
  display: flex;
  align-items: center;
}
.account .r i {
  flex-shrink: 0;
  display: inline-block;
  width: 0.08rem;
  height: 0.16rem;
  background: url(../../assets/images/icon-triangle.png) center center no-repeat;
  background-size: 100% auto;
  margin-left: 0.1rem;
}
/* 消费占比图表 */
.consumption-percent {
  width: 100%;
  width: 6.94rem;
  margin: 0 auto;
  background: linear-gradient(to right, #42475f, #545a73);
  padding: 0.4rem 0.4rem;
  border-radius: 0.15rem;
  margin-top: 0.5rem;
}
.consumption-percent .title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  .label {
    font-size: 0.3rem;
    color: #fff;
  }
  .value {
    color: #babcc6;
    font-size: 0.4rem;
    margin-left: 20px;
    font-weight: 600;
  }
}
.consumption-percent .chart {
  width: 100%;
  height: 3.3rem;
}
/* 明细 */
.detail {
  width: 100%;
  width: 6.94rem;
  margin: 0 auto;
  background: linear-gradient(to right, #42475f, #545a73);
  padding: 0.4rem 0.4rem 0.1rem 0.4rem;
  border-radius: 0.15rem;
  margin-top: 0.4rem;
}
.detail .title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.detail .title h4 {
  font-size: 0.34rem;
  color: #fff;
}
.detail .title span {
  color: #babcc6;
  font-size: 0.2rem;
}
.detail .list {
  width: 100%;
  height: auto;
  overflow: hidden;
  padding-top: 0.15rem;
}
.detail .list ul {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.detail .list ul li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.3rem 0;
  position: relative;
}
.detail .list ul li::after {
  content: '';
  display: block;
  height: 0.01rem;
  width: 5.1rem;
  background: linear-gradient(to right, #535870, #5e6273);
  position: absolute;
  bottom: 0;
  right: 0;
}
.detail .list ul li:last-child:after {
  display: none;
}
.detail .list ul li .l {
  display: flex;
  align-items: center;
}
.detail .list ul li .l img {
  width: 0.8rem;
  height: 0.8rem;
}
.detail .list ul li .l .info {
  display: flex;
  flex-direction: column;
  padding-left: 0.25rem;
}
.detail .list ul li .l .info p {
  font-size: 0.3rem;
  color: #fff;
}
.detail .list ul li .l .info p sup {
  display: inline-block;
  width: 0.34rem;
  height: 0.31rem;
  background: url(../../assets/images/icon-pic.png) center center no-repeat;
  background-size: 100% 100%;
  margin-left: 0.2rem;
}
.detail .list ul li .l .info span {
  font-size: 0.2rem;
  color: #8f92a1;
  padding-top: 0.1rem;
}
.detail .list ul li .r {
  display: flex;
  align-items: center;
}
.detail .list ul li .r span {
  font-size: 0.3rem;
  color: #fff;
  padding-right: 0.25rem;
}
.detail .list ul li .r i {
  flex-shrink: 0;
  display: inline-block;
  width: 0.4rem;
  height: 0.4rem;
  line-height: 0.4rem;
  text-align: center;
  border-radius: 100%;
  font-size: 0.22rem;
  color: #fff;
  background: #9698a5;
}
</style>
