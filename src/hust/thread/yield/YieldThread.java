package hust.thread.yield;

/**
 * yield()方法作用：临时放弃当前CPU执行权，但放弃的时间不确定，也可能刚刚放弃，马上又获得CPU执行权。
 * 
 * @author 2016-01-05
 */
public class YieldThread {

	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}
	
	@SuppressWarnings("unused")
	static class MyThread extends Thread {
		@Override
		public void run() {
			long begin = System.currentTimeMillis();
			int sum=0;
			for(int i=0; i<10000000; i++) {
//				Thread.yield();
				sum+=i;
			}
			long end = System.currentTimeMillis();
			System.out.println("用时：" + (end - begin));
		}
	}
}
