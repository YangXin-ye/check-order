import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import axios from './axios';


Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
