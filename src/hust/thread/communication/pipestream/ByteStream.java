package hust.thread.communication.pipestream;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 		在Java语言中提供了各种各样的输入/输出流Stream，使得我们很方便的对数据进行操作，其中管道流（pipeStream）是一种特殊的流，用于在
 * 不同的线程之间直接传输数据。一个线程发送数据到输出管道，另一个线程从输入管道中读数据。这种线程间的数据传送方式就不涉及到像wait/notify
 * 的同步问题（不需要强制写在同步代码块/同步方法里面）。
 * 		在JavaJDK中提供了4个类来方便线程间通信：
 * 		（1）字节流：PipedInputStream 和 PipedOutputStream
 * 		（2）字符流：PipedReader 和 PipedWriter
 * 
 * @author 2016-01-09
 *
 */
public class ByteStream {

	public static void main(String[] args) {
		
		try {
			PipedOutputStream outputStream = new PipedOutputStream();
			PipedInputStream inputStream = new PipedInputStream();
			outputStream.connect(inputStream); // 将输入输出管道流直接连接起来（这样就省去了中间临时文件的创建）
//			inputStream.connect(outputStream); // 上下两种都可以
			
			OutputClass wo = new OutputClass(outputStream);
			InputClass rin = new InputClass(inputStream);
			wo.start();
			Thread.sleep(1000);
			rin.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class OutputClass extends Thread {
		private PipedOutputStream outputStream = null;
		public OutputClass(PipedOutputStream outputStream) {
			this.outputStream = outputStream;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("write start.....");
				for(int i=0; i<10; i++) {
					outputStream.write(("_" + i).getBytes());
				}
				System.out.println("write end.....");
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class InputClass extends Thread {
		private PipedInputStream inputStream = null;
		public InputClass(PipedInputStream inputStream) {
			this.inputStream = inputStream;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("read start.....");
				byte[] buf = new byte[1024];
				int len =0;
				while((len=inputStream.read(buf)) != -1) {
					System.out.println("read " + new String(buf, 0, len));
				}
				System.out.println("read end.....");
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
