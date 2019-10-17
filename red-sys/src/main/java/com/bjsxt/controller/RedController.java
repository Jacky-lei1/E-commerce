package com.bjsxt.controller;

import com.bjsxt.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lvyelanshan
 * @create 2019-10-16 19:25
 */
@Controller
public class RedController {
    //注入由spring提供的一个对象
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送远程的HTTP请求,消费HTTP服务
     * 获得订单对象的集合
     * 只获取到了响应体信息
     */
    @RequestMapping("/loadOrderList01")
    @ResponseBody
    public List<Order> loadOrderList01(String uid){
        //通过发送请求获取远程服务中的数据

        //发送远程HTTP请求的URL
        String url = "http://localhost:7070/order/loadOrderList02";

        //发送到远程服务的参数
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("uid",uid);

        //通过restTemplate对象发送post请求,返回订单对象的集合
        Order[] orders = restTemplate.postForObject(url, params, Order[].class);

        //将获取到的订单数组装换成list返回
        return Arrays.asList(orders);
    }



    /**
     * 发送远程的HTTP请求,消费HTTP服务
     * 获得订单对象的集合
     * 不仅获取到了响应体还获取到了响应头信息
     */
    @RequestMapping("/loadOrderList02")
    @ResponseBody
    public List<Order> loadOrderList02(String uid){
        //通过发送请求获取远程服务中的数据

        //发送远程HTTP请求的URL
        String url = "http://localhost:7070/order/loadOrderList02";

        //发送到远程服务的参数
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("uid",uid);

        //通过restTemplate对象发送post请求,返回封装了订单数组的响应实体对象
        ResponseEntity<Order[]> entity = restTemplate.postForEntity(url,params,Order[].class);

        //获得响应头信息
        HttpHeaders headers = entity.getHeaders();
        for(Map.Entry<String,List<String>> e : headers.entrySet()){
            System.out.println(e.getKey()+"-----------"+e.getValue());
        }
        //获得响应的状态码
        int codeValue = entity.getStatusCodeValue();
        System.out.println(codeValue);

        //获得远程服务的响应体
        Order[] orders = entity.getBody();
        return Arrays.asList(orders);
    }

}
