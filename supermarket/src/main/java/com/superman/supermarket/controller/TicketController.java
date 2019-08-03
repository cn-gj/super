package com.superman.supermarket.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.superman.supermarket.entity.Ticket;
import com.superman.supermarket.entity.TicketDetail;
import com.superman.supermarket.entity.vo.TicketVo;
import com.superman.supermarket.service.TicketService;
import com.superman.supermarket.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author superman
 * @since 2019-07-12
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    /**
     * 根据条件查询收银单信息和初始化页面信息
     *
     * @param ticketVo
     * @return
     */
    @PostMapping("/findByCondition")
    @ResponseBody
    public String findByCondition(TicketVo ticketVo) {
        return JSON.toJSONString(ticketService.findByCondition(ticketVo));
    }

    /**
     * 根据id查询收银单信息
     *
     * @param id
     * @return
     */
    @PostMapping("/findById")
    @ResponseBody
    public String findById(int id) {
        return JSON.toJSONString(ticketService.findById(id));
    }


    /**
     * 导出收银单信息Execl
     *
     * @return
     */
    @GetMapping("/exportTicket")
    public String exportEmp(HttpServletResponse response) {
        String fileName = "收银单列表_" + DateUtil.date2Str(new Date()) + ".xls";
        //中文名称进行转码
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes(), "ISO-8859-1"));
            // 调用业务层导出excel表格
            try {
                ticketService.exportTicket(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param ticket 收银单信息
     * @param tickDetailStr 收银单明细（商品信息）
     * @return
     */
    @ResponseBody
    @PostMapping("/addTicket")
    public String addTicket(Ticket ticket,String tickDetailStr){
        Map<String,Object> map = new HashMap<>();
        boolean flag = false;
        try {
            //  小票明细集合
            List<TicketDetail> detailList = new ArrayList<TicketDetail>();
            // 将JSON格式字符串转换成jsonArray对象
            JSONArray array = JSONArray.parseArray(tickDetailStr);
            // 循环遍历array获取商品信息
            for (int i=0;i<array.size();i++){
                // 小票明细
                TicketDetail detail = new TicketDetail();
                // 获取集合中的对象
                JSONObject object = (JSONObject) array.get(i);
                detail.setGoodsId(object.getInteger("id"));
                detail.setGoodsCount(object.getInteger("count"));
                detail.setTotalMoney(object.getDouble("totalMoney"));
                detailList.add(detail);
            }
            flag = ticketService.addTicket(ticket,detailList);
            map.put("state",flag); // true:success,false:fail
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
        }
        return JSON.toJSONString(map);
    }
}

