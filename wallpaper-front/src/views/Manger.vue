<template>
  <div class="home">
    <el-header style="width: 100%;padding: 0px 0px">
      <Header></Header>
    </el-header>

    <el-main style="width: 100%">
      <router-view/>
    </el-main>
  </div>
</template>
<style>

</style>
<script>
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  components: {
    Header
  },
  data() {

    return {}
  },
  created() {
  },
  methods: {}
}
</script>
