package hust.thread.Synchronized.method;

/**
 * 多个对象多个锁
 * 
 * 关键字synchronized取得的锁都是对象锁，而不是把一段代码或方法当作锁。多线程中，哪个线程先执行带synchronized关键字的方法，哪个线程就持有该方法
 * 所属对象的锁Lock，那么其他线程只能呈等待状态，前提还是多个线程访问的是同一个对象。
 * 
 * @author 2016-01-05
 *
 */
public class SynchronizedVariable2 {

	public static void main(String[] args) {
		MyObject obj1 = new MyObject();
		MyObject obj2 = new MyObject();
		MyThreadA tA = new MyThreadA(obj1);
		MyThreadB tB = new MyThreadB(obj2);
		tA.start();
		tB.start();
	}
	
	static class MyObject {
		private int num =0;
		synchronized public void add(String username) { //这种情况下Synchronized关键字都无所谓了，不同线程操作的对象都不一样
			try {
				if(username != null && username.equals("a")) {
					num =100;
					Thread.sleep(1000);
				} else if(username != null && username.equals("b")) {
					num=200;
				}
				System.out.println("username=" + username + "  num=" + num);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	static class MyThreadA extends Thread {
		private MyObject obj;
		public MyThreadA(MyObject obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			obj.add("a");
		}
	}
	
	static class MyThreadB extends Thread {
		private MyObject obj;
		public MyThreadB(MyObject obj) {
			this.obj = obj;
		}
		
		@Override
		public void run() {
			obj.add("b");
		}
	}
}
