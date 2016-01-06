package hust.thread.Synchronized.block;

/**
 * 同步代码块
 * 
 * 如果一个方法中只有部分代码涉及到共享数据的操作，那么可以将同步方法改为同步代码块。一个方法里面可以使用多个同步代码块，它们可以共用一个
 * 对象监视器（锁）。
 * 
 * @author 2016-01-06
 *
 */
public class SynchronizedBlolck {

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
