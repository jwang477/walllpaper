<template>
  <div>
    <div class="line"></div>
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#fff"
        router
        style="height:60px;"
        text-color="#545c64"
        active-text-color="#4691d9">
      <img src="../assets/favicon.png"
           style="display: block;float: left; width: 40px ;margin-top: 10px;margin-left: 30px"/>
      <span style="float: left;line-height: 60px;margin-left: 10px;font-size: 20px;font-weight: 600">海瑶壁纸</span>

      <el-menu-item index="/home" style="margin-left: 200px;font-size: 16px;line-height:60px;"><i
          class="el-icon-s-home"></i>首页
      </el-menu-item>
      <el-menu-item index="/favorite" style="font-size: 16px;line-height:60px;"><i class="el-icon-collection-tag"></i>我的收藏
      </el-menu-item>
      <el-menu-item index="/upload" style="font-size: 16px;line-height:60px;"><i class="el-icon-upload"></i>我的上传
      </el-menu-item>
      <el-menu-item index="/register" style="float: right;font-size: 16px" v-show="!isLogin"><i
          class="el-icon-s-promotion"></i>注册
      </el-menu-item>
      <el-menu-item index="/login" style="float: right;font-size: 16px" v-show="!isLogin"><i
          class="el-icon-s-promotion"></i>登录
      </el-menu-item>
      <el-button type="text" v-show="isLogin"
                 style="float: right;color:#545c64; line-height:60px; margin-right:30px;padding: 0 0;font-size: 16px"
                 @click="logout">
        <i class="el-icon-switch-button"></i>退出登录
      </el-button>
      <el-menu-item index="/user" style="float: right;font-size: 16px;margin-right:20px;" v-show="isLogin"><i
          class="el-icon-user"></i>个人中心
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      activeIndex: '',
      isLogin: false,
      user: {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      let path = window.location.pathname;
      this.activeIndex = path;
      let token = localStorage.getItem("token");
      if (token) {
        this.isLogin = true;
      }
    },
    logout() {
      localStorage.removeItem("username");
      localStorage.removeItem("userId");
      localStorage.removeItem("token");
      localStorage.removeItem("nickname");
      this.$message.success("您已退出，欢迎下次使用~")
      this.load();
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>

</style>