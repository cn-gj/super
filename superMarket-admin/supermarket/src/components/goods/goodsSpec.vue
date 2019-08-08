<template>
    <div id="goodList">
        <div class="my-content">
            <!--搜索区-->
            <div class="my-search">
                <el-input v-model="querySpecTmp.condition" placeholder="模板名称" style="width: 150px" size="small"></el-input>
                <el-button type="primary" size="small" @click="searchSpecTmp">查询</el-button>
            </div>
            <!--工具-->
            <div class="my-tools">
                <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增商品规格</el-button>
                <el-button icon="glyphicon glyphicon-edit" size="small" @click="batchDeleteSpecTmp" style="color: red">删除规格模板</el-button>
                <el-button icon="glyphicon glyphicon-refresh" size="small">刷新数据</el-button>
            </div>
            <!--数据-->
            <table class="my-tab table table-bordered">
                <thead>
                <tr>
                    <th>
                        <el-checkbox v-model="checkAll" @change="handleCheckAllChange"></el-checkbox>
                    </th>
                    <th>序号</th>
                    <th>模板编码</th>
                    <th>模板名称</th>
                    <th>规格数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in specTmpList" :key="item.id.toString()">
                    <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopsChange(item.checked,item.id)"></el-checkbox></th>
                    <td>{{index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.specTmpName}}</td>
                    <td>{{item.sCount}}</td>
                    <td>
                        <el-tag type="danger" size="small" @click="updateSpecTmpName(item.id,item.specTmpName)">修改名称</el-tag>
                        <el-tag type="warning" size="small" @click="specDetail(item.id)">详情</el-tag>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <!--添加商品规格-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增商品规格</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="模板名称" prop="specTmpName">
                                    <el-input v-model="ruleForm.specTmpName" @blur="checkSpecTmpNameChange(ruleForm.specTmpName)"></el-input>
                                </el-form-item>
                                <el-form-item label="规格数">
                                    <el-select v-model="selectedSpecCount" filterable size="small">
                                        <el-option
                                                v-for="item in specCount"
                                                :key="item.toString()"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item v-for="item in specInputs" :label="'规格'+item.count" :key="item.count.toString()">
                                    <el-input v-model="item.val"></el-input>
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

        <!--规格详情编辑弹出框-->
        <transition>
            <div class="my-tanchukuang" v-if="showDetail">
                <div>
                    <div class="my-modal modal-content"  style="width: 800px;margin-left: -400px;">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeDetail">×</span></button>
                            <h4 class="modal-title">编辑规格模板-({{specTmp.specTmpName}})</h4>
                        </div>
                        <div class="modal-body">
                            <el-tabs type="border-card">
                                <el-tab-pane v-for="item in specTmp.specList" :label="item.specName" :key="item.id.toString()">
                                    <el-button type="primary" size="small" @click="addSpecDetail(item.id)">新增规格内容</el-button>
                                    <el-button type="primary" size="small" @click="updateSpecName(item.id,item.specName)">修改规格名称</el-button>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>编码</th>
                                            <th>名称</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr v-if="item.sdList.length == 0">
                                            <td>暂无数据</td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr v-for="sd in item.sdList" :key="sd.id.toString()">
                                            <td>{{sd.id}}</td>
                                            <td>{{sd.specDetailName}}</td>
                                            <td>
                                                <el-tag type="danger" size="small" @click="delSpecDetail(sd.id)">删除</el-tag>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </el-tab-pane>
                            </el-tabs>
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
        name: "goodsSpec",
        data() {
            return {
                // 规格模板。模板下的规格，规格下的详情
                specTmp:'',
                // 记录最近一次查看的模板详情中的模板id
                specTmpId:'',
                // 规格模板列表
                specTmpList:[],
                querySpecTmp:{},
                // 规格数可选值
                specCount:[1,2,3,4,5,6,7,8,9,10],
                // 当前选中的规格数
                selectedSpecCount:1,
                specInputs:[{count:1,val:''}],
                // 规格详情列表
                specDetailList:[
                    {id:1,specDetailName:'红色'}
                ],
                // 是否显示添加框
                showAdd:false,
                showDetail:false,
                // 是否选中所有
                checkAll:false,
                // 选中的规格模板
                checkedSpecTmpList:[],
                // 添加表单
                ruleForm: {

                },
                rules: {
                    specTmpName: [
                        { required: true, message: '请输入规格模板名称', trigger: 'blur' },
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
            // 获取规格模板
            init(){
                // let params = Qs.stringify(this.querySpecTmp);
                this.$http.post('specTmp/showSpecTmp/').then(result => {
                    let specTmpList = result.data.specTmpList;
                    specTmpList.forEach((item,index) => {
                        item.checked = false;
                    })
                    this.specTmpList = specTmpList;
                });
            },
            // 批量删除
            batchDeleteSpecTmp(){
                if (this.checkedSpecTmpList.length == 0){
                    this.$message({
                        type: 'warning',
                        message: '请选择要进行操作的数据'
                    });
                    return;
                }
                let ids = this.checkedSpecTmpList.join(',');
                this.dels(ids);
            },
            dels(ids){
                // 规格被商品占用时会删除失败
                this.$http.post('specTmp/delSpecTmp','ids='+ids).then(result => {
                    if (result.data.state){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.init();
                    } else{
                        this.$message({
                            type: 'danger',
                            message: '当前规格正在被商品占用'
                        });
                    }
                })
            },
            // 多条件查询
            searchSpecTmp(){
                let params = Qs.stringify(this.querySpecTmp);
                this.$http.post('specTmp/showSpecTmp',params).then(result => {
                    this.specTmpList = result.data.specTmpList;
                 })
            },
            openAdd() {
                this.showAdd = true;
            },
            closeAdd(){
                this.showAdd = false;
            },
            // 修改规格模板名称
            updateSpecTmpName(id,specTmpName){
                this.$prompt('请输入规格名称', '修改规格模板名称', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '规格名称有误',
                    inputValue: specTmpName
                }).then(({ value }) => {
                    if (value == null || value == ''){
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'模板名称不能为空'
                        });
                        return;
                    }
                    // 判断规格模板是否已经存在
                    this.$http.post('specTmp/selSpecTmpByName', 'specTmpName=' + value).then(result => {
                        if (result.data.state) {
                            this.$message({
                                showClose:true,
                                type:'warning',
                                message:'规格模板<'+value+'>已存在'
                            });
                        }else{
                            // 修改
                            this.$http.post('specTmp/upSpecTmp','id='+id+'&specTmpName='+value).then(result => {
                                if (result.data.state){
                                    this.$message({
                                        type: 'success',
                                        message: '修改成功'
                                    });
                                    // 重新初始化数据
                                    this.init();
                                } else{
                                    this.$message({
                                        type: 'danger',
                                        message: '修改失败'
                                    });
                                }
                            })
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            // 规格详情
            specDetail(id){
                this.specTmpId = id;
                // 获取规格模板下的规格和规格下的详情
                this.$http.post('specTmp/showSpec','ids='+id).then(result => {
                    this.specTmp = result.data.specTmpList[0];
                    this.showDetail = true;
                });
            },
            // 修改规格名称
            updateSpecName(id,specName){
                this.$prompt('请输入规格名称', '修改规格名称', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '规格名称有误',
                    inputValue:specName
                }).then(({ value }) => {
                    if (value == null || value == ''){
                        this.$message({
                            type: 'warning',
                            message: '规格名称不能为空'
                        });
                        return;
                    }
                    this.$http.post('spec/upSpec','id='+id+'&specName='+value).then(result => {
                        if (result.data.state){
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            });
                            // 重新初始化数据
                            this.init();
                            // 刷新当前显示的规格详情内容
                            this.specDetail(this.specTmpId);
                        } else{
                            this.$message({
                                type: 'success',
                                message: '修改失败'
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
            // 新增规格内容
            addSpecDetail(specId){
                this.$prompt('请输入规格内容', '新增规格内容', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '规格名称有误'
                }).then(({ value }) => {
                    if (value == null || value == ''){
                        this.$message({
                            type: 'warning',
                            message: '规格内容不能为空'
                        });
                        return;
                    }
                    this.$http.post('specDetail/addSpecDetail','specId='+specId+'&specDetailName='+value).then(result => {
                        if (result.data.state){
                            this.$message({
                                type: 'success',
                                message: '添加成功'
                            });
                            // 重新初始化数据
                            this.init();
                            // 刷新当前显示的规格详情内容
                            this.specDetail(this.specTmpId);
                        } else{
                            this.$message({
                                type: 'success',
                                message: '添加失败'
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
            // 根据指定id删除规格详情
            delSpecDetail(id){
                // 判断是否有商品正在使用该规格
                this.$http.post('specDetail/selGoodsBySpecDetailId','ids='+id).then(result => {
                    if (result.data.state){
                        // 删除
                        this.$http.post('specDetail/delSpecDetail','ids='+id).then(result => {
                            if (result.data.state){
                                this.$message({
                                    type: 'success',
                                    message: '删除成功'
                                });
                                // 重新初始化数据
                                this.init();
                                // 刷新当前显示的规格详情内容
                                this.specDetail(this.specTmpId);
                            } else{
                                this.$message({
                                    type: 'danger',
                                    message: '删除失败'
                                });
                            }
                        })
                    }else{
                        this.$message({
                            type: 'warning',
                            message: '该规格内容正在被商品使用'
                        });
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
                    this.checkedSpecTmpList = [];
                    this.specTmpList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedSpecTmpList.push(item.id);
                    })
                } else{
                    this.specTmpList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedSpecTmpList = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 门店选中状态发生改变
            handleCheckedShopsChange(val,id) {
                let specTmp = {};
                this.specTmpList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        specTmp = item;
                    }
                })
                if (specTmp.checked){
                    // 添加到选中的列表中
                    this.checkedSpecTmpList.push(specTmp.id);
                }else{
                    this.checkedSpecTmpList.forEach((item,index) => {
                        if (item == id){
                            this.checkedSpecTmpList.splice(index,1);
                        }
                    })
                }

                // 判断是否全部选中
                if (this.checkedSpecTmpList.length == this.specTmpList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }

                this.$forceUpdate();
            },
            // 判断规格模板是否已经存在
            checkSpecTmpNameChange(specTmpName) {
                this.$http.post('specTmp/selSpecTmpByName', 'specTmpName=' + specTmpName).then(result => {
                    if (result.data.state) {
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'规格模板<'+specTmpName+'>已存在'
                        });
                        this.ruleForm.specTmpName = '';
                    }
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 判断规格是否填写
                        let flag = true;
                        for (let i =0;i<this.specInputs.length;i++){
                            if (this.specInputs[i].val == null || this.specInputs[i].val == ''){
                                this.$message({
                                    showClose:true,
                                    type:'warning',
                                    message:'规格不能为空'
                                });
                                flag = false;
                                break;
                            }
                        }
                        if (flag == false) return;
                        // 进行添加操作
                       this.$http.post('specTmp/addStAndSpec','specTmpName='+this.ruleForm.specTmpName+'&specStr='+this.specStr).then(result => {
                           if (result.data.state){
                               this.$message({
                                   showClose:true,
                                   type:'success',
                                   message:'添加成功'
                               });
                               this.ruleForm = {};
                               this.specInputs = [{count:1,val:''}];
                               this.init();
                           } else{
                               this.$message({
                                   showClose:true,
                                   type:'danger',
                                   message:'添加失败'
                               });
                           }
                       }).catch(() => {
                           this.$message({
                               showClose:true,
                               type:'danger',
                               message:'添加失败'
                           });
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
        },
        watch:{
            'selectedSpecCount':function (newVal) {
                // 判断增加规格还是减少规格数
                if (newVal>this.specInputs.length){
                    // 增加
                    console.log('增加'+(newVal-this.specInputs.length)+'个');
                    let addCount = newVal-this.specInputs.length;
                    for (let i = 0;i<addCount;i++){
                        console.log('count:'+(this.specInputs.length+1));
                        this.specInputs.push({count:this.specInputs.length+1,val:''});
                    }
                } else{
                    // 减少
                    // 从哪开始移除和移除几个
                    let startIndex = newVal;
                    let reduceCount = this.specInputs.length-newVal;
                    console.log('从下标'+startIndex+'开始移除,移除'+reduceCount+'个');
                    this.specInputs.splice(startIndex,reduceCount);
                }

            }
        },
        computed:{
            specStr(){
                let str = '';
                this.specInputs.forEach((item,index) => {
                    if (index!=this.specInputs.length-1){
                        str=str+item.val+',';
                    } else {
                        str += item.val;
                    }
                })
                console.log(str);
                return str;
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