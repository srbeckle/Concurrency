package lesson3synchronizedKeyword;

public class App {

	private int count = 0;
	
	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		App app = new App();
		app.doWork();
	}
	
	public synchronized void update()
	{
		count++;
	}
	
	public void doWork()
	{
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++)
				{
					update();
				}
			}});
		
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++)
				{
					update();
				}
			}});
		
		t1.start();
		t2.start();
		
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Count = " + count);
	}

}
