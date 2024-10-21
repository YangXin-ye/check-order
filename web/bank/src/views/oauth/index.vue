<template>
  <div class="login_warp">
    <div class="loginbox fl">
      <div class="login_header">
        <span @click="cur = 0,loginType=2" :class="{ active: cur == 0 }">账号登录</span>
        <span @click="cur = 1,loginType=1" :class="{ active: cur == 1 }">快捷登录</span>
        <span @click="cur = 2" :class="{ active: cur == 2 }">扫码登录</span>
      </div>
      <div class="login_content">
        <div v-show="cur == 0" class="Cbody_item">
          <div class="form_item">
            <input
                type="text"
                v-model="username"
                placeholder="用户名"
            />
          </div>
          <div class="form_item">
            <input
                type="password"
                v-model="password"
                placeholder="密码"
            />
          </div>
          <div class="clear"></div>
          <div class="form_item">
            <input
                type="submit"
                value="登录"
                @click.prevent="login(2)"
            />
          </div>
        </div>
        <div v-show="cur == 1" class="Cbody_item">
          <div class="form_item flex-container"> <!-- 添加 flex-container 类 -->
            <input type="text" v-model="phone" placeholder="手机号" class="phone-input" />
            <button @click="getVerificationCode" class="verification-button" style="margin-top: -22px;height: 40px;">获取验证码</button> <!-- 添加按钮 -->
          </div>
          <div class="form_item">
            <input type="password" v-model="verificationCode" placeholder="验证码" />
          </div>
          <div class="clear"></div>
          <div class="form_item">
            <input type="submit" value="登录"
                   @click.prevent="login(1)"/>
          </div>
        </div>
        <div v-show="cur == 2" class="Cbody_item">
          <div class="qcode">
            <img
                src="@/assets/images/qcode.png"
                width="160"
                height="160"
                alt="二维码"
            />
          </div>
          <div class="beizhu">打开手机客户端扫码注册</div>
        </div>
      </div>
    </div>
    <div class="loginrslider fl"></div>
  </div>
</template>

<script>
import {login,sendVerificationCode} from './../../api/login'
import { Message } from 'element-ui';


export default {
  name: 'oauth',
  data() {
    return {
      cur: 0, // 默认选中第一个tab
      username: '', // 用户名
      password: '', // 密码
      rememberMe: false, // 记住密码选项
      loginType:2,
      verificationCode:'',
      phone:''
    }
  },
  methods: {
    async login(loginType) {
      // 发送登录请求
      let response = await login({
        username: this.username,
        password: this.password,
        loginType: loginType,
        code:this.verificationCode,
        phone:this.phone
      })
      // 假设后端返回一个 token 和用户信息
      if(response.code == 0){
        localStorage.setItem('token',response.token);
        console.log("saasdad");
        // this.$store.dispatch('login', response.data.userInfo); // 更新 Vuex 登录状态
        this.$router.push('/home'); // 登录成功后跳转
      }
    },
    async getVerificationCode() {
      if(this.phone == null || this.phone == ''){
        Message({
          message: '手机号不能为空',
          type: 'error', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
        return;
      }
      try {
        let phone  = this.phone;
        const response = await sendVerificationCode(phone); // 调用获取验证码的接口
        // 处理成功的逻辑，例如提示用户
        console.log('验证码已发送:', response);
      } catch (error) {
        // 处理错误，例如提示用户
        console.error('获取验证码失败:', error);
      }
    }
  }
}
</script>

<style scoped>
.fl {
  float: left;
}
.fr {
  float: right;
}
.clear {
  clear: both;
}
a {
  text-decoration: none;
  color: #333;
  transition: ease all 0.5s;
}
a {
  color: #1c65ff;
}
.login_header {
  margin-bottom: 40px;
}
.login_header span {
  margin-right: 20px;
  cursor: pointer;
}
.loginbox {
  width: 300px;
  overflow: hidden;
  padding: 20px;
  padding-top: 15px;
}
.Cbody_item {
  border: 0px solid #999;
  overflow: hidden;
}
.form_item {
  font-size: 13px;
}
.form_item input[type='text'],
.form_item input[type='password'] {
  display: block;
  width: calc(100% - 18px);
  height: 36px;
  margin: 0 auto;
  padding-left: 16px;
  outline: none;
  border: 1px solid #999;
  transition: ease all 0.5s;
  margin-bottom: 20px;
}
.form_item input[type='text']:focus,
.form_item input[type='password']:focus {
  border: 1px solid #1c65ff;
}
.form_item input[type='submit'] {
  display: block;
  width: calc(100% - 0px);
  height: 40px;
  margin: 0 auto;
  padding-left: 16px;
  outline: none;
  border: 1px solid #1c65ff;
  transition: ease all 0.5s;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: #1c65ff;
  color: #fff;
  cursor: pointer;
}
.form_item input[type='submit']:hover {
  background-color: #3f7dff;
  border: 1px solid #3f7dff;
}
.active {
  color: #3f7dff;
  padding-bottom: 10px;
  border-bottom: 3px solid #3f7dff;
}
.loginrslider {
  width: 160px;
  height: 322px;
  background-color: #1c65ff;
}
.login_warp {
  width: 500px;
  margin: 50px auto;
  border-radius: 10px;
  box-shadow: 0 0px 0px #ccc;
  overflow: hidden;
  border: 1px solid #3f7dff;
  margin-top: 10%;
  transition: ease all 0.5s;
  position: relative;
  top: 0px;
  cursor: pointer;
  background: #fff;
}
.login_warp:hover {
  top: -30px;
  box-shadow: 0 15px 21px #ccc;
}
.loginrslider {
  color: #fff;
}
.qcode {
  width: 160px;
  height: 160px;
  background-color: #ccc;
  margin: 0 auto;
  margin-top: 2px;
}
.beizhu {
  text-align: center;
  font-size: 13px;
  margin-top: 10px;
  color: #999;
}
.form_item {
  display: flex; /* 使用 flexbox 布局 */
  align-items: center; /* 垂直居中对齐 */
}

.phone-input {
  flex: 1; /* 输入框占据剩余空间 */
  margin-right: 8px; /* 输入框和验证码输入框之间的间距 */
}

.verification-container {
  display: flex; /* 使用 flexbox 布局使验证码输入框和按钮在同一行 */
}

.verification-input {
  margin-right: 8px; /* 验证码输入框和按钮之间的间距 */
}

.verification-button {
  /* 按钮样式，可以根据需求自定义 */
}
</style>
