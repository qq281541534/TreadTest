package com.liuyu.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**   
 *  
 * @Description: 线程内的共享变量以及线程内的共享变量有什么用
 * 注：同时启动了两个线程，每个线程内都一个共享变量 data, 在同一个线程内，两个类输入的data是同一个；这就是线程内的共享变量；
 * 	  这个例子充分说明了，当对数据库操作时，同一个事务在创建之后，对数据库进行操作，并且当提交事务的时候，提交的必须本线程内的同一个事务。
 * @author LiuYu   
 * @date 2014-6-30 下午11:14:21 
 *    
 */
public class ThreadScopeShareData {

	private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();
	
	
	public static void main(String[] args) {
		
		for(int i=0; i<2;i++){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data :" + data);
					threadData.put(Thread.currentThread(), data);
					new A().get();
					new B().get();
				}
			}).start();
			
		}
	}
	
	
	static class A{
		public void get(){
			int data = threadData.get(Thread.currentThread());
			System.out.println("A from " +Thread.currentThread().getName()
					+ "has put data :" + data);
		}
	}
	
	
	static class B{
		public void get(){
			int data = threadData.get(Thread.currentThread());
			System.out.println("B from " +Thread.currentThread().getName()
					+ "has put data :" + data);
		}
	}
}


