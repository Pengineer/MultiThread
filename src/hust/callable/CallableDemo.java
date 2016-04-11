package hust.callable;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 任务执行类
 * 
 * Runnable在执行任务后不会返回任何结果，如果要返回结果，应该实现Callable接口而不是Runnable接口。
 * 实现Callable接口的类需要覆写call()方法，该方法的返回值为Object类型。实现Callable接口 的类要执行
 * 任务必须通过ExecutorService.submit(Callable)方法，该方法会返回一个Future对象：
 * public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }
 * 从源码可以看出，submit方法将传递进来的任务进行了封装得到RunnableFuture对象，该接口继承了Runnable，
 * 这样就保证了任务可以被线程执行。
 * 
 * Future是Java引进的异步执行框架，Future接口既提供了同步方法，也提供了异步方法。
 * 
 * @author liangjian
 *
 */
public class CallableDemo {

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
		for(int i=1; i<7; i++) {
			Future<Integer> future = exec.submit(new Task(i));
			results.add(future);
		}
		
		/*
		 * 输出线程执行结果
		 * f.isDone()是异步计算
		 * f.get()是同步计算，在得到结果之前会产生阻塞
		 */
		for(Future<Integer> f : results) {
			System.out.println("isDone: " + f.isDone() + "  " + f.get());
		}
	}
	
}
