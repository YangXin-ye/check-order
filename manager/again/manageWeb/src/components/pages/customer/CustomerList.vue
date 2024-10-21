<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="搜索信息">
        <el-input placeholder="请输入名称" v-model="name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAllData()">查询</el-button>
        <el-button type="primary" @click="openDialogInsert()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-card class="box-card">
      <el-table :data="records" border style="width: 99%">
        <el-table-column prop="uId" label="编号"  header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="username" label="用户名"  header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="name" label="姓名"  header-align="center" align="center">
        </el-table-column>
        <el-table-column prop="gender" label="性别"  header-align="center" align="center">
        </el-table-column>
        <el-table-column label="操作" width="300" header-align="center" align="center">
          <template slot-scope="scope">
            <el-button type="primary"
                       icon="el-icon-edit"
                       circle size="small"
                       @click="openDialogUpdate(scope.row)">
            </el-button>
            <el-button type="danger"
                       icon="el-icon-delete"
                       circle size="small"
                       @click="deleteUser(scope.row.uId)">
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
    <el-dialog :title="title"
               :visible.sync="dialogFormVisible"
               width="30%"
               :before-close="handleClose">
      <el-form label-width="80px" :model="form">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-button type="success" @click="saveOrUpdate()">{{ title }}</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getUserList, updateUser, insertUser, deleteUser } from '@/api/user'

export default {
  name: 'CutomerList',
  data() {
    return {
      name:'',
      records: [],
      avatarUrl: 'https://yxy-live-share.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20240419210622.jpg',
      pageIndex: 1,
      pageSize: 5,
      total: 0,
      dialogFormVisible: false,
      form: {
        username: '',
        password: '',
        name: '',
        gender: '',
        role: ''
      },
      title: '添加用户'
    }
  },
  mounted() {
    this.getUserList()
  },
  methods: {
    getAllData(){
      this.getUserList()
    },
    getUserList() {
      const params = {
        pageNum: this.pageIndex,
        pageSize: this.pageSize,
        name: this.name
      }
      getUserList({ params }).then(res => {
        this.records = res.data.records
        // 如果图片不存在使用默认路径图片
        this.records.forEach(record => {
          if (record.avatar === null) {
            record.avatar = this.avatarUrl
          }
        })
        // 获取总数据
        this.total = res.data.total

        // 处理特殊情况
        if (res.data.records.length == 0 && this.pageIndex > 1){
          this.pageIndex = this.pageIndex - 1
          this.getUserList()
        }
      })
    },
    openDialog() {
      // 打开窗口
      this.dialogFormVisible = true;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
    },
    openDialogInsert(){
      // 打开窗口
      this.openDialog()
    },
    insertUser() {
      // 打开窗口
      this.openDialog()

      // 发送请求
      insertUser(this.form).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          })
          this.getUserList()
          this.dialogFormVisible = false
        } else {
          this.$message({
            message: '添加失败',
            type: 'error'
          })
        }
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getUserList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getUserList();
    },
    saveOrUpdate() {
      if (this.form.uId) {
        this.updateUser()
      } else {
        this.insertUser()
      }
    },
    openDialogUpdate(record) {
      // 打开窗口
      this.dialogFormVisible = true;
      this.title = '修改用户'
      this.form = {
        ...record
      }
    },
    updateUser() {
      const data = {
        ...this.form
      }
      // 发送修改用户请求
      updateUser(data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.getUserList()
          this.dialogFormVisible = false
        } else {
          this.$message({
            message: '修改失败',
            type: 'error'
          })
        }
      })
    },
    deleteUser(id) {
      alert(id);
      this.$confirm('确定要删除该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(id).then(res => {
          if (res.code === 200) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.getUserList()
          } else {
            this.$message({
              message: '删除失败',
              type: 'error'
            })
          }
        })
      })
    },
  }
}
</script>

<style scoped>

</style>
