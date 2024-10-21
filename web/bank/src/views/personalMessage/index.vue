<template>
  <div class="container">
    <van-form @submit="onSubmit">
      <van-field name="uploader" label="头像">
        <template #input>
          <div v-if="img">
            <img :src="img" alt="头像" width="80" height="80" />
          </div>
          <van-uploader
              v-model="fileList"
              multiple
              :max-count="1"
              :after-read="onAfterRead"
          />
        </template>
      </van-field>
      <van-field
        v-model="formInner.username"
        name="用户名"
        label="用户名"
        placeholder="用户名"
        :rules="[{ required: true, message: '请填写用户名' }]" />
      <van-field
          v-model="formInner.name"
          name="真实姓名"
          label="真实姓名"
          placeholder="真实姓名"
          :rules="[{ required: true, message: '请填写真实姓名' }]" />
      <van-field
          v-model="formInner.phone"
          name="手机号"
          label="手机号"
          placeholder="手机号"
          :rules="[{ required: true, message: '请填写手机号' }]" />
      <van-field
          v-model="formInner.age"
          name="年龄"
          label="年龄"
          placeholder="年龄"
          :rules="[{ required: true, message: '请填写年龄' }]" />
      <van-field
          readonly
          clickable
          name="picker"
          :value="formInner.sexName"
          label="性别"
          placeholder="点击选择性别"
          @click="showPicker = true" />
      <van-field
        v-model="formInner.password"
        name="密码"
        label="密码"
        placeholder="密码"
      />
      <van-field
        v-model="formInner.confirmpassword"
        name="确认密码"
        label="确认密码"
        placeholder="确认密码"
        />
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
import {getLoginUser,updateUser,uploadFile} from "./../../api/user"
import axios from "@/axios";
import { Message } from 'element-ui';
export default {
  data() {
    return {
      formInner: {
        username: '',
        password: '',
        name:'',
        phone:'',
        age:'',
        sexName:'',
        confirmpassword: '',
        img:''
      },
      columns: ['男', '女'],
      showPicker: false,
      fileList: [],
      img:'http://localhost:9000/manage/1.png'
    }
  },
  created(){
    this.getLoginUserInfo();
  },
  methods: {
    onAfterRead(file) {
      // 模拟图片上传，替换为你实际的上传逻辑
      this.uploadImage(file.file).then((url) => {
        this.img = url;  // 上传成功后设置回显的图片路径
      });
    },
    async uploadImage(file) {
      const formData = new FormData();
      formData.append('file', file);  // 将文件添加到 FormData
      let response = await uploadFile(formData);
      console.log(response.data.fileId);
      this.formInner.img = response.data.fileId;
    },
    onSubmit(values) {
      if(this.formInner.password != this.formInner.confirmpassword){
        Message({
          message: '密码和确认密码不一致',
          type: 'error', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
        return;
      }
      updateUser(this.formInner);
      Message({
        message: '修改成功',
        type: 'success', // 类型可选：success, warning, info, error
        duration: 3000 // 持续时间（毫秒）
      });
      setTimeout(() => {
        this.$router.go(0);
      }, 500);

    },
    onConfirm(value) {
      this.formInner.sex = value=='男'?1:2
      this.formInner.sexName = value
      this.showPicker = false
    },
    async getLoginUserInfo(){
      let res = await getLoginUser();
      this.formInner.id = res.user.id;
      this.formInner.username = res.user.username;
      this.formInner.name = res.user.name;
      this.formInner.phone = res.user.phone;
      this.formInner.age = res.user.age;
      this.formInner.sexName = res.user.sex==1?'男':'女';
      this.img = "http://localhost:8089/express/file/download?fileId="+res.user.photo;
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
