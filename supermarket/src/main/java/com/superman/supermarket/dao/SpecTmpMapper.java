package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecTmp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SpecTmpMapper extends BaseMapper<SpecTmp> {

    /**
     * 查询规格模板、以及某一个规格模板对应的规格数
     * @return
     */
    List<SpecTmp> findSpecTmpAndSpec(@Param("condition") String condition);

    /**
     * 添加规格模板
     * @param specTmp
     * @return
     */
    Integer addSpecTmpInfo(SpecTmp specTmp);

    /**
     * 添加规格
     * @param spec
     * @return
     */
    Integer addSpecInfo(@Param("spec") List<Spec> spec);

    /**
     *  删除规格模板
     * @param ids
     * @return
     */
    Integer delSpecTmpInfo(@Param("ids") int [] ids);

    /**
     * 修改规格模板
     * @param specTmp
     * @return
     */
    Integer updateSpecTmpInfo(SpecTmp specTmp);

    /**
     * 根据模板id，查询除所有的规格，和规格下的规格详情
     * @param ids
     * @return
     */
    List<SpecTmp> getSpecAndSpecDetailBySpecTmpId(@Param("ids") int [] ids);

    /**
     *查询所有规格模板、规格、规格详情
     * @return
     */
    List<SpecTmp>selectSpecTmpAndSpecAndSpecDetail();

    /**
     * 查询规格模板
     * @param specTmpName
     * @return
     */
    SpecTmp selectSpecTmpByName(String specTmpName);
}
