package lesson8WaitNotify;

import java.util.Scanner;

public class Producer {
	
	
	public void produce()
	{
		synchronized(this)
		{
			System.out.println("Producer thread running....");
			try {
				wait();
				System.out.println("Resumed producer....");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void consume()
	{
		Scanner scanner = new Scanner(System.in);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized (this) {
			System.out.println("Waiting for return key....");
			scanner.nextLine();
			System.out.println("Return key pressed....");
			notify();
		}
	}
}
