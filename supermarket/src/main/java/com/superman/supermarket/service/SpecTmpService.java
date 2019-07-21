package com.superman.supermarket.service;

import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecTmp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface SpecTmpService extends IService<SpecTmp> {

    /**
     * 查询规格模板、以及某一个规格模板对应的规格数
     * @return
     */
    List<SpecTmp> findSpecTmpAndSpec(SpecTmp specTmp);

    /**
     * 添加规格模板
     * @param specTmp
     * @return
     */
    Integer addSpecTmpInfo(SpecTmp specTmp,String specStr);

    /**
     * 添加规格
     * @param
     * @return
     */
    Integer addSpecInfo(String specStr,Integer specTmpId);

    /**
     *  删除规格模板
     * @param ids
     * @return
     */
    Integer delSpecTmpInfo(String ids);

    /**
     * 修改规格模板
     * @param specTmp
     * @return
     */
    Integer updateSpecTmpInfo(SpecTmp specTmp);

    /**
     * 根据模板id，查询除所有的规格，和规格下的规格详情
     * @param id
     * @return
     */
    List<SpecTmp> getSpecAndSpecDetailBySpecTmpId(Integer id);
}
