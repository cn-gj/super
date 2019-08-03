package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.InventoryDetail;
import com.superman.supermarket.dao.InventoryDetailMapper;
import com.superman.supermarket.entity.vo.InventoryDetailVo;
import com.superman.supermarket.service.InventoryDetailService;
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
public class InventoryDetailServiceImpl extends ServiceImpl<InventoryDetailMapper, InventoryDetail> implements InventoryDetailService {

    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    @Override
    public List<InventoryDetailVo> findByStoreId(InventoryDetailVo inventoryDetailVo) {
        return inventoryDetailMapper.findByStoreId(inventoryDetailVo);
    }

    //----------------------------------
    @Override
    public List<InventoryDetailVo> selectByStoreId(int storeId) {
        return inventoryDetailMapper.selectByStoreId(storeId);
    }
}
