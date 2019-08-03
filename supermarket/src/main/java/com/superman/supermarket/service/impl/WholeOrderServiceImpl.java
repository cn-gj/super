package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.GoodsMapper;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.dao.OrderDetailMapper;
import com.superman.supermarket.dao.WholeOrderMapper;
import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.WholeOrder;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.entity.vo.WholeOrderVo;
import com.superman.supermarket.service.WholeOrderService;
import com.superman.supermarket.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
@Transactional
public class WholeOrderServiceImpl extends ServiceImpl<WholeOrderMapper, WholeOrder> implements WholeOrderService {

    @Resource
    private WholeOrderMapper wholeOrderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private InventoryDetailMapper inventoryDetailMapper;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public boolean addWholeOrder(WholeOrderVo wholeOrderVo, String str) {
        /*WholeOrderVo wholeOrderVo = null;
        // 将JSON格式字符串转换成jsonObject对象
        JSONObject jsonObject =JSONObject.parseObject(str);
        // 将jsonObject中的listlist对象装换成JSONArray
        JSONArray array = jsonObject.getJSONArray("details");
        List<OrderDetailVo> details = new ArrayList<>();
        for (int i=0;i<array.size();i++){
            // 获取集合中的对象
            JSONObject object = (JSONObject) array.get(i);
            // JsonArray中的对象是JSONObject类型将它转换成订单明细类型对像
            OrderDetailVo detail = JSONObject.toJavaObject(object,OrderDetailVo.class);
            // 放入订单明细集合中
            details.add(detail);
        }

        wholeOrderVo = new WholeOrderVo();
        // 从JSONObject中当个取值
        wholeOrderVo.setCustomerId(jsonObject.getInteger("customerId")); // 设置客户id
        wholeOrderVo.setShopId(jsonObject.getInteger("shopId"));  // 设置店铺id
        wholeOrderVo.setReadyDate(jsonObject.getDate("readyDate")); // 设置预期收货日期
        wholeOrderVo.setEmpId(jsonObject.getInteger("empId")); // 设置员工id
        wholeOrderVo.setDetails(details); // 设置订单明细列表

        //System.out.println(wholeOrderVo.getDetails().size()+".....");
        // 添加成功总受影响行数
        int totalRow = 1 + wholeOrderVo.getDetails().size();
        // 初始化订单参数
        wholeOrderVo.setSingleState(0); // 待审核状态
        wholeOrderVo.setTakeState(0);  // 待收货状态
        // 先添加批发订单,获取批发订单id
        int rowCount = wholeOrderMapper.addWholeOrder(wholeOrderVo);
        //System.out.println("添加额批发订单id:"+wholeOrderVo.getId()+"...............");
        // 遍历订单下的订单明细进行循环添加
        for (OrderDetail od: wholeOrderVo.getDetails()) {
            // 给od设置初始化属性值
            // 订单id
            od.setOrderId(wholeOrderVo.getId());
            // 订单类型:批发订单
            od.setOrderType(1);
            // 累计受影响行数
            rowCount += orderDetailMapper.addOrderDetail(od);
        }
       if (rowCount == totalRow){
           return  true;
       }else {
           return false;
       }*/
   // System.out.println(str+"........................");
        int totalCount = 1;
        // 添加批发订单
        wholeOrderVo.setTakeState(0);
        wholeOrderVo.setSingleState(0);
      int rowCount = wholeOrderMapper.addWholeOrder(wholeOrderVo);
        // 将字符串转成list集合
        JSONArray jsonArray = JSON.parseArray(str);
        if (jsonArray != null &&jsonArray.size() > 0){
            totalCount += jsonArray.size();
            OrderDetail orderDetail = null;
            // 循环添加list集合中的订单明细
            for (int i = 0; i<jsonArray.size(); i++){
                // 将jsonArray中的元素转成实体类
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                orderDetail = JSONObject.toJavaObject(jsonObject,OrderDetail.class);
                orderDetail.setOrderId(wholeOrderVo.getId());
                orderDetail.setOrderType(1);
                if (orderDetail != null){
                    rowCount += orderDetailMapper.addOrderDetail(orderDetail);
                }
            }
        }

        // 判断是否成功添加
        if (totalCount == rowCount){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public List<WholeOrderVo> findByCondition(WholeOrderVo wholeOrderVo) {
        // 查询订单单据金额
        List<WholeOrderVo> wholeOrderVos = wholeOrderMapper.findByCondition(wholeOrderVo);
        for (WholeOrderVo w:wholeOrderVos) {
            w.setWhoMoney(orderDetailMapper.findWholeMoney(w.getId()));
        }
        return wholeOrderVos;
    }

    @Override
    public WholeOrderVo findById(int id) {
        WholeOrderVo wholeOrderVo = wholeOrderMapper.findById(id);
        if (wholeOrderVo != null){
            // 根据订单id查询该订单下的订单明细
            wholeOrderVo.setDetails(orderDetailMapper.findByWholeId(id));
        }
        return wholeOrderVo;
    }

    @Override
    public int updateWhole(WholeOrder wholeOrder) {
        return wholeOrderMapper.updateWhole(wholeOrder);
    }

    @Override
    public void exportWhole(OutputStream outputStream) {
        // 获取批发订单信息列表
        List<WholeOrderVo> whos = wholeOrderMapper.findByCondition(null);
        // 表名称
        String sheetName = "批发订单信息列表_"+new Date().toString();
        // 创建execl工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建execl表对象
        HSSFSheet sheet = workbook.createSheet();

        // 创建单元格样式对象
        HSSFCellStyle headCellStyle = workbook.createCellStyle();
        // 设置头部标题单元格水平居中
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置头部标题单元格垂直居中
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        // 创建字体样式对象
        HSSFFont headFont = workbook.createFont();
        headFont.setFontName("黑体");// 设置字体样式
        headFont.setBold(true); // 字体加粗
        headFont.setFontHeightInPoints((short)11);  // 设置字体大小

        // 给头部样式设置头部字体样式
        headCellStyle.setFont(headFont);

        // 头部标题
        String headTitle[] = {"单号","店铺名称","客户名称","单据状态","发货状态","单据金额","创建时间"};
        // 创建标题行对象
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = null;
        // 遍历标题内容到表格中
        for (int i = 0; i < headTitle.length; i++){
            // 创建单元格,并赋值
            titleCell = titleRow.createCell(i);
            titleCell.setCellValue(headTitle[i]);
            // 给头部单元格设置样式
            titleCell.setCellStyle(headCellStyle);
            // 给列设置宽度
            sheet.setColumnWidth(i,8000);
        }


        HSSFRow contentRow = null;  // 内容行对象
        Integer rowIndex = 1;   // 内容行起始索引
        // 写入员工信息到表格中
        for (WholeOrderVo w : whos) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 单号
            contentRow.createCell(0).setCellValue(w.getId());
            // 店铺名称
            contentRow.createCell(1).setCellValue(w.getShopName());
            // 客户名称
            contentRow.createCell(2).setCellValue(w.getEmpName());
            // 判断单据状态
            if(w.getSingleState() == 0){
                // 待完成
                contentRow.createCell(3).setCellValue("待完成");
            }else if(w.getSingleState() == 1){
                contentRow.createCell(3).setCellValue("完成");
            }

            if(w.getTakeState() == 0){
                // 待完成
                contentRow.createCell(4).setCellValue("待发货");
            }else if(w.getTakeState() == 1){
                contentRow.createCell(4).setCellValue("已发货");
            }else if(w.getTakeState() == 2){
                contentRow.createCell(4).setCellValue("未退货");
            }else if(w.getTakeState() == 3){
                contentRow.createCell(4).setCellValue("已退货");
            }
            // 获取单据金额
            w.setWhoMoney(orderDetailMapper.findWholeMoney(w.getId()));
            // 单据金额
            if (w.getWhoMoney() != null){
                contentRow.createCell(5).setCellValue(w.getWhoMoney());
            }else {
                contentRow.createCell(5).setCellValue(0.0);
            }
            // 创建时间
            contentRow.createCell(6).setCellValue(DateUtil.date2Str(w.getPlaceOrderDate()));

            rowIndex++;
        }

        try {
            // 将数据写入流中
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭工作簿
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    @Override
    public String outWholeStore(Integer wholeId, Integer storeId) {
        Map<String,Object> map = new HashMap<>();
        // 获取批发订单下的明细
        List<OrderDetailVo> orderDetailVos = findById(wholeId).getDetails();
        // 获取用户选择仓库下的商品明细
        List<InventoryDetail> details = inventoryDetailMapper.selectList(new QueryWrapper<InventoryDetail>().eq("store_id",storeId));
        // 获取批发订单明细商品id数组
        int [] goodsIds = new int[orderDetailVos.size()];
        for (int i = 0; i<orderDetailVos.size(); i++) {
            goodsIds[i] = orderDetailVos.get(i).getGoodsId();
          //  System.out.println(goodsIds[i]+"///////////////");
        }

        // 从库存中获取没有的商品id
       Integer[] gs =  inventoryDetailMapper.findGoodsIdByIds(goodsIds,storeId);  // 获取仓库中有的商品id数组
       /* for (int j = 0; j < gs.length; j++) {
            System.out.println(gs[j]+",,,,,,,,,,");
        }*/
        List<Integer> noId = new ArrayList<>();
        if (gs != null) {
            /*for (int i = 0; i < goodsIds.length; i++) {
                for (int j = 0; j < gs.length; j++) {
                    if (goodsIds[i] != gs[j]) {
                       // System.out.println(gs[j]+",,,,,,,,,,");
                        //System.out.println(goodsIds[i] + "==" + gs[j]);
                        noId.add(goodsIds[i]);
                    }
                }
            }*/

            for(int i=0;i<goodsIds.length;i++) {
                boolean flag = true;
                int temp = goodsIds[i];
                for (int j = 0; j < gs.length; j++) {
                    if (temp == (gs[j])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    noId.add(temp);
                }
            }

            /*for (int i=0;i<noId.size();i++){
                System.out.println(noId.get(i));
            }*/
        }



    // 判断是否有没有的商品
        if (noId.size() > 0){
            List<String> goodsNames =  goodsMapper.findGoodsNameByIds(noId);
            map.put("result",false);
            map.put("message",goodsNames);
        }else{
            // 商品都存在
            // 扣除库存商品数量
            int rowCount = 0;
            for (OrderDetailVo o :orderDetailVos) {
                //int count;
                /*if(inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId()) < 0){
                     count =  o.getGoodsCount() + inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId());
                }else{
                     count =  o.getGoodsCount() - inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId());
                }*/
               int count = inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId()) - o.getGoodsCount();
              System.out.println(inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId())+"-"+o.getGoodsCount()+"="+count);
               rowCount += inventoryDetailMapper.updateInvByGoodsIds(storeId,o.getGoodsId(),count);
            }

            // 判断是否入库成功
            if (rowCount == orderDetailVos.size()){
                 //map.put("result",true);
                 // 将订单状态改为已入库
                WholeOrder wholeOrder = new WholeOrder();
                wholeOrder.setId(wholeId);
                wholeOrder.setTakeState(1);
               int count =  wholeOrderMapper.updateWhole(wholeOrder);
               if (count > 0){
                   map.put("result",true);
               }else {
                   map.put("result",false);
               }
            }else {
                 map.put("result",false);
            }
        }
        //System.out.println(gs[0]+".....................");
        /*System.out.println(orderDetailVos.size()+"..................");
        System.out.println(details.size()+"..................");
        System.out.println(goodsIds.length+"..................");*/
        return JSON.toJSONString(map);
    }

    @Override
    public String inWholeStore(Integer wholeId, Integer storeId) {
        Map<String,Object> map = new HashMap<>();
        // 获取批发订单下的明细
        List<OrderDetailVo> orderDetailVos = findById(wholeId).getDetails();
        // 获取用户选择仓库下的商品明细
        List<InventoryDetail> details = inventoryDetailMapper.selectList(new QueryWrapper<InventoryDetail>().eq("store_id",storeId));
        // 获取批发订单明细商品id数组
        int [] goodsIds = new int[orderDetailVos.size()];
        for (int i = 0; i<orderDetailVos.size(); i++) {
            goodsIds[i] = orderDetailVos.get(i).getGoodsId();
        }

        // 从库存中获取没有的商品id
        Integer[] gs =  inventoryDetailMapper.findGoodsIdByIds(goodsIds,storeId);  // 获取仓库中有的商品id数组
        List<Integer> noId = new ArrayList<>();
        if (gs != null) {
            for(int i=0;i<goodsIds.length;i++) {
                boolean flag = true;
                int temp = goodsIds[i];
                for (int j = 0; j < gs.length; j++) {
                    if (temp == (gs[j])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    noId.add(temp);
                }
            }
        }



        // 判断是否有没有的商品
        if (noId.size() > 0){
            InventoryDetail inv = null;
            for(int i = 0; i<noId.size(); i++){
                // 仓库中没有应该加入库存中
                inv = new InventoryDetail();
                inv.setStoreId(storeId);
                inv.setGoodsId(noId.get(i));
                //inv.setGoodsCount(orderDetailMapper.getgoodsCountByWholeIdAndGoodsId(wholeId,noId.get(i)));
                inv.setGoodsCount(0);
                inventoryDetailMapper.insert(inv);
            }
        }
            // 商品都存在
            // 库存商品数量++
            int rowCount = 0;
            for (OrderDetailVo o :orderDetailVos) {
                int count = inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId()) + o.getGoodsCount();
                System.out.println(inventoryDetailMapper.findGoodsCountById(storeId,o.getGoodsId())+"+"+o.getGoodsCount()+"="+count);
                rowCount += inventoryDetailMapper.updateInvByGoodsIds(storeId,o.getGoodsId(),count);
            }

            // 判断是否入库成功
            if (rowCount == orderDetailVos.size()){
                //map.put("result",true);
                // 将订单状态改为已入库
                WholeOrder wholeOrder = new WholeOrder();
                wholeOrder.setId(wholeId);
                wholeOrder.setTakeState(2);
                int count =  wholeOrderMapper.updateWhole(wholeOrder);
                if (count > 0){
                    map.put("result",true);
                }else {
                    map.put("result",false);
                }
            }else {
                map.put("result",false);
            }

        return JSON.toJSONString(map);
    }
}
