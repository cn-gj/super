<template>
    <div id="home">
       <div class="header container-fluid">
           <div class="header-left">
<!--               <a href="#">-->
<!--                   <img src="../../resource/images/512×512-点png2.png" alt="">-->
                   Supermarket
<!--               </a>-->
           </div>
           <div class="header-right">
               <ul class="my-nav">
                   <li><span class="glyphicon glyphicon-repeat" @click="refresh"></span></li>
                   <li><span></span>{{currentTime}}</li>
                   <li><span class="glyphicon glyphicon-envelope"></span>反馈建议</li>
                   <li><span class="glyphicon glyphicon-question-sign"></span>帮助中心</li>
                   <li class="my-info" @click="openInfoList">
                       <img src="../../resource/images/512×512-点png2.png">
                       <span >{{shopName}}</span>
                       <div class="my-info-list" ref="myInfoList" v-if="showInfoList">
                           <ul>
                               <li><span class="glyphicon glyphicon-user"></span>个人信息</li>
                               <li @click="updatePassword"><span class="glyphicon glyphicon-pencil"></span>修改密码</li>
                               <li><span class="glyphicon glyphicon-list-alt"></span>操作日志</li>
                               <li style="height: 60px;line-height: 60px;border-top: 1px solid black;" @click="exit"><span class="glyphicon glyphicon-off"></span>退出系统</li>
                           </ul>
                       </div>
                   </li>
               </ul>
           </div>
       </div>
        <div class="box row">
            <div class="aside">
                <el-menu default-active="1" class="el-menu-vertical-demo"
                         background-color="#37424F"
                         text-color="#fff" :unique-opened="true">
                    <el-submenu v-for="menu in menus" :index="menu.index" :key="menu.index">
                        <template slot="title">
                            <i :class="menu.icon"></i>
                            <span slot="title">{{menu.name}}</span>
                        </template>
                        <el-menu-item-group v-for="sub in menu.sub" :key="sub.index">
                            <el-menu-item :index="sub.index" @click="addTab(sub.name,sub.routerName)">{{sub.name}}</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </div>
            <div class="main">
                <!-- 使用element 组件 -->
                <el-tabs type="border-card" v-model="activeName" @tab-click="handleOpen" @tab-remove="handleClose" style="height: 100%;">
                    <el-tab-pane style="height: 100%" v-for="item in tabs" :name="item.name" :label="item.label" :key="item.label" :closable="item.closable">
                    </el-tab-pane>
                </el-tabs>
                <div class="main-content">
                    <router-view v-if="isRouterAlive" :key="activeDate"></router-view>
                </div>
            </div>
        </div>

        <!--修改密码-->
        <transition>
            <div class="my-tanchukuang" v-if="showUpdate">
                <div>
                    <div class="my-modal modal-content">
                        <div class="modal-header">
                            <button type="button" class="close"><span aria-hidden="true" @click="closeUpdate">×</span></button>
                            <h4 class="modal-title">修改密码</h4>
                        </div>
                        <div class="modal-body">
                            <el-form>
                               <el-form-item label="密码">
                                   <el-input placeholder="请输入密码" v-model="empPwd" type="password"></el-input>
                               </el-form-item>
                                <el-form-item label="确认密码">
                                    <el-input placeholder="请输入密码" v-model="pwd" type="password"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="submitForm()">修改</button>
                        </div>
                    </div>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
    // 导入menu
    import menus from '../../menu.js';
    export default {
        name: "home",
        provide(){
            return {
                reload:this.reload
            }
        },
        data() {
            return {
                isRouterAlive:true,
                activeDate:'',//时间戳，改变时标识view-router中的内容发生变化
                activeName:'1', //当前选中的tab
                tabs:[ //选项卡
                    {name:'1',label:'首页',routerName:'welcome'},
                    {name:'2',label:'测试组件',routerName:'test',closable:true}
                ],
                showInfoList:false,
                menus:menus, //导航菜单
                currentTime:'', // 当前时间
                shopName:'', //当前登陆店铺名称,
                showUpdate:false,
                empPwd:'',
                pwd:''
            };
        },
        created(){
            this.init();
          this.clockStart();
          this.$router.push({name:'welcome'});
        },
        methods: {
            // 局部刷新组件
            reload(){
                this.isRouterAlive = false;
                this.$nextTick(() => {
                    this.isRouterAlive = true;
                })
            },
            // 刷新router-view内容
            refresh(){
                this.activeDate = Math.random().toString();
            },
            init(){
                this.$http.post('shop/shopList','id='+sessionStorage.getItem("shopId")).then(result => {
                    this.shopName = result.data.shopList[0].shopName;
                })
            },
            clockStart(){
                setInterval(() => {
                    this.nowTime();
                },1000);
            },
             nowTime(){
                //获取年月日
                let time=new Date();
                let year=time.getFullYear();
                let month=time.getMonth()+1;
                let day=time.getDate();

                //获取时分秒
                let h=time.getHours();
                let m=time.getMinutes();
                let s=time.getSeconds();

                //检查是否小于10
                h=this.check(h);
                m=this.check(m);
                s=this.check(s);
                this.currentTime = "当前时间："+year+"年"+month+"月"+day+"日  "+h+":"+m+":"+s;
            },
            //时间数字小于10，则在之前加个“0”补位。
             check(i){
                //用三元运算符
                let num;
                i<10?num="0"+i:num=i;
                return num;
            },
            openInfoList(){
                this.showInfoList = !this.showInfoList;
                this.$forceUpdate();
            },
            // 打开修改密码框
            updatePassword(){
                this.$refs.myInfoList.style.display = 'none';
                // this.showInfoList = false;
                this.showUpdate = true;
            },
            // 修改密码
            submitForm(){
                if (this.empPwd == ''){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'密码不能为空'
                    });
                    return;
                }

                if (this.empPwd.length<6){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'密码长度要大于6位'
                    });
                    return;
                }

                if (this.pwd != this.empPwd){
                    this.$message({
                        showClose:true,
                        type:'warning',
                        message:'两次密码不一致'
                    });
                    return;
                }

                this.$http.post('employee/upPwd','id='+sessionStorage.getItem("empId")+'&empPwd='+this.empPwd+'&empAccount='+sessionStorage.getItem("empAccount")).then(result => {
                    if (result.data.state){
                        this.$alert('密码修改成功，请重新登陆', '系统提示', {
                            confirmButtonText: '确定',
                            callback: action => {
                                this.exit();
                            }
                        });
                    }else{
                        this.$message({
                            showClose:true,
                            type:'error',
                            message:'密码修改失败'
                        });
                    }
                })
            },
            closeUpdate(){
              this.showUpdate = false;
            },
            // 添加选项卡
            addTab(tabName,routerName){
                // 判断该选项卡是否已经打开
                let isHas = true;
                this.tabs.find( item => {
                    // tabList中已经存在该tab项
                    if (item.label == tabName) {
                        this.activeName = item.name;
                        this.$router.push({name:routerName});
                        isHas = false;
                    }
                })

                if (isHas == false) return;
                let tab = {
                    name:(this.tabs.length+1).toString(),
                    label:tabName,
                    routerName:routerName,
                    closable:true
                }

                this.tabs.push(tab);
                this.activeName = tab.name;
                this.$router.push({name:routerName})
            },
            // 选中选项卡时触发该事件
            handleOpen(key, keyPath) {
                let tab = this.tabs.filter(item => item.name == key.name)[0];
                console.log(tab);
                this.$router.push({name:tab.routerName});
            },
            handleClose(targetName) {
                // 当前tab选中项的name
                let activeName = this.activeName;

                // 移除当前tab后，让下一个选中（如果后边有tab项）或者上一个选中（后边没有tab）
                let tabList = this.tabs;
                tabList.forEach((item,index) => {
                    // 当前选中项和操作项是同一个，需要重新选中tab
                    if (item.name == targetName) {
                        let nexTab = tabList[index+1] || tabList[index-1];
                        if (nexTab) {
                            activeName = nexTab.name;
                            this.$router.push({ name: nexTab.routerName});
                        }
                    }
                });

                // 重新将获取tab列表
                this.activeName = activeName;
                this.tabs = tabList.filter(tab => tab.name!=targetName);
                
            },
            // 退出
            exit(){
                this.$http.post('employee/exit').then(result => {
                    if (result.data.state){
                        sessionStorage.clear();
                        this.$router.push({ name: 'login'});
                    }
                })
            }
        }
    }
