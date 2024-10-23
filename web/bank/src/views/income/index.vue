<template>
  <div class="container">
    <div class="button">
      <li class="on" @click="goAccount">去记账</li>
      <li class="on" @click="goClassfiy">去分类</li>
      <li class="on" @click="downloadTemplate">下载模板</li>
      <li class="on" @click="importFile">导入文件</li>
    </div>
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
      <div class="detailOne">
        <ul v-if="type == 1">
          <li v-for="(transaction, index) in shouData" :key="index">
            <div class="title">
              <span>{{ transaction.remark }}</span>
            </div>
            <span>{{ transaction.type ==1 ? '-' : '+' }}{{ transaction.price }}</span>
          </li>
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="money"></i>-->
<!--                <span>教育基金</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">+180</div>-->
<!--          </li>-->
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="money"></i>-->
<!--                <span>稳定收益债券</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">+14100</div>-->
<!--          </li>-->
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="money"></i>-->
<!--                <span>工资</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">+18100</div>-->
<!--          </li>-->
        </ul>
        <ul v-else>
          <li v-for="(transaction, index) in zhiData" :key="index">
            <div class="title">
              <span>{{ transaction.remark }}</span>
            </div>
            <span>{{ transaction.type ==1 ? '-' : '+' }}{{ transaction.price }}</span>
          </li>
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="edu"></i>-->
<!--                <span>吃饭支出</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">-580</div>-->
<!--          </li>-->
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="edu"></i>-->
<!--                <span>购物支出</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">-4100</div>-->
<!--          </li>-->
<!--          <li>-->
<!--            <div class="title">-->
<!--              <div class="title_inner">-->
<!--                <i class="edu"></i>-->
<!--                <span>房贷支出</span>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="num">-54100</div>-->
<!--          </li>-->
        </ul>
      </div>
    </div>
    <!-- 明细 -->
    <div class="detail">
      <div class="cate">
        <span :class="cate == 2 ? 'on' : ''" @click="changeCate(2)">收支明细</span>
      </div>
      <ul v-if="cate == 1">
        <li>
          <div class="title">
            <i class="edu"></i>
            <span>教育基金</span>
          </div>
          <span>-180</span>
        </li>
        <li>
          <div class="title">
            <i class="money"></i>
            <span>稳定收益债券</span>
          </div>
          <span>+14100</span>
        </li>
      </ul>
      <ul v-else>
        <li v-for="(transaction, index) in detailData" :key="index">
          <div class="title">
            <span>{{ transaction.remark }}</span>
          </div>
          <span>{{ transaction.type ==1 ? '-' : '+' }}{{ transaction.price }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import {getMonthData,getDataDetail,importData} from './../../api/accountionRecord'
import { Message } from 'element-ui';
export default {
  name: 'income',
  data() {
    return {
      months: [
        { value: 8, label: '8月' },
        { value: 9, label: '9月' },
        { value: 10, label: '10月' },
        { value: 11, label: '11月' },
        { value: 12, label: '12月' }
      ],
      monthData:{},
      nav: 10, // 月份导航
      type: 2, // 图表类型
      cate: 2, // 明细分类
      chart: null, // 图表
      chartOptions: null, // 图表配置项
      typeTitle: '消费支出',
      detailData:[],
      shouData:[],
      zhiData:[]
    }
  },
  created(){
    this.getData();
    this.getDetailData();
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
    async getData(){
      let res = await getMonthData(this.nav);
      this.monthData = res.data;
    },
    async getDetailData(){
      let res = await getDataDetail(this.nav);
      this.detailData = res.data;
      let shou = [];
      let zhi = [];
      res.data.forEach(item =>{
        if(item.type==2){
          shou.push(item);
        }
        if(item.type ==1){
          zhi.push(item);
        }
      });
      this.shouData =shou;
      this.zhiData = zhi;
    },
    downloadTemplate() {
      // 调用 GET 接口下载模板
      const url = 'http://localhost:8089/express/downloadTemplate'; // 替换为实际的接口 URL
      window.open(url); // 打开下载链接
    },
    importFile() {
      // 处理文件导入逻辑，例如打开文件选择对话框
      const input = document.createElement('input');
      input.type = 'file';
      input.accept = '.xls, .xlsx'; // 可以根据需要修改支持的文件类型

      input.onchange = async (event) => {
        const file = event.target.files[0]; // 获取用户选择的文件
        if (file) {
          const formData = new FormData();
          formData.append('file', file); // 将文件添加到 FormData

          try {
            await importData(formData); // 调用导入数据的方法
            Message({
              message: '导入成功',
              type: 'success', // 类型可选：success, warning, info, error
              duration: 3000 // 持续时间（毫秒）
            });
          } catch (error) {
          }
        } else {
          console.warn('没有选择文件');
        }
      };

      input.click(); // 模拟点击打开文件选择对话框
    },
    goAccount() {
      this.$router.push('/account')
    },
    goClassfiy() {
      this.$router.push('/classfiy')
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
            data: ['10/11', '10/12', '10/13', '10/14', '10/15']
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
              formatter: '{value}' + 'k'
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
            name: '支出',
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
            data: [5, 8, 2, 8, 2]
          }
        ]
      }
      // 填充配置
      this.chart.setOption(this.chartOptions)
    },
    // 切换导航
    changeNav: function (item) {
      this.nav = item
      this.getData();
      this.getDetailData();
    },
    // 切换收入支出
    changeType: function (item) {
      this.type = item
      if (item == 1) {
        this.typeTitle = '转账收入'
        this.chartOptions.series[0].data = [8, 5, 6, 2, 6]
        this.chart.setOption(this.chartOptions)
      } else {
        this.typeTitle = '消费支出'
        this.chartOptions.series[0].data = [5, 8, 2, 8, 2]
        this.chart.setOption(this.chartOptions)
      }
    },
    // 切换类型
    changeCate: function (item) {
      this.cate = item
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
.detailOne {
  width: 100%;
  height: auto;
  overflow: hidden;
  background: #ffffff;
  /* padding: 0.35rem 0.3rem; */
  margin-top: 0.4rem;
  border-radius: 0.2rem;
}
.detailOne ul {
  width: 95%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  /* padding: 0 0.1rem; */
}
.detailOne ul li {
  width: 100%;
  height: 1.1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  border-bottom: 0.01rem solid #f5f5f5;
}

.detailOne ul li:last-child {
  border-bottom: 0;
}
.detailOne ul li span {
  font-size: 0.3rem;
  font-weight: bold;
  color: #5b6275;
}
.detailOne ul li .title {
  display: table;
}
.detailOne ul li .title .title_inner {
  display: flex;
  align-items: center;
}
.detailOne ul li .title i {
  display: inline-block;
  width: 0.52rem;
  height: 0.52rem;
  margin-right: 0.2rem;
}
.detailOne ul li .title i.edu {
  background: url(../../assets/images//icon-edu.png) center center no-repeat;
  background-size: 0.52rem auto;
}
.detailOne ul li .title i.money {
  background: url(../../assets/images//icon-money.png) center center no-repeat;
  background-size: 0.52rem auto;
}
.detailOne ul li .title span {
  font-size: 0.3rem;
  color: #5b6275;
  font-weight: normal;
}

/* 收支明细 */
.detail {
  width: 100%;
  height: auto;
  overflow: hidden;
  background: #ffffff;
  /* padding: 0.35rem 0.3rem; */
  margin-top: 0.4rem;
  border-radius: 0.2rem;
}
.detail .cate {
  width: 95%;
  margin: 0 auto;
  height: auto;
  overflow: hidden;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* padding: 0 0.45rem; */
  margin-bottom: 0.2rem;
}
.detail .cate span {
  font-size: 0.28rem;
  color: #999999;
  height: 0.6rem;
  line-height: 0.6rem;
  text-align: center;
  width: 1.82rem;
  border-radius: 0.6rem;
}
.detail .cate span.on {
  background: #fff1e5;
  color: #ff7100;
}
.detail ul {
  width: 95%;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  /* padding: 0 0.1rem; */
}
.detail ul li {
  width: 100%;
  height: 1.1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  border-bottom: 0.01rem solid #f5f5f5;
}
.detail ul li:last-child {
  border-bottom: 0;
}
.detail ul li span {
  font-size: 0.3rem;
  font-weight: bold;
  color: #5b6275;
}
.detail ul li .title {
  display: flex;
  align-items: center;
}
.detail ul li .title i {
  width: 0.52rem;
  height: 0.52rem;
  margin-right: 0.2rem;
}
.detail ul li .title i.edu {
  background: url(../../assets/images//icon-edu.png) center center no-repeat;
  background-size: 0.52rem auto;
}
.detail ul li .title i.money {
  background: url(../../assets/images//icon-money.png) center center no-repeat;
  background-size: 0.52rem auto;
}
.detail ul li .title span {
  font-size: 0.3rem;
  color: #5b6275;
  font-weight: normal;
}
/* 右侧浮窗 */
.fixed-right {
  position: fixed;
  right: 0.4rem;
  bottom: 3.8rem;
  width: 1.25rem;
  height: 1.25rem;
  border-radius: 100%;
  background: linear-gradient(to bottom, #fe5e81, #fe4475);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 0.2rem;
  color: #ffffff;
  box-shadow: 0 0 0.3rem rgba(255, 83, 124, 0.3);
  z-index: 999;
}
.fixed-right i {
  width: 0.52rem;
  height: 0.53rem;
  background: url(../../assets/images//icon-hg.png) center center no-repeat;
  background-size: 0.52rem auto;
}
.fixed-right span {
  margin-top: 0.05rem;
  transform: scale(0.95);
}
</style>
