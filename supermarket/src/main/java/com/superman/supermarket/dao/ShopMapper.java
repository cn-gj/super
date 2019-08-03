package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Employee;
import com.superman.supermarket.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.ShopVo;
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
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 多条件查询门店列表信息
     * @param shopVO
     * @return
     */
   List<ShopVo> getShopInfoByCondition(ShopVo shopVO);


    /**
     * 查询所有门店名称
     * @return
     */
    List<Shop> findAllShopName();

    /**
     * 根据门店id查询员工信息
     * @param id
     * @return
     */
    List<Employee> selectEmpByShopId(@Param("id") Integer id,@Param("empStatus") Integer empStatus);
    /**
     * 添加门店信息
     * @param shop
     * @return
     */
    Integer addShop(Shop shop);

    /**
     * 根据账户查询门店信息
     * @param account
     * @return
     */
    Shop findShopByAccount(String account);

    /**
     * 根据门店名称查询门店信息
     * @param shopName
     * @return
     */
    Shop getShopInfoByShopName(String shopName);

    /**
     * 修改门店列表信息
     * @param shop
     * @return
     */
    Integer updateShopInfo(Shop shop);

   /**
    * 根据id删除门店信息
    * @param id
    * @return
    */
   Integer delShopInfo(Integer id);

}
