<template>
  <div class="container">
    <div class="button_inner">
      <div class="on" @click="goAccount">收支记录</div>
    </div>
    <div class="content_inner">
      <!-- 消费占比图表 -->
      <div class="consumption-percent">
        <div class="title">
          <span class="label">总收入(元):</span>
          <span class="value">￥{{totalIncome}}</span>
        </div>
        <div class="chart" id="chart"></div>
      </div>
      <div class="consumption-percent">
        <div class="title">
          <span class="label">总支出(元):</span>
          <span class="value">￥{{totalPay}}</span>
        </div>
        <div class="chart" id="chart1"></div>
      </div>
      <!-- 本月结余 -->
<!--      <div class="detail">-->
<!--        <div class="title">-->
<!--          <h4>本月结余</h4>-->
<!--          <span>5000</span>-->
<!--        </div>-->
<!--      </div>-->
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import {totalPrice} from './../../api/accountionRecord'
export default {
  name: 'Index',
  data() {
    return {
      chart: null, // 图表
      chart1: null, // 图表
      totalIncome:0,
      totalPay:0,
      data: [
        // 数据
        { name: '工作', value: 5 },
        { name: '兼职', value: 18 },
        { name: '基金', value: 26 },
        { name: '其他', value: 14 }
      ],
      increment: [
        { name: '工作' },
        { name: '兼职'},
        { name: '基金'},
        { name: '其他'}
      ],
      data1: [
        { name: '餐饮', value: 42 },
        { name: '购物', value: 18 },
        { name: '日用', value: 26 },
        { name: '学习', value: 14 }
      ],
      increment1: [
        { name: '餐饮'},
        { name: '购物' },
        { name: '日用'},
        { name: '学习'}
      ]
    }
  },
  created() {
    this.getTotalPrice();
  },
  mounted() {
  },
  methods: {
    async getTotalPrice() {
      let response = await totalPrice();
      this.data1 = response.data.classifyTotalPay;
      this.increment1 = response.data.classifyTotalPay;
      this.data = response.data.classifyTotalIncome;
      this.increment = response.data.classifyTotalIncome;
      this.totalPay = response.data.totalPay;
      this.totalIncome = response.data.totalIncome;
      this.$nextTick(function () {
        this.initChart()
        this.getPayCharts()
      })
      window.onresize = function () {
        this.chart.resize()
        this.chart1.resize()
      }
    },
    goAccount() {
      this.$router.push('/inAndexRecord')
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
            return params.data.name + ':' + params.data.value + ''
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
              // ' ' +
              // that.getValue(that.data, name) +
              // '%' +
              // '  {a|+' +
              // that.getValue(that.data, name) +
              // '%}'
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
                return params.data.value;
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
    },
    getPayCharts() {
      let that = this
      // 初始化图表
      this.chart1 = echarts.init(document.getElementById('chart1'))
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
            console.log(params);
            console.log("666")
            console.log(params.data1)
            return params.data.name + ':' + params.data.value + '';
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
            return name;
            // if (that.getValue(that.increment1, name) > 0) {
            //   return name + ' ' + '  {a|+' + that.getValue(that.data1, name) + '%}'
            // } else {
            //   return name + ' ' + '  {b|-' + that.getValue(that.data1, name) + '%}'
            // }
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
                return params.data.value ;
              }
            },
            labelLine: {
              show: false
            },
            data: this.data1
          }
        ]
      }
      // 填充配置
      this.chart1.setOption(chartOptions)
    }
  }
}
</script>
<style scoped lang="less">
.container {
  height: auto;
  width: 95%;
  margin: 0 auto;
  overflow-y: scroll;
  padding-bottom: 60px;
}
.button_inner {
  width: 100%;
  height: 30px;
  margin-top: 20px;
  // display: flex;
  .on {
    float: right;
    width: 50px;
    height: 25px;
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
img {
  vertical-align: middle;
  max-width: 100%;
}
.content_inner {
  margin-top: 30px;
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
  .label {
    font-size: 0.34rem;
    color: #fff;
  }
  .value {
    color: #babcc6;
    font-size: 0.5rem;
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
  font-size: 0.5rem;
}
</style>
