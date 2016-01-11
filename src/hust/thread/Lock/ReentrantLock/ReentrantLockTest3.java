package hust.thread.Lock.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition的await/signal实现synchronized中的wait/notify功能
 * 
 * 来自Java API的解释：
 * Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用，
 * 为每个对象提供多个等待 set（wait-set）。其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的
 * 使用。 
 * 
 * @author 2016-01-11
 *
 */
public class ReentrantLockTest3 {

	public static void main(String[] args) {
		Service service = new Service();
		MyThread t = new MyThread(service);
		t.start();
	}
	
	static class Service {
		private Lock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();
		public void await() {
			try {
				lock.lock();
				condition.await(); //与synchronized的wait方法相似，condition的await方法必须在调用lock方法获取同步监视器后使用。
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class MyThread extends Thread {
		private Service service;
		public MyThread(Service service) {
			this.service = service;
		}
		
		@Override
		public void run() {
			System.out.println("A");
			service.await();
			System.out.println("B");
		}
	}
}
