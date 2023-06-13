<template>
  <div>
    <div>
      <el-input
          placeholder="壁纸名称"
          prefix-icon="el-icon-search"
          style="width: 200px"
          v-model="wallName">
      </el-input>
      <el-select v-model="categoryId" placeholder="分类">
        <el-option
            v-for="item in categoryList"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
        >

        </el-option>
      </el-select>
      <el-select v-model="status" placeholder="壁纸状态">
        <el-option label="审核中" value=0></el-option>
        <el-option label="审核通过" value=1></el-option>
        <el-option label="审核未通过" value=2></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="load" icon="el-icon-search">查询</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="clearSearch" icon="el-icon-refresh-right">清空
      </el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table style="text-align: center"
                :data="tableData"
                tooltip-effect="dark"
      >
        <el-table-column fixed prop="wallName" label="壁纸名称" width="150">
        </el-table-column>
        <el-table-column prop="categoryName" label="分类名称"
                         filter-placement="bottom-end" width="150">
          <template slot-scope="scope">
            <el-tag
                disable-transitions>{{ scope.row.categoryName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="wallPath" label="壁纸" width="240px">
          <template slot-scope="scope">
            <el-image
                style="height:120px "
                :src="scope.row.wallPath"
                :preview-src-list="scope.row.wallPathList"
                fit="fill"></el-image>
          </template>

        </el-table-column>
        <el-table-column prop="wallPath" label="壁纸链接" width="150">
        </el-table-column>
        <el-table-column prop="wallBrief" label="壁纸简介" width="150"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="status" label="审核状态"
                         filter-placement="bottom-end" width="150">
          <template slot-scope="scope">
            <el-tag
                :type="scope.row.status > 0 ? (scope.row.status == 1?'success':'danger') : 'warning'"
                disable-transitions>{{ scope.row.status > 0 ? (scope.row.status == 1?'审核通过':'审核未通过') : '审核中' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150">
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="150">
        </el-table-column>
        <el-table-column prop="createByName" label="作者" width="150">
        </el-table-column>
        <el-table-column label="操作" min-width="180">
          <template slot-scope="scope">
            <el-button type="primary" @click="pass(scope.row)" icon="el-icon-edit" v-show="scope.row.status==0">通过</el-button>
            <el-button type="primary" @click="notPass(scope.row)" icon="el-icon-delete"v-show="scope.row.status==0">不通过</el-button>
            <span v-show="scope.row.status!=0">无需操作</span>
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
      wallName: "",
      categoryId: "",
      status: '',
      categoryList:[]
    }
  },
  created() {
    this.loadCategory();
    this.load();
  },
  methods: {
    loadCategory(){
      request.get("/category/all", {
        params: {

        }
      }).then(res => {
        if (res.code == 200) {
          this.categoryList = res.data;
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }

      })
    },
    load() {
      request.get("/wall", {
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage,
          wallName: this.wallName,
          categoryId: this.categoryId,
          status: this.status
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.load()
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.load()
    },
    pass(val) {

      val.status = 1;
      this.save(val);
    },
    save(val) {
      request.post("/wall", val).then(res => {
        if (res.code == 200) {
          this.$message.success('保存成功!');
          this.load();
        } else if (res.code != 401) {
          this.$message.error(res.msg)
        }
      })
    },
    clearSearch() {
      this.wallName = '';
      this.status = '';
      this.categoryId = '';
      this.load();
    },
    notPass(val){
      val.status = 2;
      this.save(val);
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