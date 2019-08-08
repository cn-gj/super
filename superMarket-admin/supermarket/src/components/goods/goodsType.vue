<template>
    <div id="shopType">
        <div class="my-introduce">
            <p>商品类别：通过商品类别，可以快速帮您定位到你想要的商品区！</p>
        </div>
        <div class="my-tools">
            <el-button icon="glyphicon glyphicon-plus" size="small" @click="add">新增商品类别</el-button>
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
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in goodsTypeList" :key="item.goodsTypeName">
                <th scope="row"><el-checkbox v-model="item.checked" @change="handleCheckedShopsChange(item.checked,item.id)"></el-checkbox></th>
                <td>{{index+1}}</td>
                <td>{{item.id}}</td>
                <td>{{item.goodsTypeName}}</td>
                <td>
                    <el-tag type="warning" size="small" @click="editGoodsType(item.id,item.goodsTypeName)">编辑</el-tag>
                    <el-tag type="danger" size="small" @click="deleteGoods(item.id)">删除</el-tag>
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
                goodsTypeList:[],
                checkAll:false,
                // 选中的商品类别
                checkedGoodsType:[],
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
                this.$http.post('goodsType/findByCondition').then(result => {
                    this.goodsTypeList = result.data;
                })
            },
            add() {
                this.$prompt('请输入类别名称', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '类别名称有误'
                }).then(({ value }) => {
                    // 判断类型名称是否已经存在
                    this.$http.post('goodsType/findByName','typeName='+value).then(result => {
                        if (result.data.result) {
                            this.$message({
                                type: 'warning',
                                message: '类别名称: ' + value+' 已存在'
                            });
                        }else{
                            if (value == null || value == ''){
                                this.$message({
                                    type: 'warning',
                                    message: '类型名称不能为空'
                                });
                                return;
                            }
                            // 添加商品类型
                            this.$http.post('goodsType/addGoodsType','goodsTypeName='+value).then(result => {
                                if (result.data.result){
                                    this.$message({
                                        type: 'success',
                                        message: '添加成功'
                                    });
                                    this.init();
                                } else{
                                    this.$message({
                                        type: 'danger',
                                        message: '添加失败'
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
            // 编辑类型
            editGoodsType(id,goodsTypeName){
                // 根据类型id查询类型
                this.$prompt('请编辑类别名称', '编辑类型', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputErrorMessage: '类别名称有误',
                    inputValue:goodsTypeName
                }).then(({ value }) => {
                    // 判断类型名称是否已经存在
                    this.$http.post('goodsType/findByName','typeName='+value).then(result => {
                        if (result.data.result) {
                            this.$message({
                                type: 'warning',
                                message: '类别名称: ' + value+' 已存在'
                            });
                        }else{
                            if (value == null || value == ''){
                                this.$message({
                                    type: 'warning',
                                    message: '类型名称不能为空'
                                });
                                return;
                            }
                            // 修改商品类型
                            this.$http.post('goodsType/modifyGoodsType','id='+id+'&goodsTypeName='+value).then(result => {
                                if (result.data.result){
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
                if (this.checkedGoodsType.length == 0){
                    this.$message({
                        type: 'warning',
                        message: '请选择要操作的数据'
                    });
                    return;
                }
                let ids = this.checkedGoodsType.join(',');
                this.dels(ids);
            },
            // 根据类型id进行删除
            deleteGoods(id){
                this.dels(id);
            },
            dels(ids){
                // 判断商品类别下是否存在商品信息
                this.$http.post('goodsType/selGoodsByType','ids='+ids).then(result => {
                    if (result.data.result){
                        // 类型下存在商品信息
                        this.$message({
                            type: 'warning',
                            message: '请先将属于该类型的商品信息删除，才能进行该操作'
                        });
                    } else{
                        // 删除
                        this.$http.post('goodsType/deleteGoodsTypeById','ids='+ids).then(result => {
                            if (result.data.result){
                                this.$message({
                                    type: 'success',
                                    message: '删除成功'
                                });
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
                    this.checkedGoodsType = [];
                    this.goodsTypeList.forEach((item,index) => {
                        item.checked = true;
                        this.checkedGoodsType.push(item.id);
                    })
                } else{
                    this.goodsTypeList.forEach((item,index) => {
                        item.checked = false;
                        this.checkedGoodsType = [];
                    })
                }
                // 强制更新
                this.$forceUpdate();
            },
            // 商品选中状态发生改变
            handleCheckedShopsChange(val,id) {
                let goodsType = {};
                this.goodsTypeList.forEach((item,index) => {
                    if (item.id == id){
                        item.checked = val;
                        goodsType = item;
                    }
                })
                // 判断是否选中
                if (goodsType.checked){
                    // 添加到选中的列表中
                    this.checkedGoodsType.push(goodsType.id);
                }else{
                    this.checkedGoodsType.forEach((item,index) => {
                        if (item == id){
                            this.checkedGoodsType.splice(index,1);
                        }
                    })
                }
                // 判断是否全部选中
                if (this.checkedGoodsType.length == this.goodsTypeList.length){
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
