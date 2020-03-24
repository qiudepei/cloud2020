package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
	@Resource
	private PaymentService paymentService;

	@PostMapping(value="/payment/create")
	public CommonResult create(Payment payment){
		int result = paymentService.create(payment);
		log.info("=======插入结果："+result);
		if(result>0){
			return new CommonResult(200,"插入数据成功",result);
		}else {
			return new CommonResult(444,"插入数据失败",null);
		}
	}

	@GetMapping(value= "/payment/get/{id}")
	public CommonResult getPaymentById(@PathVariable("id") long id){
		Payment payment = paymentService.getPaymentById(id);
		log.info("===========查询结果："+payment);
		if(payment != null){
			return new CommonResult(200,"查询数据成功",payment);
		}else {
			return new CommonResult(444,"查询数据失败ID:"+id,null);
		}
	}
}
