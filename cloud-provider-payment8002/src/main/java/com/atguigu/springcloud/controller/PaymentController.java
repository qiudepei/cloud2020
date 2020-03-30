package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
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
	private String  serverPort;

	@Resource
	private DiscoveryClient discoveryClient;

	@GetMapping(value="/payment/discovery")
	public Object discovery() {
		List<String> services = discoveryClient.getServices();
		for (String element : services) {
			log.info("============element"+ element);
		}
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
		for (ServiceInstance instance : instances) {
			log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
		}
		return this.discoveryClient;
	}

	@PostMapping(value="/payment/create")
	public CommonResult<Integer> create(@RequestBody Payment payment){
		int result = paymentService.create(payment);
		log.info("=======插入结果："+result);
		if(result>0){
			return new CommonResult<Integer>(200,"插入数据成功"+serverPort,result);
		}else {
			return new CommonResult<Integer>(444,"插入数据失败",null);
		}
	}

	@GetMapping(value= "/payment/get/{id}")
	public CommonResult<Payment> getPaymentById(@PathVariable("id") long id){
		Payment payment = paymentService.getPaymentById(id);
		log.info("===========查询结果："+payment);
		if(payment != null){
			return new CommonResult<Payment>(200,"查询数据成功"+serverPort,payment);
		}else {
			return new CommonResult<Payment>(444,"查询数据失败ID:"+id,null);
		}
	}
}
