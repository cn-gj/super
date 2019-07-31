package com.superman.supermarket.service;

import com.superman.supermarket.entity.SpecDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface SpecDetailService extends IService<SpecDetail> {
    /**
     * 添加规格详情
     * @param specDetail
     * @return
     */
    Integer addSpecDetail(SpecDetail specDetail);

    /**
     * 删除规格详情
     * @param ids
     * @return
     */
    Integer deleteSpecDetail(int [] ids);

    /**
     * 根据规格id查询规格详情
     * @param id
     * @return
     */
    List<SpecDetail> selectSpecDetailBySpecId(Integer id);

    /**
     * 根据规格详情id查询商品信息
     * @param ids
     * @return
     */
    Long selectGoodsBySpecDetailId(int [] ids);
}
