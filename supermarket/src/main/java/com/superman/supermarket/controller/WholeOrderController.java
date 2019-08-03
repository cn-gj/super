package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.superman.supermarket.entity.WholeOrder;
import com.superman.supermarket.entity.vo.WholeOrderVo;
import com.superman.supermarket.service.WholeOrderService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/wholeOrder")
public class WholeOrderController {

    @Resource
    private WholeOrderService wholeOrderService;

    /**
     *  添加批发订单
     * @param
     * @return
     */
    @PostMapping("/addWholeOrder")
    @ResponseBody
    public String addWholeOrder(WholeOrderVo wholeOrderVo, String str ){
       //System.out.println(str+"...........................");
        Map<String,Object> map = new HashMap<>();
        map.put("result",wholeOrderService.addWholeOrder(wholeOrderVo,str));
        return JSON.toJSONString(map);
    }

    /**
     *  根据条件查询批发订单
     * @param wholeOrderVo
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(WholeOrderVo wholeOrderVo){
        return JSON.toJSONString(wholeOrderService.findByCondition(wholeOrderVo));
    }


    /**
     *  根据订单id查询订单详情
     * @param id
     * @return
     */
    @PostMapping("/findById")
    @ResponseBody
    public String findById(int id){
        return JSON.toJSONString(wholeOrderService.findById(id));
    }

    /**
     *  修改批发订单详情
     * @param wholeOrder
     * @return
     */
    @PostMapping("/updateWhole")
    @ResponseBody
    public String updateWhole(WholeOrder wholeOrder){
        int rowCount = wholeOrderService.updateWhole(wholeOrder);
        Map<String,Object> map = new HashMap<>();
        if (rowCount > 0){
            map.put("result",true);
        }else {
            map.put("result",false);
        }
        return JSON.toJSONString(map);
    }

    /**
     *  导出批发订单信息Execl
     * @return
     */
    @GetMapping("/exportWhole")
    public String exportWhole(HttpServletResponse response){
        String fileName = "批发订单列表_"+ DateUtil.date2Str(new Date()) +".xls";
        //中文名称进行转码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(),"ISO-8859-1"));
            // 调用业务层导出excel表格
            try {
                wholeOrderService.exportWhole(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *  出库
     * @param wholeId
     * @param storeId
     * @return
     */
    @PostMapping("/outWholeStore")
    @ResponseBody
    public String outWholeStore(Integer wholeId, Integer storeId){
        return wholeOrderService.outWholeStore(wholeId,storeId);
    }

    /**
     *  退货
     * @param wholeId
     * @param storeId
     * @return
     */
    @PostMapping("/inWholeStore")
    @ResponseBody
    public String inWholeStore(Integer wholeId, Integer storeId){
        return wholeOrderService.inWholeStore(wholeId,storeId);
    }
}

