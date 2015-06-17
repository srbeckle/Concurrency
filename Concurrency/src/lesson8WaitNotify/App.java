package lesson8WaitNotify;

public class App {

	public static void main(String[] args) throws InterruptedException {
		Producer producer = new Producer();
		
		Thread t1 = new Thread(()->producer.produce());
		Thread t2 = new Thread(()->producer.consume());
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Done");

	}

}
