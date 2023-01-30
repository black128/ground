package com.zrw.playground.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zrw
 */
@RestController
public class SendSms {

    @Autowired
    private SmsUtils smsUtils;

    public String sendSms(){




        return "0";
    }
}
