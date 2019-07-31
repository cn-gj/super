package com.superman.supermarket.service.impl;

import com.superman.supermarket.dao.SpecDetailMapper;
import com.superman.supermarket.dao.SpecMapper;
import com.superman.supermarket.entity.Spec;
import com.superman.supermarket.entity.SpecDetail;
import com.superman.supermarket.entity.SpecTmp;
import com.superman.supermarket.dao.SpecTmpMapper;
import com.superman.supermarket.service.SpecTmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private SpecMapper specMapper;

    @Resource
    private SpecDetailMapper specDetailMapper;

    /**
     * 查询规格模板
     * @return
     */
    @Override
    public List<SpecTmp> findSpecTmpAndSpec(String condition) {
        return specTmpMapper.findSpecTmpAndSpec(condition);
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
        List<Spec> specList = new ArrayList<>();
        //擦分字符串、获取规格名
        String [] specs = specStr.split(",");
        if (count > 0){
            //获取插入后的主键
            Integer specTmpId = specTmp.getId();
            //遍历数组
            for (int i = 0 ; i<specs.length; i++){
                Spec spec = new Spec();
                spec.setSpecName(specs[i]);
                spec.setSpecTmpId(specTmpId);
                specList.add(spec);
            }
            //调用方法添加规格
            specTmpMapper.addSpecInfo(specList);
        }
        return count;
    }

    /**
     * 添加规格、及规格
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
     * 查询所有规格模板、规格、规格详情
     * @return
     */
    @Override
    public List<SpecTmp> selectSpecTmpAndSpecAndSpecDetail() {
        return specTmpMapper.selectSpecTmpAndSpecAndSpecDetail();
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
     * @param ids
     * @return
     */
    @Override
    public List<SpecTmp> getSpecAndSpecDetailBySpecTmpId(int [] ids) {
        return specTmpMapper.getSpecAndSpecDetailBySpecTmpId(ids);
    }

    /**
     * 查询所有的规格模板、规格、规格详情
     * @return
     */
    @Override
    public Map<String, Object> selectSpecTmp() {
        Map<String,Object> map = new HashMap<>();
        map.put("specTmpList",specTmpMapper.selectList(null));
        map.put("specList",specMapper.selectList(null));
        map.put("sdList",specDetailMapper.selectList(null));
        return map;
    }

    /**
     * 根据模板查询模板信息
     * @param specTmpName
     * @return
     */
    @Override
    public SpecTmp selectSpecTmpByName(String specTmpName) {
        return specTmpMapper.selectSpecTmpByName(specTmpName);
    }

    /**
     * 删除规格模板
     * @param ids
     * @return
     */
    @Override
    public boolean delSpecTmp(int[] ids) {
        // TODO 需要改变mapper中的方法，将其根据单个改为多个id
        // 规格详情id
        List<Integer> detailIdList = new ArrayList<Integer>();

        // 判断改规格模板下的内容是否正被商品占用
        // 规格模板
         List<SpecTmp> specTmps = specTmpMapper.getSpecAndSpecDetailBySpecTmpId(ids);
         // 获取规格模板下的规格详情id
         for(SpecTmp item : specTmps){
             for(Spec spec : item.getSpecList()){
                 for(SpecDetail sd : spec.getSdList()){
                     System.out.println("======SD==="+sd.getId());
                     detailIdList.add(sd.getId());
                 }
             }
         }
         // 判断规格详情是否被商品占用
         Object [] num = detailIdList.toArray();
         int [] specIds = new int[num.length];
         for (int i = 0;i < num.length; i++){
             specIds[i] =(int) num[i];
             System.out.println(specIds[i]);
         }

        Long count = specDetailMapper.selectGoodsBySpecDetailId(specIds);
         if (count>0){
             return false;
         }
        // 删除模板
        try {
          specTmpMapper.delSpecTmpInfo(ids);
          return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
