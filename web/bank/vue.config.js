/*
 * @Author: yingnian
 * @Date: 2020-02-20 18:18:29
 */
const path = require('path')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: process.env.VUE_APP_STATIC,
  outputDir: process.env.VUE_APP_ENV === 'production' ? 'prod' : 'test',
  assetsDir: './',
  productionSourceMap: false,
  chainWebpack: config => {
    // 打包分析
    // config
    //   .plugin('webpack-bundle-analyzer')
    //   .use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin)

    config.resolve.alias
      .set('@cp', resolve('src/components'))
      .set('@api', resolve('src/assets/api'))
      .set('@utils', resolve('src/assets/utils'))
      .set('@css', resolve('src/assets/css'))
      .set('@img', resolve('src/assets/img'))
      .set('@config', resolve('src/assets/config')),
      // 小于5kB的图片被base64
      config.module
        .rule('images')
        .use('url-loader')
        .loader('url-loader')
        .tap(options => Object.assign(options, { limit: 5120 }))
  },
  configureWebpack: config => {
    // 删除console.log
    if (process.env.VUE_APP_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions.compress.warnings = false
      config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
      config.optimization.minimizer[0].options.terserOptions.compress.drop_debugger = true
      config.optimization.minimizer[0].options.terserOptions.compress.pure_funcs = ['console.log']
    }

    const productionGzipExtensions = ['html', 'js', 'css']
    config.plugins.push(
      new CompressionWebpackPlugin({
        filename: '[path].gz[query]',
        algorithm: 'gzip',
        test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
        threshold: 10240, // 只有大小大于该值的资源会被处理 10240
        minRatio: 0.8, // 只有压缩率小于这个值的资源才会被处理
        deleteOriginalAssets: false // 删除原文件
      })
    )

    config.optimization = {
      // runtimeChunk: {
      //   name: 'manifest'
      // },
      splitChunks: {
        cacheGroups: {
          libs: {
            name: 'chunk-vendor',
            chunks: 'initial',
            minChunks: 1,
            test: /[\\/]node_modules[\\/]/,
            priority: 1,
            reuseExistingChunk: true,
            enforce: true
          },
          common: {
            name: 'chunk-common',
            chunks: 'initial',
            minChunks: 2,
            maxInitialRequests: 5,
            minSize: 0,
            maxSize: 100000,
            priority: 2,
            reuseExistingChunk: true,
            enforce: true
          },
          vant: {
            name: 'chunk-vant',
            test: /[\\/]node_modules[\\/](vant)[\\/]/,
            chunks: 'all',
            minChunks: 1,
            priority: 3,
            reuseExistingChunk: true,
            enforce: true
          },
          vue: {
            name: 'chunk-vue',
            test: /[\\/]node_modules[\\/](vue|vue-router|vuex)[\\/]/,
            chunks: 'all',
            priority: 3,
            reuseExistingChunk: true,
            enforce: true
          },
          axios: {
            name: 'chunk-axios',
            test: /[\\/]node_modules[\\/](axios)[\\/]/,
            chunks: 'all',
            priority: 3,
            reuseExistingChunk: true,
            enforce: true
          }
        }
      }
    }
  },
  devServer: {
    open: true,
    host: process.env.HOST || '0.0.0.0',
    port: '8089',
    https: false,
    hotOnly: false,
    disableHostCheck: true,
    proxy: {
      '/express': {
        target: 'http://localhost:8089/', // 后端 API 地址
        changeOrigin: true, // 是否更改原始主机头为目标 URL
        pathRewrite: {
          '^/express': '' // 可选，重写路径
        }
      }
    }
  },
  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: [path.resolve(__dirname, 'src/assets/css/vari.less')] // 引入全局样式变量
    }
  },
  css: {
    loaderOptions: {
      postcss: {
        plugins: [
          require('postcss-pxtorem')({
            rootValue: 37.5, // 换算的基数
            selectorBlackList: ['.van'], // 要忽略的选择器并保留为px。
            propList: ['*'], //可以从px更改为rem的属性。
            minPixelValue: 2 // 设置要替换的最小像素值。
          })
        ]
      }
    }
  }
}
