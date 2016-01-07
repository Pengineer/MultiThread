package hust.thread.Synchronized.Volatile;

/**
 * volatile的使用场景：只有一个线程修改共享的volatile变量值。
 * 
 * 关于其介绍，参见 tips
 * 
 * @author 2016-01-07
 *
 */
public class VolatileEnvironment {
	public static volatile boolean shutdownRequested = false;
	
	public static void shutdown() {
		shutdownRequested = true;
	}
	
	public static void work() {
		if("19".equals(Thread.currentThread().getName())) {
			shutdown();
		}
		while(!shutdownRequested) {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[20];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					work();
				}
			}, i + "");
			threads[i].start();
		}
		
		// 等待所有的非main线程结束
		while(Thread.activeCount() > 1) {
			Thread.yield();// main线程放弃执行权
		}
		
		System.out.println("all over!");
	}
}
