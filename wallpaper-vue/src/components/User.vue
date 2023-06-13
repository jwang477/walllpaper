<template>
  <div>
    <div>
      <el-input
          placeholder="用户名"
          prefix-icon="el-icon-search"
          style="width: 200px"
          v-model="username">
      </el-input>
      <el-input
          placeholder="昵称"
          prefix-icon="el-icon-search"
          style="width: 200px;margin-left: 5px"
          v-model="nickname">
      </el-input>
      <el-select v-model="usertype" placeholder="用户类型">
        <el-option label="管理员" value=0></el-option>
        <el-option label="用户" value=1></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="clear" icon="el-icon-refresh-right">清空</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-button type="primary" style="margin-left: 5px" @click="addUser" icon="el-icon-circle-plus-outline">新增
      </el-button>
      <el-button type="danger" style="margin-left: 5px" @click="deleteDanger" icon="el-icon-delete">批量删除</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table style="text-align: center"
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange">
        <el-table-column
            type="selection" prop="userId">
        </el-table-column>
        <el-table-column fixed prop="username" label="用户名" width="150">
        </el-table-column>
        <el-table-column prop="headPortrait" label="头像">
          <template slot-scope="scope" width="100">
            <el-image
                style="width: 100px; height: 100px"
                :src="scope.row.headPortrait"
                fit="fill"></el-image>
          </template>

        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="150">
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150">
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="150">
        </el-table-column>
        <el-table-column prop="type" label="用户类型"
                         filter-placement="bottom-end" width="150">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.type == 0 ? 'primary' : 'success'"
                disable-transitions>{{ scope.row.type == 0 ? '管理员' : '用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="enableFlag" label="是否启用" width="150">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enableFlag "
                active-value="1"
                inactive-value="0"
                disabled>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150">
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="150">
        </el-table-column>
        <el-table-column label="操作" min-width="180">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" @click="deleteOne(scope.row.userId)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <div>


      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" style="width: 50%;padding-left: 25%">
        <el-form :model="user" label-width="40%" :rules="rules" ref="user" label-position="left" size="small">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="user.nickname"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="user.phone"></el-input>
          </el-form-item>
          <el-form-item label="身份证" prop="card">
            <el-input v-model="user.card"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="user.email"></el-input>
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
          <el-form-item label="用户类型" prop="type">
            <el-select v-model="user.type" placeholder="请选择用户类型">
              <el-option label="管理员" value=0></el-option>
              <el-option label="用户" value=1></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否启用" prop="enableFlag">
            <el-switch
                v-model="user.enableFlag"
                active-value="1"
                inactive-value="0"
            >
            </el-switch>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="clearForm">取 消</el-button>
          <el-button type="primary" @click="save(user)">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/util/request";

export default {
  name: "User",
  data() {
    return {
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      multipleTable: [],
      username: "",
      nickname: "",
      usertype: '',
      dialogFormVisible: false,
      selectUserIdList: [],
      user: {},
      imgUrl: '',
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 16, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {
            pattern: '^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$',
            required: true,
            message: '请输入正确的手机号',
            trigger: ['blur']
          }
        ],
        card: [
          {required: true, message: '请输入身份证号', trigger: 'blur'},
          {
            pattern: '^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$',
            required: true,
            message: '请输入正确的身份证号',
            trigger: ['blur']
          }
        ],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur']}
        ],
        type: [
          {required: true, message: '请选择用户类型', trigger: 'change'}
        ]
      }
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/user", {
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage,
          username: this.username,
          nickname: this.nickname,
          type: this.usertype
        }
      }).then(res => {
        if (res.code == 200) {
          this.tableData = res.pageInfo.data;
          this.total = res.pageInfo.totalCount;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    clear() {
      this.username = '';
      this.nickname = '';
      this.usertype = '';
      this.load();
    },
    batchDelete() {

      request.delete("/user", {
        data: this.selectUserIdList
      }).then(res => {
        if (res.code == 200) {
          this.$message.success('删除成功!');
          this.load();
        }  else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    deleteDanger() {
      if (this.selectUserIdList.length < 1) {
        this.$message.warning("至少选择一个~")
      } else {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '我再想想',
          type: 'warning'
        }).then(() => {
          this.batchDelete();
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.load()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.load()
    },
    handleSelectionChange(val) {
      this.selectUserIdList = val.map(list => list.userId);
    },
    deleteOne(val) {

      this.selectUserIdList.push(val);
      this.deleteDanger();
    },
    edit(val) {
      this.user = JSON.parse(JSON.stringify(val));
      this.imgUrl = this.user.headPortrait;
      this.dialogFormVisible = true;
    },
    handleAvatarSuccess(res, file) {
      console.log(file);
      console.log(res);
      this.imgUrl = URL.createObjectURL(file.raw);
      this.user.headPortrait = res.data
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
    save() {
      this.$refs.user.validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false;
          let array = new Array();
          array.push(this.user);
          request.post("/user", array).then(res => {
            if (res.code == 200) {
              this.$message.success('保存成功!');
              this.load();
            }  else if (res.code != 401) {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });

    },
    clearForm() {
      this.imgUrl = '';
      this.dialogFormVisible = false;
      this.user = {};
    },
    addUser() {
      this.imgUrl = '';
      this.dialogFormVisible = true;
      this.user = {};
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