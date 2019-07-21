package com.superman.supermarket.service.impl;

import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecTmp;
import com.superman.supermarket.dao.SpecTmpMapper;
import com.superman.supermarket.service.SpecTmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class SpecTmpServiceImpl extends ServiceImpl<SpecTmpMapper, SpecTmp> implements SpecTmpService {
    @Resource
    private SpecTmpMapper specTmpMapper;

    /**
     * 查询规格模板
     * @return
     */
    @Override
    public List<SpecTmp> findSpecTmpAndSpec(SpecTmp specTmp) {
        return specTmpMapper.findSpecTmpAndSpec(specTmp);
    }

    /**
     * 添加规格模板的同时添加规格
     * @param specTmp
     * @param specStr
     * @return
     */
    @Override
    public Integer addSpecTmpInfo(SpecTmp specTmp,String specStr) {
        //调用方法完成插入
        Integer count = specTmpMapper.addSpecTmpInfo(specTmp);
        if (count > 0){
            //获取插入后的主键
            Integer specTmpId = specTmp.getId();
            addSpecInfo(specStr,specTmpId);
        }
        return count;
    }

    /**
     * 添加规格
     * @param specStr
     * @param specTmpId
     * @return
     */
    @Override
    public Integer addSpecInfo(String specStr,Integer specTmpId) {
        List<Spec> specList = new ArrayList<>();
        //拆分字符串
        String[] specs = specStr.split(",");
        //遍历数组
        for(int i = 0; i < specs.length; i ++){
            //创建spec对象
            Spec spec = new Spec();
            //把数组中的名称赋值给spec对象
            spec.setSpecName(specs[i]);
            spec.setSpecTmpId(specTmpId);
            //把spec对象添加进集合
            specList.add(spec);
        }
        return specTmpMapper.addSpecInfo(specList);
    }

    /**
     * 删除规格模板
     * @param ids
     * @return
     */
    @Override
    public Integer delSpecTmpInfo(String ids) {
        //根据,进行拆分、并且写入数组
        String [] idstr = ids.split(",");
        int [] idIn = new int[idstr.length];
        for (int i = 0; i < idstr.length; i++){
            idIn[i] = Integer.parseInt(idstr[i]);
        }
        return specTmpMapper.delSpecTmpInfo(idIn);
    }

    /**
     * 修改规格模板
     * @param specTmp
     * @return
     */
    @Override
    public Integer updateSpecTmpInfo(SpecTmp specTmp) {
        return specTmpMapper.updateSpecTmpInfo(specTmp);
    }

    /**
     * 根据模板id，查询除所有的规格，和规格下的规格详情
     * @param id
     * @return
     */
    @Override
    public List<SpecTmp> getSpecAndSpecDetailBySpecTmpId(Integer id) {
        return specTmpMapper.getSpecAndSpecDetailBySpecTmpId(id);
    }
}
