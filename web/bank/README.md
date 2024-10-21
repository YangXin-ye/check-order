# vue2 + vant h5 模板

### 前置

- vscode
- 安装插件
  - Prettier - Code formatter
  - Vuter

### 运行

```
npm install

npm run dev
```

### 打包

```bash
# 生产环境
# 打包完成后输出到 /prod 目录

npm run build

# 测试环境
# 打包完成后输出到 /test 目录

npm run tbuild
```

### 别名

- 在 vue.config.js 中增加部分文件的别名

```
.set('@cp',   resolve('src/components'))
.set('@api',  resolve('src/assets/api'))
.set('@utils', resolve('src/assets/utils'))
.set('@css',  resolve('src/assets/css'))
.set('@img',  resolve('src/assets/img'))
```

### 环境的区分

```
// src/assets/api/axios
// 所有api接口的请求基础url在此配置
// 同级可建文件向外导出接口，参照api/index.js
switch(process.env.ENV) {
    case 'production':
    baseRequestUrl = 'https://www.baidu.com'
    break
    case 'test':
    baseRequestUrl = 'https://uat.baidu.com'
    break
    case 'development':
    baseRequestUrl = "https://uat.baidu.com"
    break
}
```

### 组件库

- 组件的话采用了 vant 的组件库
  - 全局引入了 使用的时候不再需要在文件中单独引用

### js 工具库

- 时间相关的使用 dayjs
- 其他工具函数使用 lodash-es

### loading/toast 单独编写

```js
// vant的Toast组件调用方式发生变化，现在改为
this.$toast('测试')

// loading单独写了一个
this.$gLoading.show()
this.$gLoading.hide()
```

> https://youzan.github.io/vant/#/zh-CN/button vant 官网地址

### css 单位配置

- css Rem 的配置
- 采用 postcss-pxtorem + lib-flexible
- 设计图为 375px
- 编写样式的时候直接写 px 即可

### 图片懒加载 `vue-lazyload`

```html
<!-- backgroundImgae方式 -->
<div v-lazy:background-image="imgUrl"></div>
<!-- img方式 -->
<img v-lazy="imgUrl" :key="" alt="" />
```
