<template>
    <div id="storeList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-select v-model="queryStore.shopId" filterable placeholder="全部门店" size="small">
                    <el-option label="全部门店" value=""></el-option>
                    <el-option
                            v-for="item in shopNameList"
                            :key="item.id.toString()"
                            :label="item.shopName"
                            :value="item.id">
                    </el-option>
                </el-select>
                <el-input v-model="queryStore.condition" placeholder="仓库名称/门店名称" style="width: 150px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchStore">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增仓库</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>仓库编号</th>
                    <th>仓库名称</th>
                    <th>归属门店</th>
                    <th>联系人</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.id.toString()">
                   <td>{{index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.storeName}}</td>
                    <td>{{item.shop.shopName}}</td>
                    <td>{{item.shop.shopLinkman}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="updateStore(item.id,item.storeName)">修改仓库名称</el-tag>
                        <el-tag type="warning" size="small" @click="openDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="deleteStore(item.id)">删除</el-tag>
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

        <!--详情-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content" style="width: 800px; margin-left: -400px">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">库存详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">仓库信息</div>
                            <el-form label-width="100px" class="demo-ruleForm">
                                <el-form-item label="仓库编号">
                                    <el-input v-model="store.id" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="仓库名称" prop="storeName">
                                    <el-input v-model="store.storeName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="归属门店">
                                    <el-input v-model="store.shop.shopName"  :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="联系人">
                                    <el-input v-model="store.shop.shopLinkman" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话">
                                    <el-input v-model="store.shop.shopPhone" :disabled="true"></el-input>
                                </el-form-item>
                            </el-form>
                            <div class="alert alert-success" role="alert">仓库明细</div>
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>库存</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in storeDetailList" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.goods.goodsCode}}</td>
                                    <td>{{item.goods.goodsName}}</td>
                                    <td>{{item.goodsCount}}</td>
                                </tr>
                                </tbody>
                            </table>
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
        name: "storeList",
        inject:['reload'],
        data() {
            return {
                // 仓库信息
                store: {},
                storeDetailList:[],
                // 查询商品条件
                queryStore: {},
                // 仓库列表
                storeList: [],
                // 商品分页数据
                pageList: [],
                // 商品类型
                shopNameList: [],
                // 分页数据
                currentPage: 1,
                totalCount: 0,
                pageSize: 10,
                // 是否显示详情
                showDetail: false,
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
            // 获取仓库列表
            init(){
                this.$http.post('store/selStoreAndShop/').then(result => {
                    this.storeList = result.data;
                    // 初始化分页器
                    this.initPage();
                    // 获取分页数据
                    this.getPageList(this.currentPage);
                });
                // 获取门店列表
                this.$http.post('shop/shopNameList/').then(result => {
                    this.shopNameList = result.data;
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.storeList.length;
                this.currentPage = 1;
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 取消全选状态
                this.checkAll = false;
                this.checkedstore = [];
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let storeList = this.storeList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<storeList.length;i++){
                    let store = storeList[i];
                    store.checked = false;
                    pageList.push(store);
                    pageCapacity++;
                    // 判断是否装满当前页
                    if (pageCapacity >= pageSize) {
                        break;
                    }
                }
                this.pageList = pageList;
            },
            // 多条件查询
            searchStore(){
                let params = Qs.stringify(this.queryStore);
                this.$http.post('store/selStoreAndShop',params).then(result => {
                    this.storeList = result.data;
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
                this.$prompt('请输入仓库名称', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    // 判断仓库是否存在
                    if (value == null || value == ''){
                        this.$message({
                            type: 'warning',
                            message: '仓库名称不能为空'
                        });
                        return;
                    }
                    // 验证本店下是否存在该仓库
                    this.$http.post('store/selStoreAndShop','storeName='+value+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                        if (result.data.length == 0){
                            // 添加仓库
                            this.$http.post('store/insertStore','storeName='+value+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                                // 仓库添加成功
                                if (result.data.state){
                                    this.$message({
                                        type: 'success',
                                        message: '添加成功'
                                    });
                                    this.init();
                                }else{
                                    this.$message({
                                        type: 'error',
                                        message: '添加失败'
                                    });
                                }
                            })
                        } else{
                            this.$message({
                                type: 'warning',
                                message: '仓库（'+value+')在本店中已存在'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            // 批量修改
            updateStore(storeId,storeName){
                this.$prompt('请输入仓库名称', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputValue:storeName
                }).then(({ value }) => {
                    // 判断仓库是否存在
                    if (value == null || value == ''){
                        this.$message({
                            type: 'warning',
                            message: '仓库名称不能为空'
                        });
                        return;
                    }
                    // 验证本店下是否存在该仓库
                    this.$http.post('store/selStoreAndShop','storeName='+value+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                        if (result.data.length == 0){
                            // 修改仓库
                            this.$http.post('store/insertStore','storeName='+value+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                                // 修改成功
                                if (result.data.state){
                                    this.$message({
                                        type: 'success',
                                        message: '修改成功'
                                    });
                                    this.init();
                                }else{
                                    this.$message({
                                        type: 'error',
                                        message: '修改失败'
                                    });
                                }
                            })
                        } else{
                            this.$message({
                                type: 'warning',
                                message: '仓库（'+value+')在本店中已存在'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            closeEdits(){
                this.showEdits = false;
            },
            // 选中所有商品
            handleCheckAllChange(val) {
                //console.log(val);
                if (val){
                    // 选中所有
                    this.checkedstore = [];
                    this.pageList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedstore.push(item.id);
                    })
                } else{
                    this.pageList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedstore = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 商品选中状态发生改变
            handleCheckedShopsChange(val,id) {
                let store = {};
                this.pageList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        store = item;
                    }
                })
                // 判断是否选中
                if (store.checked){
                    // 添加到选中的列表中
                    this.checkedstore.push(store.id);
                }else{
                    this.checkedstore.forEach((item,index) => {
                        if (item == id){
                            this.checkedstore.splice(index,1);
                        }
                    })
                }
                // 判断是否全部选中
                if (this.checkedstore.length == this.pageList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }

                this.$forceUpdate();
            },
            openDetail(storeId){
                // 获取库存信息
                this.$http.post('store/selStoreAndShop','storeId='+storeId).then(result => {
                    this.store = result.data[0];
                    this.showDetail = true;
                });

                // 获取库存明细
                this.$http.post('inventoryDetail/selGoodsByStoreId','storeId='+storeId).then(result => {
                    this.storeDetailList = result.data;
                });
            },
            // 保存更新
            updatestore(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let specDetailId = this.store.specDetailId[this.store.specDetailId.length-1];
                        this.store.specDetailId = specDetailId;
                        let params = Qs.stringify(this.store);
                        this.$http.post('store/updatestore',params).then(result => {
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
            // 删除仓库
            deleteStore(storeId){
                // 判断仓库中是否有商品存在
                this.$http.post('store/delStore','storeId='+storeId).then(result => {
                    if (result.data.state){
                        this.$message({
                            showClose:true,
                            type:'success',
                            message:'删除成功'
                        })
                        this.init();
                    } else{
                        // 删除失败
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'仓库删除失败，请检查仓库中是否还存在商品'
                        })
                    }
                })
            },
            // 添加商品信息
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let specDetailId = this.ruleForm.specDetailId[this.ruleForm.specDetailId.length-1];
                        this.ruleForm.specDetailId = specDetailId;
                        let params = Qs.stringify(this.ruleForm);
                        this.$http.post('store/addstore',params).then(result => {
                            if (result.data.result){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'添加成功'
                                })
                                // 重新进行数据初始化
                                this.ruleForm = {};
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
    #storeList{
        .el-tag{
            &:hover{
                cursor: pointer;
            }
        }
        height: 100%;
        overflow: auto;
        .my-content{
            /*width: 1500px;*/
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