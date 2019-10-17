package com.bjsxt.test.client;

import com.alibaba.fastjson.JSON;
import domain.Order;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvyelanshan
 * @create 2019-10-16 17:58
 */
public class TestHttpClient {
    public static void main(String[] args) throws IOException {
        /**
         * 启动消费者进行服务的消费
         * HttpClient发送远程请求，服务提供响应的为json串，
         * json串直接可以解析成java对象，或者java对象的集合
         */
        //创建NameValuePair对象，封装发送服务提供者的参数
        NameValuePair id = new BasicNameValuePair("uid","20002");
        //防止有多个参数，将每次请求的参数都封装到一个list集合中
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(id);

        //发送远程的httpq请求的地址
        String url = "http://localhost:7070/order/loadOrderList02";
        //创建HttpClient客户端对象
        HttpClient client = HttpClients.createDefault();
        //创建HttpPost对象，发送Post请求
        HttpPost method = new HttpPost(url);

        //封装请求体数据：将发送的数据编码为UTF-8
        method.setEntity(new UrlEncodedFormEntity(params,"utf-8"));

        //发送具体的HTTP请求,返回响应的结果
        HttpResponse response = client.execute(method);
        //获得响应头信息(包含响应头的名字和响应头的值)
        Header[] headers = response.getAllHeaders();
        for(Header h:headers){
            System.out.println(h.getName()+"-------"+h.getValue());
        }

        //获得服务提供的响应的具体数据
        HttpEntity entity = response.getEntity();
        //获得http的响应体
        InputStream content = entity.getContent();

        int len = 0;
        char[] buf = new char[1024];

        //将字节流转化为字符流
        InputStreamReader reader = new InputStreamReader(content);

        //创建StringBuffer
        StringBuffer result = new StringBuffer();
        while ((len=reader.read(buf))!=-1){
            result.append(String.valueOf(buf,0,len));
        }
        System.out.println(result);
        //将result，json字符串解析为Order集合
        List<Order> list = JSON.parseArray(result.toString(), Order.class);
        System.out.println(list);
        for (Order o : list) {
            System.out.println(o.getId()+"\t"+o.getDate()+"\t"+o.getTotal());
        }
    }
}
