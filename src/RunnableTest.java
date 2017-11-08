
public class RunnableTest {
	public static void main(String[] args) {
		//Æô¶¯Ïß³Ì
		Runner runner = new Runner();
		Thread thread = new Thread(runner);
		thread.start();
		loop();
	}
	public static void loop() {
		for(int i = 0 ;i<10;i++){
			System.out.println("main "+i);
		}
	}
}

class Runner implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		for(int i = 0 ;i<10;i++){
			System.out.println("Runner "+i);
		}
	}
	
}
/*
 * 
main 0
main 1
main 2
Runner 1
Runner 2
Runner 3
Runner 4
main 3
Runner 5
Runner 6
main 4
Runner 7
Runner 8
main 5
Runner 9
main 6
main 7
main 8
main 9*/
