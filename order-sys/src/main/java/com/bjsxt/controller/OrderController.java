package com.bjsxt.controller;

import com.alibaba.fastjson.JSON;
import com.bjsxt.domain.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lvyelanshan
 * @create 2019-10-16 16:27
 */
@Controller
public class OrderController {

    //接收http请求和响应订单集合
    //加载订单集合
    @RequestMapping("/loadOrderList")
    /*根据订单ID查询集合*/
    public String loadOrderList(String uid, Model model) {

        Order o1 = new Order();
        o1.setId("111");
        o1.setTotal(123.0);
        o1.setDate("2019-10-16");

        Order o2 = new Order();
        o2.setId("222");
        o2.setTotal(222.0);
        o2.setDate("2019-10-17");

        Order o3 = new Order();
        o3.setId("333");
        o3.setTotal(333.0);
        o3.setDate("2019-10-18");

        List<Order> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);

        model.addAttribute("list",list);

        return "index.jsp";

    }



    //接收http请求和响应订单集合，完成的是异步响应
    //将list集合序列化为json串响应
    //加载订单集合
    /*根据订单ID查询集合*/
    //将list集合序列化成json串
    @RequestMapping("/loadOrderList02")
    @ResponseBody
    public List<Order> loadOrderList02(String uid) {

        System.out.println("uid="+uid);

        Order o1 = new Order();
        o1.setId("111");
        o1.setTotal(123.0);
        o1.setDate("2019-10-16");

        Order o2 = new Order();
        o2.setId("222");
        o2.setTotal(222.0);
        o2.setDate("2019-10-17");

        Order o3 = new Order();
        o3.setId("333");
        o3.setTotal(333.0);
        o3.setDate("2019-10-18");

        List<Order> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);


        return list;

    }


    /**
     * 接收jsonP的跨域请求，响应js的字符串到客户端
     * @param uid
     * @return
     */
    @RequestMapping("/loadOrderList03")
    @ResponseBody
    public String loadOrderList03(String uid,String callback) {

        System.out.println("uid="+uid);

        Order o1 = new Order();
        o1.setId("111");
        o1.setTotal(123.0);
        o1.setDate("2019-10-16");

        Order o2 = new Order();
        o2.setId("222");
        o2.setTotal(222.0);
        o2.setDate("2019-10-17");

        Order o3 = new Order();
        o3.setId("333");
        o3.setTotal(333.0);
        o3.setDate("2019-10-18");

        List<Order> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);

        //根据jsonP的要求，响应回客户端的内容包含两个部分:
        // 一个是本地定义的js函数和需要获得的数据(数据需要是json格式的字符串)
        //这个拼接后的字符串就是需要响应到客户端的js代码
        //JSON.toJSONString(list):相当于将
        String result = callback+"("+ JSON.toJSONString(list) +")";
        System.out.println(result);
        return result;

    }



}
