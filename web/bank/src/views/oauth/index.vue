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
          <div class="form_item">
            <el-button @click="showRegisterModal = true" type="text">立即注册</el-button>
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

    <!-- 注册弹框 -->
    <el-dialog :visible.sync="showRegisterModal" title="用户注册" width="650px" center>
      <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input v-model="registerForm.name" placeholder="请输入真实姓名"></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="emailCode">
          <el-input v-model="registerForm.emailCode" placeholder="请输入邮箱验证码"></el-input>
        </el-form-item>

<!--        <el-form-item label="年龄" prop="age">-->
<!--          <el-input-number v-model="registerForm.age" :min="1" placeholder="请输入年龄"></el-input-number>-->
<!--        </el-form-item>-->

<!--        <el-form-item label="性别" prop="sex">-->
<!--          <el-radio-group v-model="registerForm.sex">-->
<!--            <el-radio :label="1">男</el-radio>-->
<!--            <el-radio :label="2">女</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->

        <!-- Province, City, Area Selection -->
        <el-form-item label="地址" prop="address">
          <el-cascader v-model="registerForm.addressList" :options="options"></el-cascader>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="sendEmailCodeFun">获取验证码</el-button>
          <el-button type="primary" @click="handleRegister">注册</el-button>
          <el-button @click="showRegisterModal = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import {login,sendVerificationCode,getDataProvinceAndCityAndArea,sendEmailCode,registerUser} from './../../api/login'
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
      phone:'',
      showRegisterModal: false,
      registerForm: {
        username: '',
        password: '',
        name: '',
        phone: '',
        addressList:'',
        email:'',
        emailCode:''
      },
      provinces: [],
      cities: [],
      areas: [],
      options: [],
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        // age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        // sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
        emailCode: [{ required: true, message: '请输入邮箱验证码', trigger: 'blur' }],
        addressList: [{ required: true, message: '请选择地址', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getTree();
    console.log("123123213123");
  },
  methods: {
    async handleRegister() {
      this.$refs.registerForm.validate(async (valid) => {
        if (valid) {
          // Proceed with registration logic
          console.log("Registration form is valid:", this.registerForm);
          try {
            const response = await registerUser(this.registerForm);
            if (response.code === 0) {
              Message.success("注册成功！");
              this.showRegisterModal = false;
            } else {
              Message.error(response.message || "注册失败");
            }
          } catch (error) {
            // Message.error("注册请求出错");
            // console.error(error);
          }
        } else {
          console.log("Form validation failed.");
          return false;
        }
      });
    },
    async sendEmailCodeFun() {
      if(this.registerForm.email == null || this.registerForm.email==''){
        Message({
          message: '邮箱不能为空',
          type: 'error', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
        return;
      }
      if (!this.isValidEmail(this.registerForm.email)) {
        Message({
          message: '邮箱格式不正确',
          type: 'error', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
      } else {
        //发送验证码
        let res = await sendEmailCode(this.registerForm.email);
        Message({
          message: '验证码发送成功',
          type: 'success', // 类型可选：success, warning, info, error
          duration: 3000 // 持续时间（毫秒）
        });
        console.log(res);
      }
    },
    isValidEmail(email) {
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailRegex.test(email);
    },
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
    },
    getTree() {
      const level = {
        level: 'province'
      }
      getDataProvinceAndCityAndArea({ params: level }).then(res => {
        // console.log(res);
        this.options = res.data
        // console.log(this.options);

        this.options.forEach(item => {
          item.value = item.id
          item.label = item.name

          const levelCity = {
            districtId: item.id,
            level: 'province'
          }
          getDataProvinceAndCityAndArea({ params: levelCity }).then(res => {
            // console.log(res);
            item.children = res.data
            item.children.forEach(item => {
              item.value = item.id
              item.label = item.name
              const levelArea = {
                districtId: item.id,
                level: 'city'
              }
              getDataProvinceAndCityAndArea({ params: levelArea }).then(res => {
                // console.log(res);
                item.children = res.data
                item.children.forEach(item => {
                  item.value = item.id
                  item.label = item.name
                })
                // console.log(item.children);
              })
            })
          })
        })
      })
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
