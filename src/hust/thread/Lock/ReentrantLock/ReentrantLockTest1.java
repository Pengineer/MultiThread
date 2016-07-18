package hust.thread.Lock.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java.util.concurrent.lock 中的 Lock 框架是Java5引入的锁定的一个抽象，它允许把锁定的实现作为 Java 类，而不是作为语言的特性来实现。这 
 * 就为Lock的多种实现留下了空间，各种实现可能有不同的调度算法、性能特性或者锁定语义。ReentrantLock 类实现了Lock，它拥有与 synchronized 相
 * 同的并发性和内存语义，但是添加了类似轮询锁、定时锁等候和可中断锁等候的一些特性。此外，它还提供了在激烈争用情况下更佳的性能。（换句
 * 话说，当许多线程都想访问共享资源时，JVM 可以花更少的时候来调度线程，把更多时间用在执行线程上。）
 * 
 * 关于synchronized与Lock的讨论：
 * http://www.ibm.com/developerworks/cn/java/j-jtp10264/index.html（文章时间有点早，但是说的很有道理）
 * 
 * 《深入理解JVM高级特性》的作者坚持认为对于绝大数使用者，我们的重点还是应该放在synchronized上。
 * 
 * @author 2016-01-11
 *
 */
public class ReentrantLockTest1 {

	public static void main(String[] args) {
		Service service = new Service();
		MyThread t1 = new MyThread(service);
		MyThread t2 = new MyThread(service);
		MyThread t3 = new MyThread(service);
		t1.start();
		t2.start();
		t3.start();
	}
	
	static class Service {
		private Lock lock = new ReentrantLock();
		public void service() {
			lock.lock();
			for(int i=0; i<5; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
			lock.unlock();
		}
	}
	
	static class MyThread extends Thread {
		private Service service;
		public MyThread(Service service) {
			this.service = service;
		}
		@Override
		public void run() {
			service.service();
		}
	}
}
