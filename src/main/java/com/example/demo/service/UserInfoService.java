package com.example.demo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="demo-service",fallback=UserInfoServiceHystric.class)
public interface UserInfoService {
	
	@RequestMapping(value="userInfo/findById")
	public String findById(@RequestParam("id") Integer id);
	
	@RequestMapping("userInfo/listUser")
	public String listUser();
}
