package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.CashierDesk;
import com.superman.supermarket.dao.CashierDeskMapper;
import com.superman.supermarket.entity.vo.CashierDeskVO;
import com.superman.supermarket.service.CashierDeskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CashierDeskServiceImpl extends ServiceImpl<CashierDeskMapper, CashierDesk> implements CashierDeskService {

    @Resource
    private CashierDeskMapper cashierDeskMapper;

    @Override
    public List<CashierDeskVO> findByConidtion(CashierDeskVO cashierDeskVO) {
        return cashierDeskMapper.findByConidtion(cashierDeskVO);
    }

    @Override
    public CashierDeskVO findById(Integer id) {
        return cashierDeskMapper.findById(id);
    }

    @Override
    public CashierDeskVO findByName(String caseName) {
        return cashierDeskMapper.findByName(caseName);
    }

    @Override
    public int addCash(CashierDesk cashierDesk) {
        return cashierDeskMapper.addCash(cashierDesk);
    }

    @Override
    public int modifyCash(CashierDeskVO cashierDeskVO) {
        return cashierDeskMapper.modifyCash(cashierDeskVO);
    }
}
