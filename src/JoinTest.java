import java.util.Date;


public class JoinTest {
	public static void main(String[] args) {
		Runner4 runner4 = new Runner4();
		runner4.start();
		try {
			runner4.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ;i < 10;i++){
			System.out.println("main thread: "+new Date());
		}
	}

}

class Runner4 extends Thread {
	public void run(){
		for(int i = 0 ;i < 10;i++){
			System.out.println("runner4 thread: "+new Date());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}
}
