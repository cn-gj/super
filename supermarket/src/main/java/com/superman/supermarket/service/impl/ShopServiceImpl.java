package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVo;
import com.superman.supermarket.service.ShopService;
import com.superman.supermarket.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.data.redis.core.RedisTemplate;
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
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;//redis模板

    /**
     * 查询所有门店名称
     * @return
     */
    @Override
    public String findAllShopName() {
        //获取redis中的门店名称
        String shopNameList = redisTemplate.opsForValue().get("shopNameList");
        if(shopNameList == null || shopNameList.length() == 0){
            //调用方法查询
            List<Shop> shopNames = shopMapper.findAllShopName();
            //把数据序列化为json格式并存入缓存
            shopNameList = JSON.toJSONString(shopNames);
            redisTemplate.opsForValue().set("shopNameList", shopNameList);
        }
        return shopNameList;
    }

    /**
     * 添加门店信息
     * @param shop
     * @return
     */
    @Override
    public Integer addShop(Shop shop) {
        //清空缓存数据
        redisTemplate.delete("shopNameList");
        /**
         * 	source:要加密的数据
         * 	salt:盐,扰乱码
         * 	hashIterations:加密次数
         */
        // 给员工密码进行加密
        Md5Hash md5Hash = new Md5Hash(shop.getShopPwd(), shop.getShopAccount(), 2);
        // 给员工设置加密后的密码
        shop.setShopPwd(md5Hash.toString());
        //调用方法完成添加
        return shopMapper.addShop(shop);
    }

    /**
     * 根据条件查询门店列表
     * 多条件查询
     * @param shopVO
     * @return
     */
    @Override
    public List<ShopVo> getShopInfoByCondition(ShopVo shopVO) {
        return  shopMapper.getShopInfoByCondition(shopVO);
    }

    /**
     * 根据门店账号查询门店信息
     * @param account
     * @return
     */
    @Override
    public Shop findShopByAccount(String account) {
        return shopMapper.findShopByAccount(account);
    }


    /**
     * 根据门店名称查询门店信息
     * @param shopName
     * @return
     */
    @Override
    public Shop getShopInfoByShopName(String shopName) {
        return shopMapper.getShopInfoByShopName(shopName);
    }


    /**
     * 修改门店信息
     * @param shop
     * @return
     */
    @Override
    public Integer updateShopInfo(Shop shop) {
        //清空缓存数据
        redisTemplate.delete("shopNameList");
        return shopMapper.updateShopInfo(shop);
    }

    /**
     * 根据门店id查询员工信息
     * @param id
     * @param empStatus
     * @return
     */
    @Override
    public List<Employee> selectEmpByShopId(Integer id, Integer empStatus) {
        return shopMapper.selectEmpByShopId(id,empStatus);
    }

    /**
     * 删除门店信息
     * @param id
     * @return
     */
    @Override
    public Integer delShopInfo(Integer id) {
        //调用方法查询门店下的员工信息
        List<Employee> list= shopMapper.selectEmpByShopId(id,null);
        int count =0;
        //如果门店下没有员工了才能进行删除
        if (list == null){
            //清空缓存数据
            redisTemplate.delete("shopNameList");
            count = shopMapper.delShopInfo(id);
        }
        return count;
    }

    /**
     * 下载门店列表信息
     * @param outputStream
     */
    @Override
    public void exportShop(OutputStream outputStream) {
        //获取门店信息列表
        List<ShopVo> shopVos = shopMapper.getShopInfoByCondition(null);
        // 表名称
        String sheetName = "门店信息列表_"+new Date().toString();
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
        String headTitle[] = {"门店编码","门店名称","门店类型","登录账号","联系人","联系电话","门店地址","创建时间"};
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
        for (ShopVo shop : shopVos) {
            // 创建行
            contentRow = sheet.createRow(rowIndex);
            // 创建单元格,赋值
            // 门店编号
            contentRow.createCell(0).setCellValue(shop.getId());
            // 门店名称
            contentRow.createCell(1).setCellValue(shop.getShopName());
            // 门店类型
            contentRow.createCell(2).setCellValue(shop.getShopType().getShopTypeName());
            //门店登录账号
            contentRow.createCell(3).setCellValue(shop.getShopAccount());
            // 判断门店联系人是否为空
            if(shop.getShopLinkman() != null){
                // 门店联系人
                contentRow.createCell(4).setCellValue(shop.getShopLinkman());
            }else {
                contentRow.createCell(4).setCellValue("null");
            }
            // 判断门店联系电话是否为空
            if(shop.getShopLinkman() != null){
                // 联系电话
                contentRow.createCell(5).setCellValue(shop.getShopPhone());
            }else {
                contentRow.createCell(5).setCellValue("null");
            }
            //门店地址
            contentRow.createCell(6).setCellValue(shop.getShopAddress());
            // 创建时间
            contentRow.createCell(7).setCellValue(DateUtil.date2Str(shop.getCreateDate()));

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
