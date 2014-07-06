package com.liuyu.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**   
 *  
 * @Description: jdk5之后的新添加的线程池包中lock类的用法，和synchronized相同
 * @author LiuYu   
 * @date 2014-7-6 下午8:33:03 
 *    
 */
public class LockTest {
	
	public static void main(String[] args) {
		
		final Output out = new Output();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.out("123456789");
				}
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.out("abcdefghij");
				}
			}
			
		}).start();
	}
	
	static class Output{
		//创建一个锁
		Lock lock = new ReentrantLock();
		public void out(String str){
			//将需要同步的代码进行加锁
			lock.lock();
			try {
				for(int i=0;i<str.length();i++){
					System.out.print(str.charAt(i));
				}
				System.out.println();
			} finally{
				//解锁，写在finally中的原因是因为防止在上锁之内的代码出现异常，永久不释放锁的问题
				lock.unlock();
			}
		}
		
	}
}


