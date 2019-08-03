package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.dao.MemberMapper;
import com.superman.supermarket.dao.TicketDetailMapper;
import com.superman.supermarket.dao.TicketMapper;
import com.superman.supermarket.entity.Ticket;
import com.superman.supermarket.entity.TicketDetail;
import com.superman.supermarket.entity.vo.TicketVo;
import com.superman.supermarket.service.TicketService;
import com.superman.supermarket.utils.DateUtil;
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
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {

    @Resource
    private TicketMapper ticketMapper;
    @Resource
    private TicketDetailMapper ticketDetailMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    @Override
    public List<TicketVo> findByCondition(TicketVo ticketVo) {
        return ticketMapper.findByCondition(ticketVo);
    }

    @Override
    public TicketVo findById(Integer id) {
        // 1.根据id去查询收银单信息
        TicketVo ticketVo = ticketMapper.findById(id);
        // 2.根据id去查询该收银单下的收银单明细
        ticketVo.setDetails(ticketDetailMapper.findByTicketId(id));
        return ticketVo;
    }


    @Override
    public void exportTicket(OutputStream outputStream) {
        // 获取收银单信息列表
        List<TicketVo> tickets = ticketMapper.findByCondition(null);
        // 表名称
        String sheetName = "收银单列表_"+new Date().toString();
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
        String headTitle[] = {"零售单号","门店名称","收银台编码","收银台名称","销售数量","金额","创建时间"};
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
        for (TicketVo  ticket: tickets) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 单号
            contentRow.createCell(0).setCellValue(ticket.getId());
            // 门店名称
            contentRow.createCell(1).setCellValue(ticket.getShopName());
            // 收银台号
            contentRow.createCell(2).setCellValue(ticket.getCashierDeskId());
            // 收银台名称
            contentRow.createCell(3).setCellValue(ticket.getCashName());
            // 销售数量
            contentRow.createCell(4).setCellValue(ticket.getSellCount());
            // 金额
            contentRow.createCell(5).setCellValue(ticket.getPracticalMoney());
            // 创建时间
            contentRow.createCell(6).setCellValue(DateUtil.date2Str(ticket.getPlaceOrderDate()));

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
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean addTicket(Ticket ticket, List<TicketDetail> detailList){
            // 1.添加收银单
            int count = ticketMapper.addTicket(ticket);
            int ticketId = ticket.getId();
            /*int num = 1/0;*/
            if (count < 0){
                return false;
            }
            // 2.如果是会员购买，需要修改会员积分
            if (ticket.getMemberId() != null){
                int [] ids = {ticket.getMemberId()};
                int mCount = memberMapper.batchUpdateSorce(ids,ticket.getSingleScore());

                if (mCount<0)  return false;
            }
            // 3.添加小票明细
            for (TicketDetail td : detailList){
                td.setTicketId(ticketId);
            }
            int dCount = ticketDetailMapper.addTicketDetail(detailList);
            if (dCount != detailList.size()) return false;

            // 4.同步仓库
            int iCount = inventoryDetailMapper.updateInvByTicketDetailList(detailList);
            if (iCount>0){
                return true;
            }
        return false;
    }
}
