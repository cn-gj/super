package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Member;
import com.superman.supermarket.dao.MemberMapper;
import com.superman.supermarket.entity.vo.GoodsVo;
import com.superman.supermarket.entity.vo.MemberVo;
import com.superman.supermarket.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;

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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private MemberMapper memberMapper; //注入会员mapper

    @Override
    public List<MemberVo> findAllMember(MemberVo memberVo) {
        return memberMapper.findAllMember(memberVo);
    }

    @Override
    public Integer addMember(Member member,Integer shopId) {
        System.out.println(shopId);
        member.setShopId(shopId);//设置会员所属门店为当前登录的门店编号
        return memberMapper.addMember(member);
    }
    @Override
    public Integer deleteMember(int[] ids) {
        return memberMapper.deleteMember(ids);
    }

    @Override
    public Integer updateMember(Member member) {
        return memberMapper.updateMember(member);
    }

    @Override
    public Integer batchUpdateMemberLevel(int[] ids, Integer levelId) {
        return memberMapper.batchUpdateMemberLevel(ids,levelId);
    }

    @Override
    public Integer batchUpdateSorce(int[] ids, Integer totalSorce) {
        return memberMapper.batchUpdateSorce(ids,totalSorce);
    }


    @Override
    public Member findMemberByMemberPhone(String memberPhone,Integer shopId) {
        return memberMapper.findMemberByMemberPhone(memberPhone,shopId);
    }


    @Override
    public MemberVo selMemberAndLevelByShopIdAndMemberPhone(String memberPhone, Integer shopId) {
        return memberMapper.selMemberAndLevelByShopIdAndMemberPhone(memberPhone,shopId);
    }

    @Override
    public void exportMember(OutputStream outputStream) {
        // 获取商品信息列表
        List<MemberVo> list = memberMapper.findAllMember(null);
        // 表名称
        String sheetName = "会员信息列表_"+new Date().toString();
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
        String headTitle[] = {"门店编码","门店名称","会员卡号","会员昵称","手机号码","会员卡类型","累计金额","剩余金额","累计积分","剩余积分"};
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
        // 写入商品信息到表格中
        for (MemberVo mb : list) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 门店编码
            contentRow.createCell(0).setCellValue(mb.getShop().getId());
            // 门店名称
            contentRow.createCell(1).setCellValue(mb.getShop().getShopName());
            // 会员卡号
            contentRow.createCell(2).setCellValue(mb.getMemberPhone());
            // 会员昵称
            contentRow.createCell(3).setCellValue(mb.getMemberName());
            // 手机号码
            contentRow.createCell(4).setCellValue(mb.getMemberPhone());
            // 会员卡类型
            contentRow.createCell(5).setCellValue(mb.getLevel().getLevelName());
            //累计金额
            contentRow.createCell(6).setCellValue(mb.getTotalMoney());
            // 剩余金额
            contentRow.createCell(7).setCellValue(mb.getResidueMoney());
            // 累计积分
            contentRow.createCell(8).setCellValue(mb.getTotalSorce());
            // 剩余积分
            contentRow.createCell(9).setCellValue(mb.getResidueSorce());
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
}
