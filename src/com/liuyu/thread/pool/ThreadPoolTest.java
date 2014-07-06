package com.liuyu.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**   
 *  
 * @Description: 固定的线程池、缓存线程池、单一线程池实例
 * @author LiuYu   
 * @date 2014-7-6 下午5:10:09 
 *    
 */
public class ThreadPoolTest {
	
	public static void main(String[] args) {
		//创建一个固定的线程池；池中有3个三个线程
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		
		//创建缓存线程池；线程池中线程数是根据任务的变化而变化的，有10个任务就会生成10个线程，有5个就生成5个线程；
		//如果在一定时间内没有调用线程，那么线程将会自动回收，不用threadPool.shutdown()方法去结束线程
//		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		//创建单一线程池；保证有线程池中只有一个线程。
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		//循环是10次，就等于这个线程池需要执行10个任务，每个任务在执行时，循环10次
		for(int i=1;i<=10;i++){
			
			final int task = i;
			//给一个线程池中放入一个任务，每个任务执行时，循环了10次
			threadPool.execute(new Runnable(){
				//任务执行
				@Override
				public void run() {
					
					for(int j=1;j<=10;j++){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()
								+"is looping of "+j+" for task of "+task);
					}
				}
			});
		}
		System.out.println("10个任务全部执行完了！！");
		//当所有线程任务执行完了，结束线程
		threadPool.shutdown();
	}
}


