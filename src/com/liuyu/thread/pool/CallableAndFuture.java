package com.liuyu.thread.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**   
 *  
 * @Description: Callable 和 Futrue的实例，这个例子说明的是当线程池提交的时候，会在线程结束时，返回Callable中的结果
 *  Future 取得的结果类型和Callable返回的类型一致，这是通过泛型来实现的。
 *  Callable 要采用ExecutorService的submit方法提交，返回的future对象可以取消任务。
 * @author LiuYu   
 * @date 2014-7-6 下午7:27:51 
 *    
 */
public class CallableAndFuture {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor(); 
		
		Future<String> future = threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "我是结果";
			}
			
		});
		System.out.println("等待结果");
		
		try {
			System.out.println("拿到的结果： "+future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("如果future没有拿到结果，会一直等在那里，下面的都不执行");
	}	

}


