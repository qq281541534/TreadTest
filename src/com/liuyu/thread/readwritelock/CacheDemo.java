package com.liuyu.thread.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**   
 *  
 * @Description: 设计一个缓存系统
 * @author LiuYu   
 * @date 2014-7-8 上午12:12:06 
 *    
 */
public class CacheDemo {
	
	//存缓存数据的map
	private Map<String, Object> cache = new HashMap<String, Object>();
	
	//读写锁
	public ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public Object getData(String key){
		//读的时候上锁
		rwl.readLock().lock();
		Object value = null;
		try {
			//先读取缓存
			value = cache.get(key);
			//如果缓存为空
			if(value == null){
				//释放读锁
				rwl.readLock().unlock();
				//更改为写锁，因为写锁是不允许其他线程进入的
				rwl.writeLock().lock();
				try {
					/**
					 * 这里加这个判断的原因是因为：当同时有多个线程走到了上面的代码rwl.readLock().unlock()时，只有第一个线程会
					 * 加上写锁，那么其他线程将堵塞在这里，当第一个线程读取数据库数据之后，会释放写锁，这时，其他线程会继续上写锁；而此时，
					 * value已经有值；
					 * 所以如果没有这判断，那么还会去读取一遍数据库数据。
					 */
					if(value == null){
						value = "读取数据库数据";
						cache.put(key, value);
					}
				} finally {
					//释放写锁
					rwl.writeLock().unlock();
				}
				//重新加上读锁
				rwl.readLock().lock();
			}
		}finally{
			//释放读锁
			rwl.readLock().unlock();
		}
		
		return value;
	}
	
}


