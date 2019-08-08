<template>
    <div id="customerList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-input v-model="queryCustomer.condition" placeholder="客户名称/联系人/员工名称//地址" style="width: 250px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchCustomer">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增客户</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>客户</th>
                    <th>联系人</th>
                    <th>联系方式</th>
                    <th>地址</th>
                    <th>归属员工</th>
                    <th>归属门店</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.customerList">
                    <td>{{index+1}}</td>
                    <td>{{item.customerName}}</td>
                    <td>{{item.customerLinkman}}</td>
                    <td>{{item.customerPhone}}</td>
                    <td>{{item.customerAddress}}</td>
                    <td>{{item.empName}}</td>
                    <td>{{item.shopName}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="customerDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="douniwan">删除</el-tag>
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
                            <h4 class="modal-title">新增客户</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="客户名称" prop="customerName">
                                    <el-input v-model="ruleForm.customerName" @blur="checkCustomer(ruleForm.customerName)"></el-input>
                                </el-form-item>
                                <el-form-item label="联系人">
                                    <el-input v-model="ruleForm.customerLinkman"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话" prop="customerPhone">
                                    <el-input type="proPhone" v-model="ruleForm.customerPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" prop="customerEmail">
                                    <el-input v-model="ruleForm.customerEmail"></el-input>
                                </el-form-item>
                                <el-form-item label="开户银行" prop="bank">
                                    <el-input v-model="ruleForm.bank"></el-input>
                                </el-form-item>
                                <el-form-item label="银行卡号" prop="bankNum">
                                    <el-input v-model="ruleForm.bankNum"></el-input>
                                </el-form-item>
                                <el-form-item label="地址" prop="customerAddress">
                                    <el-input v-model="ruleForm.customerAddress"></el-input>
                                </el-form-item>
                                <el-form-item label="负责员工" prop="empId">
                                    <el-select v-model="ruleForm.empId" filterable placeholder="请选择负责员工" size="small">
                                        <el-option label="请选择负责员工" value=""></el-option>
                                        <el-option
                                                v-for="item in empList"
                                                :key="item.id.toString()"
                                                :label="item.empName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
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

        <!--客户详情弹出框-->
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
                            <el-form :model="customer" :rules="rules" ref="customer" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="客户" prop="customerName">
                                    <el-input v-model="customer.customerName"></el-input>
                                </el-form-item>
                                <el-form-item label="联系人">
                                    <el-input v-model="customer.customerLinkman"></el-input>
                                </el-form-item>
                                <el-form-item label="联系电话" prop="customerPhone">
                                    <el-input type="proPhone" v-model="customer.customerPhone"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" prop="customerEmail">
                                    <el-input v-model="customer.customerEmail"></el-input>
                                </el-form-item>
                                <el-form-item label="开户银行">
                                    <el-input v-model="customer.bank"></el-input>
                                </el-form-item>
                                <el-form-item label="银行卡号" prop="bankNum">
                                    <el-input v-model="customer.bankNum"></el-input>
                                </el-form-item>
                                <el-form-item label="地址">
                                    <el-input v-model="customer.customerAddress"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateCustomerInfo('customer')">保存</button>
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
        name: "customerList",
        inject:['reload'],
        data() {
            return {
                customer:{},
                // 查询客户条件对象
                queryCustomer:{
                    condition:''
                },
                // 客户列表
                customerList:[],
                // 客户分页数据
                pageList:[],
                // 员工列表
                empList:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                showDetail:false,
                // 添加表单
                ruleForm: {},
                rules: {
                    customerName: [
                        { required: true, message: '请输入客户名称', trigger: 'blur' },
                    ],
                    customerPhone: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' },
                        { min: 11, max: 11, message: '请输入正确的手机号', trigger: 'blur' }
                    ],
                    proEmail: [
                        { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '邮箱格式不正确', trigger: 'blur' },
                    ],
                    bankNum:[
                        { min: 19, max: 19, message: '银行卡号有误', trigger: 'blur' }
                    ],
                    empId:[
                        { required: true, message: '请选择负责员工', trigger: 'blur' }
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
                this.$http.post('customer/findByList/').then(result => {
                    this.customerList = result.data;
                    // 初始化分页器
                    this.initPage();
                    this.getPageList(this.currentPage);
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.customerList.length;
                this.currentPage = 1;
            },
            // 多条件查询
            searchCustomer(){
                let params = Qs.stringify(this.queryCustomer);
                this.$http.post('customer/findByList',params).then(result => {
                    this.customerList = result.data;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 取消全选状态
                this.checkAll = false;
                this.checkedCustomer = [];
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let customerList = this.customerList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<customerList.length;i++){
                    let customer = customerList[i];
                    customer.checked = false;
                    pageList.push(customer);
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
                // 获取员工列表
                this.$http.post('employee/empCondition').then(result => {
                    this.empList = result.data;
                    this.showAdd = true;
                })
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 客户详情
            customerDetail(id){
                this.$http.post('customer/findByListCust','id='+id).then(result => {
                    this.customer = result.data[0];
                    this.showDetail = true;
                })
            },
            // 更新门店信息
            updateCustomerInfo(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let params = Qs.stringify(this.customer);
                        this.$http.post('customer/batchCust',params).then(result => {
                            if (result.data.result){
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
                    this.checkedCustomer = [];
                    this.pageList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedCustomer.push(item.id);
                    })
                } else{
                    this.pageList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedCustomer = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
                console.log(this.checkedCustomer);
            },
            // 门店选中状态发生改变
            handlecheckedCustomerChange(val,id) {
                let customer = {};
                this.pageList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        customer = item;
                    }
                })
                if (customer.checked){
                    // 添加到选中的列表中
                    this.checkedCustomer.push(customer.id);
                }else{
                    this.checkedCustomer.forEach((item,index) => {
                        if (item == id){
                            this.checkedCustomer.splice(index,1);
                        }
                    })
                }

                // 判断是否全部选中
                if (this.checkedCustomer.length == this.pageList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }
                this.$forceUpdate();

            },
            // 判断客户是否存在
            checkCustomer(customerName){
                // 1.判断该供应商是否已经存在
                this.$http.post('customer/findByCustomerName','customerName='+customerName).then(result => {
                    // 供应商已经存在
                    if (!result.data.state){
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'客户('+customerName+')已存在'
                        });
                        this.ruleForm.customerName = ''
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
                        this.$http.post('customer/addCustomerstatus',params).then(result => {
                            if (result.data.result){
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
            },
            // 逗你玩
            douniwan(){
                this.$confirm('确定要删除这个客户吗？搞不好你老板要怼你的', '友情提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$confirm('下定决心了吗？不会后悔吗？', '友情提示', {
                        confirmButtonText: '我意已决',
                        cancelButtonText: '我怂了',
                        type: 'warning'
                    }).then(() => {
                        this.$confirm('这个客户是你想删就删的吗？开什么国际玩笑?', '逗你玩', {
                            confirmButtonText: '我就删了',
                            cancelButtonText: '我不删了',
                            type: 'info'
                        }).then(() => {
                            this.$message({
                                showClose:true,
                                type:'warning',
                                message:'你这个月工资没了'
                            })
                        }).catch(() => {
                            this.$message({
                                type: 'info',
                                message: '已取消删除'
                            });
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '你真怂！！！'
                        });
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }

        }
    }
</script>

<style scoped lang="less">
    #customerList{
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