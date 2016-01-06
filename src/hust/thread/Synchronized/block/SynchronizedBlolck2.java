package hust.thread.Synchronized.block;

import java.util.ArrayList;

/**
 * 同步代码块
 * 
 * 如果一个方法中只有部分代码涉及到共享数据的操作，那么可以将同步方法改为同步代码块。一个方法里面可以使用多个同步代码块，它们可以共用一个
 * 对象监视器（锁）。
 * 
 * 同步方法：synchronized
 * 同步代码块：synchronized(this) 或则 synchronized(非this对象)
 * 其中，非this对象一般是实例对象或则方法参数。
 * 
 * 线程是否发生阻塞只需要看它们所持有的锁是否来自同一个对象。
 * 
 * 本实例说明不加synchronized会导致的脏读现象。
 * 
 * @author 2016-01-06
 *
 */
public class SynchronizedBlolck2 {

	public static void main(String[] args) throws InterruptedException {
		Business b = new Business();
		ArrayList<String> list = new ArrayList<String>();
		MyThreadA tA = new MyThreadA(b, list);
		MyThreadB tB = new MyThreadB(b, list);
		tA.start();
		tB.start();
		
		Thread.sleep(1000);//等待A,B线程处理完
		System.out.println(list.size());
	}
	
	static class Business {
		public void service(ArrayList<String> list) {
			System.out.println(Thread.currentThread().getName());
			synchronized(list) {
				if(list.size() < 1) { //可以使用同步方法，也可以使用同步代码块，由于是对list的操作，因此监听器对象可以选择list参数
					list.add("1");
				}
			}
			
		}
	}
	
	static class MyThreadA extends Thread {
		private Business business;
		private ArrayList<String> list;
		public MyThreadA(Business business, ArrayList<String> list) {
			this.business = business;
			this.list = list;
		}
		@Override
		public void run() {
			business.service(list);
		}
	}
	
	static class MyThreadB extends Thread {
		private Business business;
		private ArrayList<String> list;
		public MyThreadB(Business business, ArrayList<String> list) {
			this.business = business;
			this.list = list;
		}
		@Override
		public void run() {
			business.service(list);
		}
	}
}
