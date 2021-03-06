package com.liuyu.thread;

import java.util.Random;

/**   
 *  
 * @Description: ThreadLocal实现线程内的共享变量
 * @author LiuYu   
 * @date 2014-6-30 下午11:14:21 
 *    
 */
public class ThreadLocalTest1 {

	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		
		for(int i=0; i<2;i++){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data :" + data);
					x.set(data);
					new A().get();
					new B().get();
				}
			}).start();
			
		}
	}
	
	
	static class A{
		public void get(){
			int data = x.get();
			System.out.println("A from " +Thread.currentThread().getName()
					+ "has put data :" + data);
		}
	}
	
	
	static class B{
		public void get(){
			int data = x.get();
			System.out.println("B from " +Thread.currentThread().getName()
					+ "has put data :" + data);
		}
	}
}


