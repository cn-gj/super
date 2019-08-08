<template>
    <div id="shopList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-select v-model="queryMember.levelId" filterable placeholder="请选择会员等级" size="small">
                    <el-option label="请选择会员等级" value=""></el-option>
                    <el-option
                            v-for="item in levelList"
                            :key="item.id.toString()"
                            :label="item.levelName"
                            :value="item.id">
                    </el-option>
                </el-select>
                <el-select v-model="queryMember.memberSex" filterable placeholder="请选择会员性别" size="small">
                    <el-option label="请选择会员性别" value=""></el-option>
                    <el-option label="女" value="女"></el-option>
                    <el-option label="男" value="男"></el-option>
                </el-select>
                <el-select v-model="queryMember.shopId" filterable placeholder="门店名称" size="small">
                    <el-option label="全部门店名称" value=""></el-option>
                    <el-option
                            v-for="item in shopNameList"
                            :key="item.id.toString()"
                            :label="item.shopName"
                            :value="item.id">
                    </el-option>
                </el-select>
                <el-input v-model="queryMember.condition" placeholder="会员卡号/会员昵称/手机号码" style="width: 150px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchMember">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增会员</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="openEdits">批量修改</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="openSorce">增减积分</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="batchDeleteMember" style="color: red">删除会员</el-button>
                <el-button icon="glyphicon glyphicon-download-alt" size="small" @click="exportExcel">导出excel</el-button>
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
                    <th>门店名称</th>
                    <th>门店编码</th>
                    <th>会员卡号</th>
                    <th>会员昵称</th>
                    <th>手机号码</th>
                    <th>会员等级</th>
                    <th>会员性别</th>
                    <th>累计金额</th>
                    <th>钱包余额</th>
                    <th>累计积分</th>
                    <th>剩余积分</th>
                    <th>生日</th>
                    <th>注册时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.id.toString()">
                    <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopsChange(item.checked,item.id)"></el-checkbox></th>
                    <td>{{index+1}}</td>
                    <td>{{item.shop.shopName}}</td>
                    <td>{{item.shop.id}}</td>
                    <td>{{item.memberPhone}}</td>
                    <td>{{item.memberName}}</td>
                    <td>{{item.memberPhone}}</td>
                    <td>{{item.level.levelName}}</td>
                    <td>{{item.memberSex}}</td>
                    <td>{{item.totalMoney}}</td>
                    <td>{{item.residueMoney}}</td>
                    <td>{{item.totalSorce}}</td>
                    <td>{{item.residueSorce}}</td>
                    <td>{{item.memberBirthday}}</td>
                    <td>{{item.createDate}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="memberDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="deleteMember(item.id)">删除</el-tag>
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

        <!--添加会员弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增会员信息</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="会员等级" prop="levelId">
                                    <el-select v-model="ruleForm.levelId" filterable placeholder="请选择会员等级" size="small">
                                        <el-option
                                                v-for="item in levelList"
                                                :key="item.id.toString()"
                                                :label="item.levelName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="会员名称" prop="memberName">
                                    <el-input v-model="ruleForm.memberName"></el-input>
                                </el-form-item>
                                <el-form-item label="性别">
                                    <el-radio v-model="ruleForm.memberSex" label="男">男</el-radio>
                                    <el-radio v-model="ruleForm.memberSex" label="女">女</el-radio>
                                </el-form-item>
                                <el-form-item label="手机号" prop="memberPhone">
                                    <el-input  v-model="ruleForm.memberPhone" @blur="checkMemberPhone(ruleForm.memberPhone)"></el-input>
                                </el-form-item>
                                <el-form-item label="会员生日">
                                    <el-date-picker
                                            v-model="ruleForm.memberBirthday"
                                            type="date"
                                            value-format="yyyy/MM/dd"
                                            placeholder="选择日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="submitForm('ruleForm')">添加</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--批量修改-->
        <transition>
            <div class="my-tanchukuang" v-if="showEdits">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeEdits">×</span></button>
                            <h4 class="modal-title">批量修改</h4>
                        </div>
                        <div class="modal-body">
                            <div class="my-edit-item">
                                <el-form>
                                    <el-form-item label="会员等级">
                                        <el-select v-model="levelId" placeholder="请选择会员等级" size="small">
                                            <el-option
                                                    v-for="item in levelList"
                                                    :key="item.id.toString()"
                                                    :label="item.levelName"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="batchUpdateMember">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--增减积分-->
        <transition>
            <div class="my-tanchukuang" v-if="showSorce">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeSorce">×</span></button>
                            <h4 class="modal-title">增减积分</h4>
                        </div>
                        <div class="modal-body">
                            <div class="my-edit-item">
                                <el-form>
                                    <el-form-item label="增减积分数额">
                                        <el-input v-model="totalSorce"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="batchUpdateMemberSocre">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>
        <!--会员详情弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">会员详情</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="member" :rules="rules" ref="member" label-width="80px">
                                <el-form-item label="会员卡号">
                                    <el-tag>{{member.memberPhone}}</el-tag>
                                </el-form-item>
                                <el-form-item label="会员名称" prop="memberName">
                                    <el-input v-model="member.memberName"></el-input>
                                </el-form-item>
                                <el-form-item label="会员性别">
                                    <el-radio v-model="member.memberSex" label="男">男</el-radio>
                                    <el-radio v-model="member.memberSex" label="女">女</el-radio>
                                </el-form-item>
                                <el-form-item label="归属门店">
                                    <el-input v-model="member.shop.shopName" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="注册时间" >
                                    <el-input v-model="member.createDate" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="总金额">
                                    <el-input v-model="member.totalMoney" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="剩余金额">
                                    <el-input v-model="member.residueMoney" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="消费积分" >
                                    <el-input v-model="member.residueMoney" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="剩余积分" >
                                    <el-input v-model="member.residueMoney" :disabled="true"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateMember('member')">保存</button>
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
        name: "shopList",
        inject:['reload'],
        data() {
            return {
                member:{},
                levelId:'',
                totalSorce:'',
                // 会员查询对象
                queryMember:{},
                // 会员列表
                memberList:[],
                // 会员等级
                levelList:[],
                pageList:[],
                // 门店名称列表
                shopNameList:[],
                // 选中的会员
                checkedMemberList:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                // 是否显示批量修改
                showEdits:false,
                showSorce:false,
                showDetail:false,
                // 是否选中所有
                checkAll:false,
                // 添加表单
                ruleForm: {},
                rules: {
                    levelId:[
                        { required: true, message: '请选择会员类型', trigger: 'blur' },
                    ],
                    memberName: [
                        { required: true, message: '请输入会员名称', trigger: 'blur' },
                    ],
                    memberPhone: [
                        { required: true, message: '请输入会员手机号', trigger: 'blur' },
                        { min: 11, max: 11, message: '手机格式不正确', trigger: 'blur' }
                    ]
                }
            }
        },
        created(){
            // 初始化
            this.init();
        },
        methods: {
            // 刷新当前页面
            refresh() {
                this.reload();
            },
            // 获取会员列表
            init() {
                this.$http.post('member/memberList/').then(result => {
                    this.memberList = result.data.memberVoList;
                    // 初始化分页器
                    this.initPage();
                    this.getPageList(this.currentPage);
                });
                this.$http.post('level/selectlevel/').then(result => {
                    this.levelList = result.data.levellist;
                });
                this.$http.post('shop/shopNameList/').then(result => {
                    this.shopNameList = result.data;
                });
            },
            // 初始化分页器
            initPage() {
                this.totalCount = this.memberList.length;
                this.currentPage = 1;
            },
            // 多条件查询
            searchMember() {
                let params = Qs.stringify(this.queryMember);
                this.$http.post('member/memberList', params).then(result => {
                    this.memberList = result.data.memberVoList;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo) {
                // 取消全选状态
                this.checkAll = false;
                this.checkedMemberList = [];
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo - 1) * pageSize;
                // 原始数据
                let memberList = this.memberList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex; i < memberList.length; i++) {
                    let member = memberList[i];
                    member.checked = false;
                    pageList.push(member);
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
                this.showAdd = true;
            },
            closeAdd() {
                this.showAdd = false;
            },
            // 打开批量修改窗口
            openEdits() {
                // 判断是否有选中的门店
                if (this.checkedMemberList.length == 0) {
                    this.$message({
                        showClose: true,
                        type: 'warning',
                        message: '请选择要批量操作的数据'
                    });
                    return;
                }
                this.showEdits = true;
            },
            // 批量修改
            batchUpdateMember() {
                let levelId = this.levelId;
                let ids = this.checkedMemberList.join(',');
                if (levelId == null || levelId == '') {
                    this.$message({
                        showClose: true,
                        type: 'warning',
                        message: '请选择会员等级'
                    });
                    return;
                }
                this.$http.post('member/batchML', 'levelId=' + levelId + '&ids=' + ids).then(result => {
                    if (result.data.state) {
                        this.$message({
                            showClose: true,
                            type: 'success',
                            message: '修改成功'
                        });
                        this.init();
                    } else {
                        this.$message({
                            showClose: true,
                            type: 'error',
                            message: '修改失败'
                        });
                    }
                })
            },
            closeEdits() {
                this.showEdits = false;
            },
            openSorce(){
                // 是否选中数据
                if(this.checkedMemberList.length == 0){
                    this.$message({
                        showClose: true,
                        type: 'warning',
                        message: '请选择要进行操作的数据'
                    });
                    return;
                }
                this.showSorce = true;
            },
            // 增减积分
            batchUpdateMemberSocre(){
                if (this.totalSorce == null || this.totalSorce == ''){
                    this.$message({
                        showClose: true,
                        type: 'warning',
                        message: '请输入增减积分数额'
                    });
                    return;
                }

                // 进行增减操作
                let ids = this.checkedMemberList.join(',');
                this.$http.post('member/batchSorce','ids='+ids+'&totalSorce='+this.totalSorce).then(result => {
                    if (result.data.state){
                        this.$message({
                            showClose: true,
                            type: 'success',
                            message: '修改成功'
                        });
                        this.init();
                    } else{
                        this.$message({
                            showClose: true,
                            type: 'error',
                            message: '修改失败'
                        });
                    }
                })
            },
            closeSorce(){
                this.showSorce = false;
            },
            // 批量删除
            batchDeleteMember(){
                if (this.checkedMemberList.length == 0){
                    this.$message({
                        showClose: true,
                        type: 'warning',
                        message: '请选择要进行操作的数据'
                    });
                    return;
                }
                let ids = this.checkedMemberList.join(',');
                this.dels(ids);
            },
            // 根据id 删除
            deleteMember(id){
                this.dels(id);
            },
            dels(ids){
                this.$http.post('member/delMember','ids='+ids).then(result => {
                    if (result.data.state){
                        this.$message({
                            showClose: true,
                            type: 'success',
                            message: '删除成功'
                        });
                        this.init();
                    }else{
                        this.$message({
                            showClose: true,
                            type: 'error',
                            message: '删除失败'
                        });
                    }
                })
            },
            // 导出数据
            exportExcel(){
                window.location.href = this.$store.state.baseUrl +'member/exportMember';
            },
            // 门店详情
            memberDetail(id) {
                // 获取门店详情
                this.$http.post('member/memberList', 'id=' + id).then(result => {
                    this.member = result.data.memberVoList[0];
                    this.showDetail = true;
                })
            },
            // 更新会员信息
            updateMember(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.spId = '3';
                        let params = 'id='+this.member.id+'&memberName='+this.member.memberName+'&memberSex='+this.member.memberSex;
                        this.$http.post('member/upMember', params).then(result => {
                            if (result.data.state) {
                                this.$message({
                                    showClose: true,
                                    type: 'success',
                                    message: '保存成功'
                                });
                                this.init();
                            } else {
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '保存失败'
                                })
                            }
                        })
                    }else{
                        this.$message({
                            showClose: true,
                            type: 'warning',
                            message: '请填写完整信息'
                        })
                    }
                })
            },
            closeDetail() {
                this.showDetail = false;
            },
            // 选中所有会员
            handleCheckAllChange(val) {
                if (val) {
                    // 选中所有
                    this.checkedMemberList = [];
                    this.pageList.forEach((item, index) => {
                        item.checked = true;
                        this.checkedMemberList.push(item.id);
                    })
                } else {
                    this.pageList.forEach((item, index) => {
                        item.checked = false;
                        this.checkedMemberList = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 门店选中状态发生改变
            handleCheckedShopsChange(val, id) {
                let member = {};
                this.pageList.forEach((item, index) => {
                    if (item.id == id) {
                        item.checked = val;
                        member = item;
                    }
                })
                if (member.checked) {
                    // 添加到选中的列表中
                    this.checkedMemberList.push(member.id);
                } else {
                    this.checkedMemberList.forEach((item, index) => {
                        if (item == id) {
                            this.checkedMemberList.splice(index, 1);
                        }
                    })
                }
                // 判断是否全部选中
                if (this.checkedMemberList.length == this.pageList.length) {
                    this.checkAll = true;
                } else {
                    this.checkAll = false;
                }
                this.$forceUpdate();

            },
            // 判断会员是否已经存在
            checkMemberPhone(memberPhone) {
                this.$http.post('member/getMeByphone', 'memberPhone=' + memberPhone+'&shopId=').then(result => {
                    if (result.data.member != null) {
                        this.$message({
                            showClose: true,
                            type: 'warning',
                            message: '会员号<' + memberPhone + '>已存在'
                        })
                        this.ruleForm.memberPhone = '';
                    }
                })
            },
            // 添加
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.spId = sessionStorage.getItem('shopId');
                        let params = Qs.stringify(this.ruleForm);
                        this.$http.post('member/addmember', params).then(result => {
                            if (result.data.state) {
                                this.$message({
                                    showClose: true,
                                    type: 'success',
                                    message: '添加成功'
                                });
                                this.ruleForm = {};
                                this.init();
                            } else {
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '添加失败'
                                })
                            }
                        })
                    }else{
                        this.$message({
                            showClose: true,
                            type: 'warning',
                            message: '请填写完整信息'
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped lang="less">
    #shopList{
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