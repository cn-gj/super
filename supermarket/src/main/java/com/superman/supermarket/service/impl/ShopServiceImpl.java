package com.superman.supermarket.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superman.supermarket.dao.ShopMapper;
import com.superman.supermarket.entity.Shop;
import com.superman.supermarket.entity.vo.ShopVO;
import com.superman.supermarket.service.ShopService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.Date;
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
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;//redis模板

    /**
     * 查询所有门店名称
     * @return
     */
    @Override
    public String findAllShopName() {
        //获取redis中的门店名称
        String shopNameList = redisTemplate.opsForValue().get("shopNameList");
        if(shopNameList == null || shopNameList.length() == 0){
            //调用方法查询
            List<Shop> shopNames = shopMapper.findAllShopName();
            //把数据序列化为json格式并存入缓存
            shopNameList = JSON.toJSONString(shopNames);
            redisTemplate.opsForValue().set("shopNameList", shopNameList);
        }
        return shopNameList;
    }

    /**
     * 添加门店信息
     * @param shop
     * @return
     */
    @Override
    public Integer addShop(Shop shop) {
        //清空缓存数据
        redisTemplate.delete("shopNameList");
        /**
         * 	source:要加密的数据
         * 	salt:盐,扰乱码
         * 	hashIterations:加密次数
         */
        // 给员工密码进行加密
        Md5Hash md5Hash = new Md5Hash(shop.getShopPwd(), shop.getShopAccount(), 2);
        // 给员工设置加密后的密码
        shop.setShopPwd(md5Hash.toString());
        //调用方法完成添加
        return shopMapper.addShop(shop);
    }

    /**
     * 根据条件查询门店列表
     * 多条件查询
     * @param shopVO
     * @return
     */
    @Override
    public List<ShopVO> getShopInfoByCondition(ShopVO shopVO) {
        return  shopMapper.getShopInfoByCondition(shopVO);
    }

    /**
     * 根据门店账号查询门店信息
     * @param account
     * @return
     */
    @Override
    public Shop findShopByAccount(String account) {
        return shopMapper.findShopByAccount(account);
    }


    /**
     * 根据门店名称查询门店信息
     * @param shopName
     * @return
     */
    @Override
    public Shop getShopInfoByShopName(String shopName) {
        return shopMapper.getShopInfoByShopName(shopName);
    }


    /**
     * 修改门店信息
     * @param shop
     * @return
     */
    @Override
    public Integer updateShopInfo(Shop shop) {
        //清空缓存数据
        redisTemplate.delete("shopNameList");
        return shopMapper.updateShopInfo(shop);
    }

    /**
     * 删除门店信息
     * @param id
     * @return
     */
    @Override
    public Integer delShopInfo(Integer id) {
        //清空缓存数据
        redisTemplate.delete("shopNameList");
        return shopMapper.delShopInfo(id);
    }


}
