import java.util.Date;


public class YeildTest {
	public static void main(String[] args) {
		Runner5 first = new Runner5("First thread!");
		Runner5 second = new Runner5("Second thread!");
		first.start();second.start();
		
	}
}

class Runner5 extends Thread{
	Runner5(String name){
		super(name);
	}
	public void run() {
		for(int i = 0 ; i<= 20; i++){
			System.out.println(getName()+" "+i);
			if(i%10==0){
				yield();
			}
		}
	}
}