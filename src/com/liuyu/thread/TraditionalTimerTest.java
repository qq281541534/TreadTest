package com.liuyu.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**   
 *  
 * @Description: 
 * @author LiuYu   
 * @date 2014-6-29 下午4:57:42 
 *    
 */
public class TraditionalTimerTest {
	
	
	static int count = 0;
	
	public static void main(String[] args) {
		
	/*	//任务调度器，每个三秒执行一次炸弹
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				System.out.println("炸弹爆炸了！");
			}
		}, 1000,3000);*/
		
		//隔2秒执行一次后，再隔4秒执行一次，交替重叠
		class MyTimerTask extends TimerTask{
			@Override
			public void run() {
				count = (count+1) % 2;
				
				System.out.println("炸弹爆炸了！");
				
				new Timer().schedule(new MyTimerTask(), 2000+2000*count);
			}
			
		}
		
		new Timer().schedule(new MyTimerTask(), 2000);
		while(true){
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


