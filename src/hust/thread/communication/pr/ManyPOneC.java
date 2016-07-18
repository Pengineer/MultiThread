package hust.thread.communication.pr;

import java.util.ArrayList;
import java.util.List;

/**
 * 多个生产者和一个消费者，堆栈List中最多一个元素
 * 
 * @author 2016-01-08
 *
 */
public class ManyPOneC {
	
	private static List<String> list = new ArrayList<String>();

	public static void main(String[] args) {
		String lock = new String("");
		Producer p1 = new Producer(lock);
		Producer p2 = new Producer(lock);
		Consumerr c1 = new Consumerr(lock);
		p1.setName("p1");
		p2.setName("p2");
		c1.setName("c1");
		p1.start();
		p2.start();
		c1.start();
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
						while(list.size() == 1) {
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						list.add(Thread.currentThread().getName() + " product " + System.nanoTime());
						System.out.println(Thread.currentThread().getName() + " product " + System.nanoTime());
						lock.notifyAll();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//消费者
	static class Consumerr extends Thread {
		public String lock;
		public Consumerr(String lock) {
			this.lock = lock;
		}
		@Override
		public void run() {
			while(true) {
				try {
					synchronized(lock) {
						if(list.size() == 0) { 
							System.out.println(Thread.currentThread().getName() + " wait");
							lock.wait();
						}
						list.remove(0);
						System.out.println(Thread.currentThread().getName() + " consume " + System.nanoTime());
						lock.notify();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
