package com.superman.supermarket.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.ShopTypeMapper;
import com.superman.supermarket.entity.ShopType;
import com.superman.supermarket.service.ShopTypeService;
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
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements ShopTypeService {

    @Resource
    private ShopTypeMapper shopTypeMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String findShopTypeList() {
        //获取redis中的门店名称
        String shopTypeList = redisTemplate.opsForValue().get("shopTypeList");
        //System.out.println(shopTypeMapper.findAllShopType());
        if (shopTypeList == null || shopTypeList.length() == 0){
            List<ShopType> shopTypes = shopTypeMapper.findAllShopType();
            //把数据序列化为json格式并存入缓存
            shopTypeList =JSON.toJSONString(shopTypes);
            redisTemplate.opsForValue().set("shopTypeList", shopTypeList);
        }
        return shopTypeList;
    }

    /**
     * 添加门店类别信息
     * @param shopType
     * @return
     */
    @Override
    public Integer addShopType(ShopType shopType) {
        redisTemplate.delete("shopTypeList");
        return shopTypeMapper.addShopType(shopType);
    }


    /**
     * 修改门店类别信息
     * @param shopType
     * @return
     */
    @Override
    public Integer upShopType(ShopType shopType) {
        //清空缓存数据
        redisTemplate.delete("shopTypeList");
        return shopTypeMapper.upShopType(shopType);
    }

    /**
     * 删除门店信息
     * @param id
     * @return
     */
    @Override
    public Integer delShopType(Integer id) {
        redisTemplate.delete("shopTypeList");
        return shopTypeMapper.delShopType(id);
    }
}
