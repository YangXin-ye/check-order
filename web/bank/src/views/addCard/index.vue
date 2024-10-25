<template>
  <div class="container">
    <van-form @submit="onSubmit">
      <van-field
        v-model="formInner.bankNum"
        label="银行卡账号"
        placeholder="请填写银行卡账号"
        :rules="[{ required: true, message: '请填写银行卡账号' }]" />
      <van-field
        readonly
        clickable
        name="picker"
        :value="formInner.name"
        label="银行名称"
        placeholder="点击选择银行名称"
        @click="showPicker = true" />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
    <van-popup v-model="showPicker" position="bottom">
      <van-picker show-toolbar :columns="columns" @confirm="onConfirm" @cancel="showPicker = false" />
    </van-popup>
  </div>
</template>
<script>
import { save,update } from './../../api/bank'
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
      columns: [ '建设银行', '农业银行', '工商银行'],
      showPicker: false,
      showCalendar: false
    }
  },
  created(){
    this.upInit();
  },
  methods: {
    upInit(){
      let id =  this.getParamData("id");
      let name =  this.getParamData("name");
      let bankNum =  this.getParamData("bankNum");
      console.log("1231321331231231231312asd");
      console.log(id);
      this.formInner.name = name;
      this.formInner.id = id;
      this.formInner.bankNum = bankNum;
    },
    onSubmit(values) {
      this.insert();
    },
    async insert(){
      if(this.formInner.id==null){
        let res = save(this.formInner);
        Message({
          message: '添加成功',
          type: 'success', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
      }else{
        let res = update(this.formInner);
        Message({
          message: '修改成功',
          type: 'success', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
      }
      this.$router.go(-1);
    },
    onConfirm(value) {
      this.formInner.name = value
      this.showPicker = false
    },
    getParamData(name){
      const url = window.location.href;
      const paramString = url.split('?')[1];
      if (!paramString) return null;

      const params = new URLSearchParams(paramString);
      return params.get(name);
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
