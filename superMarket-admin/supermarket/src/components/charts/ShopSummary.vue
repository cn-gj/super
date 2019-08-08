<template>
    <!--店铺汇总-->
    <div id="shopSummary">
        <div id="container" ref="container"></div>
    </div>
</template>

<script>
    import Highcharts from 'highcharts/highcharts.js';
    import  'highcharts/modules/data.js';
    export default {
        name: "ShopSummary",
        data(){
            return{
                shopSummaryList:[]
            }
        },
        created(){
            //this.init();
        },
        mounted(){
            this.init();

        },
        methods:{
            // 初始化
            init(){
                // 获取门店汇总数据列表
                this.$http.post('shopSummary/shopSummaryList').then(result => {
                    this.shopSummaryList = result.data;
                    let data = this.shopSummaryList.filter(item => item.bTicketList.length != 0);
                    this.cacl(data);
                })
            },
            // 渲染
            cacl(data){
                console.log("原始数据="+data);
                // 门店名称列表
                let shopNameList = [];
                // 销售金额
                let saleMoneyList = [];
                // 销售毛利
                let saleMarginList = [];
                data.forEach((item,index) => {
                    let practicalMoney = 0; // 本票实收
                    let sumPracticalMoney = 0; // 总票金额
                    let sumCostPrice = 0; // 总成本
                    let saleMargin = 0; // 毛利
                    item.bTicketList.forEach((ticket,index1) => {
                        practicalMoney = parseFloat(ticket.practicalMoney); // 本票实收
                        ticket.bTicketDetailList.forEach((detail,index2) => {
                            // 商品数量
                            let goodsCount = parseInt(detail.goodsCount);
                            // 商品进价
                            let costPrice = parseInt(detail.bGoods.costPrice);
                            // 总成本
                            sumCostPrice += goodsCount*costPrice;
                        })
                        sumPracticalMoney+=practicalMoney;
                        saleMargin = sumPracticalMoney-sumCostPrice;

                    })
                    shopNameList.push(item.shopName);
                    saleMoneyList.push(sumPracticalMoney);
                    saleMarginList.push(saleMargin);
                })

                this.render(shopNameList,saleMoneyList,saleMarginList);
            },
            render(shopNameList,saleMoneyList,saleMarginList) {
                console.log("门店名称="+shopNameList);
                console.log("销售金额="+saleMoneyList);
                console.log("毛利="+saleMarginList);
                let option = {
                    series: [{
                        name: '销售金额',
                        // data: [1,4,6,9],
                        data: saleMoneyList,
                        color: '#71c671'
                    },
                        {
                            name: '销售毛利',
                            // data: [2,4,5,9],
                            data: saleMarginList,
                            color: '#ee7621'
                        }
                    ],
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '店铺汇总'
                        // 该功能依赖 data.js 模块，详见https://www.hcharts.cn/docs/data-modules
                    },
                    xAxis:{//shopNameList
                        // categories:['洋洋便利店','精致便利店','网易云音乐','澳门皇冠赌场']
                        categories:shopNameList
                    },
                    yAxis: {
                        allowDecimals: false,
                        title: {
                            text: '元',
                            rotation: 0
                        }
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                this.point.y + ' 元' ;
                            //this.point.name.toLowerCase()
                        }
                    }
                }
                Highcharts.chart('container',option);
            }
        }
    }
</script>

<style scoped>

</style>