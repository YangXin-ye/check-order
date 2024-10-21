<template>
  <div class="container">
    <div class="button">
      <li class="on" @click="goAccount">设置预算</li>
    </div>
    <div class="month_inner">
      <div class="title">月度收支情况</div>
      <!-- 月份 -->
      <div class="month">
        <ul>
          <li
              v-for="month in months"
              :key="month.value"
              :class="nav === month.value ? 'on' : ''"
              @click="changeNav(month.value)"
          >
            {{ month.label }}
          </li>
        </ul>
      </div>
      <!-- 信息总览 -->
      <div class="total-info">
        <ul>
          <li>
            <p>
              <i>¥</i>
              {{this.monthData.totalIncome}}
            </p>
            <span>收入</span>
          </li>
          <li>
            <p>
              <i>¥</i>
              {{this.monthData.totalPay}}
            </p>
            <span>支出</span>
          </li>
          <li>
            <p>
              <i>¥</i>
              {{this.monthData.averageDayPay}}
              <span>/天</span>
            </p>
            <span>日均支出</span>
          </li>
        </ul>
      </div>
      <!-- 消费支出 -->
      <div class="chart">
        <div class="title">
          <h3>{{ typeTitle }}</h3>
          <div class="type">
            <span :class="type == 1 ? 'on' : ''" @click="changeType(1)">收入</span>
            <span :class="type == 2 ? 'on' : ''" @click="changeType(2)">支出</span>
          </div>
        </div>
        <div id="chart"></div>
      </div>
    </div>
    <div class="year_inner">
      <div class="title">年度收支情况</div>
      <!-- 年份 -->
      <div class="month">
        <ul>
          <li :class="navY == 2023 ? 'on' : ''" @click="changeNavY(2023)">2023</li>
          <li :class="navY == 2024 ? 'on' : ''" @click="changeNavY(2024)">2024</li>
          <li :class="navY == 2025 ? 'on' : ''" @click="changeNavY(2025)">2025</li>
          <li :class="navY == 2026 ? 'on' : ''" @click="changeNavY(2026)">2026</li>
        </ul>
      </div>
      <!-- 信息总览 -->
      <div class="total-info">
        <ul>
          <li>
            <p>
              <i>¥</i>
              {{this.yearData.totalIncome}}
            </p>
            <span>收入</span>
          </li>
          <li>
            <p>
              <i>¥</i>
              {{this.yearData.totalPay}}
            </p>
            <span>支出</span>
          </li>
          <li>
            <p>
              <i>¥</i>
              {{this.yearData.averageDayPay}}
              <span>/天</span>
            </p>
            <span>月均支出</span>
          </li>
        </ul>
      </div>
      <!-- 消费支出 -->
      <div class="chart">
        <div class="title">
          <h3>{{ typeTitleY }}</h3>
          <div class="type">
            <span :class="typeY == 1 ? 'on' : ''" @click="changeTypeY(1)">收入</span>
            <span :class="typeY == 2 ? 'on' : ''" @click="changeTypeY(2)">支出</span>
          </div>
        </div>
        <div id="chartY"></div>
      </div>
    </div>
    <!-- 设置预算 -->
    <van-dialog v-model="Yshow" title="设置预算" :showConfirmButton="false">
      <div class="container">
        <van-form @submit="onSubmit">
          <van-field
            v-model="formInner.budgetPrice"
            label="金额"
            placeholder="请填写金额"
            :rules="[{ required: true, message: '请填写金额' }]" />
          <van-field
            readonly
            clickable
            name="picker"
            :value="formInner.typeName"
            label="日期类型"
            placeholder="点击选择日期类型"
            @click="showPicker = true" />
          <div style="margin: 16px">
            <van-button round block type="info" native-type="submit">提交</van-button>
          </div>
        </van-form>
      </div>
    </van-dialog>
    <van-popup v-model="showPicker" position="bottom">
      <van-picker show-toolbar :columns="columns" @confirm="onConfirm" @cancel="showPicker = false" />
    </van-popup>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import {getMonthData,getYearData} from './../../api/accountionRecord'
