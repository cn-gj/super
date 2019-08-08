<template>
    <div id="shopInfo">
        <el-form ref="shop" :model="shop" :rules="rules" label-width="80px">
            <el-form-item label="门店编码">
                <el-tag>{{shop.id}}</el-tag>
            </el-form-item>
            <el-form-item label="创建时间">
                <el-tag>{{shop.createDate}}</el-tag>
            </el-form-item>
            <el-form-item label="LOGO">
                <input type="file" ref="fileUpload" v-show="false" @change="chooseShopLogo" accept="image/*"/>
                <!--../../resource/images/512×512-点png.png-->
                <img ref="shopLogo" class="my-logo" src="http://192.168.16.104:8080/yanlingji.jpg" @click="openFileDialog">
                <span style="color: red;">图片分辨率:建议500*500</span>
            </el-form-item>
            <el-form-item label="门店名称" prop="shopName">
                <el-input v-model="shop.shopName" @change="shopNameChange"></el-input>
            </el-form-item>
            <el-form-item label="联系人" prop="shopLinkman">
                <el-input v-model="shop.shopLinkman"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="shopPhone">
                <el-input v-model="shop.shopPhone"></el-input>
            </el-form-item>
            <el-form-item label="营业时间">
                <el-time-picker
                        is-range
                        v-model="shop.doBusinessTime"
                        value-format="HH:mm"
                        range-separator="-"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        placeholder="选择时间范围">
                </el-time-picker>
            </el-form-item>
            <el-form-item label="门店公告">
                <el-input type="textarea" v-model="shop.shopAdvice"></el-input>
            </el-form-item>
            <el-form-item label="门店地址">
                <el-input v-model="shop.shopAddress"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('shop')">保存</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
        <div style="height: 100px"></div>
    </div>
</template>

<script>
    export default {
        name: "shopInfo",
       inject:['reload'],
        data() {
            return {
                shop:{},
                rules:{
                    shopName:[
                        { required: true, message: '请输入门店名称', trigger: 'blur' }

                    ],
                    shopLinkman:[
                        { required: true, message: '请输入联系人名称', trigger: 'blur' }
                    ],
                    shopPhone:[
                        { min: 11, max: 11, message: '手机格式不正确', trigger: 'blur' }
                    ]
                },
                index:1,
                imageUrl: ''
            }
        },
        created(){
            this.init();
        },
        methods: {
            // 刷新当前页面
            refresh(){
                this.reload();
            },
            init(){
                // 获取本店信息
                this.$http.post('employee/selectshop','id='+sessionStorage.getItem('shopId')).then(result => {
                    let shop = result.data.shopListByEmpId;
                    if (shop.shopHours != null && shop.shopHours != ''){
                        let shopHours = shop.shopHours.split('-');
                        let begin = shopHours[0].split(':');
                        let end = shopHours[1].split(":");
                        let start = new Date(2019,7,22,parseInt(begin[0]),parseInt(begin[1]));
                        let over = new Date(2019,7,22,parseInt(end[0]),parseInt(end[1]));
                        shop.doBusinessTime = [start,over];
                    }
                    this.shop = shop;
                })

            },
            // 选择门店logo
            openFileDialog(){
                this.$refs.fileUpload.click();
            },
            chooseShopLogo(){
                let vue = this;
                let logo = this.$refs.fileUpload;

                if (!/^image/.test(logo.files[0].type)){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'请选择一个图片类型作为门店Logo'
                    })
                    return;
                }
                if (logo.value != null && logo.value != ''){
                        let obj = logo.files[0];
                        let fr=new FileReader();
                        fr.onload = function () {
                            vue.$refs.shopLogo.src = this.result;
                        };
                        fr.readAsDataURL(obj);
                        vue.shop.shopLogo = obj;
                }
            },
            // 判断门店名称是否改变
            shopNameChange(shopAccount,shopName){
                this.$http.post('shop/accountAndName','account='+shopAccount).then(result => {
                    let shop = result.data.shopAccount;
                    if (shop.shopName != shopName){
                        // 判断门店名称是否被占用
                        this.$http.post('shop/accountAndName','shopName='+shopName).then(result => {
                            if (result.data.shopName != null && result.data.shopName != undefined) {
                                this.$message({
                                    showClose:true,
                                    type:'warning',
                                    message:'门店名称'+shopName+'已经被占用'
                                })
                                this.shop.shopName = shop.shopName;
                            }
                        });
                    }
                });
            },
            submitForm(shop) {
                this.$refs[shop].validate((valid) => {
                    if (valid) {
                        // 获取营业时间
                        if (this.shop.doBusinessTime != null){
                            let start = this.shop.doBusinessTime[0];
                            let startStr = start.getHours()+":"+start.getMinutes();
                            let end = this.shop.doBusinessTime[1];
                            let endStr = end.getHours()+":"+end.getMinutes();
                            this.shop.shopHours = startStr+"-"+endStr;
                        }else{
                            this.shop.shopHours = '';
                        }
                        // 进行更新操作
                        let shop = this.shop;
                        let formData = new FormData();
                        formData.append('id',shop.id);
                        formData.append('shopName',shop.shopName);
                        formData.append('shopPhone',shop.shopPhone);
                        formData.append('shopHours',shop.shopHours);
                        formData.append('shopLinkman',shop.shopLinkman);
                        formData.append('shopAdvice',shop.shopAdvice);
                        formData.append('shopAddress',shop.shopAddress);
                       formData.append('fileName',shop.shopLogo);
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$http.post('shop/upShop',formData,config).then(result => {
                            console.log(result);
                            if (result.data.state){
                                this.init();
                                this.$message({
                                    showClose:true,
                                    type:'success',
                                    message:'门店信息保存成功'
                                })
                            } else{
                                this.$message({
                                    showClose:true,
                                    type:'danger',
                                    message:'门店信息保存失败'
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
        }
    }
</script>

<style lang="less">

    #shopInfo{
        height: 100%;
        overflow: auto;

        .my-logo{
            width: 60px;
            height: 60px;
            border: 1px solid gray;
            border-radius: 5px;
            box-shadow: 0 0 rgba(0,0,0,0.2);
        }
    }
</style>