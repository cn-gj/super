package com.superman.supermarket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superman.supermarket.entity.GoodsType;
import com.superman.supermarket.dao.GoodsTypeMapper;
import com.superman.supermarket.service.GoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> findByCondition(GoodsType goodsType) {
        // 判断商品名称是否为空,空查询所有,否则根据商品名称查询
        if (goodsType != null && goodsType.getGoodsTypeName() != null){
            QueryWrapper<GoodsType> wrapper = new QueryWrapper<>();
            wrapper.eq("goods_type_name",goodsType.getGoodsTypeName());
            return goodsTypeMapper.selectList(wrapper);
        }else {
            return goodsTypeMapper.selectList(null);
        }
    }

    @Override
    public int modifyById(GoodsType goodsType) {
        return goodsTypeMapper.updateById(goodsType);
    }

    @Override
    public int addGoodsType(GoodsType goodsType) {
        return goodsTypeMapper.insert(goodsType);
    }

    @Override
    public int deleteByIds(int [] ids) {
        return goodsTypeMapper.deleteByIds(ids);
    }

    @Override
    public Boolean selectGoodsByGoodsType(int[] ids) {
        if (goodsTypeMapper.selectGoodsByGoodsType(ids) > 0){
            return true;
        }
        return false;
    }
}
