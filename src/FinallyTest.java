import java.util.concurrent.TimeUnit;

public class FinallyTest {

	public static void main(String[] args) {
		try {

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			System.out.println("test");
		}
	}
}
