package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.UserInfoService;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(Integer id) {
		return userInfoService.findById(id);
	}
	
	@RequestMapping("/listUser")
	@ResponseBody
	public String listUser() {
		return userInfoService.listUser();
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return userInfoService.test();
	}
}
