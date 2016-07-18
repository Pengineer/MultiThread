package hust.thread.Lock.ReentrantLock.pr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用java.util.concurrent.locks实现与synchronized相同功能的生产者/消费者：一对一。
 * 
 * @author 2016-01-11
 *
 */
public class OnePOneC {
	
	public static void main(String[] args) {
		Producer p = new Producer();
		Consumer c = new Consumer();
		p.start();
		c.start();
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
					if(LockService.list.size() == 1) {
						LockService.condition.await();
					}
					LockService.list.add("1");
					System.out.println("produce one...");
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
					if(LockService.list.size() == 0) {
						LockService.condition.await();
					}
					LockService.list.remove(0);
					System.out.println("consume one。。");
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
