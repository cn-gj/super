package com.superman.supermarket.dao;

import com.superman.supermarket.entity.WholeOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.WholeOrderVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface WholeOrderMapper extends BaseMapper<WholeOrder> {

    /**
     *  添加批发订单,返回受影响的行数和主键
     * @param wholeOrderVo
     * @return
     */
    int addWholeOrder(WholeOrderVo wholeOrderVo);

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
}
