<template>
  <div class="home">
    <el-container >

      <el-aside :width="asideSize +'px'" >
        <Aside :isCollapse= "isCollapse"></Aside>
      </el-aside>
      <el-container>
        <el-header >

          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>

        </el-header>

        <el-main>
          <router-view/>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>
<style>

</style>
<script>
// @ is an alias to /src

import Aside from "@/components/Aside";
import Header from "@/components/Header";
export default {
  name: 'HomeView',
  components: {
    Aside,
    Header
  },
  data() {

    return {

      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      asideSize: 200,
    }
  },
  created() {
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.asideSize = 64;
        this.collapseBtnClass = "el-icon-s-unfold";
      } else {
        this.asideSize = 200;
        this.collapseBtnClass = "el-icon-s-fold";
      }
    }

  }
}
</script>
