package com.superman.supermarket.service;

import com.superman.supermarket.entity.TicketDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.vo.TicketDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface TicketDetailService extends IService<TicketDetail> {
    /**
     *  根据收银单id查询收银单详情信息列表
     * @param id
     * @return
     */
    // List<TicketDetailVo> findByTicketId(Integer id);
}
