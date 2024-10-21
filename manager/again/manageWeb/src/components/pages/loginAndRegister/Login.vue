<template>
    <div class="login_container">
        <div class="login_form">
            <p class="login_title">后台管理系统</p>
            <el-form :model="form" :rules="rules" status-icon label-width="100px" class="demo-ruleForm">
                <el-form-item label="账号" prop="username">
                    <el-input v-model="form.username" placeholder="请输入账号"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="medium" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { login } from "@/api/login";


export default {
    name: "Login",
    data() {
        return {
            form: {
                username: "",
                password: "",
            }, rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' }
                ], password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ]
            }
        };
    },
  methods: {
    login() {
      login(this.form).then(res => {
        // 存入token
        localStorage.setItem('token', res.data)
        // 跳转到首页
        this.$router.push('/')
      }).catch(err => {
        alert(err.data)
      })
    }
  }
};
</script>
<style lang="less" scoped>
.login_container {
    width: 100vw;
    /* 使用视口宽度 */
    height: 100vh;
    background-color: rgba(242, 242, 242, 1);
    background-image: url(../../../assets/images/login_bg.png);
    background-position: center bottom;
    background-repeat: no-repeat;
    background-attachment: fixed;
    /* 修改为fixed */
    background-size: cover;
    /* 使用cover来铺满整个容器 */
    background-origin: border-box;
    border: none;
    border-radius: 0px;
    display: flex;
    justify-content: flex-end;
    /* 让表单靠右对齐 */
    align-items: center;
    /* 垂直居中 */
    height: 100vh;

    .login_form {
        width: 510px;
        margin-right: 50px;
        /* 从右侧添加 50px 距离 */
        padding: 20px 40px;
        /* 上下各留出 20px 的空间 */
        background-color: #fff;
        border: none;
        border-radius: 5px;
        box-shadow: 0 0 25px #cac6c6;
        /* 添加阴影效果 */

        .login_title {
            font-family: '微软雅黑 Bold', '微软雅黑';
            font-weight: 700;
            text-decoration: none;
            color: rgb(0, 121, 254);
            font-size: 32px;
            margin-top: 50px;
            margin-bottom: 30px;
            text-align: center;
        }
    }
}
</style>
