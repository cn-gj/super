package com.superman.supermarket.dao;

import com.superman.supermarket.entity.CashierDesk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.CashierDeskVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface CashierDeskMapper extends BaseMapper<CashierDesk> {

    /**
     *  根据条件查询,空值查询所有
     * @param cashierDeskVO
     * @return
     */
    List<CashierDeskVO> findByConidtion(CashierDeskVO cashierDeskVO);

    /**
     *  根据id查询收银台信息
     * @param id
     * @return
     */
    CashierDeskVO findById(Integer id);

    /**
     *  根据收银台名称查询收银台信息
     * @param caseName
     * @return
     */
    CashierDeskVO findByName(String caseName);

    /**
     *  添加收银台
     * @param cashierDesk
     * @return
     */
    int addCash(CashierDesk cashierDesk);

    /**
     *  修改收银台,可以修改基本信息,停用收营台
     * @param cashierDeskVO
     * @return
     */
    int modifyCash(CashierDeskVO cashierDeskVO);
}
