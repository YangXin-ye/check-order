<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="搜索备注">
        <el-input placeholder="请输入备注" v-model="remark"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="type" placeholder="请选择">
          <el-option label="全部" :value="0"></el-option>
          <el-option label="支出" :value="1"></el-option>
          <el-option label="收入" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAllData()">查询</el-button>
      </el-form-item>
    </el-form>

    <el-card class="box-card">
      <el-table :data="records" border style="width: 99%">
        <el-table-column prop="id" label="编号" header-align="center" align="center"></el-table-column>
        <el-table-column prop="userName" label="用户名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="classifyName" label="分类名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.type === 1 ? '支出' : '收入' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="金额" header-align="center" align="center"></el-table-column>
        <el-table-column prop="remark" label="备注" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" header-align="center" align="center"></el-table-column>
        <el-table-column label="操作" width="300" header-align="center" align="center">
          <template slot-scope="scope">
            <el-button type="primary" @click="restoreData(scope.row)">
              恢复数据
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
  </div>
</template>

<script>
import { getDelAccountionRecords,restoreData } from '@/api/accountionRecord';

export default {
  name: 'AccountionRecordList',
  data() {
    return {
      type: '', // 收支类型
      remark: '', // 备注
      records: [], // 数据列表
      pageIndex: 1, // 当前页
      pageSize: 5, // 每页显示数
      total: 0 // 总数据量
    }
  },
  mounted() {
    this.getAllData(); // 初始化获取数据
  },
  methods: {
    getAllData() {
      this.getAccountionRecordList();
    },
    getAccountionRecordList() {
      const params = {
        pageNum: this.pageIndex,
        pageSize: this.pageSize,
        type: this.type,
        remark: this.remark
      };
      getDelAccountionRecords({ params }).then(res => {
        this.records = res.data.records;
        this.total = res.data.total;

        // 如果数据为空且当前页码大于1时，减少页码重新加载数据
        if (res.data.records.length === 0 && this.pageIndex > 1) {
          this.pageIndex--;
          this.getAccountionRecordList();
        }
      });
    },
    restoreData(row){
      console.log(row);
      let id = row.id;
      restoreData(id).then(res=>{
        this.$message({ message: '恢复成功', type: 'success' });
        this.getAllData();
      })
    },
    // 每页数改变
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.getAccountionRecordList();
    },
    // 当前页改变
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getAccountionRecordList();
    }
  }
}
</script>

<style scoped>
</style>
