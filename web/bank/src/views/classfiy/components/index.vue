<template>
  <div class="container">
    <van-form @submit="onSubmit">
      <van-field
        v-model="formInner.name"
        label="收支名称"
        placeholder="请填写收支名称"
        :rules="[{ required: true, message: '请填写收支名称' }]" />
      <van-field
        readonly
        clickable
        name="picker"
        :value="formInner.typeName"
        label="收支类型"
        placeholder="点击选择收支类型"
        @click="showPicker = true" />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit" @click="sub">提交</van-button>
      </div>
    </van-form>
    <van-popup v-model="showPicker" position="bottom">
      <van-picker show-toolbar :columns="columns" @confirm="onConfirm" @cancel="showPicker = false" />
    </van-popup>
  </div>
</template>
<script>
import { save,update } from './../../../api/classfiy'
import { Message } from 'element-ui';
export default {
  data() {
    return {
      formInner: {
        money: '',
        remark: '',
        typeName: '',
        date: ''
      },
      value: '',
      columns: ['收入', '支出'],
      showPicker: false,
      showCalendar: false
    }
  },
  mounted() {
    this.upInit();
  },
  methods: {
    onSubmit(values) {
      console.log('submit', values)
    },
    onConfirm(value) {
      this.formInner.typeName = value
      this.showPicker = false
    },
    upInit(){
      let id =  this.getParamData("id");
      let name =  this.getParamData("name");
      let type =  this.getParamData("type");
      console.log(name);
      this.formInner.name = name;
      this.formInner.id = id;
      this.formInner.type = type;
      this.formInner.typeName = type==1?'支出':'收入';
    },
    sub(){
      this.formInner.type=this.formInner.typeName=='支出'?1:2;
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
