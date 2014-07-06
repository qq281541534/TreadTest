package com.liuyu.thread.pool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**   
 *  
 * @Description: CompetionService用于提交一组Callable任务，其take()方法返回已完成的callable任务对应的Future对象;
 * 					作用好比：同时种了几块地的麦子，然后等待收割，收割时不是按照种地的顺序进行收割，而是哪块地先熟，就先收割哪块
 * 					所以代码中让每个线程中的任务睡眠的时间不同
 * @author LiuYu   
 * @date 2014-7-6 下午7:45:50 
 *    
 */
public class CompletionServiceTest {
	
	public static void main(String[] args) {
		//创建一个线程池，池中有10个线程
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		//已完成的服务类
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool);
		
		for(int i=1;i<=10;i++){
			final int seq = i;
			completionService.submit(new Callable<Integer>(){
				
				@Override
				public Integer call() throws Exception {
					//每个任务进来都会休眠不超过5秒
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}
		System.out.println("任务完成的顺序是：");
		for(int i=1;i<=10;i++){
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


