package hust.thread.Lock.ReentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantLock具有完全互斥排他的效果，即同一时间只有一个线程在执行ReentrantLock.lock()方法后面的任务。这样做虽然保证了实例变量的
 * 线程安全性，但效率却是比较低下的。所以JDK提供了一种读写锁ReentrantReadWriteLock类。
 * 
 * 特点：
 * "读写"、"写读"和"写写"都是互斥了；而"读读"是异步的，非互斥的。
 * 
 * @author 2016-01-11
 *
 */
public class ReentrantReadWriteLockTest1 {

	public static void main(String[] args) {
		Service service = new Service();
//		MyThreadA tread1 = new MyThreadA(service);
//		MyThreadA tread2 = new MyThreadA(service);
//		tread1.start();
//		tread2.start();
		
//		MyThreadA tread1 = new MyThreadA(service);
//		MyThreadB twrite1 = new MyThreadB(service);
//		tread1.start();
//		twrite1.start();
		
		MyThreadB twrite1 = new MyThreadB(service);
		MyThreadB twrite2 = new MyThreadB(service);
		twrite1.start();
		twrite2.start();
	}
	
	static class Service {
		private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		public void read() {
			try {
				try {
					lock.readLock().lock();
					System.out.println(Thread.currentThread().getName() + " get read lock");
					Thread.sleep(5000);
				} finally {
					lock.readLock().unlock();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void write() {
			try {
				try {
					lock.writeLock().lock();
					System.out.println(Thread.currentThread().getName() + " get write lock");
					Thread.sleep(5000);
				} finally {
					lock.writeLock().unlock();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class MyThreadA extends Thread {
		private Service service;
		public MyThreadA(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {
			service.read();
		}
	}
	
	static class MyThreadB extends Thread {
		private Service service;
		public MyThreadB(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {
			service.write();
		}
	}
}