</script>

<style lang="less">
    #home{
        height: 100%;

        /*element组件样式*/
        .el-menu{
            border: none;
        }
        .el-menu-vertical-demo:not(.el-menu--collapse) {
            width: 100%;
            min-height: 400px;
        }

        .header{
            height: 60px;
            background-color: #09c;
            line-height: 60px;
            .header-left{
                height: 100%;
                line-height: 60px;
                float: left;
                /*h1{*/
                /*    height: 60px;*/
                /*    line-height: 60px;*/
                /*}*/
                //-----------------------------
                font-size: 25px;
                background: #EEE url(data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAIAAAAmkwkpAAAAHklEQVQImWNkYGBgYGD4//8/A5wF5SBYyAr+//8PAPOCFO0Q2zq7AAAAAElFTkSuQmCC) repeat;
                text-shadow: 3px -3px #f5f6e9, 2px -2px #be4747;
                font-weight: bold;
                -webkit-text-fill-color: transparent;
                -webkit-background-clip: text;
                img{
                    width: 115px;
                    height: 35px;
                }
                
            }
            .header-right{
                height: 60px;
                float: right;
                .my-nav{
                    list-style: none;
                    &>li{
                        height: 60px;
                        line-height: 60px;
                        padding: 0 2px;
                        float: left;
                        margin-left: 20px;
                        color: white;
                        &:hover{
                            background: #008FBF;
                            /*.my-info-list{*/
                            /*    display: block;*/
                            /*}*/
                        }
                        span{
                            padding: 0 5px;
                        }
                    }
                    .my-info{
                        position: relative;
                        vertical-align: middle;

                        img{
                            width: 35px;
                            height: 35px;
                            border-radius: 50%;
                        }
                        .my-info-list{
                            /*display: none;*/
                            width: 100%;
                            /*height: 300px;*/
                            position: absolute;
                            top: 60px;
                            left: 0;
                            background: white;
                            z-index: 10001;
                            ul{
                                width: 100%;
                                padding: 0;
                                list-style: none;
                                li{
                                    width: 100%;
                                    height: 30px;
                                    line-height: 30px;
                                    color: #000;
                                    span{
                                        margin-right: 5px;
                                    }
                                    &:hover{
                                        background: gray;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        .box{
            display: flex;
            width: 100%;
            position: absolute;
            top: 60px;
            bottom: 0;
            left: 0;
            padding: 0;
            margin: 0;
            transition: all 1s linear;
        }

        .aside{
            position: relative;
            height: 100%;
            width: 200px;
            background-color: #37424F;
            margin: 0;
            padding: 0;
            overflow-y: auto;
            overflow-x: hidden;
        }

        .main{
            position: absolute;
            left: 200px;
            right: 0px;
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: green;

            .el-tabs{
                height: 100%;
                /*overflow: auto;*/

                /*.el-tabs__content{*/
                /*    position: absolute;*/
                /*    top: 39px;*/
                /*    bottom: 0;*/
                /*    left: 0;*/
                /*    right: 0;*/
                /*}*/
            }
            .main-content{
                position: absolute;
                top: 39px;
                bottom: 0;
                left: 0;
                right: 0;
                padding: 15px;
            }
        }

        .my-tanchukuang{
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            background-color: rgba(0,0,0,0.5);
            /*border: 15px solid white;*/
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