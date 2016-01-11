package hust.thread.api.threadlocal;

/**
 * 	变量值的共享可以使用public static 变量的形式，所有的线程都使用同一个public static变量。如果想实现每一个线程都有自己的共享变量该如何解决呢？
 * JDK中提供的类ThreadLocal正是为了解决这个问题。
 * 	类ThreadLocal主要解决的就是变量在不同线程间的隔离性，不同的线程中的值是可以统一放入ThreadLocal类中进行保存。
 * 
 * 关于线程变量的隔离，JavaSE还提供了一个类InheritableThreadLocal，该类可以在子线程中取得父线程中的变量，也可以设置子线程自己的隔离变量。
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
