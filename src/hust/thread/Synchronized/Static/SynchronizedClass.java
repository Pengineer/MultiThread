package hust.thread.Synchronized.Static;

/**
 * 关键字synchronized应用在静态方法上，是对整个Class类进行封锁。
 * 
 * 如果Service类的两个方法都是普通的非静态方法，那么这两个方法的同步锁是一把锁，不同的线程在调用这两个方法时，会发生阻塞；
 * 如果Service类的两个方法都是普通的静态方法，那么这两个方法的同步锁也是一把锁，不同的线程在调用这两个方法时，会发生阻塞；
 * 但是，如果Service类的两个方法一个是静态的，一个是非静态的，那么调用这两个方法的的不同线程是不会发生阻塞的。
 * 
 * 原因：
 * 静态方法上的锁是Class锁，非静态方法上的锁是类的实例对象锁。
 * 可以在同步代码块中使用Class锁，synchronized(Service.class)，一定注意了，这是类锁，和静态方法共用一个锁；非静态方法上的是对象锁，它们
 * 共用一把锁。
 * 
 * 对于Class锁，所有的对象使用的同一把；
 * 对于对象锁，仅针对本实例对象。
 * 
 * @author 2016-01-06
 *
 */
public class SynchronizedClass {
	public static void main(String[] args) {
		Service service = new Service();
//		MyThreadA tA = new MyThreadA(service);
//		MyThreadB tB = new MyThreadB(service);
//		tA.start();
//		tB.start();

		MyThreadC tC1 = new MyThreadC();
		MyThreadC tC2 = new MyThreadC();
		tC1.start();
		tC2.start();
	}
}
