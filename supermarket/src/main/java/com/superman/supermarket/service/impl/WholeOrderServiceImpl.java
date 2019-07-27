package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.superman.supermarket.dao.OrderDetailMapper;
import com.superman.supermarket.entity.OrderDetail;
import com.superman.supermarket.entity.WholeOrder;
import com.superman.supermarket.dao.WholeOrderMapper;
import com.superman.supermarket.entity.vo.OrderDetailVo;
import com.superman.supermarket.entity.vo.WholeOrderVo;
import com.superman.supermarket.service.WholeOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Transactional
public class WholeOrderServiceImpl extends ServiceImpl<WholeOrderMapper, WholeOrder> implements WholeOrderService {

    @Resource
    private WholeOrderMapper wholeOrderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean addWholeOrder(String str) {
        WholeOrderVo wholeOrderVo = null;
        // 将JSON格式字符串转换成jsonObject对象
        JSONObject jsonObject =JSONObject.parseObject(str);
        // 将jsonObject中的listlist对象装换成JSONArray
        JSONArray array = jsonObject.getJSONArray("details");
        List<OrderDetailVo> details = new ArrayList<>();
        for (int i=0;i<array.size();i++){
            // 获取集合中的对象
            JSONObject object = (JSONObject) array.get(i);
            // JsonArray中的对象是JSONObject类型将它转换成订单明细类型对像
            OrderDetailVo detail = (OrderDetailVo) JSONObject.toJavaObject(object,OrderDetailVo.class);
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
}
