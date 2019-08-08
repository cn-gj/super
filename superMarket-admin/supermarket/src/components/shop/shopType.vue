<template>
    <div id="shopType">
        <div class="my-introduce">
            <p>门店类别：管理门店的一个方式，方便商家集中管理门店，也方便商家选择门店！</p>
        </div>
        <div class="my-tools">
            <el-button icon="glyphicon glyphicon-plus" size="small" @click="add">新增门店类别</el-button>
            <el-button icon="glyphicon glyphicon-trash" size="small" style="color: red;" @click="dels">批量删除</el-button>
            <el-button icon="glyphicon glyphicon-refresh" size="small" @click="refresh">刷新数据</el-button>
        </div>
        <table class="my-tab table table-bordered">
            <thead>
            <tr>
                <th><el-checkbox v-model="checkAll" @change="handleCheckAllChange"></el-checkbox></th>
                <th>序号</th>
                <th>类别编号</th>
                <th>类别名称</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in shopTypeList" :key="item.shopTypeName">
                <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopTypeChange(item.checked,item.id)"></el-checkbox></th>
                <td>{{index+1}}</td>
                <td>{{item.id}}</td>
                <td>{{item.shopTypeName}}</td>
                <td>{{item.createDate}}</td>
                <td>
                    <el-tag type="warning" size="small" @click="editShopType(item.id)">编辑</el-tag>
                    <el-tag type="danger" size="small" @click="del(item.id)">删除</el-tag>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "shopType",
        inject:['reload'],
        data(){
            return {
                // 门店类型列表
                shopTypeList:[
                    {id:'1',checked:false,shopTypeName:'加盟/连锁',createDate:'2019-09-08'},
                    {id:'2',checked:false,shopTypeName:'加盟/连',createDate:'2019-09-08'}
                ],
                // 是否选中所有
                checkAll:false,
                // 选中的门店类型
                checkedShopType:[],
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
                this.$http.post('shopType/showShopTypeList').then(result => {
                    this.shopTypeList = result.data;
                })
            },
            // 添加门店类别
            add() {
                this.$prompt('请输入类别名称', '添加门店类别', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '类别名称有误'
                }).then(({ value }) => {
                    this.$http.post('shopType/addShopType','shopTypeName='+value).then(result => {
                        if (result.data.state){
                            this.$message({
                                type: 'success',
                                message: '添加成功'
                            });
                            // 重新初始化数据
                            this.init();
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
            editShopType(id){
                this.$prompt('门店类型名称', '编辑门店类型', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '类别名称有误'
                }).then(({ value }) => {
                    this.$http.post('shopType/upShopType','id='+id+'&shopTypeName='+value).then(result => {
                        if (result.data.state){
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            });
                            // 重新初始化数据
                            this.init();
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
            // 批量删除
            dels(){
                // 判断是否有选中的门店
                if (this.checkedShopType.length == 0){
                    this.$message({
                        showClose: true,
                        type:'warning',
                        message: '请选择要批量操作的数据'
                    });
                    return;
                }
                let ids = this.checkedShopType.join(',');
                let flag = window.confirm("确定删除编号为("+ids+")的数据吗？该操作会将所有属于此类型的门店类型重置");

                if (flag == false) return;
                // console.log(ids);
                this.$http.post('shopType/delShopType','ids='+ids).then(result => {
                    if (result.data.state){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.checkedShopType = [];
                        // 重新初始化数据
                        this.init();
                    } else{
                        this.$message({
                            type: 'success',
                            message: '删除失败'
                        });
                    }
                })

            },
            // 根据id删除
            del(id){
                let flag = window.confirm("确定删除编号为("+id+")的数据吗？该操作会将所有属于此类型的门店类型重置");
                if (flag == false) return;
                // console.log(ids);
                this.$http.post('shopType/delShopType','ids='+id).then(result => {
                    if (result.data.state){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.checkedShopType = [];
                        // 重新初始化数据
                        this.init();
                    } else{
                        this.$message({
                            type: 'success',
                            message: '删除失败'
                        });
                    }
                })

            },
            // 选中所有门店类别
            handleCheckAllChange(val) {
                console.log(val);
                if (val){
                    // 选中所有
                    this.checkedShopType = [];
                    this.shopTypeList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedShopType.push(item.id);
                    })
                } else{
                    this.shopTypeList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedShopType = [];
                    })
                }
                // 强制更新
               // this.$forceUpdate();
            },
            // 门店类别选中状态发生改变
            handleCheckedShopTypeChange(val,id) {
                let shopType = {};
                this.shopTypeList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        shopType = item;
                    }
                })
                if (shopType.checked){
                    // 添加到选中的列表中
                    this.checkedShopType.push(shopType.id);
                }else{
                    this.checkedShopType.forEach((item,index) => {
                        if (item == id){
                            this.checkedShopType.splice(index,1);
                        }
                    })
                }

                // 判断是否全部选中
                if (this.checkedShopType.length == this.shopTypeList.length){
                    this.checkAll = true;
                } else{
                    this.checkAll = false;
                }
                this.$forceUpdate();
            }
        }
    }
</script>

<style scoped lang="less">
    #shopType{
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
    }
</style>
