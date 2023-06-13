<template>
  <div>
    hello {{ nickname }}！ 欢迎再次使用海瑶壁纸后台管理系统
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "Home",
  data() {
    return {
      username: '',
      headPortrait: '',
      nickname: ''
    }
  },
  created() {

    this.nickname = localStorage.getItem("nickname");
    if (!this.nickname) {
      this.$message.error('请登录~');
      this.$router.push("/login");
    }
  }
}
</script>

<style scoped>

</style>