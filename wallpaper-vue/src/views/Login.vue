<template>
  <div class="wrapper">
    <div style="font-size: 50px;margin: 100px auto;text-align: center;color: #676767">
      海瑶壁纸后台管理系统
    </div>
    <div style="margin: 100px auto;background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="user">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>

      </el-form>

      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" autocomlete="off" @click="login">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs.user.validate((valid) => {
        if (valid) {
          this.user.type = 0;
          request.post("/user/login", this.user).then(res => {
            if (res.code == 200) {
              localStorage.setItem("username", res.data.username);
              localStorage.setItem("token", res.data.token);
              localStorage.setItem("headPortrait", res.data.headPortrait);
              localStorage.setItem("nickname", res.data.nickname);
              this.$message.success('登录成功~');
              let pathname = localStorage.getItem("prePath");
              if (pathname) {
                localStorage.removeItem("prePath");
                this.$router.push(pathname);
              } else {
                this.$router.push("/");

              }

            } else {
              this.$message.error(res.msg);
            }
            console.log(res)
          })
        } else {
          return false;
        }
      });

    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  /*background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);*/
  background-image: url("https://gulimall-wangxiwem.oss-cn-hangzhou.aliyuncs.com/2054167.jpg");
  overflow: hidden;
}
</style>