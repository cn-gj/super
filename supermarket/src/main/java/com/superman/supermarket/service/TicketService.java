package com.superman.supermarket.service;

import com.superman.supermarket.entity.Ticket;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superman.supermarket.entity.TicketDetail;
import com.superman.supermarket.entity.vo.TicketVo;

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
public interface TicketService extends IService<Ticket> {

    /**
     *  根据条件查询收银单信息
     * @param ticketVo
     * @return
     */
    List<TicketVo> findByCondition(TicketVo ticketVo);

    /**
     *  根据id查询收银单信息
     * @param id
     * @return
     */
    TicketVo findById(Integer id);


    /**
     *  导出收银单execl表格
     * @param outputStream
     */
    void exportTicket(OutputStream outputStream);

    /**
     * 添加收银单
     * @param ticket 收银单
     * @param detailList 收银单明细
     * @return
     */
    boolean addTicket(Ticket ticket, List<TicketDetail> detailList);
}
