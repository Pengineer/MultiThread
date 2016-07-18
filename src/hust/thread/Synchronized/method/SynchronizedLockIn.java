package hust.thread.Synchronized.method;

/**
 * 可重入锁：线程可以再次获取自己已有的锁。
 * 可重入锁保证了当线程拥有对象的锁，执行对象的synchronized方法时，该方法可以调用另一个synchronized方法。如果锁不可重入，就会导致死锁。
 * 
 * 可重入锁同样适用于父子类继承的环境。也就是说子类完全可以通过可重入锁的方式调用父类的同步方法。
 * 
 * 
 * @author 2016-01-05
 *
 */
public class SynchronizedLockIn {

	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}
	
	static class Service {
		synchronized public void method1() {
			System.out.println("method1");
			method2();
		}
		synchronized public void method2() {
			System.out.println("method2");
			method3();
		}
		synchronized public void method3() {
			System.out.println("method3");
		}
	}
	
	static class MyThread extends Thread {
		@Override
		public void run() {
			Service s = new Service();
			s.method1();
		}
	}
}
