<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card>
          <div slot="header">
            <span>菜单管理</span>
          </div>
          <div>
            <el-button type="primary" @click="saveMenu(0)">新增</el-button>
          </div>
          <div style="height: 20px"></div>
          <el-table
            :data="menuData"
            border
            stripe
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
            :expand-row-keys="expandedRowKeys"
            @expand-change="handleExpandChange"
          >
            <el-table-column type="expand">
              <template #default="{ row }">
                <el-table
                  :data="row.children"
                  border
                  stripe
                  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                >
                  <el-table-column prop="label" label="菜单名称"></el-table-column>
                  <el-table-column prop="icon" label="菜单图标"></el-table-column>
                  <el-table-column prop="path" label="菜单路由"></el-table-column>
                  <el-table-column>
                    <template #default="{ row }">
                      <el-button size="mini" type="text" @click="editMenu(row)">编辑</el-button>
                      <el-button size="mini" type="text" @click="deleteMenu(row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column prop="label" label="菜单名称"></el-table-column>
            <el-table-column prop="icon" label="菜单图标"></el-table-column>
            <el-table-column prop="path" label="菜单路由"></el-table-column>
            <el-table-column>
              <template #default="{ row }">
                <el-button size="mini" type="text" @click="saveMenu(row.id)">新增</el-button>
                <el-button size="mini" type="text" @click="editMenu(row)">编辑</el-button>
                <el-button size="mini" type="text" @click="deleteMenu(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" :model="form">
        <el-form-item label="菜单标识">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="form.label"></el-input>
        </el-form-item>
        <el-form-item label="菜单标签">
          <el-input v-model="form.icon"></el-input>
        </el-form-item>
        <el-form-item label="菜单路径">
          <el-input v-model="form.url"></el-input>
        </el-form-item>
        <el-form-item label="菜单路由">
          <el-input v-model="form.path"></el-input>
        </el-form-item>
        <el-button type="success" @click="saveOrUpdate()">{{ title }}</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { ref } from 'vue';
import { addMenu, deleteMenu, updateMenu, getMenuList ,getChildrenList} from '@/api/menu';

export default {
  data() {
    return {
      menuData: [],
      expandedRowKeys: [], // 存储展开的行的 key
      form: {},
      dialogFormVisible: false,
      title: '',
    };
  },
  mounted() {
    this.getMenuList();
  },
  methods: {
    async getMenuList() {
      const res = await getMenuList();
      if (res.code === 200) {
        this.menuData = res.data;
      } else {
        this.$message({
          type: 'error',
          message: res.message,
        });
      }
    },
    handleExpandChange(row, expanded) {
      if (expanded && !row.childrenLoaded) {
        this.loadChildren(row);
      }
    },
    async loadChildren(row) {
      row.childrenLoaded = true;
      const res = await getChildrenList(row.id);
      if (res.code === 200) {
        row.children = res.data;
      } else {
        this.$message({
          type: 'error',
          message: res.message,
        });
      }
    },
    saveMenu(parentId) {
      this.dialogFormVisible = true;
      this.title = '新增菜单';
      this.form = {
        parent:parentId
      }; // 清空表单数据
    },
    editMenu(data) {
      this.dialogFormVisible = true;
      this.title = '修改菜单';
      this.form = { ...data };
    },
    async deleteMenu(data) {
      this.$confirm('确定删除该菜单吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          const res = await deleteMenu(data.id);
          if (res.code === 200) {
            this.$message({
              type: 'success',
              message: '删除成功!',
            });
            this.getMenuList();
          } else {
            this.$message({
              type: 'error',
              message: res.message,
            });
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          });
        });
    },
    async saveOrUpdate() {
      if (this.title === '新增菜单') {
        const res = await addMenu(this.form);
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '新增成功!',
          });
          this.getMenuList();
        } else {
          this.$message({
            type: 'error',
            message: res.message,
          });
        }
      } else if (this.title === '修改菜单') {
        console.log(this.form);
        const res = await updateMenu(this.form);
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '修改成功!',
          });
          this.getMenuList();
        } else {
          this.$message({
            type: 'error',
            message: res.message,
          });
        }
      }
      this.dialogFormVisible = false;
    },
  },
};
</script>

<style scoped>
.el-table .el-table__header {
  background: #f5f7fa;
}
</style>
