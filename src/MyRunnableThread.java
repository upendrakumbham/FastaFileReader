/**
 * Created by ukumbham on 04/10/2018.
 */
public class MyRunnableThread implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.print("stmts executing by threads...");
    }

    public static void main(String a[]){
        Thread t = new Thread(new MyRunnableThread());
        t.start();
    }
}
