<template>
    <div id="goodList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-date-picker
                        v-model="queryCashierDesk.beginDate"
                        type="date"
                        size="small"
                        value-format="yyyy/MM/dd"
                        placeholder="开始日期">
                </el-date-picker>
                <el-date-picker
                        v-model="queryCashierDesk.endDate"
                        type="date"
                        size="small"
                        value-format="yyyy/MM/dd"
                        placeholder="结束日期">
                </el-date-picker>
                <el-select v-model="queryCashierDesk.goodsTypeId" filterable placeholder="全部商品类别" size="small">
                    <el-option label="全部商品类别" value=""></el-option>
                    <el-option
                            v-for="item in goodsTypeList"
                            :key="item.goodsTypeName"
                            :label="item.goodsTypeName"
                            :value="item.id">
                    </el-option>
                </el-select>
                <el-input v-model="queryCashierDesk.condition" placeholder="商品条码/商品名称" style="width: 150px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchGoods">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增商品信息</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="openSort">编辑排序</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="openEdits">批量修改</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="batchDeleteGoods" style="color: red">批量删除</el-button>
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
                    <th>商品条码</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in pageList" :key="item.goodsName">
                    <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopsChange(item.checked,item.id)"></el-checkbox></th>
                    <td>{{index+1}}</td>
                    <td>{{item.goodsCode}}</td>
                    <td>{{item.goodsName}}</td>
                    <td>零售价:{{item.price}}批发价:{{item.wholePrice}}<br>进价:{{item.costPrice}}</td>
                    <td>{{item.goodsCount}}</td>
                    <td>
                        <el-tag type="warning" size="small" @click="openDetail(item.id)">详情</el-tag>
                        <el-tag type="danger" size="small" @click="deleteGoods(item.id)">删除</el-tag>
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
        <!--添加商品弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增商品信息</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="商品类别" prop="goodsTypeId">
                                    <el-select v-model="ruleForm.goodsTypeId" filterable placeholder="请选择商品类别" size="small">
                                        <el-option
                                                v-for="item in goodsTypeList"
                                                :key="item.goodsTypeName"
                                                :label="item.goodsTypeName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="商品条码" prop="goodsCode">
                                    <el-input v-model="ruleForm.goodsCode" @change="checkGoodsCode(ruleForm.goodsCode)"></el-input>
                                </el-form-item>
                                <el-form-item label="商品名称" prop="goodsName">
                                    <el-input v-model="ruleForm.goodsName"></el-input>
                                </el-form-item>
                                <el-form-item label="供应商" prop="providerId">
                                    <el-select v-model="ruleForm.providerId" filterable placeholder="请选择供应商" size="small">
                                        <el-option
                                                v-for="item in providerList"
                                                :key="item.proName"
                                                :label="item.proName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="单位">
                                    <el-input v-model="ruleForm.unitId"></el-input>
                                </el-form-item>
                                <el-form-item label="规格" prop="specDetailId">
                                    <el-cascader
                                            :options="specTmpList"
                                            v-model="ruleForm.specDetailId"
                                            @change="handleSpecChange"
                                    >
                                    </el-cascader>
                                </el-form-item>
                                <el-form-item label="零售价" prop="price">
                                    <el-input v-model="ruleForm.price" placeholder="请输入商品零售价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="批发价" prop="wholePrice">
                                    <el-input v-model="ruleForm.wholePrice" placeholder="请输入商品批发价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="进价" prop="costPrice">
                                    <el-input v-model="ruleForm.costPrice" placeholder="请输入商品进货价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="产地" prop="goodsAddress">
                                    <el-input v-model="ruleForm.goodsAddress"></el-input>
                                </el-form-item>
                                <el-form-item label="生产日期" prop="yieldDate">
                                    <el-date-picker
                                            v-model="ruleForm.yieldDate"
                                            value-format="yyyy/MM/dd"
                                            align="right"
                                            type="date"
                                            placeholder="选择日期">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="保质期(天)" prop="expirationDay">
                                    <el-input v-model="ruleForm.expirationDay"></el-input>
                                </el-form-item>
                                <el-form-item label="过期日期" prop="expirationDate">
                                    <el-date-picker
                                            v-model="ruleForm.expirationDate"
                                            value-format="yyyy/MM/dd"
                                            align="right"
                                            type="date"
                                            placeholder="选择日期">
                                    </el-date-picker>
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

        <!--排序选择-->
        <transition>
            <div class="my-tanchukuang" v-if="showSort">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeSort">×</span></button>
                            <h4 class="modal-title">商品排序</h4>
                        </div>
                        <div class="modal-body">
                            <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="排序方式" prop="shopTypeId">
                                    <el-select v-model="sort.sortName" filterable placeholder="请选择排序方式" size="small">
                                        <el-option label="商品编号" value="g.id"></el-option>
                                        <el-option label="商品名称" value="goods_name"></el-option>
                                        <el-option label="商品库存" value="goods_count"></el-option>
                                        <el-option label="商品零售价" value="price"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="排序类别">
                                    <el-radio v-model="sort.sortType" label="asc">升序</el-radio>
                                    <el-radio v-model="sort.sortType" label="desc">降序</el-radio>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="goodsSort">排序</button>
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
                            <el-form>
                                <el-form-item label="商品类型">
                                    <el-select v-model="goods.goodsTypeId" filterable placeholder="请选择商品类型" size="small">
                                        <el-option
                                                v-for="item in goodsTypeList"
                                                :key="item.goodsTypeName"
                                                :label="item.goodsTypeName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="batchUpdateGoods(goods.goodsTypeId)">保存</button>
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
                            <h4 class="modal-title">商品详情</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="goods" :rules="rules" ref="goodsForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="商品条码" prop="goodsCode">
                                    <el-input v-model="goods.goodsCode" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="商品类别" prop="goodsTypeId">
                                    <el-select v-model="goods.goodsTypeId" filterable placeholder="请选择商品类别" size="small">
                                        <el-option
                                                v-for="item in goodsTypeList"
                                                :key="item.goodsTypeName"
                                                :label="item.goodsTypeName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="商品名称" prop="goodsName">
                                    <el-input v-model="goods.goodsName"></el-input>
                                </el-form-item>
                                <el-form-item label="供应商" prop="providerId">
                                    <el-select v-model="goods.providerId" filterable placeholder="请选择供应商" size="small">
                                        <el-option
                                                v-for="item in providerList"
                                                :key="item.proName"
                                                :label="item.proName"
                                                :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="单位">
                                    <el-input v-model="goods.unitId"></el-input>
                                </el-form-item>
                                <el-form-item label="规格" prop="specDetailId">
                                    <el-cascader
                                            :options="specTmpList"
                                            v-model="goods.specDetailId"
                                            @change="handleSpecChange"
                                    >
                                    </el-cascader>
                                </el-form-item>
                                <el-form-item label="库存">
                                    <el-input v-model="goods.goodsCount" :disabled="true"></el-input>
                                </el-form-item>
                                <el-form-item label="零售价" prop="price">
                                    <el-input v-model="goods.price" placeholder="请输入商品零售价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="批发价" prop="wholePrice">
                                    <el-input v-model="goods.wholePrice" placeholder="请输入商品批发价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="进价" prop="costPrice">
                                    <el-input v-model="goods.costPrice" placeholder="请输入商品进货价，单位（元）"></el-input>
                                </el-form-item>
                                <el-form-item label="产地" prop="goodsAddress">
                                    <el-input v-model="goods.goodsAddress"></el-input>
                                </el-form-item>
                                <el-form-item label="生产日期" prop="yieldDate">
                                    <el-date-picker
                                            v-model="goods.yieldDate"
                                            value-format="yyyy/MM/dd"
                                            align="right"
                                            type="date"
                                            placeholder="选择日期">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item label="保质期(天)" prop="expirationDay">
                                    <el-input v-model="goods.expirationDay"></el-input>
                                </el-form-item>
                                <el-form-item label="过期日期" prop="expirationDate">
                                    <el-date-picker
                                            v-model="goods.expirationDate"
                                            value-format="yyyy/MM/dd"
                                            align="right"
                                            type="date"
                                            placeholder="选择日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateGoods('goodsForm')">保存</button>
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
        name: "goodsList",
        inject:['reload'],
        data() {
            return {
                goods:{},
                // 查询商品条件
                queryCashierDesk:{},
                // 商品列表
                goodsList:[],
                // 商品分页数据
                pageList:[
                    {id:'1',goodsCode:'1001',goodsName:'养乐多',shopName:'洋洋便利店',price:'5',wholePrice:'5',costPrice:'5',goodsCount:'5',yieldDate:'2019-8-9'},
                    {id:'2',goodsCode:'1002',goodsName:'养乐多多',shopName:'洋洋便利店',price:'5',wholePrice:'5',costPrice:'5',goodsCount:'5',yieldDate:'2019-8-9'}
                ],
                // 商品类型
                goodsTypeList:[{id:1,goodsTypeName:'内衣'},{id:2,goodsTypeName:'鞋包'}],
                // 供应商列表
                providerList:[],
                // 商品规格
                specTmpList:[],
                // 选中的商品
                checkedGoods:[],
                // 分页数据
                currentPage: 1,
                totalCount:0,
                pageSize:10,
                // 排序
                sort:{
                  sortName:'g.id',
                  sortType:'asc'
                },
                // 是否显示添加框
                showAdd:false,
                // 是否显示排序选择
                showSort:false,
                // 是否显示批量修改
                showEdits:false,
                // 是否显示详情
                showDetail:false,
                // 是否选中所有
                checkAll:false,
                // 添加商品的表单对象
                ruleForm: {
                },
                rules: {
                    goodsTypeId:[
                        { required: true, message: '请选择商品类型', trigger: 'blur' }
                    ],
                    goodsCode: [
                        { required: true, message: '请输入商品条码', trigger: 'blur' },
                    ],
                    goodsName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                    ],
                    providerId: [
                        { required: true, message: '请选择供应商', trigger: 'blur' },
                    ],
                    specDetailId: [
                        { required: true, message: '请选择商品规格', trigger: 'blur' },
                    ],
                    price: [
                        { required: true, message: '请输入商品零售价', trigger: 'blur' },
                        { pattern:/(?!^0*(\.0{1,2})?$)^\d{1,13}(\.\d{1,2})?$/,message:'请输入合法金额',trigger:'blur'}
                    ],
                    wholePrice: [
                        { required: true, message: '请输入商品批发价', trigger: 'blur' },
                        { pattern:/(?!^0*(\.0{1,2})?$)^\d{1,13}(\.\d{1,2})?$/,message:'请输入合法金额',trigger:'blur'}
                    ],
                    costPrice: [
                        { required: true, message: '请输入商品进价', trigger: 'blur' },
                        { pattern:/(?!^0*(\.0{1,2})?$)^\d{1,13}(\.\d{1,2})?$/,message:'请输入合法金额',trigger:'blur'}
                    ],
                    yieldDate: [
                        { required: true, message: '生产日期不能为空', trigger: 'blur' }
                    ],
                    expirationDay: [
                        { required: true, message: '保质期不能为空', trigger: 'blur' },
                        { pattern:/^[1-9]\d*$/,message:'保质期为一个正整数',trigger:'blur'}
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
                this.$http.post('goods/findByCondition/').then(result => {
                    this.goodsList = result.data;
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
                this.totalCount = this.goodsList.length;
                this.currentPage = 1;
            },
            // 根据当前页码获取门店分页数据 pageNo 当前页码
            getPageList(pageNo){
                // 取消全选状态
                this.checkAll = false;
                this.checkedGoods = [];
                // 页面大小
                let pageSize = this.pageSize;
                // 进行数据截取
                // 起始下标
                let startIndex = (pageNo-1)*pageSize;
                // 原始数据
                let goodsList = this.goodsList;
                // 分页数据
                let pageList = [];
                let pageCapacity = 0;
                for (let i = startIndex;i<goodsList.length;i++){
                    let goods = goodsList[i];
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
            searchGoods(){
                let params = Qs.stringify(this.queryCashierDesk);
                this.$http.post('goods/findByCondition',params).then(result => {
                    this.goodsList = result.data;
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
            // 打开排序选择
            openSort(){
                this.showSort = true;
            },
            closeSort(){
                this.showSort = false;
            },
            // 商品排序
            goodsSort(){
                let params = this.queryCashierDesk;
                params.sortName = this.sort.sortName;
                params.sortType = this.sort.sortType;
                this.$http.post('goods/findByCondition',Qs.stringify(params)).then(result => {
                    this.goodsList = result.data;
                    // 初始化分页器
                    this.initPage();
                    // 获取分页数据
                    this.getPageList(this.currentPage);
                    this.showSort = false;
                })
            },
            // 获取商品规格
            getSpecTmpList(){
                // 查询商品规格
                this.$http.post('specTmp/seSpecTmpAndSpecAndSd').then(result => {
                    // 规格模板
                    let specTmpList = result.data.specTmpList;
                    // 规格
                    let specList = result.data.specList;
                    // 规格详情
                    let sdList = result.data.sdList;
                    // 遍历规格模板
                    specTmpList.forEach((item, index) => {
                        let specTmp = {label: item.specTmpName, value: item.specTmpName, id: item.id};
                        specTmp.children = [];
                        // 遍历规格，并和specTmp进行匹配
                        specList.forEach((item, index) => {
                            let spec = {label: item.specName, value: item.specName, id: item.id};
                            spec.children = [];
                            if (item.specTmpId == specTmp.id) {
                                sdList.forEach((item, index) => {
                                    let detail = {label: item.specDetailName, value: item.id};
                                    if (item.specId == spec.id) {
                                        spec.children.push(detail);
                                    }
                                });
                                specTmp.children.push(spec);
                            }
                        });
                        this.specTmpList.push(specTmp);
                    })
                })
            },
            openAdd() {
                this.ruleForm = {};
                // 获取供应商列表
                this.$http.post('provider/proList','shopId='+sessionStorage.getItem("shopId")+'&proStatus=1').then(result => {
                    this.providerList = result.data.providerList;
                });
                // 获取商品规格
                this.getSpecTmpList();
                this.showAdd = true;
            },
            // 规格模板选中发生改变
            handleSpecChange(value){
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 打开批量修改窗口
            openEdits(){
                // 判断是否有选中的门店
                if (this.checkedGoods.length == 0){
                    this.$message({
                        showClose: true,
                        type:'warning',
                        message: '请选择要批量操作的数据'
                    });
                    return;
                }
              this.showEdits = true;
            },
            // 批量修改
            batchUpdateGoods(goodsTypeId){
                let ids = this.checkedGoods.join(',');
                this.$http.post('goods/batchModifyGoods','ids='+ids+'&goodsTypeId='+goodsTypeId).then(result => {
                    if (result.data.result){
                        this.$message({
                            showClose: true,
                            type:'warning',
                            message: '修改成功'
                        });
                        this.init();
                        this.showEdits = false;
                    } else{
                        this.$message({
                            showClose: true,
                            type:'warning',
                            message: '修改失败'
                        });
                    }
                })
            },
            closeEdits(){
                this.showEdits = false;
            },
            // 选中所有商品
            handleCheckAllChange(val) {
                //console.log(val);
                if (val){
                    // 选中所有
                    this.checkedGoods = [];
                    this.pageList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedGoods.push(item.id);
                    })
                } else{
                    this.pageList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedGoods = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 商品选中状态发生改变
            handleCheckedShopsChange(val,id) {
                let goods = {};
                this.pageList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        goods = item;
                    }
                })
                // 判断是否选中
                if (goods.checked){
                    // 添加到选中的列表中
                    this.checkedGoods.push(goods.id);
                }else{
                    this.checkedGoods.forEach((item,index) => {
                        if (item == id){
                            this.checkedGoods.splice(index,1);
                        }
                    })
                }
                // 判断是否全部选中
                if (this.checkedGoods.length == this.pageList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }

                this.$forceUpdate();
            },
            openDetail(id){
                // 获取供应商列表
                this.$http.post('provider/proList').then(result => {
                    this.providerList = result.data.providerList;
                });
                // 获取商品规格
                this.getSpecTmpList();

                this.$http.post('goods/findByCondition','id='+id).then(result => {
                    let goods = result.data[0];
                    goods.yieldDate = goods.yieldDate
                    goods.specDetailId = [goods.spceTmpName,goods.specName,goods.specDetailId];
                    this.goods = goods;
                    this.showDetail = true;
                })
            },
            // 保存更新
            updateGoods(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let specDetailId = this.goods.specDetailId[this.goods.specDetailId.length-1];
                        this.goods.specDetailId = specDetailId;
                        let params = Qs.stringify(this.goods);
                        this.$http.post('goods/updateGoods',params).then(result => {
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
            // 删除商品批量
            batchDeleteGoods(){
                // 判断是否有选中的商品
                if (this.checkedGoods.length == 0){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请选择要进行操作的数据'
                    })
                    return;
                }
                let ids = this.checkedGoods.join(',');
                this.dels(ids);
            },
            // 根据id删除指定商品
            deleteGoods(id){
                this.dels(id);
            },
            dels(ids){
                // 友情提示
                let flag = window.confirm("确定删除编号为("+ids+")的数据吗？");

                if (flag == false) return;
                this.$http.post('goods/batchDeleteGoods','ids='+ids).then(result => {
                    if (result.data.result){
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'商品删除成功'
                        })
                        this.init();
                    }else{
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'商品删除失败'
                        })
                    }
                })
            },
            // 导出数据
            exportExcel(){
                window.location.href = this.$store.state.baseUrl+'goods/exportGoods';
            },
            // 检查商品条码是否已经存在
            checkGoodsCode(code){
                this.$http.post('goods/findByGoodsCode','goodsCode='+code).then(result => {
                    if (result.data.result){
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'商品条码'+code+'已经存在'
                        })
                        this.ruleForm.goodsCode = '';
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
                        this.$http.post('goods/addGoods',params).then(result => {
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
    #goodList{
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