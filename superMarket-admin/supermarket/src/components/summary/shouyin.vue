<template>
    <div id="shouyin">
        <div class="alert alert-success" role="alert">店铺汇总</div>
        <!--数据-->
        <table class="my-tab table table-bordered">
            <thead>
            <tr>
                <th>序号</th>
                <th>店铺</th>
                <th>销售金额</th>
                <th>销售毛利</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(shop,index) in shopSummaryList" :key="shop.shopName">
                <td>{{index+1}}</td>
                <td>{{shop.shopName}}</td>
                <td>{{shop.sumPracticalMoney}}</td>
                <td>{{shop.saleMargin}}</td>
            </tr>
            </tbody>
        </table>
        <div class="alert alert-success" role="alert">出售商品汇总</div>
        <!--数据-->
        <table class="my-tab table table-bordered">
            <thead>
            <tr>
                <th>序号</th>
                <th>商品名称</th>
                <th>销量</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(goods,index) in goodsSummaryList" :key="goods.goodsName">
                <td>{{index+1}}</td>
                <td>{{goods.goodsName}}</td>
                <td>{{goods.goodsCount}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "shouyin",
        data(){
            return{
                shopSummaryList:[],
                goodsSummaryList:[]
            }
        },
        created() {
            this.init();
        },
        methods:{
            init(){
                // 获取门店汇总数据列表
                this.$http.post('shopSummary/shopSummaryList').then(result => {
                    let data = result.data;
                    let shopSummaryList = [];
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
                        shopSummaryList.push({shopName:item.shopName,sumPracticalMoney:sumPracticalMoney,saleMargin:saleMargin});
                    })
                    this.shopSummaryList = shopSummaryList;
                })
                // 获取商品汇总数据列表
                this.$http.post('shopSummary/selGoodsSellCake').then(result => {
                    this.goodsSummaryList = result.data;
                })
            }
        }
    }
</script>

<style scoped>

</style>