package hust.thread.Lock.ReentrantLock.pr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用java.util.concurrent.locks实现与synchronized相同功能的生产者/消费者：多对多。
 * 
 * @author 2016-01-11
 *
 */
public class ManyPManyC {
	
	public static void main(String[] args) {
		Producer p1 = new Producer();
		Producer p2 = new Producer();
		Producer p3 = new Producer();
		Consumer c1 = new Consumer();
		Consumer c2 = new Consumer();
		Consumer c3 = new Consumer();
		p1.start();
		p2.start();
		p3.start();
		c1.start();
		c2.start();
		c3.start();
	}
	
	static class LockService {
		public static ReentrantLock lock = new ReentrantLock();
		public static Condition condition = lock.newCondition();
		public static volatile List<String> list = new ArrayList<String>();
	}
	
	static class Producer extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					LockService.lock.lock();
					while (LockService.list.size() == 1) {
						LockService.condition.await();
					}
					LockService.list.add("1");
					System.out.println(Thread.currentThread().getName() + " produce one......");
					Thread.sleep(100);
					LockService.condition.signalAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					LockService.lock.unlock();
				}
			}
		}
	}
	
	static class Consumer extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					LockService.lock.lock();
					while (LockService.list.size() == 0) {
						LockService.condition.await();
					}
					LockService.list.remove(0);
					System.out.println(Thread.currentThread().getName() + " consume one。。。。");
					Thread.sleep(100);
					LockService.condition.signalAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					LockService.lock.unlock();
				}
			}
		}
	}
}
