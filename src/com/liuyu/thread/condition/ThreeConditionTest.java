package com.liuyu.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**   
 *  
 * @Description: 启动三个线程，按顺序轮流执行线程中的内容，每个线程需要使用condition控制下个线程对象的执行
 * 					这就是condition与传统线程wait，notify的区别。
 * @author LiuYu   
 * @date 2014-7-13 下午10:34:55 
 *    
 */
public class ThreeConditionTest {

	
	public static void main(String[] args) {
	
		final Business bus = new Business();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 1; i <= 50; i++){
					bus.funOne(i);
				}
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 1; i <= 50; i++){
					bus.funTwo(i);
				}
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 1; i <= 50; i++){
					bus.funThree(i);
				}
			}
			
		}).start();
	}
	
	
	
	static class Business{
		
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		
		int flag = 1;
		
		public void funOne(int i){
			lock.lock();
			try {
				try {
					while(flag != 1){
						condition1.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int j = 1; j <= 10; j++){
					System.out.println("funOne Thread queue of " +j+ ",loop of " +i);
				}
				flag = 2;
				condition2.signal();
			} finally{
				lock.unlock();
			}
			
		}
		public void funTwo(int i){
			lock.lock();
			try {
				try {
					while(flag != 2){
						condition2.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int j = 1; j <= 10; j++){
					System.out.println("funTwo Thread queue of " +j+ ",loop of " +i);
				}
				flag = 3;
				condition3.signal();
			} finally{
				lock.unlock();
			}
			
		}
		public void funThree(int i){
			lock.lock();
			try {
				try {
					while(flag != 3){
						condition3.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int j = 1; j <= 10; j++){
					System.out.println("funThree Thread queue of " +j+ ",loop of " +i);
				}
				flag = 1;
				condition1.signal();
			} finally{
				lock.unlock();
			}
			
		}
		
		
	}
	
}


