<template>
  <div>
    <div>
      <el-input
          placeholder="壁纸类别"
          prefix-icon="el-icon-search"
          style="width: 200px"
          v-model="categoryName">
      </el-input>
      <el-select v-model="enableFlag" placeholder="是否有效">
        <el-option label="有效" value=1></el-option>
        <el-option label="无效" value=0></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="search" icon="el-icon-search">查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="clear" icon="el-icon-refresh-right">清空</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-button type="primary" style="margin-left: 5px" @click="addCategory" icon="el-icon-circle-plus-outline">新增
      </el-button>
      <el-button type="danger" style="margin-left: 5px" @click="deleteDanger" icon="el-icon-delete">批量删除</el-button>
    </div>


    <div style="margin-top: 10px">
      <el-table style="text-align: center"
                :data="tableData"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange">
        <el-table-column
            type="selection" prop="categoryId">
        </el-table-column>
        <el-table-column prop="categoryName" label="壁纸类别名称">
        </el-table-column>

        <el-table-column prop="enableFlag" label="是否启用">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.enableFlag "
                active-value="1"
                inactive-value="0"
                disabled>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间">
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" @click="deleteOne(scope.row.categoryId)" icon="el-icon-delete">删除</el-button>
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

      <el-dialog title="壁纸分类信息" :visible.sync="dialogFormVisible" style="width: 50%; padding-left: 25%">
        <el-form :model="category" label-width="50%" label-position="left" size="small" >
          <el-form-item label="壁纸分类名称" prop="username">
            <el-input v-model="category.categoryName"></el-input>
          </el-form-item>
          <el-form-item label="是否启用" prop="enableFlag">
            <el-switch
                v-model="category.enableFlag"
                active-value="1"
                inactive-value="0"
            >
            </el-switch>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="clearForm">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>


</template>

<script>
import request from "@/util/request";

export default {
  name: "Category",
  data() {
    return {
      categoryName: '',
      enableFlag: '',
      total: 0,
      pageSize: 5,
      currentPage: 1,
      tableData: [],
      categoryIdList: [],
      dialogFormVisible: false,
      category: {}
    }
  },
  created() {
    this.search();
  },
  methods: {
    search() {
      request.get("/category", {
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage,
          categoryName: this.categoryName,
          enableFlag: this.enableFlag,
        }
      }).then(res => {
        if (res.code ==200){
          this.tableData = res.pageInfo.data;
          this.total = res.pageInfo.totalCount;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    clear() {
      this.categoryName = '';
      this.enableFlag = '';
      this.search();
    },
    batchDelete() {

      request.delete("/category", {
        data: this.categoryIdList
      }).then(res => {
        if (res.code == 200) {
          this.$message.success('删除成功!');
          this.search();
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })

    },
    deleteDanger() {
      if (this.categoryIdList.length < 1) {
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
      this.search()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search()
    },

    handleSelectionChange(val) {
      this.categoryIdList = val.map(list => list.categoryId);
    },

    deleteOne(val) {
      this.categoryIdList = [];
      this.categoryIdList.push(val);
      this.deleteDanger();
    },
    edit(val) {
      this.category = JSON.parse(JSON.stringify(val));
      this.dialogFormVisible = true;
    },
    clearForm() {
      this.dialogFormVisible = false;
      this.category = {};
    },
    save() {
      this.dialogFormVisible = false;
      let array = new Array();
      array.push(this.category);
      request.post("/category", array).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功!');
          this.search();
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    addCategory(){
      this.dialogFormVisible = true;
      this.category = {};
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