<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="搜索分类">
        <el-input placeholder="请输入分类名称" v-model="name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAllData()">查询</el-button>
        <el-button type="primary" @click="openDialogInsert()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-card class="box-card">
      <el-table :data="records" border style="width: 99%">
        <el-table-column prop="id" label="编号" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="name" label="分类名称" header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.type === 1 ? '支出' : '收入' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" header-align="center" align="center">
        </el-table-column>
        <el-table-column label="操作" width="300" header-align="center" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle size="small" @click="openDialogUpdate(scope.row)">
            </el-button>
            <el-button type="danger" icon="el-icon-delete" circle size="small" @click="deleteClassify(scope.row.id)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[5, 10, 20, 50, 100]"
        :page-size="pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top:30px;">
      </el-pagination>
    </el-card>

    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="30%" :before-close="handleClose">
      <el-form label-width="80px" :model="form">
        <el-form-item label="分类名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio :label="1">支出</el-radio>
            <el-radio :label="2">收入</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-button type="success" @click="saveOrUpdate()">{{ title }}</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getClassifyList, insertClassify, updateClassify, deleteClassify } from '@/api/classify'

export default {
  name: 'ClassifyList',
  data() {
    return {
      name: '',
      records: [],
      pageIndex: 1,
      pageSize: 5,
      total: 0,
      dialogFormVisible: false,
      form: {
        id: '',
        name: '',
        type: 1
      },
      title: '添加分类'
    }
  },
  mounted() {
    this.getClassifyList()
  },
  methods: {
    getAllData(){
      this.getClassifyList()
    },
    getClassifyList() {
      const params = {
        pageNum: this.pageIndex,
        pageSize: this.pageSize,
        name: this.name
      }
      getClassifyList({ params }).then(res => {
        this.records = res.data.records
        this.total = res.data.total

        // 如果当前页没有数据，返回上一页
        if (res.data.records.length == 0 && this.pageIndex > 1){
          this.pageIndex = this.pageIndex - 1
          this.getClassifyList()
        }
      })
    },
    openDialog() {
      // 打开对话框
      this.dialogFormVisible = true;
    },
    openDialogInsert() {
      this.form = { id: '', name: '', type: 1 }; // 重置表单
      this.title = '添加分类';
      this.openDialog();
    },
    openDialogUpdate(record) {
      // 打开窗口并填充数据
      this.form = { ...record };
      this.title = '修改分类';
      this.dialogFormVisible = true;
    },
    saveOrUpdate() {
      if (this.form.id) {
        this.updateClassify();
      } else {
        this.insertClassify();
      }
    },
    insertClassify() {
      insertClassify(this.form).then(res => {
        if (res.code === 200) {
          this.$message({ message: '添加成功', type: 'success' });
          this.getClassifyList();
          this.dialogFormVisible = false;
        } else {
          this.$message({ message: '添加失败', type: 'error' });
        }
      })
    },
    updateClassify() {
      updateClassify(this.form).then(res => {
        if (res.code === 200) {
          this.$message({ message: '修改成功', type: 'success' });
          this.getClassifyList();
          this.dialogFormVisible = false;
        } else {
          this.$message({ message: '修改失败', type: 'error' });
        }
      })
    },
    deleteClassify(id) {
      this.$confirm('确定要删除该分类吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteClassify(id).then(res => {
          if (res.code === 200) {
            this.$message({ message: '删除成功', type: 'success' });
            this.getClassifyList();
          } else {
            this.$message({ message: '删除失败', type: 'error' });
          }
        })
      })
    },
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getClassifyList();
    },
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getClassifyList();
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(_ => done());
    }
  }
}
</script>

<style scoped>
</style>
