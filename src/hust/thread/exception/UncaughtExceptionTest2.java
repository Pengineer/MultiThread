package hust.thread.exception;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 捕获指定线程中的异常
 * 使用setDefaultUncaughtExceptionHandler为某一类线程类设置默认异常处理器。
 * 
 * @author liangjian
 *
 */
public class UncaughtExceptionTest2 {

	public static void main(String[] args) {
		MyThread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("线程 " + t.getName() + " 出现异常了");
			}
		});
		
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		t1.setName("线程t1");
		t2.setName("线程t2");
		t1.start();
		t2.start();
	}
	
	static class MyThread extends Thread {
		@Override
		public void run() {
			String username =null;
			username.hashCode();
		}
	} 
}
