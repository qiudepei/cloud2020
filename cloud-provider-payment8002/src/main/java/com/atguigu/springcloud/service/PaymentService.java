package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface PaymentService {
	public int create(Payment payment);
	public Payment getPaymentById(@Param("id") long id);
}
