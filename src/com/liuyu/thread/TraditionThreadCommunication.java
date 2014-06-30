package com.liuyu.thread;
/**   
 *  
 * @Description: 子线程先运行10次，主线程再运行100次，这样交替运行50次
 * @author LiuYu   
 * @date 2014-6-29 下午6:22:40 
 *    
 */
public class TraditionThreadCommunication {
	
	public static void main(String[] args) {
		final Business bus = new Business();
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					for(int i = 1; i <= 50; i++){
						bus.sub(i);
					}
				}
			}
		).start();
		
		for(int i=1;i<=50;i++){
			bus.main(i);
		}
		
	}
	
	
}

class Business{
	
	private boolean flag = true;
	
	//子线程调用的方法
	public synchronized void sub(int i){
		//首先判断是否子线程运行标识，如果是false，让本线程等待
		while(!flag){
			try {
				System.out.println("子线程在等待");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=1;j<=10;j++){
			System.out.println("子线程 ：" +j+ ",loop of： " + i);
		}
		//当本线程运行结束，更改状态为主线程标识
		flag = false;
		//唤醒被等待的主线程
		this.notify();
	}
	
	//主线程调用的方法
	public synchronized void main(int i){
		//判断是否主线程运行标识，如果是true，让本线程等待
		while(flag){
			try {
				System.out.println("主线程在等待");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=1;j<=100;j++){
			System.out.println("主线程： " +j+ ",loop of: " + i);
		}
		//当本线程运行结束，更改状态为子线程标识
		flag = true;
		//唤醒子线程
		this.notify();
	}
	
}

