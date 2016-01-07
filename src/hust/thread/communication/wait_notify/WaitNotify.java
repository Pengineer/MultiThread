package hust.thread.communication.wait_notify;

/**
 * A，B，C三个线程使用相同的对象锁，测试让A和B同时等待，然后C线程执行一次notify随机唤醒其中的一个线程。
 * 
 * 补充：
 * 如果notify在wait之前执行，wait线程将不会被唤醒。可以通过设置一个全局变量来控制wait线程中wait()方法的执行。
 * 
 * @author 2016-01-07
 *
 */
public class WaitNotify {

	public static void main(String[] args) {
		try {
			Object lock = new Object();
			MyThreadA tA = new MyThreadA(lock);
			MyThreadB tB = new MyThreadB(lock);
			MyThreadC tC = new MyThreadC(lock);
			tA.start();
			tB.start();
			Thread.sleep(2000); //让A,B线程执行等待
			tC.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	
	static class MyThreadA extends Thread {
		Object lock;
		public MyThreadA(Object lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			synchronized(lock) {
				try {
					System.out.println("Thread A start.....");
					lock.wait();
					System.out.println("Thread A end.....");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class MyThreadB extends Thread {
		Object lock;
		public MyThreadB(Object lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			synchronized(lock) {
				try {
					System.out.println("Thread B start.....");
					lock.wait();
					System.out.println("Thread B end.....");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class MyThreadC extends Thread {
		Object lock;
		public MyThreadC(Object lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			synchronized(lock) {
				System.out.println("Thread C start.....");
				lock.notify();
				System.out.println("Thread C end.....");
			}
		}
	}
}
