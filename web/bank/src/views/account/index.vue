<template>
  <div class="container">
    <van-form @submit="onSubmit">
      <van-field
        v-model="formInner.price"
        label="金额"
        placeholder="请填写金额"
        :rules="[{ required: true, message: '请填写金额' }]" />
      <van-field
        readonly
        clickable
        name="picker"
        :value="formInner.typeName"
        label="分类"
        placeholder="点击选择分类"
        @click="showPicker = true" />
      <van-field
        readonly
        clickable
        name="calendar"
        :value="formInner.time"
        label="日期"
        placeholder="点击选择日期"
        @click="showCalendar = true" />

      <van-field
        v-model="formInner.remark"
        label="备注"
        placeholder="请填写备注"
        :rules="[{ required: true, message: '请填写请填写备注' }]" />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>

    <van-calendar v-model="showCalendar" @confirm="onConPickerfirm" />
    <van-popup v-model="showPicker" position="bottom">
      <van-picker show-toolbar :columns="columns" @confirm="onConfirm" @cancel="showPicker = false" />
    </van-popup>
  </div>
</template>
<script>
import {save} from './../../api/accountionRecord'
import {list} from './../../api/classfiy'
import { Message } from 'element-ui';
export default {
  data() {
    return {
      formInner: {
        money: '',
        remark: '',
        type: '',
        date: ''
      },
      value: '',
      columns: [],
      showPicker: false,
      showCalendar: false
    }
  },
  created(){
    this.classfiyInit();
  },
  methods: {
    async classfiyInit(){
      let classList = await list();
      console.log(classList);
      classList.data.forEach(item=>{
        this.columns.push(item.name);
      })
      console.log(this.columns);
    },
    onSubmit(values) {
     console.log(this.formInner);
      save(this.formInner);
      Message({
        message: '添加成功',
        type: 'success', // 类型可选：success, warning, info, error
        duration: 3000 // 持续时间（毫秒）
      });
      this.$router.push("/income")
    },
    onConfirm(value) {
      this.formInner.typeName = value
      this.showPicker = false
    },
    onConPickerfirm(date) {
      this.formInner.time = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
      this.showCalendar = false
    }
  }
}
</script>
<style lang="less" scoped>
.container {
  height: auto;
  width: 95%;
  margin: 0 auto;
}
::v-deep {
  .van-cell {
    background: linear-gradient(to right, #2c324d, #3f4561);
  }
  .van-field__body,
  .van-cell__value,
  .van-field__control {
    background: linear-gradient(to right, #2c324d, #3f4561);
    color: #fff;
  }
}
</style>
