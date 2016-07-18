package hust.thread.communication.pr;

import java.util.ArrayList;
import java.util.List;

/**
 * 多个生产者和多个消费者，堆栈List中最多3个元素
 * 
 * @author 2016-01-08
 *
 */
public class ManyPManyC_Update {
	
	private static List<String> list = new ArrayList<String>();

	public static void main(String[] args) {
		String lock = new String("Lock");
		Producer p1 = new Producer(lock);
		Producer p2 = new Producer(lock);
		Producer p3 = new Producer(lock);
		Producer p4 = new Producer(lock);
		Consumer c1 = new Consumer(lock);
		Consumer c2 = new Consumer(lock);
		Consumer c3 = new Consumer(lock);
		Consumer c4 = new Consumer(lock);
		p1.setName("p1");
		p2.setName("p2");
		p3.setName("p3");
		p4.setName("p4");
		c1.setName("c1");
		c2.setName("c2");
		c3.setName("c3");
		c4.setName("c4");
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
	}
	
	//生产者
	static class Producer extends Thread {
		public String lock;
		public Producer(String lock) {
			this.lock = lock;
		}
		@Override
		public void run() {
			while(true) {
				try {
					synchronized(lock) {
						while(list.size() == 3) {
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						list.add(Thread.currentThread().getName() + " product " + System.nanoTime());
						System.out.println(Thread.currentThread().getName() + " product " + System.nanoTime());
						lock.notifyAll();
						Thread.sleep(1000); //发现如果不延时一会的话，总是一个线程一次性生产3个对象。延时也符合实际情况。
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//消费者
	static class Consumer extends Thread {
		public String lock;
		public Consumer(String lock) {
			this.lock = lock;
		}
		@Override
		public void run() {
			while(true) {
				try {
					synchronized(lock) {
						/*
						 * 此处必须是while，不能是if：假设是if，那么C1被唤醒消费后，如果notify唤醒的是C2，那么C2就会跳出if执行remove，
						 * 这样就会出现IndexOutOfBoundsException异常。
						 * 经验：
						 * 生产和消费多的一方使用while，少的一方while和if均可。
						 */
						while(list.size() == 0) { 
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						list.remove(0);
						System.out.println(Thread.currentThread().getName() + " consume " + System.nanoTime());
						lock.notifyAll(); //多的一方必须是notifyAll，少的一方notify和notifyAll均可
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
