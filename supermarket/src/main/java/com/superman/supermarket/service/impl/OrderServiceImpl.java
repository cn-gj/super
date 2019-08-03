package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.OrderMapper;
import com.superman.supermarket.entity.Order;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.entity.vo.OrderVo;
import com.superman.supermarket.service.InventoryDetailService;
import com.superman.supermarket.service.OrderService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Service
@EnableTransactionManagement
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private InventoryDetailService inventoryDetailService;

    /**
     * 多条件查询订单
     * @param orderVo
     * @return
     */
    @Override
    public List<OrderVo> findAllOrderOrCondition(OrderVo orderVo) {
        return orderMapper.findAllOrderOrCondition(orderVo);
    }

    /**
     * 添加采购订单
     *
     * @param orderVo 订单对象
     * @param str 订单明细对象（多个）
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer addOrder(OrderVo orderVo, String str) {
        //用于接收订单明细对象
        List<OrderDetailVo> details = new ArrayList<>();
        OrderDetailVo detail = null;
        // 把jaon字符串转换为数组
        JSONArray array = JSON.parseArray(str);
        //调用方法生成订单
        int count = orderMapper.addOrder(orderVo);
        //遍历jaon字符串转换后的数组
        for (int i = 0; i < array.size(); i++) {
            // 获取数组中的对象
            JSONObject object = (JSONObject) array.get(i);
            detail = new OrderDetailVo();
            detail.setGoodsId(object.getInteger("id"));
            detail.setGoodsCount(object.getInteger("goodsCount"));
            detail.setTotalMoney(object.getDouble("totalMoney"));
            detail.setOrderId(orderVo.getId());
            // JsonArray中的对象是JSONObject类型将它转换成订单明细类型对像
            /*detail = (OrderDetailVo) JSONObject.toJavaObject(object, OrderDetailVo.class);
            //获取到订单的id、并设置进订单明细中
            detail.setOrderId(orderVo.getId());*/
            // 放入订单明细集合中
            details.add(detail);
        }
        //给order设置参数、把订单明细集合放入订单中
        orderVo.setOrderDetailVoList(details);
        count += orderMapper.addOrderDetail(details);
        return count;
    }


    /**
     * 修改采购订单单据状态
     * @param id
     * @return
     */
    @Override
    public Integer updateOrderSingleState(Integer id,Integer singleState) {
        return orderMapper.updateOrderSingleState(id,singleState);
    }

    /**
     * 根据订单id查询订单信息即订单详情
     * @param id
     * @return
     */
    @Override
    public List<OrderVo> selOrderAndOrderDetailByOrderId(Integer id) {
        return orderMapper.selOrderAndOrderDetailByOrderId(id);
    }

    /**
     * 修改收货状态
     * @param orderId 采购订单id
     * @param takeState 采购订单收货状态
     * @param storeId 仓库id
     * @param goodsStr  商品明细JSON
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean updateTakeState(Integer orderId,Integer takeState,Integer storeId,String goodsStr) {
        //修改订单收获状态
        Integer count = orderMapper.updateTakeState(takeState,orderId);
        //同步库存
        boolean flag = inventoryDetailService.updateInventoryByOrderDetail(storeId,goodsStr);
        if (count > 0 && flag == true){
            return true;
        }
        return false;
    }

    /**
     * 删除订单的同时删除订单详情
     * @param id
     * @return
     */
    @Override
    public Integer deleteOrder(Integer id) {
        return orderMapper.deleteOrder(id);
    }

    /**
     * 下载订单列表
     * @param outputStream
     */
    @Override
    public void exportOrder(OutputStream outputStream) {
        //获取门店信息列表
        List<OrderVo> orderVoList = orderMapper.findAllOrderOrCondition(null);
        // 表名称
        String sheetName = "订单信息列表_"+new Date().toString();
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
        String headTitle[] = {"单号","店铺名称","供应商","单据状态","收获状态","收货人","收货电话","收货地址"};
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
        for (OrderVo order : orderVoList) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 单号
            contentRow.createCell(0).setCellValue(order.getId());
            // 门店名称
            contentRow.createCell(1).setCellValue(order.getShop().getShopName());
            // 供应商
            contentRow.createCell(2).setCellValue(order.getProvider().getProName());
            //订单状态
            if (order.getSingleState() == 0){
                contentRow.createCell(3).setCellValue("待审核");
            }else if (order.getSingleState() == 1){
                contentRow.createCell(3).setCellValue("已审核");
            }else {
                contentRow.createCell(3).setCellValue("null");
            }

            // 收获状态
            if (order.getTakeState() == 0){
                contentRow.createCell(4).setCellValue("待收货");
            }else if (order.getTakeState() == 1){
                contentRow.createCell(4).setCellValue("已退货");
            }else if (order.getTakeState() == 2){
                contentRow.createCell(4).setCellValue("带退货");
            }else {
                contentRow.createCell(4).setCellValue("null");
            }
            // 收货人
            contentRow.createCell(5).setCellValue(order.getEmployee().getEmpName());
            //收货电话
            contentRow.createCell(6).setCellValue(order.getEmployee().getEmpPhone());
            // 收货地址
            contentRow.createCell(7).setCellValue(order.getShop().getShopAddress());

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

    /**
     * 修改订单的退货状态
     * @param orderId 订单id
     * @param storeId 仓库id
     * @param takeState  退货状态
     * @param goodsStr 商品明细JSON
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Boolean updateOrderTackState(Integer orderId,Integer takeState,Integer storeId,String goodsStr) {
       Integer count = orderMapper.updateTakeState(takeState,orderId);
       boolean falg= inventoryDetailService.updateInventoryByOrderDetailReturn(storeId, goodsStr);
        System.out.println(falg+"_+_+_+_+_+_+_+_+_+_");
       if (count > 0 && falg){
           System.out.println("count&&&+++++++++++++++++++++++++++++++");
           return true;
       }
        return false;
    }
}
