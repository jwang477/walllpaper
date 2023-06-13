<template>
  <div>
    <ul style="margin-top: 200px;text-align: center;font-size: 50px;color: #65b1ff">
      <li style="margin-top: 50px">
        <el-button type="text" style="font-size: 50px" @click="userDetail">个人信息</el-button>
      </li>
      <li style="margin-top: 50px;padding: 9px 0"><a href="/favorite"
                                                     style="text-decoration:none;color: #409eff">我的收藏</a></li>
      <li style="margin-top: 50px;padding: 9px 0"><a href="/upload" style="text-decoration:none;color: #409eff">我的壁纸</a>
      </li>
      <li style="margin-top: 50px">
        <el-button type="text" style="font-size: 50px" @click="userComment">我的评论</el-button>
      </li>
    </ul>


    <el-dialog title="我的信息" :visible.sync="userDetailVisible" style="width: 80%;padding-left: 10%">
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
        <el-button @click="userDetailVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCurrentUser">保存</el-button>
      </div>
    </el-dialog>

    <el-dialog title="我的评论" :visible.sync="dialogFormVisible" style="width: 80%;padding-left: 10%">
      <div>
        <el-row :gutter="20">
          <el-col v-for="item in commentList" :span="20" :key="item.commentId"
                  style="padding-left:0px;width:100%" v-show="commentList.length >0">
            <el-card :body-style="{ padding: '0px' }">
              <el-image
                  class="image"
                  :src=item.wallPath
              >
              </el-image>
              <div>
                <div style="float: right;margin-left: 10px;font-size: 18px" v-show="item.status==0">
                  <el-tag type="warning">审核中</el-tag>
                </div>
                <div style="float: right;margin-left: 10px;font-size: 18px" v-show="item.status==2">
                  <el-tag type="warning">审核未通过</el-tag>
                </div>
                <div style="padding: 14px; ">
                  <div class="bottom clearfix">
                    <time class="time">评论时间：{{ item.createTime }}</time>
                  </div>
                </div>
                <div style="padding: 14px; ">
                  <span >评论详情：</span>
                </div>
                <div style="padding: 14px; ">
                  <span style="margin-left: 30px">{{ item.comment }}</span>
                </div>
              </div>
              <div>
                <el-button type="text" class="button" style="margin-right: 10px;font-size: 14px"
                           @click="deleteComment(item.commentId)" > 删除评论
                </el-button>



              </div>
            </el-card>
          </el-col>
          <el-empty description="暂无评论，快去发表第一条评论吧~" v-show="commentList.length ==0"></el-empty>
        </el-row>
      </div>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10]"
          :page-size="pageSize"
          style="margin-top: 20px"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </el-dialog>
  </div>
</template>

<script>

import request from "@/util/request";

export default {
  name: "User",
  data() {
    return {
      userId: -1,
      currentPage: 1,
      total: 0,
      pageSize: 5,
      dialogFormVisible: false,
      userDetailVisible: false,
      commentList: [],
      imgUrl: '',
      currentUser: {},
      oldPassword: '',
      visible:false
    }
  }, created() {
    this.userId = localStorage.getItem("userId");
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
      this.getUserComment()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getUserComment()
    },
    userDetail() {
      this.userDetailVisible = true;
      this.getCurrentUser()

    },
    getCurrentUser() {
      request.get("/user/" + this.userId, {
        params: {}
      }).then(res => {
        if (res.code == 200) {
          this.currentUser = res.data;
          this.imgUrl = this.currentUser.headPortrait;
          this.oldPassword = this.currentUser.password;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    saveCurrentUser() {
      this.userDetailVisible = false;
      let array = new Array();
      array.push(this.currentUser);
      request.post("/user", array).then(res => {
        if (res.code == 200) {
          if (this.currentUser.password !== this.oldPassword) {
            this.$message.success('修改密码重新登录!');
            localStorage.removeItem("username");
            localStorage.removeItem("token");
            localStorage.removeItem("nickname");
            localStorage.removeItem("userId");
            localStorage.setItem("prePath", '/user');
            this.$router.push('/login');
          } else {
            localStorage.setItem("username", this.currentUser.username);
            localStorage.setItem("nickname", this.currentUser.nickname);
            this.$message.success('保存成功!');
          }
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(res, file) {
      this.imgUrl = URL.createObjectURL(file.raw);
      this.currentUser.headPortrait = res.data
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
    userComment(){
      this.dialogFormVisible = true;
      this.getUserComment();
    },
    getUserComment(){
      request.get("/user/comment"  ,{
        params: {
          pageSize:this.pageSize,
          currentPage:this.currentPage
        }
      }).then(res => {
        if (res.code == 200) {
          this.commentList = res.pageInfo.data;
          this.total = res.pageInfo.totalCount;
          this.oldPassword = this.currentUser.password;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    deleteComment(val){
      request.delete("/user/comment/"+val, {

      }).then(res => {
        if (res.code == 200) {
          this.$message.success('删除成功!');
          this.getUserComment()
        }  else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;

}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>