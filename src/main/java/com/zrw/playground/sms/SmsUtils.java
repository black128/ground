package com.zrw.playground.sms;

import org.springframework.stereotype.Component;

@Component
public class SmsUtils {

    public String userLogin(String strUserId, String strUserPass, String strUserIP) {
        return "0";
    }

    public String setMessage(String strUserID, String strPhone, String strMessage, String strTime, String strType, String strServiceID, String strCompany) {
// strUserID: 用户帐号。
// strPhone: 要发送的对方手机号码。--11位标准长度号码
// strMessage:  要发送的短信内容。--小于varchar(2000)，GBK编码
// strTime: 预约短信时间，如果是要预约短信，在这里加入预约短信的时间。如果是即时发送短信，填空值。--字符串格式，例：201912261005
// strType: 要发送的短信类型。--赋值 参照之前对接短信类型填写
// strServiceID: 要发送短信的serviceid代码。--分公司serviceid，例：天津：1201
// strCompany: 要发送短信的机构代码。--八位标准机构代码 注意：大连71000000、宁波72000000、厦门73000000、青岛74000000、深圳7500000 杭州33010000  广州44010000

        return "1";
    }
    public String userLogOut(String strUserId){
        return "0";
    }
}
