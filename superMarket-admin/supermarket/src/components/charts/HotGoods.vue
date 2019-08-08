<template>
    <!--热销商品-->
    <div id="shopSummary">
        <div id="container1" ref="container"></div>
    </div>
</template>

<script>
    import Highcharts from 'highcharts/highcharts.js';
    import  'highcharts/modules/data.js';
    export default {
        name: "HotGoods",
        data(){
            return {
                goodsSymmaryList:[]
            }
        },
        mounted(){
            this.init();

        },
        methods:{
            // 初始化
            init(){
                // 获取商品汇总数据列表
                this.$http.post('shopSummary/selGoodsSellCake','limit=5').then(result => {
                    this.goodsSymmaryList = result.data;
                    this.cacl(result.data);
                })
            },
            cacl(data){
                let sum = 0;
                let hotGoodsList = [];
                data.forEach((item,index) => {
                    sum += parseInt(item.goodsCount);
                    let hotGoods = {name:item.goodsName,count:item.goodsCount};
                    if (index == 0){
                        hotGoods.sliced = true;
                        hotGoods.selected = true;
                    }

                    hotGoodsList.push(hotGoods);
                })

                hotGoodsList.forEach((item,index) => {
                    item.y = parseInt(item.count)/sum;
                })

                console.log(hotGoodsList);
                this.render(hotGoodsList);
            },
            render(data) {
                Highcharts.chart('container1', {
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: '热销商品占比'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series: [{
                        name: '占比',
                        colorByPoint: true,
                        data:data
                        // data: [{
                        //     name: '小海一条龙服务',
                        //     y: 61.41,
                        //     sliced: true,
                        //     selected: true
                        // }, {
                        //     name: '养乐多',
                        //     y: 11.84
                        // }, {
                        //     name: '百事可乐',
                        //     y: 10.85
                        // }, {
                        //     name: '宝格丽项链',
                        //     y: 4.67
                        // }, {
                        //     name: '钻石王老五',
                        //     y: 4.18
                        // }, {
                        //     name: '六位地黄丸',
                        //     y: 1.64
                        // }]
                    }]
                });
            }
        }
    }
</script>

<style scoped>

</style>