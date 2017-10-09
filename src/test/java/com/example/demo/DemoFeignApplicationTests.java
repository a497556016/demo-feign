package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoFeignApplicationTests {
private int threadNum = 1000;
	
	private CountDownLatch cdl = new CountDownLatch(threadNum);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Test
	public void contextLoads() {
		for(int i=0;i<threadNum;i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						cdl.await();
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
					}
					System.out.println(userInfoService.findById(1));
				}
			}).start();;
			cdl.countDown();
		}
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
