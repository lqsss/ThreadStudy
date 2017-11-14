package ThreadCollaboration;


/**
 * wait+notify实现线程协作 简单模型：一家餐厅，服务员（消费者）必须等待厨师（生产者）完成一道菜，才可以端走
 * 
 * @author liqiushi
 * 
 */

class Meal{
	private int count;
	private int orderId;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String toString(){
		return orderId+ " ";
	}
}
/**
 * 服务生
 * 
 * @author liqiushi
 * 
 */

class Waiter implements Runnable {
	private Restaurant restaurant = null;

	public Waiter(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {		
		try {
			while(!Thread.interrupted()){
				while(restaurant.getMeal() == null){
					this.wait(); //等待chef
				}
				System.out.println("服务生已经端走菜："+ restaurant.getMeal());
			}
		} catch (Exception e) {
			
		}
	
	}

}

public class Restaurant {
	private Waiter waiter = new Waiter(this);
	private Meal meal = null;

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Meal getMeal() {
		return meal;
	}

	public static void main(String[] args) {

	}
}
