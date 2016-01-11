package hust.thread.api.join;

/**
 * join的作用是使所属线程对象x正常执行run()方法中的任务，而使当前线程z进行无期限的阻塞，等待线程x销毁后再继续执行线程z后面的代码。
 * 方法join具有使线程排队运行的作用，有些类似同步的运行效果。join与synchronized的区别是：join在内存使用wait()方法进行等待，而
 * synchronized关键字使用的是"对象监视器"原理做同步。
 * 
 * 当线程A的运行依赖于线程B的最终运行结果，那么可以线程A中执行B.join().
 * 
 * 源码：
 * if (millis == 0) {
	    while (isAlive()) {
			wait(0);  //while限制了即使线程被唤醒，只要子线程还活着，则继续等待。
	    }
 * }
 * 问题：
 * 虽然B.join()被调用的地方是发生在“Father主线程”中，但是B.join()是通过“子线程B”去调用的join()。那么，join()方法中的isAlive()应该是
 * 判断“子线程B”是不是Alive状态；对应的wait(0)也应该是“让子线程B”等待才对。但如果是这样的话，B.join()的作用怎么可能是“让主线程等待，
 * 直到子线程B完成为止”呢，应该是让"子线程等待才对(因为调用子线程对象B的wait方法嘛)"？
 * 
 * 答案：wait()的作用是让“当前线程”等待，而这里的“当前线程”是指当前在CPU上运行的线程。所以，虽然是调用子线程的wait()方法，但是它是通过
 * “主线程”去调用的；所以，休眠的是主线程，而不是“子线程”！
 * 
 * B线程join到A线程后，如果A线程被发生interrupt，则A线程会抛出InterruptException异常，但是不会影响到B线程。
 * 
 * join(long)与sleep(long)的区别：
 * 前者底层是用wait实现的，因此在join(long)执行后会释放锁。执行sleep(long)不对释放当前线程所持有的锁。
 * 另外join方法本身也是加锁同步的。
 * 	
 * 
 * @author 2016-01-09
 *
 */
public class JoinDemo {

	public synchronized static void main(String[] args) {
		System.out.println("main start...");
		try {
			MyThread t = new MyThread();
			t.start();
			t.join(); //咋一看，这种顺序执行与普通的方法调用没什么区别，但是join的线程如果发生异常是不会影响主线程的
			System.out.println(Thread.currentThread().isAlive());
			System.out.println(t.isAlive());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main end...");
	}
	
	static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("MyThread start...");
			try {
				for(int i=0; i<5; i++) {
					System.out.println(i);
					Thread.sleep(1000);
//					throw new RuntimeException();
				} 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("MyThread end...");
		}
	}
}
