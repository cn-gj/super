<template>
    <div id="rollbackList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-input v-model="queryOrder.condition" placeholder="供应商名称/联系人/联系电话/地址" style="width: 250px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchOrder">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openChooseOrder">从采购收货单中生成</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>单号</th>
                    <th>店铺名称</th>
                    <th>供应商</th>
                    <th>单据状态</th>
                    <th>收获状态</th>
                    <th>单据日期</th>
                    <th>收货人</th>
                    <th>收获电话</th>
                    <th>地址</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.id.toString()">
                    <td>{{index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.shopName}}</td>
                    <td>{{item.proName}}</td>
                    <td v-if="item.singleState == 0">待审核</td>
                    <td v-if="item.singleState == 1">已审核</td>
                    <td v-if="item.takeState == 0">待收货</td>
                    <td v-if="item.takeState == 1">已收货</td>
                    <td v-if="item.takeState == 2">已退货</td>
                    <td>{{item.placeOrderDate}}</td>
                    <td>{{item.empName}}</td>
                    <td>{{item.empPhone}}</td>
                    <td>{{item.shopAddress}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="orderDetail(item.id)">详情</el-tag>
                    </td>
                </tr>
                </tbody>
            </table>
            <!--分页-->
            <div class="my-page">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[1,10, 20, 30]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="totalCount">
                </el-pagination>
            </div>
            <div style="height: 200px"></div>

        </div>

        <!--选择要进行退货的采购收货单-->
        <transition>
            <div class="my-tanchukuang" v-if="showChooseOrder">
                <div>
                    <div class="my-modal modal-content" style="width: 800px;margin-left: -400px;">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeChooseOrder">×</span></button>
                            <h4 class="modal-title">选择采购收货单</h4>
                        </div>
                        <div class="modal-body">
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>单号</th>
                                    <th>店铺名称</th>
                                    <th>供应商</th>
                                    <th>单据状态</th>
                                    <th>收获状态</th>
                                    <th>单据日期</th>
                                    <th>收货人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in chooseOrderList" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.id}}</td>
                                    <td>{{item.shopName}}</td>
                                    <td>{{item.proName}}</td>
                                    <td v-if="item.singleState == 0">待审核</td>
                                    <td v-if="item.singleState == 1">已审核</td>
                                    <td v-if="item.takeState == 0">待收货</td>
                                    <td v-if="item.takeState == 1">已收货</td>
                                    <td>{{item.placeOrderDate}}</td>
                                    <td>{{item.empName}}</td>
                                    <td>
                                        <el-tag type="success" size="small" @click="openAdd(item.id)">选择</el-tag>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </transition>
        <!--添加采购订单弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content" style="z-index: 2000">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增采购退货单</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">采购订单信息</div>
                            <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="供应商">
                                    <el-input v-model="order.provider.proName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收获店铺">
                                    <el-input v-model="order.shop.shopName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="期望到货日期">
                                    <el-input v-model="order.readyDate" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收货人">
                                    <el-input v-model="order.employee.empName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="存货仓库" prop="storeId">
                                    <el-select v-model="ruleForm.storeId" filterable placeholder="请选择仓库" size="small">
                                        <el-option
                                                v-for="item in storeList"
                                                :key="item.id.toString()"
                                                :label="item.storeName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div class="alert alert-success" role="alert">采购订单详情</div>
                            <!--明细-->
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>价格</th>
                                    <th>采购数量</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in order.orderDetailVoList" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.goodsVo.goodsCode}}</td>
                                    <td>{{item.goodsVo.goodsName}}</td>
                                    <td>{{item.goodsVo.costPrice}}</td>
                                    <td>{{item.goodsCount}}</td>
                                    <td>{{item.totalMoney}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="submitForm('ruleForm',order.id)">确认退货</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--采购退货单详情-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content" style="width: 800px;margin-left: -400px;">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">采购订单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">采购订单信息</div>
                            <el-form label-width="100px" class="demo-ruleForm">
                                <el-form-item label="供应商">
                                    <el-input v-model="order.provider.proName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收获店铺">
                                    <el-input v-model="order.shop.shopName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="期望到货日期">
                                    <el-input v-model="order.readyDate" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收货人">
                                    <el-input v-model="order.employee.empName" :disabled="true"></el-input>
                                </el-form-item>
                            </el-form>
                            <div class="alert alert-success" role="alert">采购订单详情</div>
                            <!--明细-->
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>价格</th>
                                    <th>采购数量</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in order.orderDetailVoList" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.goodsVo.goodsCode}}</td>
                                    <td>{{item.goodsVo.goodsName}}</td>
                                    <td>{{item.goodsVo.costPrice}}</td>
                                    <td>{{item.goodsCount}}</td>
                                    <td>{{item.totalMoney}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    import Qs from 'qs';
    export default {
        name: "rollbackList",
        inject:['reload'],
        data() {
            return {
                order:{},
                orderList:[],
                // 订单明细中的商品列表
                goodsList:[],
                // 供应商提供的商品
                proGoods:[],
                // 要生成收获单的对象
                chooseOrderList:[],
                //-----------------
                // 查询采购订单条件对象
                queryOrder:{},
                // 仓库列表
                storeList:[],
                // 采购订单分页数据
                pageList:[],
                // 选中的订单明细（要进行持久化的商品数据）
                checkedDetailGoods:[],
                // 选中的供应商提供的商品（要添加到持久化的商品中的数据）
                checkedProGoods:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                showChooseGoods:false,
                showDetail:false,
                showChooseOrder:false,
                // 是否选中所有要进行持久话的商品
                checkedAllDetail:false,
                // 是否选中供应商下的所有商品
                checkedAllProGoods:false,
                ruleForm: {},
                rules: {
                    proId: [
                        { required: true, message: '请选择供应商', trigger: 'blur' },
                    ],
                    readyDate: [
                        { required: true, message: '请录入期望到货日期', trigger: 'blur' }
                    ],
                    empId: [
                        { required: true, message: '请选择收货人', trigger: 'blur' }
                    ],
                    storeId:[
                        { required: true, message: '请选择仓库', trigger: 'blur' }
                    ]
                }
            }
        },
        created(){
            // 初始化
            this.init();
        },
        methods:{
            // 刷新当前页面
            refresh(){
                this.reload();
            },
            // 获取采购订单列表
            init(){
                this.$http.post('order/orderList/','takeState=2').then(result => {
                    this.orderList = result.data.orderVoList;
                    // 初始化分页器
                    this.initPage();
                    this.getPageList(this.currentPage);
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.orderList.length;
                this.currentPage = 1;
            },
            // 多条件查询
            searchOrder(){
                let params = Qs.stringify(this.queryOrder);
                this.$http.post('order/orderList',params+'&takeState=1').then(result => {
                    this.orderList = result.data.orderVoList;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let orderList = this.orderList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<orderList.length;i++){
                    let order = orderList[i];
                    if (order.shop !=null){
                        order.shopName = order.shop.shopName;
                        order.shopAddress = order.shop.shopAddress;
                    }
                    if (order.employee != null){
                        order.empName = order.employee.empName;
                        order.empPhone = order.employee.empPhone;
                    }
                    if (order.provider !=null){
                        order.proName = order.provider.proName;
                    }
                    pageList.push(order);
                    pageCapacity++;
                    // 判断是否装满当前页
                    if (pageCapacity >= pageSize) {
                        break;
                    }
                }
                this.pageList = pageList;
            },
            // 页面大小改变
            handleSizeChange(val) {
                this.pageSize = val;
                this.currentPage = 1;
                this.getPageList(this.currentPage);
            },
            // 当前页码改变
            handleCurrentChange(val) {
                this.getPageList(val);
            },
            // 选择要进行退货的订单
            openChooseOrder(){
                this.$http.post('order/orderList/','singleState=1&takeState=1').then(result => {
                    let chooseOrderList = result.data.orderVoList;
                    chooseOrderList.forEach((item,index) => {
                        if (item.shop !=null){
                            item.shopName = item.shop.shopName;
                            item.shopAddress = item.shop.shopAddress;
                        }
                        if (item.employee != null){
                            item.empName = item.employee.empName;
                        }
                        if (item.provider != null){
                            item.proName = item.provider.proName;
                        }
                    })
                    this.chooseOrderList = chooseOrderList;
                    this.showChooseOrder = true;
                });
            },
            closeChooseOrder(){
                this.showChooseOrder = false;
            },
            // 打开新建采购退货单
            openAdd(id) {
                this.ruleForm = {};
                this.$http.post('order/selOrderAndDetailByOrderId/','id='+id).then(result => {
                    this.order = result.data[0];
                    this.showAdd = true;
                });

                // 获取本店下的仓库
                this.$http.post('store/findByShopId/','shopId='+sessionStorage.getItem("shopId")).then(result => {
                    this.storeList = result.data;
                });
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 供应商详情
            orderDetail(id){
                this.$http.post('order/selOrderAndDetailByOrderId/','id='+id).then(result => {
                    this.order = result.data[0];
                    this.showDetail = true;
                });
            },
            closeDetail(){
                this.showDetail = false;
            },
            // 添加
            submitForm(formName,orderId) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // orderId,takeState,storeId,goodsStr
                        let params = 'orderId='+orderId+'&takeState=2&storeId='+this.ruleForm.storeId;
                        let goods =  this.order.orderDetailVoList;
                        let goodsList = [];
                        goods.forEach((item,index) => {
                            goodsList.push({goodsId:item.goodsId,goodsCount:item.goodsCount});
                        })
                        let goodsStr = JSON.stringify(goodsList);
                        this.$http.post('order/upOrderTakeState',params+'&goodsStr='+goodsStr).then(result => {
                            if (result.data.state){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'退货成功'
                                });
                                this.init();
                                this.ruleForm = {};
                                this.showAdd = false;
                                this.showChooseOrder = false;
                            } else{
                                this.$message({
                                    showClose:true,
                                    type:'error',
                                    message:'退货失败，请查看库存中商品数据是否满足退货需求'
                                });
                            }
                        })
                    } else {
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'请填写完整信息'
                        })
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped lang="less">
    #rollbackList{
        .el-tag{
            &:hover{
                cursor: pointer;
            }
        }
        height: 100%;
        overflow: auto;
        .my-content{
            width: 1500px;
            height: 100%;
            position: relative;
            .my-search{
                padding: 0 0 10px 0;
                border-bottom: 1px solid gray;
            }
            .my-tools{
                padding: 10px 0;
            }
            .my-page{

            }

        }
        .my-tanchukuang{
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            background-color: rgba(0,0,0,0.5);
            border: 15px solid white;
            z-index: 1000;
            .my-modal{
                overflow: auto;
                height: 404px;
                width: 600px;
                position: absolute;
                top: 50%;
                left: 50%;
                margin-top: -202px;
                margin-left: -300px;

                .my-edit-item{
                    padding: 15px;
                }
            }

            .my-logo{
                width: 60px;
                height: 60px;
                border: 1px solid gray;
            }
        }

        .v-enter,
        .v-leave-to {
            /*v-enter 动画未开始时，v-leave-to 动画已经结束*/
            opacity: 0;
        }

        .v-enter-active,
        .v-leave-active {
            /*v-enter-active 入场时触发的动画，v-leave-active 离场时触发的动画*/
            transition: all 0.8s ease;
        }

    }
</style>