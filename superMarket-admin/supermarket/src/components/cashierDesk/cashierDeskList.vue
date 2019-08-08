<template>
    <div id="cashierDeskList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-select v-model="queryCashierDesk.cashierState" filterable placeholder="全部收银台使用状态" size="small">
                    <el-option label="全部收银台使用状态" value=""></el-option>
                    <el-option label="正在使用" value="1"></el-option>
                    <el-option label="已停用" value="0"></el-option>
                </el-select>
                <el-input v-model="queryCashierDesk.condition" placeholder="收银台名称/店名" style="width: 150px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchDesk">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增收银台</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>收银台号</th>
                    <th>收银台名称</th>
                    <th>归属店铺</th>
                    <th>使用状态</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th>地址</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.id.toString()">
                     <td>{{index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.cashierName}}</td>
                    <td>{{item.shop.shopName}}</td>
                    <td v-if="item.cashierState == 0">已停用</td>
                    <td v-if="item.cashierState == 1">正在使用</td>
                    <td>{{item.createDate}}</td>
                    <td>{{item.modifyDate}}</td>
                    <td>{{item.shop.shopAddress}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="openDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="stopUse(item.id)">停用</el-tag>
                        <el-tag type="success" size="small" @click="startUse(item.id)">启用</el-tag>
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
        <!--添加收银台-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增收银台</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="归属店铺" prop="goodsTypeId">
                                    <el-select v-model="ruleForm.shopId" filterable size="small">
                                        <el-option
                                                v-for="item in shopNameList"
                                                :key="item.id.toString()"
                                                :label="item.shopName"
                                                :value="item.id.toString()">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="收银台名称" prop="cashierName">
                                    <el-input v-model="ruleForm.cashierName"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="resetForm('ruleForm')">重置</button>
                            <button type="button" class="btn btn-primary" @click="submitForm('ruleForm')">添加</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--详情-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">收银台详情</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="cashierDesk" :rules="rules" ref="cashierDesk" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="收银台号">
                                    <el-tag>{{cashierDesk.id}}</el-tag>
                                </el-form-item>
                                <el-form-item label="归属店铺">
                                    <el-input :value="cashierDesk.shop.shopName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="收银台名称" prop="cashierName">
                                    <el-input v-model="cashierDesk.cashierName"></el-input>
                                </el-form-item>
                                <el-form-item label="地址">
                                    <el-input :value="cashierDesk.shop.shopAddress" :disabled="true"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateCashierDesk('cashierDesk')">保存</button>
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
        name: "cashierDeskList",
        inject:['reload'],
        data() {
            return {
                cashierDesk:{},
                // 查询商品条件
                queryCashierDesk:{},
                // 收银台列表
                cashierList:[],
                // 收银台分页数据
                pageList:[
                ],
                // 归属门店
                shopNameList:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                // 是否显示排序选择
                showEdits:false,
                // 是否显示详情
                showDetail:false,
                // 添加收银台的表单对象
                ruleForm: {
                },
                rules: {
                    cashierName:[
                        { required: true, message: '请输入收银台名称', trigger: 'blur' }
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
            // 获取商品列表
            init(){
                this.$http.post('cashierDesk/findByCondition/').then(result => {
                    this.cashierList = result.data;
                    // 初始化分页器
                    this.initPage();
                    // 获取分页数据
                    this.getPageList(this.currentPage);
                });
                // 获取商品类型
                this.$http.post('goodsType/findByCondition/').then(result => {
                    this.goodsTypeList = result.data;
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.cashierList.length;
                this.currentPage = 1;
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let cashierList = this.cashierList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<cashierList.length;i++){
                    let goods = cashierList[i];
                    goods.checked = false;
                    pageList.push(goods);
                    pageCapacity++;
                    // 判断是否装满当前页
                    if (pageCapacity >= pageSize) {
                        break;
                    }
                }
                this.pageList = pageList;
            },
            // 多条件查询
            searchDesk(){
                let params = Qs.stringify(this.queryCashierDesk);
                this.$http.post('cashierDesk/findByCondition',params).then(result => {
                    this.cashierList = result.data;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 当前页面大小发生改变
            handleSizeChange(val) {
                this.pageSize = val;
                this.currentPage = 1;
                this.getPageList(this.currentPage);
            },
            // 当前页码改变
            handleCurrentChange(val) {
                this.getPageList(val);
            },
            openAdd() {
                this.ruleForm = {shopId:sessionStorage.getItem("shopId")};
                // 获取门店
                this.$http.post('shop/shopNameList').then(result => {
                    this.shopNameList = result.data;
                    this.showAdd = true;
                });
            },
            closeAdd(){
                this.showAdd = false;
            },
            openDetail(id){
                // 获取收银台详情
                this.$http.post('cashierDesk/findById','id='+id).then(result => {
                    this.cashierDesk = result.data;
                    this.showDetail = true;
                });

            },
            // 保存更新
            updateCashierDesk(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('cashierDesk/modifyCash','id='+this.cashierDesk.id+'&cashierName='+this.cashierDesk.cashierName).then(result => {
                            if (result.data.result){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'保存成功'
                                })
                                // 重新进行数据初始化
                                this.init();
                            }else{
                                this.$message({
                                    showClose:true,
                                    type:'danger',
                                    message:'保存失败'
                                })
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
            closeDetail(){
                this.showDetail = false;
            },
            // 停用收银台
            stopUse(id){
              this.updateCashierState(id,0)
            },
            // 启用收银台
            startUse(id){
              this.updateCashierState(id,1);
            },
            // 修改收银台状态
            updateCashierState(id,state){
                // 启用 停用
                this.$http.post('cashierDesk/modifyCash','id='+id+'&cashierState='+state).then(result => {
                    if (result.data.result){
                        this.$message({
                            showClose:true,
                            type:'success',
                            message:'状态修改成功'
                        })
                        // 重新进行数据初始化
                        this.init();
                    }else{
                        this.$message({
                            showClose:true,
                            type:'danger',
                            message:'状态修改失败'
                        })
                    }
                })
            },
            // 添加商品信息
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let params = Qs.stringify(this.ruleForm);
                        this.$http.post('cashierDesk/addCash',params).then(result => {
                            if (result.data.result){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'添加成功'
                                })
                                // 重新进行数据初始化
                                this.ruleForm = {shopId:sessionStorage.getItem("shopId")};
                                this.init();
                            }else{
                                this.$message({
                                    showClose:true,
                                    type:'danger',
                                    message:'添加失败'
                                })
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
    #cashierDeskList{
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