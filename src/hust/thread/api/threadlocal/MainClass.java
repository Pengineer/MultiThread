package hust.thread.api.threadlocal;

/**
 * 	变量值的共享可以使用public static 变量的形式，所有的线程都使用同一个public static变量。如果想实现每一个线程都有自己的共享变量该如何解决呢？
 * JDK中提供的类ThreadLocal正是为了解决这个问题。
 * 	类ThreadLocal主要解决的就是变量在不同线程间的隔离性，不同的线程中的值是可以统一放入ThreadLocal类中进行保存。
 * 
 * 关于线程变量的隔离，JavaSE还提供了一个类InheritableThreadLocal，该类可以在子线程中取得父线程中的变量，也可以设置子线程自己的隔离变量。
 * 
 * ThreadLocal类的作用是为每个线程都创建一个变量副本, 每个线程都可以修改自己所拥有的变量副本, 而不会影响其他线程的副本. 其实这也是解决线程安全
 * 的问题的一种方法.
 * 
 * ThreadLocal是如何做到为每一个线程维护变量的副本的呢？其实实现的思路很简单：在ThreadLocal类中有一个Map，用于存储每一个线程的变量副本。
 * 
 * @author 2016-01-11
 *
 */
public class MainClass {

	public static void main(String[] args) {
		MyThreadA tA = new MyThreadA();
		MyThreadB tB = new MyThreadB();
		tA.start();
		tB.start();
	}
}
