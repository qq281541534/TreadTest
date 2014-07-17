package com.liuyu.thread.condition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**   
 *  
 * @Description: jdk5中CountDownLatch的同步工具的实例
 * 				
 * 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
 * 之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。
 * 如果需要重置计数，请考虑使用 CyclicBarrier。 
 *	
 * CountDownLatch 是一个通用同步工具，它有很多用途。将计数 1 初始化的 CountDownLatch 用作一个简单的开/关锁存器，
 * 或入口：在通过调用 countDown() 的线程打开入口前，所有调用 await 的线程都一直在入口处等待。
 * 用 N 初始化的 CountDownLatch 可以使一个线程在 N 个线程完成某项操作之前一直等待，或者使其在某项操作完成 N 次之前一直等待。 
 *
 * CountDownLatch 的一个有用特性是，它不要求调用 countDown 方法的线程等到计数到达零时才继续，而在所有线程都能通过之前，
 * 它只是阻止任何线程继续通过一个 await
 *	
 * @author LiuYu   
 * @date 2014-7-17 下午11:10:32 
 *    
 */
public class CountdwonLatchTest {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		//倒数计数器
		final CountDownLatch cdOrder = new CountDownLatch(1);
		final CountDownLatch cdAnswer = new CountDownLatch(3);
		
		for(int i=0; i<3; i++){
			Runnable runnable = new Runnable(){

				@Override
				public void run() {
					try {
						System.out.println("线程" + Thread.currentThread().getName() +
								"正准备接受命令");
						cdOrder.await();
						System.out.println("线程" + Thread.currentThread().getName() +
								"已接受命令");
						Thread.sleep((long)(Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() +
								"回应命令处理结果");
						cdAnswer.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		
		
		try {
			Thread.sleep((long)(Math.random() * 10000));
			System.out.println("线程" + Thread.currentThread().getName() +
					"即将发布命令");
			cdOrder.countDown();
			System.out.println("线程" + Thread.currentThread().getName() +
					"已发送命令，正在等待结果");
			cdAnswer.await();
			System.out.println("线程" + Thread.currentThread().getName() +
					"已收到所有响应结果");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.shutdown();
		
	}
}


