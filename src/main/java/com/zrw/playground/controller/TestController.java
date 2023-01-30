package com.zrw.playground.controller;

import com.zrw.playground.util.MobileCodeWS;
import com.zrw.playground.util.MobileCodeWSSoap;
import com.zrw.playground.weather.WeatherUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zrw
 */
@RestController
@RequestMapping(value = "/zrw")
public class TestController {



    @GetMapping(value = "/test")
    @ApiOperation(value = "测试swagger3", notes = "zrw")
    public String Test(){
        //创建服务视图
        MobileCodeWS mobileCodeWS=new MobileCodeWS();
        //获取服务实现类
        MobileCodeWSSoap mobileCodeWSSoap= mobileCodeWS.getPort(MobileCodeWSSoap.class);
        //调用查询方法
        String message=mobileCodeWSSoap.getMobileCodeInfo("15313059257", null);
        System.out.println(message);
        return "Hello world!!!";
    }
    @GetMapping(value = "/weather/{name}")
    @ApiOperation(value = "测试swagger3", notes = "zrw")
    public List<String> WeatherTest(@PathVariable("name") String name){
        String[] pInfo = WeatherUtil.getRegion(name);

        String provinceName = pInfo[0];
        String cityName = pInfo[1];

        int provinceCode = WeatherUtil.getProvinceCode(provinceName);

        int cityCode = WeatherUtil.getCityCode(provinceCode, cityName);

        List<String> weatherList = WeatherUtil.getWeather(cityCode);

        for (String weather : weatherList) {
            System.out.println(weather);
        }
        return weatherList;
    }
}
