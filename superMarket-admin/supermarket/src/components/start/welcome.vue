<template>
    <div id="welcome">
        <div class="left">
            <shop-summary></shop-summary>
        </div>
        <div class="right">
            <hot-goods></hot-goods>
        </div>
<!--        <div class="left">3</div>-->
    </div>
</template>

<script>
    // 导入店铺汇总分析图表
    import shopSummary from '../../components/charts/ShopSummary.vue';
    // 热销商品占比
    import hotGoods from '../../components/charts/HotGoods.vue';
    export default {
        name: "welcome",
        data() {
            return {}
        },
        created(){
            this.sayHello();
        },
       mounted(){
       },
        methods:{
            sayHello(){
                this.$notify({
                    title: '老板问候',
                    iconClass:'glyphicon glyphicon-hand-right',
                    duration:4500,
                    offset:100,
                    message: this.bossMessage()
                });
            },
            bossMessage(){
                let message = [
                    '祝您今天工作顺利，万事如意，加油，你是最棒的',
                    '要成功，就要长期等待而不焦躁，态度从容却保持敏锐，不怕挫折且充满希',
                    '十年前你是谁，一年前你是谁，甚至昨天你是谁，都不重要。重要的是，今天你是谁？',
                    '人生就像一场马拉松，你的起点高也好，你的提速快也好，但结果比较的是谁能坚持到最远',
                    '凡事回归原点，不懂就不懂，努力学习；懂了也要相信人外有人，放下架子，谦虚，能力提升方可最大化！',
                    '祝您，吉祥如意，意气风发，发愤图强，强身健体，体恤入微，威风八面，面目一新，欣欣向荣',
                    '如果我们把每一天的工作都当做追求梦想的一部分，那我们的工作就会变得有意思得多。',
                    '用快乐去奔跑，用心去倾听，用思维去发展，用努力去奋斗，用目标去衡量，用爱去生活。'
                ];
                // parseInt(Math.random()*(max-min+1)+min,10);
                let index = parseInt(Math.random()*((message.length-1)-0+1)+0,10);
                return message[index];
            }
        },
        components:{
            'shop-summary':shopSummary,
            'hot-goods':hotGoods
        }
    }
</script>

<style scoped lang="less">
    #welcome{
        height: 100%;
        overflow: auto;
        box-shadow: 0 0 7px rgba(0,0,0,0.4);

        >div{
            width: 45%;
            min-height: 200px;
            /*border: 1px solid pink;*/
            float: left;
            margin-top: 15px;
            margin-bottom: 5px;
        }
        >.left{
            margin-left: 3.33%;
            margin-right: 3.33%;
        }
    }
</style>