package hust.thread.communication.pr;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个生产者和多个消费者，堆栈List中最多一个元素
 * 
 * 消费者class中的总结。
 * 
 * @author 2016-01-08
 *
 */
public class OnePManyC {
	
	private static List<String> list = new ArrayList<String>();

	public static void main(String[] args) {
		String lock = new String("");
		Producer p = new Producer(lock);
		Consumer c1 = new Consumer(lock);
		Consumer c2 = new Consumer(lock);
		p.setName("p1");
		c1.setName("c1");
		c2.setName("c2");
		p.start();
		c1.start();
		c2.start();
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
						if(list.size() == 1) {
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						list.add(Thread.currentThread().getName() + " product " + System.nanoTime());
						System.out.println(Thread.currentThread().getName() + " product " + System.nanoTime());
						lock.notify();
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
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
