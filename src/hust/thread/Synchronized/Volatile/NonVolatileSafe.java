package hust.thread.Synchronized.Volatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用原子操作解决基本类型变量修改安全问题
 * 
 * Atomic类采用了CAS原子操作，CAS指令需要三个操作数，内存位置（Java中简单理解为变量在内存中的地址V）、旧的预期值A、和新值B。CAS
 * 指令执行时，当且仅当V中的值为A时，用新值B更新V中的值。形式：boolean compareAndSet(expectedValue, updateValue);
 * Synchronized和Lock均是同步阻塞方式，这种互斥同步属于悲观的并发策略；CAS采用的是同步非阻塞方式，它不需要把线程挂起，属于乐观的
 * 并发策略，底层由计算机硬件直接实现。
 * 
 * CAS也有漏洞，存在“ABA”的问题，Java提供了一个原子引用类AtomicStampedReference来解决，它通过控制变量值的版本来保证CAS的正确性。
 * 
 * CAS原子操作是JDK1.5之后引入的。
 * 
 * @author liangjian
 *
 */
public class NonVolatileSafe {

	public static AtomicInteger race = new AtomicInteger(0);
	public static int increase() {
//		race.incrementAndGet(); //下面的代码就是本句话的源码
		for (;;) {
			int current = race.get();
			int next = current + 1;
			if (race.compareAndSet(current, next))
				return next;
		}
	}
	
	private static final int THREADS_COUNT = 20;
	
	public static void main(String[] args) throws Exception {
		Thread[] threads = new Thread[THREADS_COUNT];
		for(int i=0; i<THREADS_COUNT; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 1000000; j++) {
						increase();
					}
				}
			});
		}
		for (int i = 0; i < THREADS_COUNT; i++) {
			threads[i].start();
		}
		
		while(Thread.activeCount() > 1) {
			Thread.yield();
		}
		
		System.out.println("race: " + race);
	}
}
