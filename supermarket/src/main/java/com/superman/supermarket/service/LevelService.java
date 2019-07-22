package com.superman.supermarket.service;

import com.superman.supermarket.entity.Level;
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
public interface LevelService extends IService<Level> {
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
}
