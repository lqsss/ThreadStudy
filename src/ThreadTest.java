
public class ThreadTest {
	public static void main(String[] args) {
		//Æô¶¯Ïß³Ì
		Runner1 runner1 = new Runner1();
		runner1.start();
		loop();
	}
	public static void loop() {
		for(int i = 0 ;i<10;i++){
			System.out.println("main "+i);
		}
	}
}

class Runner1 extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0 ;i<10;i++){
			System.out.println("Runner1 "+i);
		}
	}
	
}
/* main 0
main 1
Runner1 0
main 2
main 3
main 4
main 5
main 6
main 7
main 8
Runner1 1
Runner1 2
main 9
Runner1 3
Runner1 4
Runner1 5
Runner1 6
Runner1 7
Runner1 8
Runner1 9
*/