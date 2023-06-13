<template>
  <div>
    <el-form ref="form" :model="wall" size="mini">
      <el-form-item label="壁纸名称">
        <el-input v-model="wall.wallName" style="width:500px"></el-input>
      </el-form-item>
      <el-form-item label="活动区域">
        <el-select v-model="wall.categoryId" placeholder="请选择分类">
          <el-option
              v-for="item in categoryList"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="壁纸" v-model="wall.wallPath">
        <el-upload
            class="avatar-uploader-detail"
            action="http://localhost:9090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <img v-if="this.imgUrl" :src="this.imgUrl" class="avatar-detail">
          <i v-else class=" el-icon-plus avatar-uploader-icon2" style="line-height: 540px;"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="简介">
        <el-input type="textarea" v-model="wall.wallBrief" :autosize="{ minRows: 2, maxRows: 5} "
                  style="width: 960px"></el-input>
      </el-form-item>
      <el-form-item label="排名">
        <el-input type="number" v-model="wall.sort" style="width: 100px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "User",
  data() {
    return {
      wall: {},
      categoryList: [],
      imgUrl: ''
    }
  },
  created() {

    let param = window.location.search;
    console.log(param)

    if (param.concat("?")) {
      let str = param.replace("?", "")
      this.load(str);
    } else {
      this.wall = {};
    }
    this.loadCategory();
  },
  methods: {
    loadCategory() {
      request.get("/category/all", {
        params: {}
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    load(str) {
      request.get("/wall/" + str, {}).then(res => {
        if (res.code == 200) {
          if (res.data) {
            this.wall = res.data;
            this.imgUrl = this.wall.wallPath;
          }
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    handleAvatarSuccess(res, file) {
      this.imgUrl = URL.createObjectURL(file.raw);
      this.wall.wallPath = res.data
    },
    beforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
      const isLt10M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传壁纸图片只能是 JPG 格式或 PNG 格式！');
      }
      if (!isLt10M) {
        this.$message.error('上传壁纸图片大小不能超过 10MB!');
      }
      return isJPG && isLt10M;
    },
    save() {
      this.wall.status = 1;
      request.post("/wall", this.wall).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功!');
          // this.load(res.data.wallId);
          this.$router.push("/wallDetail?" + res.data.wallId)
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>


.avatar-uploader-detail .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader-detail .el-upload:hover {
  border-color: #409EFF;
}


.avatar-uploader-icon2{
  font-size: 28px;
  color: #8c939d;
  width: 960px;
  height: 540px;
  text-align: center;
}

.avatar-detail {
  width: 960px;
  height: 540px;
  display: block;
}

</style>