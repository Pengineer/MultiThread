package hust.thread.Lock.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock实现同步
 * 
 * @author 2016-01-11
 *
 */
public class ReentrantLockTest2 {

	public static void main(String[] args) {
		Service service = new Service();
		MyThreadA tA1 = new MyThreadA(service);
//		MyThreadA tA2 = new MyThreadA(service);
		MyThreadB tB1 = new MyThreadB(service);
//		MyThreadB tB2 = new MyThreadB(service);
		tA1.start();
//		tA2.start();
		tB1.start();
//		tB2.start();
	}
	
	static class Service {
		private Lock lock = new ReentrantLock();
		
		public void method1() {
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " start method1...");
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getName() + "   end method1...");
				while(true){}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
		public void method2() {
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " start method2...");
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getName() + "   end method2...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			System.out.println(".....");/////
		}
	}
	
	static class MyThreadA extends Thread {
		private Service service;
		public MyThreadA(Service service) {
			this.service = service;
		}
		@Override
		public void run() {
			service.method1();
		}
	}
	
	static class MyThreadB extends Thread {
		private Service service;
		public MyThreadB(Service service) {
			this.service = service;
		}
		@Override
		public void run() {
			service.method2();
		}
	}
}
