package hust.thread.api.share;

/**
 * 共享数据：只定义一个自定义线程对象，将该对象通过构造函数的方式绑定到多个新线程上（Thread本身实现了Runnable接口），显然各线程共享该对象。
 * 非共享数据：定义多个自定义线程对象，每一个对象作为一个单独线程启动。
 * 
 * 补充：
 * 本代码共享方式存在线程安全问题，因为cnt--操作在JVM中并不是一步能完成的，因此各工作内存中的数据可能不能及时同步回主内存，需要在run方法上加锁同步。
 * 
 * @author 2016-01-05
 *
 */
public class ShareData {

	public static void main(String[] args) {
		//共享数据的方式，至始至终，只有一个自定义线程对象。
		MyThread t = new MyThread();
		new Thread(t, "A").start();
		new Thread(t, "B").start();
		new Thread(t, "C").start();
		new Thread(t, "D").start();
		
		
		//不共享数据的方式，每一个自定义线程对象维护自己的属性数据
//		MyThread tt1 = new MyThread("A");
//		MyThread tt2 = new MyThread("B");
//		MyThread tt3 = new MyThread("C");
//		MyThread tt4 = new MyThread("D");
//		tt1.start(); //等价于 new Thread(tt1).start();
//		tt2.start();
//		tt3.start();
//		tt4.start();
	}
	
	static class MyThread extends Thread {
		private int cnt =10;
		@Override
		public void run() {
			for (int i = 0; i < 2; i++) {
				cnt--;
				System.out.println(Thread.currentThread().getName() + " : " + cnt);
			}
		}
		
		public MyThread(){}
		public MyThread(String name) {
			this.setName(name);
		}
	}
	
}
