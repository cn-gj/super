package com.superman.supermarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.WholeOrder;
import com.superman.supermarket.entity.vo.WholeOrderVo;

import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface WholeOrderService extends IService<WholeOrder> {

    /**
     *  添加批发订单,返回受影响的行数和主键
     * @param str
     * @return
     */
    boolean addWholeOrder(WholeOrderVo wholeOrderVo,String str);

    /**
     *  根据条件查询批发订单
     * @param wholeOrderVo
     * @return
     */
    List<WholeOrderVo> findByCondition(WholeOrderVo wholeOrderVo);

    /**
     *  根据id查询批发订单
     * @param id
     * @return
     */
    WholeOrderVo findById(int id);

    /**
     * 修改批发订单
     * @param wholeOrder
     * @return
     */
    int updateWhole(WholeOrder wholeOrder);

    /**
     *  导出批发订单
     * @param outputStream
     */
    void exportWhole(OutputStream outputStream);

    /**
     *  批发订单出库
     * @param
     * @return
     */
    String outWholeStore(Integer wholeId,Integer storeId);

    /**
     *  批发订单入库即退货
     * @param
     * @return
     */
    String inWholeStore(Integer wholeId,Integer storeId);

}
