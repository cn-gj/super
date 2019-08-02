package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Goods;
import com.superman.supermarket.dao.GoodsMapper;
import com.superman.supermarket.entity.vo.GoodsVo;
import com.superman.supermarket.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Goods findByGoodsCode(String goodsCode) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_code",goodsCode);
        List<Goods> list = goodsMapper.selectList(wrapper);
        if(list != null  && list.size() >  0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public GoodsVo selGoodsAndInventoryDetailByGoodsCode(String goodsCode) {
        return goodsMapper.selGoodsAndInventoryDetailByGoodsCode(goodsCode);
    }

    @Override
    public Goods findByGoodsName(String goodsName) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_name",goodsName);
        List<Goods> list = goodsMapper.selectList(wrapper);
        if(list != null && list.size() >  0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int addGoods(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public List<GoodsVo> findByCondition(GoodsVo goodsVo) {
        return goodsMapper.findByCondition(goodsVo);
    }

    @Override
    public int batchModify(int[] ids, int goodsTypeId) {
        return goodsMapper.batchModify(ids,goodsTypeId);
    }

    @Override
    public int batchDelete(int[] ids) {
        return goodsMapper.batchDelete(ids);
    }

    @Override
    public List<GoodsVo> findById(Integer id,Integer providerId) {
        return goodsMapper.findById(id,providerId);
    }

    /**
     * 根据供应商id查询商品信息
     * @param providerId
     * @return
     */
    @Override
    public List<Goods> selGoodsByProviderId(Integer providerId) {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.eq("provider_id",providerId);
        return goodsMapper.selectList(query);
    }

    @Override
    public void exportGoods(OutputStream outputStream) {
        // 获取商品信息列表
        List<GoodsVo> list = goodsMapper.findByCondition(null);
        // 表名称
        String sheetName = "商品信息列表_"+new Date().toString();
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
        String headTitle[] = {"商品名称","商品条码","零售价","会员价","批发价","进价","库存","商品类型","单位"};
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
        for (GoodsVo gv : list) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 商品名称
            contentRow.createCell(0).setCellValue(gv.getGoodsName());
            // 商品编码
            contentRow.createCell(1).setCellValue(gv.getGoodsCode());
            // 零售价
            contentRow.createCell(2).setCellValue(gv.getPrice());
            // 会员价
            contentRow.createCell(3).setCellValue(gv.getMemberPrice());
            // 批发价
            contentRow.createCell(4).setCellValue(gv.getWholePrice());
            // 进货价
            contentRow.createCell(5).setCellValue(gv.getCostPrice());
            if (gv.getGoodsCount() == null){
                contentRow.createCell(6).setCellValue("");
            }else {
                // 库车
                contentRow.createCell(6).setCellValue(gv.getGoodsCount());
            }
            // 商品类型
            contentRow.createCell(7).setCellValue(gv.getGoodsTypeName());
            // 单位
            contentRow.createCell(8).setCellValue(gv.getUnitId());

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
    public int updateGoods(Goods goods) {
        return goodsMapper.updateById(goods);
    }
}
