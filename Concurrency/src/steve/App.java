package steve;

public class App {

	public static void main(String[] args)
	{
		Runner runner = new Runner();
		
		Thread t1 = new Thread(()->runner.produce());
		Thread t2 = new Thread(()->runner.consume());
		
		t1.start();
		t2.start();
		
	}
}
