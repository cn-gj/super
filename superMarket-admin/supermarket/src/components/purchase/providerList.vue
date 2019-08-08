<template>
    <div id="providerList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-input v-model="queryProvider.condition" placeholder="供应商名称/联系人/联系电话/地址" style="width: 250px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchProvider">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增供应商</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="batchFrozenProvider">冻结供应商</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="batchThawProvider">解冻供应商</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>
                        <el-checkbox v-model="checkAll" @change="handleCheckAllChange"></el-checkbox>
                    </th>
                    <th>序号</th>
                    <th>供应商</th>
                    <th>供应商状态</th>
                    <th>联系人</th>
                    <th>联系电话</th>
                    <th>地址</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.shopName">
                    <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedProviderChange(item.checked,item.id)"></el-checkbox></th>
                    <td>{{index+1}}</td>
                    <td>{{item.proName}}</td>
                    <td v-if="item.proStatus == 0">已停用</td>
                    <td v-if="item.proStatus == 1">正常</td>
                    <td>{{item.proLinkman}}</td>
                    <td>{{item.proPhone}}</td>
                    <td>{{item.proAddress}}</td>
                    <td>{{item.createDate}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="providerDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="frozen(item.id)">冻结</el-tag>
                        <el-tag type="success" size="small" @click="thaw(item.id)">解冻</el-tag>
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

        <!--添加供应商弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增供应商</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="供应商" prop="proName">
                                    <el-input v-model="ruleForm.proName" @blur="checkProvider(ruleForm.proName)"></el-input>
                                </el-form-item>
                                <el-form-item label="联系人" prop="proLinkman">
                                    <el-input v-model="ruleForm.proLinkman"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话" prop="proPhone">
                                    <el-input type="proPhone" v-model="ruleForm.proPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" prop="proEmail">
                                    <el-input v-model="ruleForm.proEmail"></el-input>
                                </el-form-item>
                                <el-form-item label="开户银行" prop="bank">
                                    <el-input v-model="ruleForm.bank"></el-input>
                                </el-form-item>
                                <el-form-item label="银行卡号" prop="bankNum">
                                    <el-input v-model="ruleForm.bankNum"></el-input>
                                </el-form-item>
                                <el-form-item label="地址" prop="proAddress">
                                    <el-input v-model="ruleForm.proAddress"></el-input>
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

        <!--门店详情弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">门店详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">供应商信息</div>
                            <el-form :model="provider" :rules="rules" ref="provider" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="供应商" prop="proName">
                                    <el-input v-model="provider.proName" @blur="checkProvider(provider.proName)"></el-input>
                                </el-form-item>
                                <el-form-item label="联系人">
                                    <el-input v-model="provider.proLinkman"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话" prop="proPhone">
                                    <el-input type="proPhone" v-model="provider.proPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" prop="proEmail">
                                    <el-input v-model="provider.proEmail"></el-input>
                                </el-form-item>
                                <el-form-item label="开户银行">
                                    <el-input v-model="provider.bank"></el-input>
                                </el-form-item>
                                <el-form-item label="银行卡号" prop="bankNum">
                                    <el-input v-model="provider.bankNum"></el-input>
                                </el-form-item>
                                <el-form-item label="地址">
                                    <el-input v-model="provider.proAddress"></el-input>
                                </el-form-item>
                            </el-form>
                            <div class="alert alert-success" role="alert">合作店铺</div>
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>店铺名称</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in provider.shopList" :key="item.id.toString()">
                                    <td>{{index+1}}</td>
                                    <td>{{item.shopName}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateProviderInfo('provider')">保存</button>
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
        name: "providerList",
        inject:['reload'],
        data() {
            return {
                provider:{},
                // 查询门店条件对象
                queryProvider:{
                    condition:''
                },
                // 供应商列表
                providerList:[],
                // 门店分页数据
                pageList:[
                    {id:'1001',shopName:'厦门沃尔玛',checked:false,shopType:{shopTypeName:'a'},shopAccount:'',shopLinkman:'',shopPhone:'',shopAddress:'',createdDate:'',shopHours:'08:30-22:00'},
                    {id:'1002',shopName:'阿里巴巴',checked:false,shopType:{shopTypeName:'a'},shopAccount:'',shopLinkman:'',shopPhone:'',shopAddress:'',createdDate:'',shopHours:'09:30-22:00'}
                ],
                // 门店类型
                shopTypeList:[],
                // 门店名称列表
                shopNameList:[],
                // 选中的门店
                checkedProvider:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                // 是否显示批量修改
                showEdits:false,
                showDetail:false,
                // 是否选中所有
                checkAll:false,
                checked:false,
                radio:'1',
                // 添加表单
                ruleForm: {},
                rules: {
                    proName: [
                        { required: true, message: '请输入供应商名称', trigger: 'blur' },
                    ],
                    proPhone: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' },
                        { min: 11, max: 11, message: '请输入正确的手机号', trigger: 'blur' }
                    ],
                    proEmail: [
                        { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '邮箱格式不正确', trigger: 'blur' },
                    ],
                    bankNum:[
                        { min: 19, max: 19, message: '银行卡号有误', trigger: 'blur' }
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
            // 获取供应商列表
            init(){
                this.$http.post('provider/proList/').then(result => {
                    this.providerList = result.data.providerList;
                    // 初始化分页器
                    this.initPage();
                    this.getPageList(this.currentPage);
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.providerList.length;
                this.currentPage = 1;
            },
            // 多条件查询
            searchProvider(){
                let params = Qs.stringify(this.queryProvider);
                this.$http.post('provider/proList',params).then(result => {
                    this.providerList = result.data.providerList;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 取消全选状态
                this.checkAll = false;
                this.checkedProvider = [];
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let providerList = this.providerList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<providerList.length;i++){
                    let provider = providerList[i];
                    provider.checked = false;
                    pageList.push(provider);
                    pageCapacity++;
                    // 判断是否装满当前页
                    if (pageCapacity >= pageSize) {
                        break;
                    }
                }
                // console.log(shopPageList);
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
            openAdd() {
                this.ruleForm = {};
                this.showAdd = true;
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 供应商详情
            providerDetail(id){
                this.$http.post('provider/proList','id='+id).then(result => {
                    this.provider = result.data.providerList[0];
                    this.showDetail = true;
                })
            },
            // 冻结
            frozen(id){
                this.$confirm('冻结该供应商后,所有门店将终止其合作关系(搞不好要扣你工资的哟,为了生活,请谨慎操作), 是否继续?', '友情提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.updateProStatus(id,0);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '操作已取消'
                    });
                });
            },
            thaw(id){
                this.updateProStatus(id,1);
            },
            // 批量冻结供应商
            batchFrozenProvider(){
                // 判断是否有选中数据
                if (this.checkedProvider.length == 0){
                    this.$message({
                        type: 'warning',
                        message: '请选择要进行操作的数据'
                    });
                    return;
                }
                this.$confirm('冻结该供应商后,所有门店将终止其合作关系(搞不好要扣你工资的哟,为了生活,请谨慎操作), 是否继续?', '友情提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = this.checkedProvider.join(',');
                    this.updateProStatus(ids,0);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '操作已取消'
                    });
                });
            },
            // 批量解冻供应商
            batchThawProvider(){
                // 判断是否有选中数据
                if (this.checkedProvider.length == 0){
                    this.$message({
                        type: 'warning',
                        message: '请选择要进行操作的数据'
                    });
                    return;
                }

                let ids = this.checkedProvider.join(',');
                this.updateProStatus(ids,1);
            },
            // 修改供应商状态
            updateProStatus(id,status){
                this.$http.post('provider/upProStatus','ids='+id+'&proStatus='+status).then(result => {
                    if (result.data.state){
                        this.$message({
                            showClose:true,
                            type:'success',
                            message:'供应商状态修改成功'
                        })
                        this.init();
                    } else{
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'供应商状态修改失败'
                        })
                    }
                })
            },
            // 更新门店信息
            updateProviderInfo(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                       let provider = this.provider;
                       let id = provider.id;
                       let proName = provider.proName;
                       let proLinkman = provider.proLinkman;
                       let proPhone = provider.proPhone;
                       let proEmail = provider.proEmail;
                       let bank = provider.bank;
                       let bankNum = provider.bankNum;
                       let proAddress = provider.proAddress;

                       let params = `id=`+id+`&proName=`+proName+`&proLinkman=`+proLinkman+`&proPhone=`+proPhone
                                    +`&proEmail=`+proEmail+`&bank=`+bank+`&bankNum=`+bankNum+`&proAddress=`+proAddress;

                        this.$http.post('provider/upPro',params).then(result => {
                            if (result.data.state){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'保存成功'
                                })
                                this.init();
                            } else{
                                this.$message({
                                    showClose:true,
                                    type:'error',
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
                })
            },
            closeDetail(){
                this.showDetail = false;
            },
            // 选中所有门店
            handleCheckAllChange(val) {
                if (val){
                    // 选中所有
                    this.checkedProvider = [];
                    this.pageList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedProvider.push(item.id);
                    })
                } else{
                    this.pageList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedProvider = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
                console.log(this.checkedProvider);
            },
            // 门店选中状态发生改变
            handleCheckedProviderChange(val,id) {
                let provider = {};
                this.pageList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        provider = item;
                    }
                })
                if (provider.checked){
                    // 添加到选中的列表中
                    this.checkedProvider.push(provider.id);
                }else{
                    this.checkedProvider.forEach((item,index) => {
                        if (item == id){
                            this.checkedProvider.splice(index,1);
                        }
                    })
                }

                // 判断是否全部选中
                if (this.checkedProvider.length == this.pageList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }
                this.$forceUpdate();

            },
            // 判断供应商是否存在
            checkProvider(proName){
                // 1.判断该供应商是否已经存在
                this.$http.post('provider/selProByProNameAndShopId','proName='+proName+'&shopId=').then(result => {
                    // 供应商已经存在
                    if (result.data.state){
                        let proId = result.data.proId;
                        // 判断该供应商是否正在为本店提供服务
                        this.$http.post('provider/selProByProNameAndShopId','proName='+proName+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                            if (result.data.state){
                                // 正在为本店提供服务
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'供应商('+proName+')正在为本店提供服务'
                                })
                                this.ruleForm.proName = '';
                            } else{
                                // 是否建立合作
                                this.cooperation(proName,proId);
                            }
                        })
                    }
                })
            },
            // 建立合作关系
            cooperation(proName,proId) {
                this.$confirm('供应商('+proName+')已经存在，是否与该供应商建立合作合作关系?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'info'
                }).then(() => {
                   this.$http.post('proShopRelation/addProShop','proId='+proId+'&shopId='+sessionStorage.getItem('shopId')).then(result => {
                       if (result.data.state){
                           this.$message({
                               showClose:true,
                               type:'success',
                               message:'合作成功'
                           })
                           this.ruleForm.proName = '';
                       } else{
                           this.$message({
                               showClose:true,
                               type:'error',
                               message:'合作失败'
                           })
                       }
                   })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '操作取消'
                    });
                    this.ruleForm.proName = '';
                });
            },
            // 添加
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 添加供应商，并和本店建立合作关系
                        let params = Qs.stringify(this.ruleForm);
                        this.$http.post('provider/addPro',params+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                            if (result.data.state){
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'添加成功'
                                })
                                this.ruleForm = {};
                                this.init();
                            } else{
                                this.$message({
                                    showClose:true,
                                    type:'error',
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
    #providerList{
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