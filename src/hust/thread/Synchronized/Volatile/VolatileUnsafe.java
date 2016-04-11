package hust.thread.Synchronized.Volatile;

/**
 * 关于volatile的不安全性，这种不安全并不是volatile导致的，而是volatile所修饰的变量的操作的非原子性导致的。
 * 
 * @author liangjian
 *
 */
public class VolatileUnsafe {

	public static volatile int race = 0;
	
	public static void increase() {
		race++;
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
