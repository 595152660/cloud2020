package com.lun.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFallBackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "paymentInfo_OK+++";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "paymentInfo_TimeOut+++";
    }
}
