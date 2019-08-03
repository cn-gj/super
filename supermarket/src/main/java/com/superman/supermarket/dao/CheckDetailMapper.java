package com.superman.supermarket.dao;

import com.superman.supermarket.entity.CheckDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superman.supermarket.entity.vo.CheckDetailVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
public interface CheckDetailMapper extends BaseMapper<CheckDetail> {

    /**
     *  根据盘点id查询盘点明细
     * @param id
     * @return
     */
    List<CheckDetailVo> findByInvId(int id);

}
