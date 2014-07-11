package com.liuyu.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**   
 *  
 * @Description: 使用jdk5中的新特性lock和condition实现两个两个线程之间互通信的实例
 * 				   主线程和子线程，轮流调用
 * @author LiuYu   
 * @date 2014-7-11 上午10:56:02 
 *    
 */
public class ConditionTest {

	public static void main(String[] args) {
		
		final Business bus = new Business();
		new Thread(){
			public void run(){
				for(int i=1;i<=50;i++){
					bus.main(i);
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				for(int i=1;i<=50;i++){
					bus.sub(i);
				}
			}
		}.start();
	}
	
	
	//内部业务类
	static class Business{
		//实例化一个锁
		Lock lock = new ReentrantLock();
		//基于lock锁 new出类
		Condition condition = lock.newCondition();
		
		boolean flag = true;
		
		public void sub(int i){
			//上锁
			lock.lock();
			try {
				while(!flag){
					try {
						System.out.println("子线程在等待");
						//子线程等待
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				for(int j=1;j<=10;j++){
					System.out.println("sub thread sequece of " +j+ ",loop of " + i);
				}
				flag = false;
				//唤醒主线程
				condition.signal();
			} finally{
				//解锁
				lock.unlock();
			}
			
		}
		
		
		public void main(int i){
			//主线程上锁
			lock.lock();
			try {
				while(flag){
					try {
						System.out.println("主线程在等待");
						//主线程等待
						condition.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int j=1;j<=100;j++){
					System.out.println("main thread sequece of " +j+ ",loop of " + i);
				}
				flag = true;
				//子线程别唤醒
				condition.signal();
			} finally{
				//主线程解锁
				lock.unlock();
			}
			
			
		}
	}
}


