package com.liuyu.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**   
 *  
 * @Description: 任务调度线程池
 * @author LiuYu   
 * @date 2014-7-6 下午7:01:41 
 *    
 */
public class ScheduledThreadPoolTest {

	public static void main(String[] args) {
		//创建一个任务调度线程池，池中有三个线程，6秒之后执行run方法
		Executors.newScheduledThreadPool(3).schedule(new Runnable(){

			@Override
			public void run() {
				System.out.println("bombing!!");
			}
			
		}, 6, TimeUnit.SECONDS);
		
		
		//6秒之后每隔2秒重复调度
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				System.out.println("循环爆炸！！");
			}
			
		}, 6, 2, TimeUnit.SECONDS);
		
	}
}


