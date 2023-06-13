<template>
  <div>

    <div>
      <el-button type="primary" style="margin-left: 5px" @click="addHandler" icon="el-icon-circle-plus-outline">新增
      </el-button>
    </div>
    <div class="tab" style="margin-top: 10px">
      <el-row :gutter="20">
        <el-col v-for="item in tableData" :span="5" :key="item.wallId" style="height:100%;padding-left:0px;width:25%">
          <el-card :body-style="{ padding: '0px' }">
            <el-tooltip class="item" effect="dark" :content=item.wallBrief placement="left-start">
              <el-image
                  class="image"
                  :src=item.wallPath
                  :preview-src-list="srcList">
              </el-image>
            </el-tooltip>
            <div style="padding: 14px; ">
              <span>{{ item.wallName }}</span>
              <div class="bottom clearfix">
                <span v-show="item.status ==0" style="font-size: 8px"><el-tag  type="warning">审核中</el-tag>等待管理员审核后上架</span>
                <span v-show="item.status ==2" style="font-size: 8px"><el-tag  type="danger">审核未通过</el-tag>重新编辑后提交审核</span>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                           @click="wallComment(item.wallId)"> 查看评论
                </el-button>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                ><a :href="item.wallPath" style="color: #409eff;text-decoration: none;">下载</a>
                </el-button>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                           @click="editHandler(item)">编辑
                </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[4, 8, 16, 20]"
          :page-size="pageSize"
          style="margin-top: 20px"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

    </div>

    <el-dialog title="用户评论" :visible.sync="dialogFormVisible" style="width: 80%;padding-left: 10%">
      <div>
        <el-row :gutter="20">
          <el-col v-for="item in commentList" :span="20" :key="item.commentId"
                  style="padding-left:0px;width:100%" v-show="commentList.length >0">
            <el-card :body-style="{ padding: '0px' }">
              <div style="height:50px;line-height: 50px">
                <el-avatar :src="item.headPortrait" style="margin-top: 5px;margin-left: 10px;float: left"></el-avatar>
                <div style="float: left;margin-left: 10px;font-size: 18px"><span>{{ item.nickname }}</span></div>
                <div style="float: right;margin-left: 10px;font-size: 18px" v-show="item.status==0">
                  <el-tag type="warning">审核中</el-tag>
                </div>
              </div>
              <div style="padding: 14px; ">
                <span style="margin-left: 30px">{{ item.comment }}</span>
              </div>
              <div style="padding: 14px; ">
                <div class="bottom clearfix">
                  <time class="time">{{ item.createTime }}</time>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-empty description="暂无评论，快来发表第一条评论吧~" v-show="commentList.length ==0"></el-empty>
        </el-row>
      </div>
      <el-pagination
          @size-change="commentHandleSizeChange"
          @current-change="commentHandleCurrentChange"
          :current-page="commentCurrentPage"
          :page-sizes="[5, 10]"
          :page-size="commentPageSize"
          style="margin-top: 20px"
          layout="total, sizes, prev, pager, next, jumper"
          :total="commentTotal">
      </el-pagination>
      <div style="margin-top: 10px">
        <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入你的评论,你的评论会被审核,请文明发言~"
            v-model="comment">
        </el-input>
        <el-button type="primary" style="float: right;margin-top: 10px" round @click="releaseCommand">发布评论</el-button>
      </div>
      <div style="height: 30px;"></div>
    </el-dialog>

    <el-dialog :title="wallTitle" :visible.sync="wallDetailVisible" style="width:100%;">
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
            <i v-else class=" el-icon-plus avatar-uploader-icon2" style="line-height: 360px;"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" v-model="wall.wallBrief" :autosize="{ minRows: 2, maxRows: 5} "
                    style="width: 600px"></el-input>
        </el-form-item>
        <el-form-item label="排名">
          <el-input type="number" v-model="wall.sort" style="width: 100px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">保存</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>

import request from "@/util/request";

export default {
  name: "Upload",
  data() {
    return {
      isLogin: false,
      user: {},
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 8,
      srcList: [],
      sort: '',
      categoryId: '',
      keywords: '',
      categoryList: [],
      dialogFormVisible: false,
      commentList: [],
      commentCurrentPage: 1,
      commentTotal: 0,
      commentPageSize: 8,
      wallId: '',
      comment: '',
      userComment: {},
      wall: {},
      wallDetailVisible: false,
      wallTitle: '',
      imgUrl: ''
    }
  },
  created() {
    this.load()
    this.loadCategory()

  },
  methods: {
    checkLogin() {
      let token = localStorage.getItem("token");
      if (token) {
        this.isLogin = true;
      }
    },
    load() {
      this.checkLogin();
      if (!this.isLogin) {
        localStorage.setItem("prePath", "/favorite");
        this.$message.error("您还未登录，请先登录~")
        this.$router.push("/login")
      } else {
        request.get("/user/wall", {
          params: {
            pageSize: this.pageSize,
            currentPage: this.currentPage
          }
        }).then(res => {
          if (res.code == 200) {
            this.tableData = res.pageInfo.data;
            this.total = res.pageInfo.totalCount;
            this.srcList = this.tableData.map(list => list.wallPath);
          } else if (res.code != 401) {
            this.$message.error(res.msg)
          }

        })
      }
    },
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.load()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.load()
    },
    commentHandleSizeChange(val) {
      this.commentPageSize = val;
      this.wallComment(this.wallId)
    },
    commentHandleCurrentChange(val) {
      this.commentCurrentPage = val;
      this.wallComment(this.wallId)
    },
    wallComment(val) {
      this.wallId = val;
      this.dialogFormVisible = true;
      request.get("/wall/comment/" + val, {
        params: {}
      }).then(res => {
        if (res.code == 200) {
          this.commentList = res.pageInfo.data;
          this.commentTotal = res.pageInfo.totalCount;

        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    releaseCommand() {
      if (!this.comment) {
        this.$message.warning("请输入您的评论~")
      } else {
        if (!this.isLogin) {
          localStorage.setItem("prePath", "/home");
          this.$message.error("您还未登录，请先登录~")
          this.$router.push("/login")
        } else {
          this.userComment.comment = this.comment;
          this.userComment.wallId = this.wallId;
          this.userComment.status = 0;
          request.post("/wall/comment", this.userComment).then(res => {
            if (res.code == 200) {
              this.$message.success('发布成功!');
              this.comment = ''
              this.wallComment(this.wallId);
            } else if (res.code != 401) {
              this.$message.error(res.msg)
            }
          })
        }
      }

    },
    addHandler() {
      this.wallTitle = '壁纸上传'
      this.wallDetailVisible = true;
      this.wall = {};
      this.imgUrl ='';
    },
    editHandler(val) {
      this.wallDetailVisible = true;
      this.wallTitle = '壁纸信息'
      console.log(val)
      this.wall = val;
      this.imgUrl = this.wall.wallPath;
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
      if (this.wall.wallId ==null){
        this.wall.status = 0;
      }
      if (this.wall.wallId !=null && this.wall.status ==2){
        this.wall.status = 0;
      }
      request.post("/wall", this.wall).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功!');
          this.wallDetailVisible = false;
          this.load();
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
  width: 640px;
  height: 360px;
  text-align: center;
}

.avatar-detail {
  width: 640px;
  height: 360px;
  display: block;
}
</style>