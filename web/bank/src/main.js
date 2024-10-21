import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vconsole from 'vconsole'
import 'lib-flexible/flexible.js'
import mixin from './mixins/mixins'
import VueLazyLoad from 'vue-lazyload'
import 'default-passive-events'
import { GetQueryValue } from '@utils'

Vue.config.productionTip = false
Vue.mixin(mixin)

import '../src/assets/css/preset.less'
import '../src/assets/css/flex.less'

// 自定义的简单的 loading
import gLoading from './plugins'
Vue.use(gLoading)

import Vant from 'vant'
import 'vant/lib/index.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(Vant)

// 图片懒加载
// Vue.use(VueLazyLoad, {
//   loading: require('./assets/img/placeholder5.png'),
//   error: require('./assets/img/error2.png')
// })

if (GetQueryValue('log') == '1' || process.env.VUE_APP_ENV !== 'production') {
  // new Vconsole()
}


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
