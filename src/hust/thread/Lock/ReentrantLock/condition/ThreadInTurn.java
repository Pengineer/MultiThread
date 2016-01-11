package hust.thread.Lock.ReentrantLock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现线程的顺序执行
 * 
 * @author 2016-01-11
 *
 */
public class ThreadInTurn {

	private volatile static int nextPrintWho = 1;
	private static ReentrantLock lock = new ReentrantLock();
	private static Condition c1 = lock.newCondition();
	private static Condition c2 = lock.newCondition();
	private static Condition c3 = lock.newCondition();
	
	public static void main(String[] args) {
		Thread threadA = new Thread() {
			@Override
			public void run() {
				try {
					lock.lock();
					while(nextPrintWho != 1) {
						c1.await();
					}
					System.out.println("ThreadA");
					nextPrintWho = 2;
					c2.signalAll();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			
		};
		
		Thread threadB = new Thread() {
			@Override
			public void run() {
				try {
					lock.lock();
					while(nextPrintWho != 2) {
						c2.await();
					}
					System.out.println("ThreadB");
					nextPrintWho = 3;
					c3.signalAll();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			
		};
		
		Thread threadC = new Thread() {
			@Override
			public void run() {
				try {
					lock.lock();
					while(nextPrintWho != 3) {
						c3.await();
					}
					System.out.println("ThreadC");
					nextPrintWho = 1;
					c1.signalAll();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			
		};
		
		Thread[] aArray = new Thread[5];
		Thread[] bArray = new Thread[5];
		Thread[] cArray = new Thread[5];
		for(int i =0; i < 3; i++) {
			aArray[i] = new Thread(threadA);
			bArray[i] = new Thread(threadB);
			cArray[i] = new Thread(threadC);
			aArray[i].start();
			bArray[i].start();
			cArray[i].start();
		}
	}
	
}
