package hust.thread.Synchronized.method;

/**
 * 调用被锁对象的异步方法
 * 如果一个对象被一个线程锁定，那么其他线程仍然可以调用该对象的异步方法的（没有加synchronized关键字的方法）。
 * 
 * 控制台可能输出结果：
 * A start
 * B
 * A end
 * 也就是说对象在被线程A锁定期间，线程B依然可以调用对象的非同步方法
 * 
 * @author 2016-01-05
 *
 */
public class SynchronizedVariable3 {

	public static void main(String[] args) {
		MyObject obj = new MyObject();
		MyThreadA tA = new MyThreadA(obj);
		MyThreadB tB = new MyThreadB(obj);
		tA.setName("A");
		tB.setName("B");
		tA.start();
		tB.start();
	}
	
	static class MyObject {
		synchronized public void method1() {
			System.out.println(Thread.currentThread().getName() + " start");
			try {
				Thread.sleep(3000);//锁定对象
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " end");
		}
		
		public void method2() {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	static class MyThreadA extends Thread {
		private MyObject obj;
		public MyThreadA(MyObject obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			obj.method1();
		}
	}
	
	static class MyThreadB extends Thread {
		private MyObject obj;
		public MyThreadB(MyObject obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			obj.method2();
		}
	}
}
