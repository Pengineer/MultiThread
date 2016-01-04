package hust.thread.simple;

/**
 * 线程的创建：两种方式
 * （1）继承Thread，覆写run()方法；
 * （2）实现Runnable接口，实现run()方法。（克服Java单继承的局限性）
 * 
 * 补充：
 * 1，其实Thread类本身也是实现了Runnable接口。
 * 2，Runnable接口里面只有run抽象方法，因此通过实现Runnable接口的方式来创建线程需要借助Thread类的构造函数。
 * 
 * @author 2015-01-05
 *
 */
public class CreateThread {

	public static void main(String[] args) {
		ThreadCreatePattern1 thread1 = new ThreadCreatePattern1();
		thread1.start(); // 或则new Thread(thread1).start(); ————补充1
//		thread1.start();  同一线程，只能start一次，否则java.lang.IllegalThreadStateException
		
		System.out.println("-----------------------"); // 此输出有时先于thread1，说明start方法并不表示线程开始执行，而是通知线程调度器此线程已经准备好，可以随时执行其run方法——线程的异步执行效果。

		Runnable thread2 = new ThreadCreatePattern2();	
		new Thread(thread2, "A").start(); 
	}
	
	static class ThreadCreatePattern1 extends Thread {
		@Override
		public void run() {
			super.run();
			System.out.println("Extends");
		}
	}
	
	static class ThreadCreatePattern2 implements Runnable {
		@Override
		public void run() {
			System.out.println("Implements");
		}
	}
	
}
