<template>
  <div>
    <div class="wrapper">
      <div style="font-size: 50px;margin: 30px auto;text-align: center;color: #676767">
        海瑶壁纸
      </div>
      <div style="margin: 100px auto;background-color: #fff;width: 300px;height: 600px;padding: 20px;border-radius: 10px">
        <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>用 户 注 册</b></div>
        <el-form :model="user" label-width="40%"  :rules="rules" label-position="left" size="small">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="user.nickname"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="user.phone"></el-input>
          </el-form-item>
          <el-form-item label="身份证" prop="card">
            <el-input v-model="user.card"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="user.email"></el-input>
          </el-form-item>

          <el-form-item label="头像" prop="headPortrait">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="this.imgUrl" :src="this.imgUrl" class="avatar">
              <i v-else class=" el-icon-plus avatar-uploader-icon" style="line-height: 120px;"></i>
            </el-upload>
          </el-form-item>

        </el-form>
        <div style="margin: 10px 0; text-align: center">
          <el-button type="primary" autocomlete="off" style="width: 100px" @click="register">注册</el-button>
        </div>
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
      imgUrl:'',
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 16, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {
            pattern: '^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$',
            required: true,
            message: '请输入正确的手机号',
            trigger: ['blur']
          }
        ],
        card: [
          {required: true, message: '请输入身份证号', trigger: 'blur'},
          {
            pattern: '^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$',
            required: true,
            message: '请输入正确的身份证号',
            trigger: ['blur']
          }
        ],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur']}
        ],
        type: [
          {required: true, message: '请选择用户类型', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imgUrl = URL.createObjectURL(file.raw);
      this.user.headPortrait = res.data
    },
    beforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式 或 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    register() {
      this.user.type=1;
      this.user.enableFlag=1;
      request.post("/user/register", this.user).then(res => {
        if (res.code == 200) {
          localStorage.setItem("username", res.data.username);
          localStorage.setItem("token", res.data.token);
          localStorage.setItem("nickname", res.data.nickname);
          localStorage.setItem("userId", res.data.userId);
          this.$message.success('注册成功~');
          this.$router.push("/");
        } else {
          this.$message.error(res.msg);
        }
        console.log(res)
      })
    }
  }
}
</script>

<style >
.wrapper {
  /*height: 100vh;*/
  /*background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);*/
  /*background-image: url("https://haiyao-wall.oss-cn-hangzhou.aliyuncs.com/1001540.jpg");*/
  /*overflow: hidden;*/
}
</style>