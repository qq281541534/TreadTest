package com.liuyu.thread.condition;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**   
 *  
 * @Description: jdk5的CyclicBarrier同步工具使用的实例
 * 					创建一个线程池，池中有三个线程；创建三个循环屏障，那么只有当有三个线程同时都完成之后，才能继续进行；
 * 
 * 同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，
 * 这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * 
 * CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。
 * 若在继续所有参与线程之前更新共享状态，此屏障操作 很有用。
 * 
 * @author LiuYu   
 * @date 2014-7-16 下午11:45:01 
 *    
 */
public class CyclicBarrierTest {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final CyclicBarrier cb = new CyclicBarrier(3);
		
		for(int i=0; i<3; i++){
			Runnable runnable = new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep((long)(Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() +
								"即将到达集合地点1，当前已有" + (cb.getNumberWaiting() + 1) + "个线程到达，" +
								(cb.getNumberWaiting() == 2?"都到齐了，继续走啊":"正在等候")	);
						//在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
						cb.await();
						
						Thread.sleep((long)(Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点2，当前已有" + (cb.getNumberWaiting() + 1) + "个线程到达，" +
								(cb.getNumberWaiting() == 2?"都到齐了，继续走啊":"正在等候"));
						cb.await();
						
						Thread.sleep((long)(Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() +
								"即将到达集合地点3，当前已有" + (cb.getNumberWaiting() + 1) + "个线程到达，" +
								(cb.getNumberWaiting() == 2?"都到齐了，继续走啊":"正在等候"));
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
		
	}
	
}


