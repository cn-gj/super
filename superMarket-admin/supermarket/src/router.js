/*
 * 路由管理模块
 */

// 导入路由模块
import VueRouter from 'vue-router';

// 测试组件
import test from './components/start/test.vue';
// 导入start模块组件
import login from './components/start/login.vue';
import home from  './components/start/home.vue';
import welcome from './components/start/welcome.vue';
// 导入shop模块组件
import shopInfo from './components/shop/shopInfo.vue';
import shopList from './components/shop/shopList.vue';
import shopType from './components/shop/shopType.vue';
import empMgr from './components/shop/empMgr.vue';
// 导入goods模块组建
import goodsList from './components/goods/goodsList.vue';
import goodsType from './components/goods/goodsType.vue';
import goodsSpec from './components/goods/goodsSpec.vue';
// 导入member模块
import memberList from './components/member/memberList.vue';
import memberLevel from './components/member/memberLevel.vue';
// 导入cashierDest 模板
import cashierDesk from './components/cashierDesk/cashierDeskList.vue';
import ticket from './components/cashierDesk/ticket.vue';
// 导入purchase 模块
import providerList from './components/purchase/providerList.vue';
import orderList from './components/purchase/orderList.vue';
import invoiceList from './components/purchase/invoiceList.vue';
import rollbackList from './components/purchase/rollbackList.vue';
// 导入wholesale模块
import customerList from './components/wholesale/customerList.vue';
import wholeOrderList from './components/wholesale/wholeOrderList.vue';
import wholeInvoiceList from './components/wholesale/wholeInvoiceList.vue';
import wholeRollbackList from './components/wholesale/wholeRollbackList.vue';
// 导入store模块
import storeList from './components/store/storeList.vue';
import inventoryCheck from './components/store/inventoryCheck.vue';
// 导入summary模块
import shouyin from './components/summary/shouyin.vue';
const router = new VueRouter({
  //  mode:'history',
    // 匹配规则
    routes:[
        {path:'/',redirect:'/login'},
        {
            path:'/login',
            component:login,
            name:'login',
            meta:{
                // 表示不被拦截验证
                noAuth:true
            }
        },
        {
            path:'/home',
            component:home,
            name:'home',
            children:[
                {path:'test',component:test,name:'test'},
                {path:'welcome',component:welcome,name:'welcome'},
                {path:'shopInfo',component:shopInfo,name:'shopInfo'},
                {path:'shopList',component:shopList,name:'shopList'},
                {path:'shopType',component:shopType,name:'shopType'},
                {path:'empMgr',component:empMgr,name:'empMgr'},
                {path:'goodsList',component:goodsList,name:'goodsList'},
                {path:'goodsType',component:goodsType,name:'goodsType'},
                {path:'goodsSpec',component:goodsSpec,name:'goodsSpec'},
                {path:'memberList',component:memberList,name:'memberList'},
                {path:'memberLevel',component:memberLevel,name:'memberLevel'},
                {path:'cashierDesk',component:cashierDesk,name:'cashierDesk'},
                {path:'ticket',component:ticket,name:'ticket'},
                {path:'providerList',component:providerList,name:'providerList'},
                {path:'orderList',component:orderList,name:'orderList'},
                {path:'invoiceList',component:invoiceList,name:'invoiceList'},
                {path:'rollbackList',component:rollbackList,name:'rollbackList'},
                {path:'customerList',component:customerList,name:'customerList'},
                {path:'wholeOrderList',component:wholeOrderList,name:'wholeOrderList'},
                {path:'wholeInvoiceList',component:wholeInvoiceList,name:'wholeInvoiceList'},
                {path:'wholeRollbackList',component:wholeRollbackList,name:'wholeRollbackList'},
                {path:'storeList',component:storeList,name:'storeList'},
                {path:'inventoryCheck',component:inventoryCheck,name:'inventoryCheck'},
                {path:'shouyin',component:shouyin,name:'shouyin'}
            ]
        },
    ]
});

// 路由验证
router.beforeEach((to,from,next) => {
    // 判断是否需要进行拦截验证
    if (to.meta.noAuth == true){
        // 不需要进行拦截
        next();
    }else{
        // 验证有没有进行登陆
        if (sessionStorage.getItem('empId') == null) {
            // 需要先进性登陆
            alert('请先进行登陆');
            next({
                path:'/login'
            })
        }else{
            next();
        }
    }
});

export default router;