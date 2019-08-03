package com.superman.supermarket.dao;

import com.superman.supermarket.entity.TicketDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.TicketDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface TicketDetailMapper extends BaseMapper<TicketDetail> {

    /**
     *  根据收银单id查询收银单详情信息列表
     * @param id
     * @return
     */
    List<TicketDetailVo> findByTicketId(Integer id);

    /**
     * 添加小票明细
     * @param detailList
     * @return
     */
    int addTicketDetail(@Param("detailList") List<TicketDetail> detailList);
}
