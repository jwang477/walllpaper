<template>
  <div class="el-header" style=" font-size: 12px;display: flex; ">
    <div style="flex: 1;font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
    </div>
    <el-dropdown style="padding-right: 0px ;width: 100px;cursor: pointer">

            <span style="">
               <el-avatar :src="headPortrait" size="small" style="margin-right: 5px;"></el-avatar><i>{{
                nickname
              }}</i>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <el-button type="text" @click="userInfo">个人信息</el-button>
        </el-dropdown-item>
        <el-dropdown-item>
          <el-button type="text" @click="logout">退出</el-button>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-dialog title="用户信息" :visible.sync="userVisible" style="width: 50%;padding-left: 25%">
      <el-form :model="currentUser" label-width="40%" label-position="left" size="small">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="currentUser.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="currentUser.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="currentUser.nickname"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="currentUser.phone"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="card">
          <el-input v-model="currentUser.card"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="currentUser.email"></el-input>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="userVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCurrentUser">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function
  },
  data() {
    return {
      username: "",
      nickname: "",
      headPortrait: '',
      currentUser: {},
      userVisible: false,
      oldPassword: '',
      imgUrl: ''
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.username = localStorage.getItem("username");
      this.headPortrait = localStorage.getItem("headPortrait");
      this.nickname = localStorage.getItem("nickname");
    },
    logout() {
      console.log(this.username)
      this.username = "";
      this.headPortrait = "";
      localStorage.removeItem("username");
      localStorage.removeItem("headPortrait");
      localStorage.removeItem("token");
      localStorage.removeItem("nickname");
      this.$message.success("您已退出，欢迎下次使用~")
      this.$router.push('/login');
    },
    handleAvatarSuccess(res, file) {
      this.imgUrl = URL.createObjectURL(file.raw);
      this.currentUser.headPortrait = res.data
    },
    beforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    saveCurrentUser() {
      this.userVisible = false;
      let array = new Array();
      array.push(this.currentUser);
      request.post("/user", array).then(res => {
        if (res.code == 200) {

          if (this.currentUser.password !== this.oldPassword) {
            this.$message.success('修改密码重新登录!');
            localStorage.removeItem("username");
            localStorage.removeItem("headPortrait");
            localStorage.removeItem("token");
            localStorage.removeItem("nickname");
            this.$router.push('/login');
          } else {
            localStorage.setItem("username", this.currentUser.username);
            localStorage.setItem("headPortrait", this.currentUser.headPortrait);
            localStorage.setItem("nickname", this.currentUser.nickname);
            this.load();
            this.$message.success('保存成功!');
            location.reload();
          }
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })

    },
    userInfo() {
      request.get("/user/userInfo", {
        params: {
          username: this.username
        }
      }).then(res => {
        if (res.code == 200) {
          this.currentUser = res.data;
          this.userVisible = true;
          this.imgUrl = this.currentUser.headPortrait;
          this.oldPassword = this.currentUser.password;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    }
  }

}
</script>

<style>
.el-header {
  /*background-color: #B3C0D1;*/
  color: #333;
  line-height: 60px;
  border-bottom: #ccc 1px solid;
}
</style>