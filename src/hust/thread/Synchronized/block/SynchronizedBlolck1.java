package hust.thread.Synchronized.block;

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
 * 线程是否发生阻塞只需要看它们所持有的锁是否来自同一个对象，与对象的属性是否发生变化无关。
 * 
 * @author 2016-01-06
 *
 */
public class SynchronizedBlolck1 {

	public static void main(String[] args) {
		Business b = new Business();
		MyThreadA tA = new MyThreadA(b);
		MyThreadB tB = new MyThreadB(b);
		tA.start();
		tB.start();
		
	}
	
	static class Business {
		private int sum=0;
		public void service() {
			System.out.println("bussniess start.");

			synchronized(this) {
				for(int i=0; i<10; i++) {
					sum+=1;
					System.out.println(Thread.currentThread().getName() + ": " + sum);
				}	
			}
			
			System.out.println("第一次处理结果：" + sum);
			try {
				Thread.sleep(3000);  //从输出结果看，同步代码块结束后，线程就释放了对象锁
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized(this) {
				for(int i=0; i<10; i++) {
					sum+=1;
					System.out.println(Thread.currentThread().getName() + ": " + sum);
				}	
			}
			
			System.out.println("bussniess end.");
		}
	}
	
	static class MyThreadA extends Thread {
		private Business business;
		public MyThreadA(Business business) {
			this.business = business;
		}
		@Override
		public void run() {
			business.service();
		}
	}
	
	static class MyThreadB extends Thread {
		private Business business;
		public MyThreadB(Business business) {
			this.business = business;
		}
		@Override
		public void run() {
			business.service();
		}
	}
}
