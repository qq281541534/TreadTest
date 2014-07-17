package com.liuyu.thread.condition;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**   
 *  
 * @Description: 使用jdk5中的新特性Exchanger同步工具
 * 
 * 可以在对中对元素进行配对和交换的线程的同步点。每个线程将条目上的某个方法呈现给 exchange 方法，与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。
 * Exchanger 可能被视为 SynchronousQueue 的双向形式。Exchanger 可能在应用程序（比如遗传算法和管道设计）中很有用。 
 * 
 * @author LiuYu   
 * @date 2014-7-17 下午11:39:08 
 *    
 */
public class ExchangerTest {
		
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger exchanger = new Exchanger();
		service.execute(new Runnable(){

			@Override
			public void run() {
				try {
					String data = "毒品";
					System.out.println("线程" + Thread.currentThread().getName() +
							"正在把数据:" + data +" 换出去");
					Thread.sleep((long)(Math.random() * 10000));
					String data1 = (String) exchanger.exchange(data);
					System.out.println("线程" + Thread.currentThread().getName() +
							"换回的数据为:" + data1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		service.execute(new Runnable(){

			@Override
			public void run() {
				
				try {
					String data = "人民币";
					System.out.println("线程" + Thread.currentThread().getName() +
							"正在把数据:" + data +" 换出去");
					Thread.sleep((long)(Math.random() * 10000));
					String data1 = (String) exchanger.exchange(data);
					System.out.println("线程" + Thread.currentThread().getName() +
							"换回的数据为:" + data1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
		service.shutdown();
		
		
		
	}
}


