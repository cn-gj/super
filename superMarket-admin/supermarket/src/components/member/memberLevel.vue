<template>
    <div id="memberLevel">
        <div class="my-introduce">
            <p>商品类别：通过商品类别，可以快速帮您定位到你想要的商品区！</p>
        </div>
        <div class="my-tools">
            <el-button icon="glyphicon glyphicon-plus" size="small" @click="openAdd">新增会员等级</el-button>
            <el-button icon="glyphicon glyphicon-trash" size="small" style="color: red;" @click="batchDeleteLevelType">批量删除</el-button>
            <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
        </div>
        <table class="my-tab table table-bordered">
            <thead>
            <tr>
                <th>
                    <el-checkbox v-model="checkAll" @change="handleCheckAllChange"></el-checkbox>
                </th>
                <th>序号</th>
                <th>类别编号</th>
                <th>类别名称</th>
                <th>折扣</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in levelList" :key="item.id.toString()">
                <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopsChange(item.checked,item.id)"></el-checkbox></th>
                <td>{{index+1}}</td>
                <td>{{item.id}}</td>
                <td>{{item.levelName}}</td>
                <td>{{item.discount}}</td>
                <td>
                    <el-tag type="warning" size="small" @click="editLevel(item.id,item.discount)">编辑</el-tag>
                </td>
            </tr>
            </tbody>
        </table>
        
        <!--添加等级-->
        <transition>
            <div class="my-tanchukuang" v-if="showAdd">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeAdd">×</span></button>
                            <h4 class="modal-title">新增等级</h4>
                        </div>
                        <div class="modal-body">
                            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <el-form-item label="等级名称" prop="levelName">
                                    <el-input v-model="ruleForm.levelName" @blur="checkLevelName(ruleForm.levelName)"></el-input>
                                </el-form-item>
                                <el-form-item label="折扣" prop="discount">
                                    <el-input v-model="ruleForm.discount"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="resetForm('ruleForm')">清空</button>
                            <button type="button" class="btn btn-primary" @click="submitForm('ruleForm')">添加</button>
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
        name: "memberLevel",
        inject:['reload'],
        data(){
            return {
                levelList:[],
                checkAll:false,
                // 选中的商品类别
                checkedLevelType:[],
                showAdd:false,
                ruleForm:{},
                rules:{
                    levelName: [
                        { required: true, message: '请输入等级名称', trigger: 'blur' }
                    ],
                    discount:[
                        { required: true, message: '请输入折扣', trigger: 'blur' },
                        { pattern:/^(0(\.\d{1,2})?|1(\.0{1,2})?)$/,message:'折扣取值区间为0~1',trigger:'blur'}
                    ]
                }
            }
        },
        created(){
            this.init();
        },
        methods:{
            // 刷新当前页面
            refresh(){
                this.reload();
            },
            // 初始化数据
            init(){
                this.$http.post('level/selectlevel').then(result => {
                    this.levelList = result.data.levellist;
                })
            },
            openAdd(){
                this.showAdd = true;
            },
            closeAdd(){
                this.showAdd = false
            },
            // 检查等级名称是否已经存在
            checkLevelName(levelName){
                // 判断类型名称是否已经存在
                this.$http.post('level/findLevelByName','levelName='+levelName).then(result => {
                    if (!result.data.state) {
                        this.$message({
                            type: 'warning',
                            message: '等级名称: ' + levelName + ' 已存在'
                        });
                        this.ruleForm.levelName = '';
                    }
                })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid){
                        // 添加会员等级
                        let params = Qs.stringify(this.ruleForm);
                        this.$http.post('level/insertlevel',params).then(result => {
                            if (result.data.state){
                                this.$message({
                                    type: 'success',
                                    message: '添加成功'
                                });
                                this.ruleForm = {};
                                this.init();
                            } else{
                                this.$message({
                                    type: 'danger',
                                    message: '添加失败'
                                });
                            }
                        })
                    } else{
                        this.$message({
                            showClose:true,
                            type:'warning',
                            message:'请将信息填写完整后在进行操作'
                        })
                        return false;
                    }
                })
            },
            // 编辑类型
            editLevel(id,discount){
                // 根据类型id查询类型
                this.$prompt('请输入折扣', '编辑等级折扣', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '',
                    inputValue:discount
                }).then(({ value }) => {
                    if (value>1 || value<0){
                        this.$message({
                            type: 'warning',
                            message: '折扣取值区间为0~1'
                        });
                        return;
                    }
                    // 修改商品类型
                    this.$http.post('level/updatelevel','id='+id+'&discount='+value).then(result => {
                        if (result.data.state){
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            });
                            this.init();
                        } else{
                            this.$message({
                                type: 'danger',
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
            // 批量删除
            batchDeleteLevelType(){
                // 判断是否有选中数据
                if (this.checkedLevelType.length == 0){
                    this.$message({
                        type: 'warning',
                        message: '请选择要操作的数据'
                    });
                    return;
                }
                let ids = this.checkedLevelType.join(',');
                this.dels(ids);
            },
            dels(ids){
                // 判断会员等级是否正被使用
                this.$http.post('level/findMemberCountByIds','ids='+ids).then(result => {
                    if (!result.data.state){
                        // 类型下存在商品信息
                        this.$message({
                            type: 'warning',
                            message: '请先将属于该类型的商品信息删除，才能进行该操作'
                        });
                    } else{
                        // 删除
                        this.$http.post('level/batchLevel','ids='+ids).then(result => {
                            if (result.data.state){
                                this.$message({
                                    type: 'success',
                                    message: '删除成功'
                                });
                                this.checkAll = false;
                                this.checkedLevelType = [];
                                this.init();
                            } else{
                                this.$message({
                                    type: 'danger',
                                    message: '删除失败'
                                });
                            }
                        })
                    }
                })
            },
            // 选中所有门店
            handleCheckAllChange(val) {
                //console.log(val);
                if (val){
                    // 选中所有
                    this.checkedLevelType = [];
                    this.levelList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedLevelType.push(item.id);
                    })
                } else{
                    this.levelList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedLevelType = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 商品选中状态发生改变
            handleCheckedShopsChange(val,id) {
                let levelType = {};
                this.levelList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        levelType = item;
                    }
                })
                // 判断是否选中
                if (levelType.checked){
                    // 添加到选中的列表中
                    this.checkedLevelType.push(levelType.id);
                }else{
                    this.checkedLevelType.forEach((item,index) => {
                        if (item == id){
                            this.checkedLevelType.splice(index,1);
                        }
                    })
                }
                // 判断是否全部选中
                if (this.checkedLevelType.length == this.levelList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }

                this.$forceUpdate();
            },
        }
    }
</script>

<style scoped lang="less">
    #memberLevel{
        .my-introduce{
            width: 100%;
            height: 60px;
            background: #fcf8e3;
            border-radius: 5px;
            /*border-bottom: 1px solid gray;*/
            p{
                height: 60px;
                line-height: 60px;
                text-indent: 2em;
            }
        }
        .my-tools{
            padding: 10px 0;
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
