package com.lun.springcloud.controller;

import com.lun.springcloud.entities.CommonResult;
import com.lun.springcloud.entities.Payment;
import com.lun.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {


    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/creat")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("结果"+result);

        if (result > 0){
            return new CommonResult(200,"插入成功serverPort"+serverPort,result);
        }else {
            return new CommonResult(444,"插入失败",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        System.out.println("1111");
        Payment payment = paymentService.getPaymentById(id);
        log.info("结果"+payment);

        if (payment != null){
            return new CommonResult(200,"查询成功serverPort"+serverPort,payment);
        }else {
            return new CommonResult(444,"查询失败"+id,null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String a: services
             ) {
            log.info(a);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance a: instances
             ) {
            log.info(a.getInstanceId()+a.getHost()+a.getPort());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


}