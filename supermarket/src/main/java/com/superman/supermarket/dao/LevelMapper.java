package com.superman.supermarket.dao;

import com.superman.supermarket.entity.Level;
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
public interface LevelMapper extends BaseMapper<Level> {
    /**
     * 编辑会员等级
     * @return
     */
    int findUpdateLeve(Level level);

    /**
     * 删除会员等级
     * @return
     */
    int findDeleteLeve(Integer id);

    /**
     * 查询会员等级
     * @return
     */
    List<Level> findSelectLeve();

    /**
     * 新增会员等级
     * @return
     */
    int findinsertLeve(Level level);

    /**
     * 根据等级id批量查询会员个数
     * @param ids
     * @return
     */
    long findMemberCountByIds(@Param("ids") int[]ids);

    /**
     * 根据等级id批量查询会员个数
     * @param ids
     * @return
     */
    int batchLevel(@Param("ids") int[]ids);
}
