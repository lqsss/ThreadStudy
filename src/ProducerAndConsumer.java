/*public class ProducerAndConsumer {  
    public static void main(String[] args) {  
        ProductList pl = new ProductList();  
        Factory f = new Factory(pl);  
        Consumer c = new Consumer(pl);  
  
        Thread t1 = new Thread(f);  
        Thread t2 = new Thread(c);  
  
        t1.start();  
        t2.start();  
  
    }  
}  
  
class Product {  
    private int id;  
  
    Product(int id) {  
        this.id = id;  
    }  
  
    @Override  
    public String toString() {  
        return "Product [id=" + id + "]";  
    }  
  
}  
  
class ProductList {  
    int index = 0;  
    private Product[] p = new Product[6];  
  
    public synchronized void push(Product pr) {  
  
        while (index == p.length) {  
            try {  
                this.wait();  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        this.notify();  
        p[index] = pr;  
        System.out.println("生产了" + p[index]);
        index++;  
  
    }  
  
    public synchronized Product pop() {  
  
        while (index == 0) {  
            try {  
                this.wait();  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        this.notify();  
        index--;  
        System.out.println("消费了" + p[index]);
        return p[index];  
  
    }  
}  
  
class Factory implements Runnable {  
  
    private ProductList pl = null;  
  
    Factory(ProductList pl) {  
        this.pl = pl;  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        for (int i = 0; i < 20; i++) {  
            Product p = new Product(i);  
            pl.push(p);  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
  
    }  
}  
  
class Consumer implements Runnable {  
    private ProductList pl = null;  
  
    Consumer(ProductList pl) {  
        this.pl = pl;  
    }  
  
    @Override  
    public void run() {  
        // TODO Auto-generated method stub  
        for (int i = 0; i < 20; i++) {  
            Product p = pl.pop();  
            try {  
                Thread.sleep(2000);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
  
    }  
}  */