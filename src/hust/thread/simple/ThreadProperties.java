package hust.thread.simple;

/**
 * 线程的基本API属性
 * 
 * @author 2016-01-05
 *
 */
public class ThreadProperties {

	public static void main(String[] args) {
		System.out.println("1: " + Thread.activeCount());
		System.out.println("2: " + Thread.currentThread().getId());
		System.out.println("3: " + Thread.currentThread().getName());
		System.out.println("4: " + Thread.currentThread().getPriority());
		System.out.println("5: " + Thread.currentThread().getContextClassLoader());
		System.out.println("6: " + Thread.currentThread().getStackTrace());
		System.out.println("7: " + Thread.currentThread().getState());
		System.out.println("8: " + Thread.currentThread().getThreadGroup());
		System.out.println("9: " + Thread.currentThread().getUncaughtExceptionHandler());

		System.out.println("10: " + Thread.currentThread().isAlive());
		System.out.println("11: " + Thread.currentThread().isDaemon());
		System.out.println("12: " + Thread.currentThread().isInterrupted());
		
		System.out.println("13: " + Thread.MAX_PRIORITY);
		System.out.println("14: " + Thread.MIN_PRIORITY);
		System.out.println("15: " + Thread.NORM_PRIORITY);
	}
}
