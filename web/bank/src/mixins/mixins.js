/*
 * @Descripttion: 全局混入
 * @Author: jiansheng
 */

const mixin = {
  methods: {
    // 软键盘弹出的时候 隐藏底部footer
    keyWordResize() {
      const screenH = document.body.scrollHeight
      window.onresize = function() {
        if (document.body.scrollHeight < screenH) {
          document.getElementById('footer_container').style.display = 'none'
        } else {
          document.getElementById('footer_container').style.display = 'block'
        }
      }
    },
    // 阻止键盘弹起
    noBomBox(Event) {
      document.activeElement.blur()
    },
    // 错误打印
    logError(text) {
      this.$toast('系统异常，稍后重试！')
      console.error(text)
    }
  }
}

export default mixin
