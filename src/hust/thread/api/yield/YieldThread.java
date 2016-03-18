package hust.thread.api.yield;

/**
 * yield()方法作用：临时放弃当前CPU执行权，但放弃的时间不确定，也可能刚刚放弃，马上又获得CPU执行权。
 * 
 * sleep()与yield()方法的区别：
 * 1.sleep()方法会给其他线程运行的机会,而不管其他线程的优先级,因此会给较低优先级的线程运行的机会;yeild()方法只会给优先
 * 级相同的或者比自己高的线程运行的机会.
 * 2.sleep()方法声明抛出InterruptionException异常,而yeild()方法没有声明抛出任何异常.
 * 3.sleep()方法比yeild()方法具有更高的可移植性.
 * 4.sleep()方法使线程进入阻塞状态,而yeild()方法使线程进入就绪状态.
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
