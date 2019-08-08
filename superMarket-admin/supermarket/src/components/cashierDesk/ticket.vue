<template>
    <div id="ticket">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-date-picker
                        v-model="queryTicket.startDate"
                        type="date"
                        size="small"
                        value-format="yyyy/MM/dd"
                        placeholder="开始日期">
                </el-date-picker>
                <el-date-picker
                        v-model="queryTicket.endDate"
                        type="date"
                        size="small"
                        value-format="yyyy/MM/dd"
                        placeholder="结束日期">
                </el-date-picker>
                <el-input v-model="queryTicket.condition" placeholder="店铺名称/单号/会员/收银员" style="width: 250px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchTicket">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增小票</el-button>
                <el-button icon="glyphicon glyphicon-download-alt" size="small" @click="exportExcel">导出excel</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>归属门店</th>
                    <th>实收金额</th>
                    <th>折扣金额</th>
                    <th>销售数量</th>
                    <th>本单积分</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.id.toString()">
                    <td>{{index+1}}</td>
                    <td>{{item.shopName}}</td>
                    <td>{{item.practicalMoney}}</td>
                    <td>{{item.discountMoney}}</td>
                    <td>{{item.sellCount}}</td>
                    <td>{{item.singleScore}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="ticketDetail(item.id)">详情</el-tag>
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

        <!--添加小票弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增小票信息</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="收银台">
                                    <el-select v-model="ruleForm.cashierDeskId" filterable placeholder="请选择收银台" size="small">
                                        <el-option label="请选择收银台" value=""></el-option>
                                        <el-option
                                                v-for="item in cashierDeskList"
                                                :key="item.id.toString()"
                                                :label="item.cashierName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="商品条码">
                                    <el-input v-model="ruleForm.goodsCode" @blur="queryGoods(ruleForm.goodsCode)"></el-input>
                                </el-form-item>
                                <el-form-item label="会员卡号">
                                    <el-input v-model="ruleForm.memberPhone" @blur="queryMember(ruleForm.memberPhone)"></el-input>
                                </el-form-item>
                                <el-form-item label="会员信息">
                                    <el-tag>会员卡号:{{member.memberPhone}}</el-tag>
                                    <el-tag>会员积分:{{member.residueSorce}}</el-tag>
                                    <el-tag>折扣:{{member.discount}}</el-tag>
                                </el-form-item>
                            </el-form>
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in goodsList" :key="item.id.toString()">
                                    <td style="vertical-align: middle">{{item.goodsName}}</td>
                                    <td style="vertical-align: middle">{{item.price}}</td>
                                    <td><el-input v-model="item.count" :disabled="true"></el-input></td>
                                    <td><el-tag type="danger">{{item.totalMoney}}</el-tag></td>
                                </tr>
                                </tbody>
                            </table>
                            <el-tag>原总价:{{ruleForm.originalCountMoney}}元</el-tag>
                            <el-tag>实收金额:{{ruleForm.practicalMoney}}元</el-tag>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="submitForm()">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>

        <!--小票详情-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">小票详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-success" role="alert">小票信息</div>
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>代号</th>
                                    <th>值</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr><td>小票编号</td><td>{{ticket.id}}</td></tr>
                                <tr><td>原价合计</td><td>{{ticket.originalCountMoney}}</td></tr>
                                <tr><td>实收金额</td><td>{{ticket.practicalMoney}}</td></tr>
                                <tr><td>折扣金额</td><td>{{ticket.discountMoney}}</td></tr>
                                <tr><td>销售数量</td><td>{{ticket.sellCount}}</td></tr>
                                </tbody>
                            </table>
                            <div class="alert alert-success" role="alert">小票详情</div>
                            <table class="my-tab table table-bordered">
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>总金额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="(item,index) in ticket.details" :key="index.toString()">
                                    <td>{{item.goods.goodsName}}</td>
                                    <td>{{item.goods.price}}</td>
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
        name: "ticket",
        inject:['reload'],
        data() {
            return {
                // 小票详情信息
                ticket:{},
                // 查询小票条件对象
                queryTicket:{},
                // 小票列表
                ticketList:[],
                // 小票分页数据
                pageList:[],
                // 当前购买商品的会员信息
                member:{},
                // 本店收银台列表
                cashierDeskList:[],
                // 选中的小票
                checkedTicket:[],
                // 商品列表,(添加小票时的商品列表)
                goodsList:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 是否显示添加框
                showAdd:false,
                // 是否显示批量修改
                showEdits:false,
                showDetail:false,
                // 添加表单
                ruleForm: {},
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
            // 获取收银单列表
            init(){
                this.$http.post('ticket/findByCondition/').then(result => {
                    this.ticketList = result.data;
                    // 初始化分页器
                    this.initPage();
                    // 获取分页数据
                    this.currentPage = 1;
                    this.getPageList(this.currentPage);
                });
            },
            // 初始化分页器
            initPage(){
                this.totalCount = this.ticketList.length;
                this.currentPage = 1;
            },
            // 多条件查询
            searchTicket(){
                let params = Qs.stringify(this.queryTicket);
                this.$http.post('ticket/findByCondition',params).then(result => {
                    this.ticketList = result.data;
                    // 获取分页数据
                    this.initPage();
                    this.getPageList(this.currentPage);
                })
            },
            // 根据当前页码获取小票分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let ticketList = this.ticketList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<ticketList.length;i++){
                    let ticket = ticketList[i];
                    pageList.push(ticket);
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
            openAdd() {
                this.ruleForm = {};
                // 获取当前登陆店铺下的收银台
                this.$http.post('cashierDesk/findByCondition','shopId='+sessionStorage.getItem("shopId")+'&cashierState=1').then(result => {
                    this.cashierDeskList = result.data;
                    this.showAdd = true;
                })
            },
            closeAdd(){
                this.showAdd = false;
                this.goodsList = [];
                this.ruleForm = {};
            },
            // 导出小票数据
            exportExcel(){
                window.location.href = this.$store.state.baseUrl+'shop/exportShop';
            },
            // 小票详情
            ticketDetail(id){
                this.$http.post('ticket/findById','id='+id).then(result => {
                    this.ticket = result.data;
                    this.showDetail = true;
                })
            },
            closeDetail(){
                this.showDetail = false;
            },
            // 商品原价，实收，折扣金额计算，销售数量计算
            ticketCalc(){
                // 已经扫描的商品
                let goodsList = this.goodsList;
                let originalCountMoney = 0; // 原总价=所有(商品单价*商品数量)
                let sellCount = 0; // 本单销售商品总件数
                // 会员
                let member = this.member;
                let practicalMoney = 0; // 实收金额=原总价*会员折扣
                let discountMoney = 0 // 折扣金额（优惠了多少）
                let singleScore = 0; // 本单积分

                goodsList.forEach((item,index) => {
                    if (item.count == null || item.count == '') item.count = 1;
                    originalCountMoney += parseInt(item.price) * parseInt(item.count);
                    sellCount += item.count;

                    // 该商品的总金额
                    item.totalMoney = parseInt(item.price) * parseInt(item.count);
                })
                // 如果使用了会员
                if (member != null && member.memberPhone != null && member.memberPhone != ''){
                    practicalMoney = originalCountMoney * member.discount;
                    discountMoney = originalCountMoney - practicalMoney;
                    singleScore = originalCountMoney;
                    this.ruleForm.memberId = member.id;
                } else{
                    practicalMoney = originalCountMoney;
                    discountMoney = 0;
                    singleScore = 0;
                }
                this.ruleForm.originalCountMoney = originalCountMoney;
                this.ruleForm.practicalMoney = practicalMoney;
                this.ruleForm.discountMoney = discountMoney;
                this.ruleForm.sellCount = sellCount;
                this.ruleForm.singleScore = singleScore;
            },
            // 根据商品条码查询商品信息
            queryGoods(goodsCode){
                // 判断商品条码是否为空
                if (goodsCode == null || goodsCode == ''){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请输入商品条码'
                    })
                    return;
                }
                // 判断该商品是否已经存在
                let goods = this.goodsList.filter(item => item.goodsCode == goodsCode)[0];
                if (goods != undefined && goods != null){
                    // 商品已经在列表中存在，只需要改变数量即可
                    if (goods.count == null || goods.count == ''){
                        goods.count = 2;
                    } else{
                        goods.count+=1;
                    }
                    this.ruleForm.goodsCode = '';
                    // 重新计算小票信息
                    this.ticketCalc();
                    this.$forceUpdate();
                    return;
                }
                this.$http.post('goods/selGoodsAndIndByGoodsCode','goodsCode='+goodsCode).then(result => {
                    if (result.data) {
                        this.goodsList.push(result.data);
                        this.ruleForm.goodsCode = '';
                        // 计算小票信息
                        this.ticketCalc();
                    }else{
                        this.$message({
                            showClose:true,
                            type:'error',
                            message:'未找到该商品信息'
                        })
                    }
                })
            },
            // 查询本店会员信息，根据会员卡号进行查询
            queryMember(memberPhone){
                if (memberPhone == null || memberPhone == ''){
                    return;
                }
                this.$http.post('member/getMemberByMemberPhoneAndShopId','memberPhone='+memberPhone+'&shopId='+sessionStorage.getItem("shopId")).then(result => {
                    let member = result.data.member;
                       if (member == null){
                           this.$message({
                               showClose:true,
                               type:'warning',
                               message:'该会员信息不存在'
                           })
                       } else{
                           member.discount = member.level.discount;
                           this.member = member;
                           // 重新计算ticketCalc;
                           this.ticketCalc();
                       }
                })
            },
            // 添加小票
            submitForm() {
                // 要实现的功能
                // 1.生成小票单
                // 2.生成小票明细
                // 3.收银
                // 4.收银成功后，同步库存

                // 判断是否选中收银台
                if (this.ruleForm.cashierDeskId == null || this.ruleForm.cashierDeskId == ''){
                    this.$message({
                        showClose:true,
                        type:'error',
                        message:'请选择收银台'
                    });
                    return;
                }
                // 判断是否有商品存在
                if (this.goodsList.length == 0){
                    this.$message({
                        showClose:true,
                        type:'error',
                        message:'没有商品信息，无法生成收银单'
                    })
                    return;
                }

                // 生成小票
                let params=Qs.stringify(this.ruleForm);
                let goodsList = JSON.stringify(this.goodsList);
                this.$http.post('ticket/addTicket',params+'&empId='+sessionStorage.getItem("empId")+'&tickDetailStr='+goodsList).then(result => {
                    if (result.data.state){
                        this.$message({
                            showClose:true,
                            type:'success',
                            message:'添加成功'
                        })
                    } else{
                        this.$message({
                            showClose:true,
                            type:'error',
                            message:'添加失败'
                        })
                    }
                })
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped lang="less">
    #ticket{
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