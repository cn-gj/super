// 导入vue模块
import Vue from 'vue';
// 导入路由模块
import VueRouter from 'vue-router';
// 注册路由
Vue.use(VueRouter);
// 导入路由匹配模块
import router from './router.js';
// 导入axios模块
import axios from 'axios';
axios.defaults.baseURL = 'http://192.168.16.104:8080/'; //龙溪
// axios.defaults.baseURL = 'http://192.167.9.126:8080/'; // 晓港
// axios.defaults.baseURL = 'http://192.168.9.131';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
Vue.prototype.$http = axios;
// 导入vuex
import Vuex from 'vuex'
Vue.use(Vuex)
// 导入element-ui组件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
// 引入bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
// import 'bootstrap/dist/js/bootstrap.min.js';
// 导入app组件
import app from './app.vue';

// // 统一异常处理
// //  REQUEST 请求异常拦截
// axios.interceptors.request.use(config=> {
//     //==========  所有请求之前都要执行的操作  ==============
//     console.log('发送请求')
//     return config;
// }, err=> {
//     //==================  错误处理  ====================
//     Vue.$message({
//         showClose:true,
//         type:'warning',
//         message:'请求超时，请稍后重试'
//     })
//     console.log('请求异常')
//     return Promise.resolve(err);
// })
//
// //  RESPONSE 响应异常拦截
// axios.interceptors.response.use(data=> {
// //==============  所有请求完成后都要执行的操作  ==================
//     // 第二种方式，仅对200和error状态处理
//     if (data.status && data.status == 200 && data.data.status == 'error') {
//
//         return;
//     }
//     return data;
// }, err=> {
// //==============  错误处理  ====================
//     if (err && err.response) {
//         switch (err.response.status) {
//             case 400: err.message = '请求错误(400)'; break;
//             case 401: err.message = '未授权，请重新登录(401)'; break;
//             case 403: err.message = '拒绝访问(403)'; break;
//             case 404: err.message = '请求出错(404)'; break;
//             case 408: err.message = '请求超时(408)'; break;
//             case 500: err.message = '服务器错误(500)'; break;
//             case 501: err.message = '服务未实现(501)'; break;
//             case 502: err.message = '网络错误(502)'; break;
//             case 503: err.message = '服务不可用(503)'; break;
//             case 504: err.message = '网络超时(504)'; break;
//             case 505: err.message = 'HTTP版本不受支持(505)'; break;
//             default: err.message = `连接出错(${err.response.status})!`;
//         }
//     } else {
//         err.message = '连接服务器失败!'
//     }
//     Vue.$message({
//         showClose:true,
//         type:'error',
//         message:err.message
//     })
//     return Promise.resolve(err);
// })

// 创建一个sotre进行公共数据管理
var store = new Vuex.Store({
    state:{
        baseUrl:'http://192.168.16.104:8080/'
    },
    mutations:{

    },
    getters:{

    }
});
// 创建Vue实例
const vue = new Vue({
    el:'#app',
    // 渲染组件
    render:c => c(app),
    // 挂载路由
    router,
    // 挂载store
    store
})