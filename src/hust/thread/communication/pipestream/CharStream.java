package hust.thread.communication.pipestream;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 		在Java语言中提供了各种各样的输入/输出流Stream，使得我们很方便的对数据进行操作，其中管道流（pipeStream）是一种特殊的流，用于在
 * 不同的线程之间直接传输数据。一个线程发送数据到输出管道，另一个线程从输入管道中读数据。这种线程间的数据传送方式就不涉及到像wait/notify
 * 的同步问题（不需要强制写在同步代码块/同步方法里面）。
 * 		在JavaJDK中提供了4个类来方便线程间通信：
 * 		（1）字节流：PipedaReader 和 PipedWriter
 * 		（2）字符流：PipedReader 和 PipedWriter
 * 
 * @author 2016-01-09
 *
 */
public class CharStream {

	public static void main(String[] args) {
		
		try {
			PipedWriter writer = new PipedWriter();
			PipedReader reader = new PipedReader();
			writer.connect(reader); // 将输入输出管道流直接连接起来（这样就省去了中间临时文件的创建）
//			reader.connect(writer); // 上下两种都可以
			
			WriterClass wc = new WriterClass(writer);
			ReaderClass rc = new ReaderClass(reader);
			wc.start();
			Thread.sleep(1000);
			rc.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class WriterClass extends Thread {
		private PipedWriter writer = null;
		public WriterClass(PipedWriter writer) {
			this.writer = writer;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("write start.....");
				for(int i=0; i<10; i++) {
					writer.write("_" + i);
				}
				System.out.println("write end.....");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class ReaderClass extends Thread {
		private PipedReader reader = null;
		public ReaderClass(PipedReader reader) {
			this.reader = reader;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("read start.....");
				char[] buf = new char[1024];
				int len =0;
				while((len=reader.read(buf)) != -1) {
					System.out.println("read " + new String(buf, 0, len));
				}
				System.out.println("read end.....");
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
