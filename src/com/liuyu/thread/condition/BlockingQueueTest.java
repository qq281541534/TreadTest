package com.liuyu.thread.condition;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**   
 *  
 * @Description: 阻塞队列：当往队列中放的值超过了设置的队列数时，会阻塞等待队列取值；
 * 						当从队列取值取的太快发现队列中没值时，同样会阻塞等待放值。
 * 	
 * @author LiuYu   
 * @date 2014-7-18 上午12:05:38 
 *    
 */
public class BlockingQueueTest {
	
	public static void main(String[] args) {
		final BlockingQueue queue = new ArrayBlockingQueue(3);
		for(int i=0; i<2; i++){
			new Thread(){
				public void run(){
					while(true){
						try {
							Thread.sleep((long)(Math.random() * 10000));
							System.out.println("线程" + Thread.currentThread().getName() +
									"准备放数据！");
							//放值
							queue.put(1);
							System.out.println("线程" + Thread.currentThread().getName() +
									"已经放了数据，队列目前有" + queue.size() + "个数据");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}.start();
		}
		
		new Thread(){
			public void run(){
				while(true){
					try {
						//将取数据的睡眠时间调成100,效果明显不同
						Thread.sleep(1000);
						System.out.println("线程" + Thread.currentThread().getName() + "准备取数据");
						//取值
						queue.take();
						System.out.println("线程" + Thread.currentThread().getName() + "已经取走数据，" +
								"队列目前有" + queue.size() + "个数据");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}.start();
		
	}
}


