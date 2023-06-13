<template>
  <div>

    <div>
      <el-input
          placeholder="壁纸名称"
          prefix-icon="el-icon-search"
          style="width: 200px"
          v-model="keywords">
      </el-input>

      <el-select v-model="categoryId" placeholder="分类">
        <el-option
            v-for="item in categoryList"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
        ></el-option>
      </el-select>
      <el-select v-model="sort" placeholder="排序方式">
        <el-option label="推荐" value="sort"></el-option>
        <el-option label="最新" value="create_time"></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" icon="el-icon-search" @click="load">查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="clear" icon="el-icon-refresh-right">清空</el-button>
    </div>


    <div class="tab" style="margin-top: 10px">
      <el-row :gutter="20">
        <el-col v-for="item in tableData" :span="5" :key="item.wallId" style="height:100%;padding-left:0px;width:25%">
          <el-card :body-style="{ padding: '0px' }">
            <el-tooltip class="item" effect="dark" :content=item.wallBrief placement="left-start">
              <el-image
                  class="image"
                  style="height: 260px;"
                  :src=item.wallPath
                  :preview-src-list="srcList">
              </el-image>
            </el-tooltip>
            <div style="padding: 14px; ">
              <span>{{ item.wallName }}</span>
              <div class="bottom clearfix">
                <time class="time">上架时间：{{ item.createTime }}
                  <br/>
                  作品作者：{{ item.createByName }}
                </time>

                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                           v-if="item.favorite ==0" @click="favoriteWall(item)">收藏
                </el-button>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                           @click="cancelFavoriteWall(item.favoriteId)" v-else>已收藏
                </el-button>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                           @click="wallComment(item.wallId)"> 查看评论
                </el-button>
                <el-button type="text" class="button" style="margin-left: 10px;font-size: 14px"
                ><a :href="item.wallPath" style="color: #409eff;text-decoration: none;">下载</a>
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
          <el-empty description="暂无评论，快来发表第一条评论吧~" v-show="commentList.length ==0" ></el-empty>
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
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "Home",
  data() {
    return {
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 8,
      srcList: [],
      sort: '',
      categoryId: '',
      keywords: '',
      categoryList: [],
      isLogin: false,
      dialogFormVisible: false,
      commentList: [],
      commentCurrentPage: 1,
      commentTotal: 0,
      commentPageSize: 8,
      wallId: '',
      comment: '',
      userComment: {}
    }
  },
  created() {
    this.load();
    this.loadCategory();
    this.checkLogin();
  },
  methods: {
    checkLogin() {
      let token = localStorage.getItem("token");
      if (token) {
        this.isLogin = true;
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
    load() {
      request.get("/wall/font", {
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage,
          sort: this.sort,
          categoryId: this.categoryId,
          keywords: this.keywords
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
    },
    clear() {
      this.sort = '';
      this.categoryId = '';
      this.keywords = '';
      this.load();
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
    favoriteWall(val) {
      if (!this.isLogin) {
        localStorage.setItem("prePath", "/home");
        this.$message.error("您还未登录，请先登录~")
        this.$router.push("/login")
      } else {
        request.post("/wall/favorite", val).then(res => {
          if (res.code == 200) {
            this.$message.success('已收藏!');
            this.load();
          } else if (res.code != 401) {
            this.$message.error(res.msg)
          }
        })
      }
    },
    cancelFavoriteWall(val) {
      if (!this.isLogin) {
        localStorage.setItem("prePath", "/home");
        this.$message.error("您还未登录，请先登录~")
        this.$router.push("/login")
      } else {
        request.delete("/wall/favorite", {
          data: val
        }).then(res => {
          if (res.code == 200) {
            this.$message.success('已取消收藏!');
            this.load();
          } else if (res.code != 401) {
            this.$message.error(res.msg)
          }
        })
      }
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
      }else{
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
              this.comment=''
              this.wallComment(this.wallId);
            } else if (res.code != 401) {
              this.$message.error(res.msg)
            }
          })
        }
      }

    }
  }
}

</script>

<style>

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>