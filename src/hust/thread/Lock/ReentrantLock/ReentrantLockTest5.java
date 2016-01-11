package hust.thread.Lock.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用多个Condition实现通知部分线程：
 * await和signal都是Condition的方法，await必须使用对应Condition对象的signal方法才能唤醒。（可以实现有针对性的唤醒）
 * 
 * @author 2016-01-11
 *
 */
public class ReentrantLockTest5 {

	public static void main(String[] args) {
		try {
			Service service = new Service();
			MyThread1 t1 = new MyThread1(service);
			MyThread2 t2 = new MyThread2(service);
			t1.start();
			t2.start();
			Thread.sleep(2000);
			service.signal1();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class Service {
		private Lock lock = new ReentrantLock();
		private Condition condition1 = lock.newCondition();
		private Condition condition2 = lock.newCondition();
		public void await1() {
			try {
				lock.lock();
				System.out.println("1 进入等待。。。");
				condition1.await(); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
		public void signal1() {
			try {
				lock.lock();
				condition1.signalAll();
				System.out.println("。。。1 被唤醒");
			} finally {
				lock.unlock();
			}
		}
		
		public void await2() {
			try {
				lock.lock();
				System.out.println("2 进入等待。。。");
				condition2.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
		public void signal2() {
			try {
				lock.lock();
				condition2.signalAll();
				System.out.println("。。。2 被唤醒");
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class MyThread1 extends Thread {
		private Service service;
		public MyThread1(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {
			System.out.println("1 start ...");
			service.await1();
			System.out.println("1 end ...");
		}
	}
	
	static class MyThread2 extends Thread {
		private Service service;
		public MyThread2(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {
			System.out.println("2 start ...");
			service.await2();
			System.out.println("2 end ...");
		}
	}
}
