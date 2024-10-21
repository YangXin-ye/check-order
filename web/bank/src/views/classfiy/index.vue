<template>
  <div class="container">
    <div class="button">
      <li class="on" @click="goAdd">新增</li>
    </div>

    <div class="head">
      <van-row>
        <van-col span="10">收支名称</van-col>
        <van-col span="10">收支类型</van-col>
        <van-col span="4">操作</van-col>
      </van-row>
    </div>
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <van-cell v-for="(item, index) in list" :key="index">
        <van-row>
          <van-col span="10">{{ item.name }}</van-col>
          <van-col span="10">{{ item.type }}</van-col>
          <van-col span="4">
            <div class="options">
              <van-button icon="edit" type="danger" size="mini" @click="edit(item)"></van-button>
              <van-button icon="delete-o" type="warning" size="mini" @click="del(item)"></van-button>
            </div>
          </van-col>
        </van-row>
      </van-cell>
    </van-list>
  </div>
</template>
<script>
import { list,deleteData } from './../../api/classfiy'
export default {
  data() {
    return {
      list: [
        {
          id: '0',
          name: '购物',
          type: '支出'
        },
        {
          id: '1',
          name: '学习',
          type: '支出'
        },
        {
          id: '2',
          name: '工作',
          type: '收入'
        }
      ],
      loading: true,
      finished: true
    }
  },
  activated() {
    console.log("123123");
    this.listData();
  },
  methods: {
    onLoad() {
      // 异步更新数据
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
    },
    goAdd() {
      this.$router.push('/classfiyDrtail')
    },
    edit(row) {
      this.$router.push({ path: '/classfiyDrtail', query: { id: row.id ,name:row.name,type:row.type} });
    },
    async del(row) {
      console.log(row);
      if (confirm("你确定要删除这条记录吗？")) {
        // 发送删除请求
        row.type='';
        await deleteData(row);
        // 显示成功消息
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.listData();
      }
    },
    async listData(){
      let res = await list();
      res.data.forEach(item => {
        item.type= item.type==1?'支出':'收入';
      })
      this.list = res.data;
      console.log(res);
    }
  },
  created(){
    this.listData();
  }
}
</script>
<style lang="less" scoped>
.container {
  height: auto;
  width: 95%;
  margin: 30px auto 0 auto;
}
.button {
  width: 100%;
  height: 30px;
  margin-bottom: 20px;
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
.head {
  width: 100%;
  height: 30px;
  .van-row {
    height: 100%;
    .van-col {
      height: 100%;
      text-align: center;
      line-height: 30px;
      font-size: 0.4rem;
      color: #fff;
    }
  }
}
.van-list {
  .van-row {
    height: 100%;
    .van-col {
      height: 100%;
      text-align: center;
      line-height: 30px;
      font-size: 0.3rem;
      color: #fff;
    }
  }
  .options {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
}
::v-deep {
  .van-cell {
    padding: 10px 0;
    background: linear-gradient(to right, #2c324d, #3f4561);
  }
}
</style>
