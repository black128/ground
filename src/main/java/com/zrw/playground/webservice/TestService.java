package com.zrw.playground.webservice;

import javax.xml.ws.Endpoint;

//@WebService
public class TestService {
//    @WebMethod(exclude = true)
    public String test(){
        Endpoint.publish("http://www.webxml.com.cn/zh_cn/index.aspx", new TestService());
        //这个地方其实就是进行了封装，里面根据指定参数启动了一个servicesocket，并且生成了一个WSDL文档。
        System.out.println("test");
        return "test";
    }
}
