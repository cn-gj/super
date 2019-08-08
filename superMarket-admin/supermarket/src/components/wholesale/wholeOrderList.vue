<template>
    <div id="wholeOrderList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-input v-model="queryOrder.condition" placeholder="门店名称/订单号/客户名称/收货人/收货地址" style="width: 350px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchOrder">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新建批发单</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>单号</th>
                    <th>店铺名称</th>
                    <th>客户</th>
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
                    <td>{{item.customerName}}</td>
                    <td v-if="item.singleState == 0">待审核</td>
                    <td v-if="item.singleState == 1">已审核</td>
                    <td v-if="item.singleState == 2">未通过审核</td>
                    <td v-if="item.takeState == 0">待发货</td>
                    <td v-if="item.takeState == 1">已发货</td>
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

        <!--添加批发订单弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content" style="width: 800px;margin-left: -400px;">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增批发订单</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">批发订单信息</div>
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="客户" prop="customerId">
                                    <el-select v-model="ruleForm.customerId" filterable placeholder="请选择客户" size="small" @change="customerChange">
                                        <el-option
                                                v-for="item in customerList"
                                                :key="item.id.toString()"
                                                :label="item.customerName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="收获地址">
                                    <el-input v-model="ruleForm.customerAddress" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="预到货日期" prop="readyDate">
                                    <el-date-picker
                                            v-model="ruleForm.readyDate"
                                            type="date"
                                            size="small"
                                            value-format="yyyy/MM/dd"
                                            placeholder="预出货日期">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="出货仓库" prop="storeId">
                                    <el-select v-model="ruleForm.storeId" filterable placeholder="请选择仓库" size="small" @change="storeChange">
                                        <el-option label="请选择仓库" value=""></el-option>
                                        <el-option
                                                v-for="item in storeList"
                                                :key="item.id.toString()"
                                                :label="item.storeName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div class="alert alert-success" role="alert">批发订单详情</div>
                            <!--操作-->
                            <el-button icon="glyphicon glyphicon-plus" size="small" @click="openChooseGoods">添加商品</el-button>
                            <el-button icon="glyphicon glyphicon-plus" size="small" @click="removeChooseGoods" style="color:red;">删除商品</el-button>
                            <!--明细-->
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>
                                        <el-checkbox v-model="checkedAllDetail" @change="handleCheckedAllDetailGoodsChange"></el-checkbox>
                                    </th>
                                    <th>序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>价格</th>
                                    <th>采购数量</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in goodsList" :key="item.goodsName">
                                    <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedDetailGoodsChange(item.checked,item.goodsId)"></el-checkbox></th>
                                    <td>{{index+1}}</td>
                                    <td>{{item.goodsCode}}</td>
                                    <td>{{item.goodsName}}</td>
                                    <td>{{item.wholePrice}}</td>
                                    <td>
                                        <el-tag @click="changeGoodsCount(item)">{{item.goodsCount}}</el-tag>
                                    </td>
                                    <td>
                                        {{item.totalMoney}}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="submitForm('ruleForm')">添加</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--选择商品-->
        <transition>
            <div class="my-tanchukuang" v-if="showChooseGoods" style="z-index: 2000">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeChooseGoods">×</span></button>
                            <h4 class="modal-title">选择商品</h4>
                        </div>
                        <div class="modal-body">
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>
                                        <el-checkbox v-model="checkedAllGoods" @change="handleCheckAllGoodsChange"></el-checkbox>
                                    </th>
                                    <th>序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>批发价</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in goods" :key="item.goodsName">
                                    <th scope="row"><el-checkbox v-model="item.checked" @change="handlecheckedGoodsChange(item.checked,item.goodsId)"></el-checkbox></th>
                                    <td>{{index+1}}</td>
                                    <td>{{item.goods.goodsCode}}</td>
                                    <td>{{item.goods.goodsName}}</td>
                                    <td>{{item.goods.wholePrice}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="addGoodsToGoodsList">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--批发订单详情弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content" style="width: 800px;margin-left: -400px;">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">批发订单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">批发订单信息</div>
                            <el-form label-width="100px" class="demo-ruleForm">
                                <el-form-item label="客户">
                                   <el-input v-model="order.customerName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收获地址">
                                    <el-input v-model="order.customerAddress" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="预期到货日期">
                                    <el-input v-model="order.readyDate" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收货人">
                                    <el-input v-model="order.customerLinkMan" :disabled="true"></el-input>
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
                                <tr v-for="(item,index) in order.details" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.goodsVo.goodsCode}}</td>
                                    <td>{{item.goodsVo.goodsName}}</td>
                                    <td>{{item.goodsVo.wholePrice}}</td>
                                    <td>{{item.goodsCount}}</td>
                                    <td>{{item.totalMoney}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="pass(order.id,1)" >通过审核</button>
                            <button type="button" class="btn btn-primary" @click="noPass(order.id,2)" >审核不通过</button>
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
        name: "wholeOrderList",
        inject:['reload'],
        data() {
            return {
                order:{},
                goodsCount:0,
                orderList:[],
                // 订单明细中的商品列表
                goodsList:[],
                // 供应商提供的商品
                goods:[],
                //-----------------
                // 查询采购订单条件对象
                queryOrder:{},
                // 客户列表
                customerList:[],
                // 采购订单分页数据
                pageList:[],
                // 门店名称列表
                shopNameList:[],
                // 员工列表
                empList:[],
                // 选中的订单明细（要进行持久化的商品数据）
                checkedDetailGoods:[],
                // 选中的供应商提供的商品（要添加到持久化的商品中的数据）
                checkedGoods:[],
                // 本店仓库
                storeList:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                showChooseGoods:false,
                showDetail:false,
                // 是否选中所有要进行持久话的商品
                checkedAllDetail:false,
                // 是否选中供应商下的所有商品
                checkedAllGoods:false,
                ruleForm: {},
                rules: {
                    customerId: [
                        { required: true, message: '请选择供应商', trigger: 'blur' },
                    ],
                    readyDate: [
                        { required: true, message: '请录入期望到货日期', trigger: 'blur' }
                    ],
                    storeId:[
                        { required: true, message: '请选择出货仓库', trigger: 'blur' }
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
                this.$http.post('wholeOrder/findByCondition/').then(result => {
                    this.orderList = result.data;
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
                this.$http.post('wholeOrder/findByCondition',params).then(result => {
                    this.orderList = result.data;
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
            // 打开新建采购单
            openAdd() {
                this.ruleForm = {};
                this.checkedDetailGoods = [];
                this.checkedGoods = [];
                // 获取客户列表（状态为正常）
                this.$http.post('customer/findByList').then(result => {
                    this.customerList = result.data;
                });
                // 获取本店下的仓库
                this.$http.post('store/findByShopId/','shopId='+sessionStorage.getItem("shopId")).then(result => {
                    this.storeList = result.data;
                });
                this.showAdd = true;
            },
            // 更改客户
            customerChange(val){
                let customer = this.customerList.filter(item => item.id == val)[0];
                this.ruleForm.customerAddress = customer.customerAddress;
            },
            // 更改出货仓库
            storeChange(){
                this.checkedAllDetail = false;
                this.checkedDetailGoods = [];
                this.goodsList = [];
            },
            // 打开选择采购商品的弹出框
            openChooseGoods(){
                if (this.ruleForm.storeId == null || this.ruleForm.storeId == ''){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请选择出货仓库'
                    })
                    return;
                }

                this.checkedGoods = [];
                this.checkedAllGoods = false;
                // 获取仓库下的所有商品信息
                this.$http.post('inventoryDetail/selGoodsByStoreId','storeId='+this.ruleForm.storeId).then(result => {
                    this.goods = result.data;
                    this.showChooseGoods = true;
                })
                
            },
            // 移除选中的商品
            removeChooseGoods(){
                if (this.checkedDetailGoods.length == 0){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请选择要移除的商品信息'
                    })
                    return;
                }
                this.checkedDetailGoods.forEach((item,idx) => {
                    this.goodsList.forEach((goods,index) => {
                        if (goods.id == item){
                            this.goodsList.splice(index,1);
                        }
                    })
                })
                this.checkedDetailGoods = [];
                this.checkedAllDetail = false;
            },
            // 手动改变商品数量时重新计算订单信息
            changeGoodsCount(goods){
                this.$prompt('请输入商品数量', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[1-9]{1,}[\d]*$/,
                    inputValue:goods.goodsCount,
                    inputErrorMessage: '商品数量必须是一个正整数'
                }).then(({ value }) => {
                   goods.goodsCount = value;
                   goods.totalMoney = parseInt(goods.goodsCount)*parseFloat(goods.wholePrice);
                   this.$forceUpdate();
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            // 将选择的商品添加到goodsList中
            addGoodsToGoodsList(){
                if (this.checkedGoods.length == 0){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请选择要添加的商品'
                    })
                    return;
                }
                // 将选中的商品添加goodsList
                this.goods.forEach((goods,index)=> {
                    this.checkedGoods.forEach((item,index) => {
                        if (goods.goodsId == item) {
                            console.log('进入');
                            goods.checked = false;
                            // 判断商品是否已经存在
                            let detail = this.goodsList.filter(detail => detail.goodsId == item)[0];
                            if (detail != null){
                                detail.goodsCount=parseInt(detail.goodsCount)+1;
                                detail.totalMoney=parseInt(detail.goodsCount)*parseFloat(detail.wholePrice);
                            } else{
                                goods.goodsCode = goods.goods.goodsCode;
                                goods.goodsName = goods.goods.goodsName;
                                goods.wholePrice = goods.goods.wholePrice;
                                goods.goodsCount = 1;
                                goods.totalMoney = parseInt(goods.goodsCount)*parseFloat(goods.wholePrice);
                                this.goodsList.push(goods);
                                console.log('come in');
                            }
                        }
                    })
                })
                this.showChooseGoods = false;
                this.$forceUpdate();
            },
            closeChooseGoods(){
                this.showChooseGoods = false;
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 选中采购订单中所有要进行持久化的商品
            handleCheckedAllDetailGoodsChange(val) {
               this.handleCheckedAllStateChange(val,this.goodsList,0);
            },
            // 商品选中状态发生改变（订单明细）
            handleCheckedDetailGoodsChange(val,id) {
                this.handleCheckedStateChange(val,id,this.checkedDetailGoods,this.goodsList,this.checkedAllDetail,0);
            },
            // 选中供应商下的所有商品
            handleCheckAllGoodsChange(val) {
                this.handleCheckedAllStateChange(val,this.goods,1);
            },
            // 商品选中状态发生改变(供应商)
            handlecheckedGoodsChange(val,id) {
                this.handleCheckedStateChange(val,id,this.checkedGoods,this.goods,this.checkedAllGoods,1);
            },
            // 选中所有状态发生改变
            // val 选中状态 checkedList 选中项的列表 dataSourceList 原始数据（及供选择的数据）
            // who 0:订单明细（checkedDetailGoods），1：供应商（checkedGoods）
            handleCheckedAllStateChange(val,dataSourceList,who){
                // 选中所有
                let checkedList = [];
                if (val){
                    dataSourceList.forEach((item,index) => {
                        item.checked = true;
                        checkedList.push(item.goodsId);
                    })
                } else{
                    dataSourceList.forEach((item,index) => {
                        item.checked = false;
                        checkedList = [];
                    })
                }
                if (who == 0){
                    this.checkedDetailGoods = checkedList;
                } else{
                    this.checkedGoods  = checkedList;
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 选中状态发生改变
            handleCheckedStateChange(val,id,checkedList,dataSourceList,checkedAll,who){
                let object = {};
                dataSourceList.forEach((item,index) => {
                    if (item.goodsId == id){
                        item.checked = val;
                        object = item;
                    }
                })
                // 判断是否选中
                if (object.checked){
                    // 添加到选中的列表中
                    checkedList.push(object.goodsId);
                }else{
                    checkedList.forEach((item,index) => {
                        if (item == id){
                            checkedList.splice(index,1);
                        }
                    })
                }
                // 判断是否全部选中
                if (checkedList.length == dataSourceList.length){
                    checkedAll = true;
                } else{
                    checkedAll = false;
                }

                if (who == 0){
                    this.checkedAllDetail = checkedAll;
                    this.checkedDetailGoods = checkedList;
                } else{
                    this.checkedAllGoods = checkedAll;
                    this.checkedGoods  = checkedList;
                }
                this.$forceUpdate();
            },
            // 订单详情
            orderDetail(id){
                this.$http.post('wholeOrder/findById/','id='+id).then(result => {
                    this.order = result.data;
                    this.showDetail = true;
                });
            },
            // 通过审核
            pass(orderId,singleState){
                // 获取当前订单状态，并进行判断是否需要修改
                this.$http.post('wholeOrder/findById/','id='+orderId).then(result => {
                    let order = result.data;
                    // 目标状态和当前状态一致
                    if (order.singleState == singleState){
                        this.$message({
                            showClose:true,
                            type:'info',
                            message:'该订单已审核通过'
                        });
                    } else{
                        // 通过审核
                        this.updateOrderSingleState(orderId,singleState);
                    }

                });
            },
            // 拒绝通过审核
            noPass(orderId,singleState){
                // 获取当前订单状态，并进行判断是否需要修改
                this.$http.post('wholeOrder/findById/','id='+orderId).then(result => {
                    let order = result.data;
                    // 目标状态和当前状态一致
                    if (order.singleState == singleState){
                        this.$message({
                            showClose:true,
                            type:'info',
                            message:'该订单已被拒绝通过审核'
                        });
                    } else{
                        // 判断是否已发货或者退货
                        if (order.takeState == 1 || order.takeState == 2){
                            this.$message({
                                showClose:true,
                                type:'info',
                                message:'该订单审核状态已被修改'
                            });
                        } else{
                            // 拒绝通过审核
                            this.updateOrderSingleState(orderId,singleState);
                        }
                    }

                });
            },
            // 修改单据状态
            updateOrderSingleState(orderId,singleState){
                // 获取当前订单状态，并进行判断是否需要修改
                this.$http.post('wholeOrder/updateWhole/','id='+orderId+'&singleState='+singleState).then(result => {
                    if (result.data.result){
                        this.$message({
                            showClose:true,
                            type:'success',
                            message:'操作成功'
                        });
                        this.init();
                    } else{
                        this.$message({
                            showClose:true,
                            type:'error',
                            message:'操作失败'
                        });
                    }
                });
            },
            closeDetail(){
                this.showDetail = false;
            },
            // 添加
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 判断订单详情列表是否为空
                        if (this.goodsList.length == 0){
                            this.$message({
                                showClose:true,
                                type:'warning',
                                message:'请选择要出售的商品'
                            });
                            return false;
                        }

                        // 生成批发订单
                        let params = Qs.stringify(this.ruleForm);

                        let goodsList = [];
                        this.goodsList.forEach((item,index) => {
                            let goods = {goodsId:item.goodsId,goodsCount:item.goodsCount,totalMoney:item.totalMoney};
                            goodsList.push(goods);
                        })
                         let goodsListStr = JSON.stringify(goodsList);

                        // console.log(params+'---'+goodsList);
                        // return;
                        this.$http.post('wholeOrder/addWholeOrder',params+'&shopId='+sessionStorage.getItem("shopId")+'&empId='+sessionStorage.getItem("empId")+'&str='+goodsListStr).then(result => {
                            if (result.data.result){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'添加成功'
                                });
                                this.init();
                                this.ruleForm = {};
                                this.checkedDetailGoods = [];
                                this.checkedAllDetail = false;
                                this.goodsList = [];
                            } else{
                                this.$message({
                                    showClose:true,
                                    type:'error',
                                    message:'添加失败'
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
    #wholeOrderList{
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