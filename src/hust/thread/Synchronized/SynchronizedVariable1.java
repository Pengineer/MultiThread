package hust.thread.Synchronized;

/**
 * 非线程安全问题会在多个线程对同一个对象中的实例变量进行并发访问时发生。方法中的局部变量不会发生线程安全问题。
 * 
 * 
 * @author 2016-01-05
 *
 */
public class SynchronizedVariable1 {

	public static void main(String[] args) {
		MyObject obj = new MyObject();
		MyThreadA tA = new MyThreadA(obj);
		MyThreadB tB = new MyThreadB(obj);
		tA.start();
		tB.start();
	}
	
	static class MyObject {
		private int num =0;
		synchronized public void add(String username) {  //如果无synchronized同步关键字，输出错误。多个线程访问同一个对象中的同步方法时一定是线程安全的
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
