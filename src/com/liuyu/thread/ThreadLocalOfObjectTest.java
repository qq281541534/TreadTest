package com.liuyu.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**   
 *  
 * @Description: ThreadLocal实现线程内的共享对象
 * @author LiuYu   
 * @date 2014-6-30 下午11:14:21 
 *    
 */
public class ThreadLocalOfObjectTest {

	
	
	public static void main(String[] args) {
		
		for(int i=0; i<2;i++){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " has put data :" + data);
					MyThreadScopeData myThread = MyThreadScopeData.getThreadInstance();
					myThread.setName("name"+data);
					myThread.setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
			
		}
	}
	
	
	static class A{
		public void get(){
			System.out.println("A from " +Thread.currentThread().getName()
					+ "has put data :" + MyThreadScopeData.getThreadInstance().getName());
		}
	}
	
	
	static class B{
		public void get(){
			System.out.println("B from " +Thread.currentThread().getName()
					+ "has put data :" + MyThreadScopeData.getThreadInstance().getName());
		}
	}
}

class MyThreadScopeData{
	private String name;
	private int age;
	
	private static ThreadLocal<MyThreadScopeData> x = new ThreadLocal<MyThreadScopeData>();
	
	//private static MyThreadScopeData myThread;
	
	private MyThreadScopeData(){
		
	}
	
	//这里很巧妙，并不在外部使用静态变量，而是在构造方法内部用线程共享去获取实例对象，而当获取的对象为null时，再去实例对象，然后再放入线程共享中去
	//每次实例化该对象都会从新生成一个线程共享对象
	public static MyThreadScopeData getThreadInstance(){
		MyThreadScopeData myThread = x.get();
		if(myThread == null){
			myThread = new MyThreadScopeData();
			x.set(myThread);
		}
		
		return myThread;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}