import {save} from './../../api/budget'
export default {
  name: 'income',
  data() {
    return {
      nav: 10, // 默认选中为"本月"
      months: [
        { value: 8, label: '8月' },
        { value: 9, label: '9月' },
        { value: 10, label: '10月' },
        { value: 11, label: '11月' },
        { value: 12, label: '12月' }
      ],
      monthImg:{
        zhiTime:[],
        zhiVal: [],
        shouTime:[],
        shouVal:[]
      },
      yearImg:{
        zhiTime:[],
        zhiVal: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        shouTime:[],
        shouVal:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
      },
      monthData:{},
      yearData:{},
      type: 2, // 图表类型
      cate: 2, // 明细分类
      chart: null, // 图表
      chartOptions: null, // 图表配置项
      typeTitle: '消费支出',
      navY: 2024, // 年份导航
      typeY: 2, // 图表类型
      cateY: 2, // 明细分类
      chartY: null, // 图表
      chartOptionsY: null, // 图表配置项
      typeTitleY: '消费支出',
      Yshow: false,
      showPicker: false,
      formInner: {
        budgetPrice: '',
        typeName: ''
      },
      columns: ['月预算', '年预算'],
      yearName:'支出',
      monthName:'支出',
    }
  },
  created(){
    this.nav = this.getCurrentMonth();
    this.getMonthData();
    this.getYearData();
    setTimeout(() => {
      this.initData(); // 这里的 this 会指向外部的上下文
    }, 1000);
  },
  mounted() {
  },
  methods: {
    getCurrentMonth() {
      const date = new Date();
      return date.getMonth() + 1; // getMonth() 返回的月份从 0 开始，因此加 1
  },
    initData(){
      this.$nextTick(function () {
        this.initChart()
        this.initChartYear()
      })
      window.onresize = function () {
        this.chart.resize()
        this.chartY.resize()
      }
    },
    async getMonthData(){
      this.monthImg.zhiTime = [];
      this.monthImg.zhiVal = [];
      this.monthImg.shouTime = [];
      this.monthImg.shouVal = [];
      let res = await getMonthData(this.nav);
      this.monthData = res.data;
      console.log("55")
      console.log(res.data.detailInfos);
      for(let i = 0; i <res.data.detailInfos.length; i++){
        let d =res.data.detailInfos[i];
        if(d.type == 1){
          //支出
          this.monthImg.zhiTime.push(d.time);
          this.monthImg.zhiVal.push(d.price);
        }else{
          this.monthImg.shouTime.push(d.time);
          this.monthImg.shouVal.push(d.price);
        }
      }

    },
    async getYearData(){
      this.yearImg.zhiTime = [];
      this.yearImg.zhiVal =[0,0,0,0,0,0,0,0,0,0,0];
      this.yearImg.shouTime = [];
      this.yearImg.shouVal = [0,0,0,0,0,0,0,0,0,0,0];
      let res = await getYearData(this.navY);
      this.yearData = res.data;
      res.data.detailInfos.forEach(item =>{
        if(item.type==1){
          console.log(item.time);
          this.yearImg.zhiVal.splice(parseInt(item.time)-1, 0, item.price);
        }else{
          this.yearImg.shouVal.splice(parseInt(item.time)-1, 0, item.price);
        }
      })
      console.log(this.yearImg.zhiVal);
      console.log(this.yearImg.shouVal);

    },
    onSubmit(values) {
      this.saveYuSuan();
      this.Yshow = false
      this.$message({
        message: "设置成功",
        type: "success",
      });
    },
    async saveYuSuan(){
      this.formInner.type = this.formInner.typeName=='年预算'?1:2;
       await save(this.formInner);
    },
    goAccount() {
      this.Yshow = true
    },
    onConfirm(value) {
      this.formInner.typeName = value
      this.showPicker = false
    },
    initChart() {
      // 初始化图表
      this.chart = echarts.init(document.getElementById('chart'))
      // 配置项
      this.chartOptions = {
        grid: {
          top: 35,
          bottom: 5,
          left: 10,
          right: 10,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            lineStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: 'rgba(255,255,255,0)' // 0% 处的颜色
                  },
                  {
                    offset: 0.5,
                    color: 'rgba(255,255,255,1)' // 100% 处的颜色
                  },
                  {
                    offset: 1,
                    color: 'rgba(255,255,255,0)' // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            }
          }
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            axisLabel: {
              formatter: '{value}',
              fontSize: 12,
              margin: 20,
              textStyle: {
                color: '#bfbfbf'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#e9e9e9'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#f7f7f7'
              }
            },
            axisTick: {
              show: false
            },
            data: this.monthImg.zhiTime
          }
        ],
        yAxis: [
          {
            boundaryGap: false,
            type: 'value',
            axisLabel: {
              textStyle: {
                color: '#bfbfbf'
              },
              formatter: '{value}' + ''
            },
            nameTextStyle: {
              color: '#fff',
              fontSize: 12,
              lineHeight: 40
            },
            splitLine: {
              lineStyle: {
                color: '#f7f7f7'
              }
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#e9e9e9'
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: this.monthName,
            type: 'line',
            smooth: false,
            showSymbol: false,
            symbolSize: 0,
            zlevel: 3,
            itemStyle: {
              color: '#19a3df',
              borderColor: '#a3c8d8'
            },
            lineStyle: {
              normal: {
                width: 3,
                color: '#28d7b7'
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: '#e0f9f5'
                    },
                    {
                      offset: 0.8,
                      color: '#e0f9f5'
                    }
                  ],
                  false
                )
              }
            },
            data: []
          }
        ]
      }
      // 填充配置
      this.chart.setOption(this.chartOptions)
    },
    // 切换导航
    changeNav: function (item) {
      this.nav = item
      console.log(item);
      this.getMonthData();
      setTimeout(() => {
        this.initData(); // 这里的 this 会指向外部的上下文
      }, 500);
      this.changeType(2)
    },
    // 切换收入支出
    changeType: function (item) {
      //支出
      this.type = item
      if (item == 1) {
        this.typeTitle = '转账收入'
        this.chartOptions.series[0].data = this.monthImg.shouVal
        this.chart.setOption(this.chartOptions)
        this.monthName = '收入'
      } else {
        this.typeTitle = '消费支出'
        this.chartOptions.series[0].data = this.monthImg.zhiVal
        this.chart.setOption(this.chartOptions)
        this.monthName = '支出'
      }
    },
    // 切换类型
    changeCate: function (item) {
      this.cate = item
    },
    initChartYear() {
      // 初始化图表
      this.chartY = echarts.init(document.getElementById('chartY'))
      // 配置项
      this.chartOptionsY = {
        grid: {
          top: 35,
          bottom: 5,
          left: 10,
          right: 10,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            lineStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: 'rgba(255,255,255,0)' // 0% 处的颜色
                  },
                  {
                    offset: 0.5,
                    color: 'rgba(255,255,255,1)' // 100% 处的颜色
                  },
                  {
                    offset: 1,
                    color: 'rgba(255,255,255,0)' // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            }
          }
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            axisLabel: {
              formatter: '{value}',
              fontSize: 12,
              margin: 20,
              textStyle: {
                color: '#bfbfbf'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#e9e9e9'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#f7f7f7'
              }
            },
            axisTick: {
              show: false
            },
            data: [1,2,3,4,5,6,7,8,9,10,11,12]
          }
        ],
        yAxis: [
          {
            boundaryGap: false,
            type: 'value',
            axisLabel: {
              textStyle: {
                color: '#bfbfbf'
              },
              formatter: '{value}' + ''
            },
            nameTextStyle: {
              color: '#fff',
              fontSize: 12,
              lineHeight: 40
            },
            splitLine: {
              lineStyle: {
                color: '#f7f7f7'
              }
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#e9e9e9'
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: this.yearName,
            type: 'line',
            smooth: false,
            showSymbol: false,
            symbolSize: 0,
            zlevel: 3,
            itemStyle: {
              color: '#19a3df',
              borderColor: '#a3c8d8'
            },
            lineStyle: {
              normal: {
                width: 3,
                color: '#28d7b7'
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: '#e0f9f5'
                    },
                    {
                      offset: 0.8,
                      color: '#e0f9f5'
                    }
                  ],
                  false
                )
              }
            },
            data: this.yearImg.zhiVal
          }
        ]
      }
      // 填充配置
      this.chartY.setOption(this.chartOptionsY)
    },
    // 切换导航
    changeNavY: function (item) {
      this.navY = item;
      this.getYearData();
      setTimeout(() => {
        this.initData(); // 这里的 this 会指向外部的上下文
      }, 500);
      this.changeTypeY(2)
    },
    // 切换收入支出
    changeTypeY: function (item) {
      this.typeY = item
      if (item == 1) {
        this.typeTitleY = '转账收入'
        this.chartOptionsY.series[0].data = this.yearImg.shouVal
        this.chartY.setOption(this.chartOptionsY)
        this.yearName = '收入'
      } else {
        this.typeTitleY = '消费支出'
        this.chartOptionsY.series[0].data = this.yearImg.zhiVal
        this.chartY.setOption(this.chartOptionsY)
        this.yearName = '支出'
      }
    },
    // 切换类型
    changeCate: function (item) {
      this.cateY = item
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: auto;
  width: 95%;
  margin: 0 auto;
  overflow: scroll;
  padding-bottom: 60px;
}
.button {
  width: auto;
  height: 30px;
  margin-top: 20px;
  float: right;
  display: flex;
  .on {
    width: 50px;
    font-size: 0.28rem;
    color: #ff7100;
    padding: 0.1rem;
    background: #fff1e5;
    border-radius: 0.25rem;
    list-style: none;
    text-align: center;
    margin-right: 0.1rem;
    line-height: 25px;
  }
}
.year_inner,
.month_inner {
  margin-top: 20px;
  .title {
    width: 100%;
    height: 30px;
    font-size: 0.4rem;
    color: #fff;
    line-height: 30px;
  }
}
/* 月份 */
.month {
  width: 100%;
  height: auto;
  overflow: hidden;
  padding: 0.4rem 0;
}
.month ul {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.month ul li {
  font-size: 0.28rem;
  color: #999999;
  text-align: center;
  width: 1.1rem;
  margin: 0 0.2rem;
}
.month ul li.on {
  font-size: 0.28rem;
  color: #ff7100;
  padding: 0.1rem;
  background: #fff1e5;
  border-radius: 0.25rem;
}
/* 信息总览 */
.total-info {
  width: 100%;
  height: auto;
  overflow: hidden;
}
.total-info ul {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 2rem;
  background: linear-gradient(to right, #fd738b, #ff2c6a);
  border-radius: 0.2rem;
}
.total-info ul li {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
}
.total-info ul li::after {
  content: '';
  display: block;
  width: 0.01rem;
  height: 0.35rem;
  background: #fd849d;
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}
.total-info ul li p {
  font-size: 0.38rem;
  color: #ffffff;
}
.total-info ul li p i {
  font-size: 0.28rem;
  margin-right: 0.02rem;
}
.total-info ul li p span {
  margin-left: 0.05rem;
  color: #ffffff;
}
.total-info ul li span {
  font-size: 0.28rem;
  color: #ffc9d7;
  margin-top: 0.1rem;
}
/* 消费支出 */
.chart {
  width: 100%;
  height: auto;
  overflow: hidden;
  background: #ffffff;
  padding: 0.35rem 0;
  margin-top: 0.4rem;
  border-radius: 0.2rem;
}
.chart .title {
  width: 95%;
  margin: 0 auto;
  height: auto;
  overflow: hidden;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.chart .title h3 {
  font-size: 0.34rem;
  color: #333333;
  font-weight: bold;
}
.chart .title .type {
  display: flex;
  align-items: center;
}
.chart .title .type span {
  width: 0.8rem;
  height: 0.45rem;
  line-height: 0.45rem;
  text-align: center;
  font-size: 0.2rem;
  color: #bcbcbc;
  background: #f7f7f7;
}
.chart .title .type span.on {
  background: #747d99;
  color: #ffffff;
}
.chart .title .type span:first-child {
  border-radius: 0.1rem 0 0 0.1rem;
}
.chart .title .type span:last-child {
  border-radius: 0 0.1rem 0.1rem 0;
}
.chart #chart {
  width: 100%;
  height: 3.8rem;
}
.chart #chartY {
  width: 100%;
  height: 3.8rem;
}
</style>
