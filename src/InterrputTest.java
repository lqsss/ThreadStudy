import java.util.Date;


public class InterrputTest {
	public static void main(String[] args) {
		Runner3 runner3 = new Runner3();
		runner3.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runner3.interrupt();
	}
}

class Runner3 extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println(new Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("kill");
				e.printStackTrace();
				return;
			}
		}
	}
}
