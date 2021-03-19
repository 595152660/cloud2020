package com.lun.springcloud.service.impl;

import com.lun.springcloud.dao.PaymentDao;
import com.lun.springcloud.entities.Payment;
import com.lun.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource//java自带的 Autowire spring的
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
