<template>
  <div  style="height: 100vh;">

    <el-menu
             style="min-height:100%;overflow-x: hidden"
             background-color="#2f4156"
             text-color="#fff"
             :collapse="isCollapse"
             active-text-color="#409EFF"
             :collapse-transition="false"
             router
    >

      <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span slot="title">主页</span>

      </el-menu-item>

      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-message"></i>
          <span slot="title">系统管理</span>
        </template>
        <el-menu-item index="/user" class="el-icon-user"> 用户管理</el-menu-item>
        <el-menu-item index="/category" class="el-icon-document-copy"> 分类管理</el-menu-item>
        <el-menu-item index="/wall" class="el-icon-tickets"> 壁纸管理</el-menu-item>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">审核管理</span>
        </template>
        <el-menu-item index="/wallAudit" class="el-icon-s-tools"> 壁纸审核</el-menu-item>
        <el-menu-item index="/commentAudit" class="el-icon-help"> 评论审核</el-menu-item>
      </el-submenu>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean
  }
}
</script>

<style >

</style>