import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/reset.css';
import request from "@/util/request";
Vue.config.productionTip = false

Vue.use(ElementUI,{size:"small"});
Vue.prototype.request = request
var vue = new Vue({
  router,
  render: h => h(App)
}).$mount('#app')


export default vue