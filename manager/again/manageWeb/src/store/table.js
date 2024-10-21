export default {
  state: {
    isCollapse: false, // 控制菜单的状态
    tabsList: [
      // 面包屑数据
      {
        path: "/",
        name: "main",
        label: "首页",
        icon: "s-home",
        url: "FristPage"    // 这里是FristPage.vue中定义的name
      }
    ]
  },
  mutations: {
    collapseChange(state) {
      state.isCollapse = !state.isCollapse
    },
    selectMenu(state, val) {
      if (val.name !== 'main') {
        // 查询 集合数据中 对应名称的数据的索引
        const index = state.tabsList.findIndex(item => item.name === val.name)
        if (index === -1) {
          // 说明不存在
          state.tabsList.push(val)
        }
      }
    },
    closeTag(state,item) {
      // 获取需要移除的数据的索引
      const index = state.tabsList.findIndex(val => val.name === item.name)
      // 如果是首页标签，直接返回，不执行删除操作
      if (item.name === 'main') {
        return;
      }
      // 从集合中移除数据
      if (index !== -1) {
        state.tabsList.splice(index, 1);
      }
    }
  }
}
