package com.lun.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.lun.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_OK,id:" + id + "\t" + "O(∩_∩)O哈哈~";

    }


    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 3;
        try {
            // 暂停3秒钟
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t" +
                "O(∩_∩)O哈哈~  耗时(秒)" + timeNumber;
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfoCircuitBreaker_fallback",commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enable",value = "false"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0){
            throw new RuntimeException("id为负数");
        }
        String s = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"    进程:"+s;
    }

    @Override
    public String aaa(Integer id) {
        return "aaaaa";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler,id:" + id + "\t" + "响应超时";
    }



    public String paymentInfoCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数 id: "+id;
    }


}

