<template>
  <div class="container">
    <div class="button">
      <van-search v-model="searchData" show-action placeholder="请输入搜索关键词" @search="onSearch">
        <template #action>
          <div @click="onSearch" class="search_btn">搜索</div>
        </template>
      </van-search>
    </div>

    <div class="head">
      <van-row>
        <van-col span="5">收支类型</van-col>
        <van-col span="5">费用</van-col>
        <van-col span="11">内容</van-col>
        <van-col span="3">操作</van-col>
      </van-row>
    </div>
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <van-cell v-for="(item, index) in list" :key="index">
        <van-row>
          <van-col span="5">{{ item.type }}</van-col>
          <van-col span="5">{{ item.price }}</van-col>
          <van-col span="11">{{ item.remark }}</van-col>
          <van-col span="3">
            <div class="options">
              <van-button icon="delete-o" type="warning" size="mini" @click="del(item.id)"></van-button>
            </div>
          </van-col>
        </van-row>
      </van-cell>
    </van-list>
  </div>
</template>
<script>
import { Toast } from 'vant'
import {getList,deleteData} from './../../api/accountionRecord'
export default {
  data() {
    return {
      value: '',
      searchData:'',
      list: [
        {
          id: '0',
          price: '30',
          type: '支出',
          content: '买了牙膏'
        },
        {
          id: '1',
          price: '30',
          type: '支出',
          content: '吃了火锅'
        },
        {
          id: '2',
          price: '30',
          type: '收入',
          content: '发工资了'
        }
      ],
      loading: true,
      finished: true
    }
  },
  created() {
    this.getData();
  },
  methods: {
    onSearch() {
      this.getData();
    },
    onLoad() {
      // 异步更新数据
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
    },
    goAdd() {
      this.$router.push('/classfiyDrtail')
    },
    edit() {
      this.$router.push('/classfiyDrtail')
    },
    async del(id) {
      // 确认删除操作
      if (confirm("你确定要删除这条记录吗？")) {
        // 发送删除请求
        await deleteData(id);
        // 显示成功消息
        this.$message({
          message: "删除成功",
          type: "success",
        });
        this.getData();
      }
    },
    async getData() {
      let response = await getList(this.searchData);
      console.log("777")
      response.data.forEach((item) => {
        item.type = item.type === 1 ? '支出' : '收入';
      });
      this.list = response.data;
    }
  },
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
  height: auto;
  margin-bottom: 20px;
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
.search_btn {
  color: #ff7100;
}
.van-search {
  width: 100%;
  background: linear-gradient(to right, #2c324d, #3f4561);
  border: 1px solid #fff;
  border-radius: 20px;
}
.van-search__content {
  background: linear-gradient(to right, #2c324d, #3f4561);
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
  .van-field__control {
    color: #fff;
  }
}
</style>
