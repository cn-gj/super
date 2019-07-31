package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.entity.Level;
import com.superman.supermarket.dao.LevelMapper;
import com.superman.supermarket.service.LevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {
    @Resource
    private LevelMapper levelMapper;

    /**
     * 编辑会员等级
     * @return
     */
    @Override
    public int findUpdateLeve(Level level) {
        return levelMapper.findUpdateLeve(level);
    }

    /**
     * 删除会员等级
     * @return
     */
    @Override
    public int findDeleteLeve(Integer id) {
        return levelMapper.findDeleteLeve(id);
    }

    /**
     * 查询会员等级
     * @return
     */
    @Override
    public List<Level> findSelectLeve() {
        return levelMapper.findSelectLeve();
    }

    /**
     * 新增会员等级
     * @return
     */
    @Override
    public int findinsertLeve(Level level) {
        return levelMapper.findinsertLeve(level);
    }

    @Override
    public Level findLevelByName(String levelName) {
        QueryWrapper<Level> wrapper = new QueryWrapper<>();
        wrapper.eq("level_name",levelName);
        List<Level> levels = levelMapper.selectList(wrapper);
        if (levels != null && levels.size() > 0){
            return levels.get(0);
        }
        return null;
    }

    @Override
    public long findMemberCountByIds(int[] ids) {
        return levelMapper.findMemberCountByIds(ids);
    }

    @Override
    public int batchLevel(int[] ids) {
        return levelMapper.batchLevel(ids);
    }
}
