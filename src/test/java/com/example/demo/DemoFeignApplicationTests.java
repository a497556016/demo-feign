package com.example.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoFeignApplicationTests {
	private int threadNum = 1000;
	
	private CountDownLatch cdl = new CountDownLatch(threadNum);
	
	@Autowired
	private UserInfoService userInfoService;
	
	private RestTemplate template = new RestTemplate();
	
	private int countNum = 0;
	
	@Test
	public void contextLoads() {
		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		for(int i=0;i<threadNum;i++) {
			executorService.execute(new Server(i));
			cdl.countDown();
		}
		executorService.shutdown();
		while(true) {
			if(executorService.isTerminated()) {
				System.out.println("失败的任务数量是:"+countNum);
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class Server implements Runnable{
		private int i;
		
		public Server(int i) {
			super();
			this.i = i;
		}

		@Override
		public void run() {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			try {
				String result = template.postForObject("http://localhost/userInfo/findById?id=2", map, String.class);
				System.out.println(result+"\t"+i);
			} catch (RestClientException e) {
				System.out.println("异常了"+(countNum++));
			}
		}
		
	}

}
