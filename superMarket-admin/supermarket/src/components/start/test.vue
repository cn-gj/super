<template>
    <div id="test">
        <div id="container" ref="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
    </div>
</template>

<script>
    // // 导入highChart 相关内容
    // import Highcharts from 'highcharts/highstock';
    // import HighchartsMore from 'highcharts/highcharts-more';
    // import HighchartsDrilldown from 'highcharts/modules/drilldown';
    // import Highcharts3D from 'highcharts/highcharts-3d';
    // import Highmaps from 'highcharts/modules/map';
    // import 'highcharts/modules/data.js';
    // HighchartsMore(Highcharts);
    // HighchartsDrilldown(Highcharts);
    // Highcharts3D(Highcharts);
    // Highmaps(Highcharts);

    // 引入highcharts
    import Highcharts from 'highcharts/highcharts.js';
    import 'highcharts/modules/data.js';

    export default {
        name: "test",
        data(){
            return {
                chart:null,
            }
        },
        mounted(){
            this.init();

        },
        methods:{
            // 初始化
            init() {
                Highcharts.chart('container', {
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
                        data: [{
                            name: '小海一条龙服务',
                            y: 61.41,
                            sliced: true,
                            selected: true
                        }, {
                            name: '养乐多',
                            y: 11.84
                        }, {
                            name: '百事可乐',
                            y: 10.85
                        }, {
                            name: '宝格丽项链',
                            y: 4.67
                        }, {
                            name: '钻石王老五',
                            y: 4.18
                        }, {
                            name: '六位地黄丸',
                            y: 1.64
                        }]
                    }]
                });
            }
        }
    }
</script>

<style scoped lang="less">
    #test{
        #datatable {
            border: 1px solid #ccc;
            border-collapse: collapse;
            border-spacing: 0;
            font-size: 12px;
        }
        td,th {
            border: 1px solid #ccc;
            padding: 4px 20px;
        }
    }

</style>