package hust.thread.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 捕获指定线程中的异常
 * 在Java多线程中，可以对多线程中的异常进行“捕获”，使用的UncaughtExceptionHandler类，该类的作用就是对指定的线程对象设置默认的异常处理器。
 * 
 * @author liangjian
 *
 */
public class UncaughtExceptionTest1 {

	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.setName("线程t1");
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("线程 " + t.getName() + " 出现异常了");
			}
		});
		t1.start();
	}
	
	static class MyThread extends Thread {
		@Override
		public void run() {
			String username =null;
			username.hashCode();
		}
	} 
	
	
}
