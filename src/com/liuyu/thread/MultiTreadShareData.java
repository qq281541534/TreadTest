package com.liuyu.thread;
/**   
 *  
 * @Description: 多线程间的共享变量
 * @author LiuYu   
 * @date 2014-7-5 下午7:44:14 
 *    
 */
public class MultiTreadShareData {

	//外部类的变量
	private int num;
	
	/**
	 * 创建两个线程，一个线程用于对外部类变量的+操作，另一个线程用于对外部类变量的-操作。
	 * @param args
	 */
	public static void main(String[] args) {
		MultiTreadShareData mtsd = new MultiTreadShareData();
		Inc inc = mtsd.new Inc();
		Dec dec = mtsd.new Dec();
		new Thread(inc).start();
		new Thread(dec).start();
	}
	
	//避免调用该方法时出现混乱
	public synchronized void inc(){
		num++;
		System.out.println(Thread.currentThread().getName()+"-inc:"+num);
	}

	
	public synchronized void dec(){
		num--;
		System.out.println(Thread.currentThread().getName()+"-dec:"+num);
	}
	
	//线程内部类，用于调用外部类的inc方法
	class Inc implements Runnable{
		@Override
		public void run() {
			for(int i=0;i<100;i++){
				inc();
			}
		}
	}
	
	//线程内部类，用于调用外部类的dec方法
	class Dec implements Runnable{
		@Override
		public void run() {
			for(int i=0;i<100;i++){
				dec();
			}
		}
	}
	
}


