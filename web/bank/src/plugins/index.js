/*
 * @Descripttion:
 * @Author: jiansheng
 */

import loadingV from './view/loading.vue'

let gLoading = {}
gLoading.installed = false

gLoading.install = (Vue, options = { duration: 2000, position: 'center' }) => {
  if (gLoading.installed) return

  Vue.prototype.$gLoading = {}
  Vue.prototype.$gLoading.show = () => {
    if (document.querySelector('.gloading')) return
    let loadingView = Vue.extend(loadingV)
    let tpl = new loadingView().$mount().$el
    document.body.appendChild(tpl)

    document.querySelector('.gloading').addEventListener('touchmove', function(e) {
      e.stopPropagation()
      e.preventDefault()
    })
    gLoading.installed = true
  }

  Vue.prototype.$gLoading.hide = () => {
    let tpl = document.querySelector('.gloading')
    if (tpl) document.body.removeChild(tpl)
  }
}
export default gLoading
