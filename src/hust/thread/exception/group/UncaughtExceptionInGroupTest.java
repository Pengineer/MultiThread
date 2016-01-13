package hust.thread.exception.group;

/**
 * 实现线程组内一个线程出现异常后全部线程都停止
 * 
 * @author liangjian 2016-01-12
 *
 */
public class UncaughtExceptionInGroupTest {

	public static void main(String[] args) {
		MyThreadGroup group = new MyThreadGroup("我的线程组");
		MyThread[] myThread = new MyThread[10];
		for (int i = 0; i < myThread.length; i++) {
			myThread[i] = new MyThread(group, "线程" + (i+1), "1");
			myThread[i].start();
		}
		MyThread newT = new MyThread(group, "报错线程", "a");
		newT.start();
	}
	
	//自定义线程组，覆写uncaughtException()方法，当线程出现异常时，会自动调用该方法
	static class MyThreadGroup extends ThreadGroup {
		public MyThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			super.uncaughtException(t, e);
			this.interrupt();//中断组内所有线程
		}
	}
	
	static class MyThread extends Thread {
		private String num;
		public MyThread(ThreadGroup group, String name, String num) {
			super(group, name);
			this.num = num;
		}
		@Override
		public void run() {
			int numInt = Integer.parseInt(num);
			while(this.isInterrupted() == false) {
				System.out.println("一直循环：" + Thread.currentThread().getName());
			}
		}
	}
	
}
