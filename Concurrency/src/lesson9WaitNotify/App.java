package lesson9WaitNotify;

import lesson8WaitNotify.Producer;

public class App {

	public static void main(String[] args) throws Exception {
		Processor processor = new Processor();

		Thread t1 = new Thread(()->processor.produce());
		Thread t2 = new Thread(()->processor.consume());

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println("Done");
	}

}
