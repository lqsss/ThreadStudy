
public class OrtherTest implements Runnable{
	int b = 1000;
	public static void main(String[] args) {
		OrtherTest test = new OrtherTest();
		Thread thread = new Thread(test);
		thread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("1 " +test.b);
		test.method();
		System.out.println("2 "+test.b);
		
	}
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub

			b = 2000;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(b + "run()");
		
	}
	
	public synchronized void  method() {
		b = 3000;
		System.out.println(b +" method()");
	}
	
	
}
