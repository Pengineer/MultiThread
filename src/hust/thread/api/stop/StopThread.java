package hust.thread.api.stop;

/**
 * Java中停止一个线程的方法：
 * （1）使用退出标记，使线程正常退出，也就是当run方法完成后线程终止；
 * （2）使用stop方法强行终止线程，但是不推荐使用这个方法，因为stop和suspend及resume一样，都是作废过期的方法，使用它们可能产生不可预料的结果；
 * （3）使用interrupt方法中断线程。
 * 
 * 为什么 Thread.stop 被废弃了？
 * 因为其天生是不安全的。停止一个线程会导致其解锁其上被锁定的所有监视器（监视器以在栈顶产生ThreadDeath异常的方式被解锁）（监视器即对象监视器，直接看成对象）。
 * 如果之前被这些监视器保护的任何对象处于不一致状态，其它线程看到的这些对象就会处于不一致状态。这种对象被称为受损的 （damaged）。
 * 当线程在受损的对象上进行操作时，会导致任意行为。这种行为可能微妙且难以检测，也可能会比较明显。不像其他未受检的（unchecked）异常，
 * ThreadDeath悄无声息的杀死及其他线程。因此，用户得不到程序可能会崩溃的警告。崩溃会在真正破坏发生后的任意时刻显现，甚至在数小时或数天之后。
 * 简而言之，stop()方法会对线程锁定的对象进行解锁，导致数据得不到同步，出现数据一致性问题；同时可能使得一些清理性的工作得不到完成。
 * 
 * 为什么 Thread.suspend 和 Thread.resume 被废弃了？
 * Thread.suspend 天生容易引起死锁。如果目标线程挂起时在保护系统关键资源的监视器上持有锁，那么其他线程在目标线程恢复之前都无法访问这个资源。
 * 如果要恢复目标线程的线程在调用resume之前试图锁定这个监视器，死锁就发生了。这种死锁一般自身表现为“冻结（frozen）”进程。
 * 
 * 我应该用什么来取代 Thread.suspend 和 Thread.resume ？
 * 与Thread.stop,类似，谨慎的方式，是让“目标线程”轮询一个指示线程期望状态（活动或挂起）的变量。当期望状态是挂起时，线程用 Object.wait 来等待；
 * 当恢复时，用 Object.notify 来通知目标线程。
 * 
 * @author 2016-01-06
 *
 */
public class StopThread {
	
	public static void main(String[] args) throws InterruptedException {
//		MyThread1 t1 = new MyThread1();
//		t1.start();
		
//		MyThread2 t2 = new MyThread2();
//		t2.start();
		
//		MyThread3 t3 = new MyThread3();
//		t3.start();
//		Thread.sleep(1); //切换CPU执行权，让t3运行
//		t3.interrupt();  //main获得CPU执行权，给t3加中断标识
		
		MyThread4 t4 = new MyThread4();
		t4.start();
		Thread.sleep(1);
		t4.interrupt();
		
//		MyThread5 t5 = new MyThread5();
//		t5.start();
//		Thread.sleep(1);
//		t5.interrupt();
		
//		MyThread6 t6 = new MyThread6();
//		t6.start();
//		Thread.sleep(1000);//让t6进入sleep()方法
//		t6.interrupt();
	}

	//已被抛弃的stop方法
	static class MyThread1 extends Thread {
		@Override
		public void run() {
			super.run();
			System.out.println(11);
			Thread.currentThread().stop();// stop方法会抛出一个ThreadDeath异常，但是该异常不需要显示的捕获。
			System.out.println(12);
		}
	}
	
	//并不能停止线程的interrupt方法
	static class MyThread2 extends Thread {
		@Override
		public void run() {
			super.run();
			System.out.println(21);
			Thread.currentThread().interrupt();  //使用interrupt方法仅仅是在当前线程中打了一个中断标记，并不是真的停止线程。
			System.out.println(Thread.interrupted());
			System.out.println(22);
		}
	}
	
	//不完美的停止线程的方式：interrupt+interrupted状态标志位校验法
	static class MyThread3 extends Thread {
		@Override
		public void run() {
			for(int i=0; i < 1000000; i++) {
				if(interrupted()) {
					System.out.println("线程已中断，退出！");
					break;//仅仅是退出for循环
				}
				//业务代码
				System.out.println(i);
			}
			
			System.out.println("线程并未真正停止，继续运行！");
		}
	}
	
	//return停止线程的方式：return+isInterrupted
	static class MyThread4 extends Thread {
		@Override
		public void run() {
			for(int i=0; i < 1000000; i++) {
				if(isInterrupted()) {
					System.out.println("线程已中断，退出！");
					return;
				}
				//业务代码
				System.out.println(i);
			}
			
			System.out.println("线程停止，无法继续运行！");
		}
	}
	
	//可停止线程的方式（人为可控）：异常+interrupt+interrupted状态标志位校验法。（没有标志位判断的线程异常终止是人为不可抗的）
	static class MyThread5 extends Thread {
		@Override
		public void run() {
			try{
				for(int i=0; i < 1000000; i++) {
					if(interrupted()) {//interrupted()会将中断标志位清为false，isInterrupted()不会清标志位（当然了，if里面的条件也可以是任意的）
						System.out.println("线程已中断，退出！");
						throw new InterruptedException();
					}
					//业务代码
					System.out.println(i);
				}
				System.out.println("线程已停止，本句执行不到了！");
			} catch(InterruptedException e) {
				//业务终止
			}
		}
	}
	
	//通过sleep停止线程（与异常相关，但并不是人为抛出的异常）：在sleep状态下尝试中断某一线程，会触发InterruptException异常。（也就是说我们可以人为控制这种异常的发生，我在smdb中停止线程池就是这么干的）
	static class MyThread6 extends Thread {
		@Override
		public void run() {
			try{
				//业务代码，不需要interrupt标志位的协助，但是需要interrupt触发异常
				System.out.println("业务执行");
				if (true) { // if里面的条件一般是人为设置的一个全局参数，不能使用interrupted()来判断，但可以使用isInterrupted()
					Thread.sleep(10000); // 既可以在sleep之前设置中断标志位，也可以在sleep时设置，都会触发异常
				}
				System.out.println("业务还能继续执行么。。。");
			} catch(InterruptedException e) {
				System.out.println("业务被人为终止");
			}
		}
	}
}
