package com.liuyu.thread.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**   
 *  
 * @Description: java5的Semaphere同步工具使用的实例
 * 				创建一个线程池，在池中放入了10个线程；而用了三个信号灯去阻塞同步，也就是说：虽然有池中有10个线程，但是只能
 * 				有3个线程并发，如果其中有一个线程离开，才会有1个线程进入。直到所有线程都结束。
 * @author LiuYu   
 * @date 2014-7-14 上午10:52:58 
 *    
 */
public class SemaphereTest {
	
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		//创建一个信号量，放入三个许可
		final Semaphore sp = new Semaphore(3);
		
		for(int i = 0; i < 10; i++){
			Runnable runnable = new Runnable(){
				
				@Override
				public void run() {
					try {
						//从此信号量获取一个许可
						sp.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//sp.availablePermits() 返回此信号量中现在可用的许可
					System.out.println("线程" + Thread.currentThread().getName() + "进入，当前已有" +
							(3 - sp.availablePermits()) + "个线程并发");
					try {
						Thread.sleep((long)(Math.random()*10000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
					//当一个线程结束时，释放此信号量中的一个许可，那么下一个线程就可以继续循环拿到信号量的许可，进入并发
					sp.release();
					
					System.out.println("线程" + Thread.currentThread().getName() + "已离开，当前已有" +
							(3 - sp.availablePermits()) + "个线程并发");
					
				}
				
			}; 
			service.execute(runnable);
		}
		
	}
	
}


