package com.liuyu.thread.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**   
 *  
 * @Description: 读写锁的使用实例，读写锁在多线程并发时，读与读之间不互斥，只有在读与写，写与写，写与读之间互斥。
 * @author LiuYu   
 * @date 2014-7-9 下午10:16:10 
 *    
 */
public class ReadWriteLockTest {
	
	public static void main(String[] args) {
		//创建了三个线程读数据，三个线程写数据
		final Queue q = new Queue();
		for(int i=0;i<3;i++){
			
			new Thread(){
				public void run(){
					while(true){
						q.get();
					}
				}
			}.start();
			
			
			new Thread(){
				public void run(){
					while(true){
						q.put(Math.random() * 10000);
					}
				}
				
			}.start();
			
		}
		
	}
	
}

class Queue{
	
	//共享数据，只能有一个线程写该数据，但可以有多个线程同时读该数据
	private Object data = null;
	//读写锁
	ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public void get(){
		//获取的操作上读锁
		rwl.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "be ready to read data!");
			Thread.sleep((long)Math.random() * 1000);
			System.out.println(Thread.currentThread().getName() + "have read data :" + data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			rwl.readLock().unlock();
		}
		
	}
	
	
	public void put(Object data){
		//写的操作上写锁
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "be ready to write data!");
			Thread.sleep((long)Math.random() * 1000);
			this.data = data;
			System.out.println(Thread.currentThread().getName() + "have write data :" + data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			rwl.writeLock().unlock();
		}
	}
	
	
}


