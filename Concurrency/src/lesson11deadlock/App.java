package lesson11deadlock;


public class App {

	public static void main(String[] args) {
		final Runner runner = new Runner();
		
		Thread t1 = new Thread(()->runner.firstThread());
		Thread t2 = new Thread(()->runner.secondThread());
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runner.finished();
		
		

	}

}
