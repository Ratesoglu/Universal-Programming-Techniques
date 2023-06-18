import java.util.ArrayList;

public class Letters extends ArrayList<Thread> {
    Letters(String value)
    {
        for(int a =0;a <value.length(); a++)
        {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true)
                    {
                        System.out.print(Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            });
            thread.setName(String.valueOf(value.charAt(a)));
            this.add(thread);
        }
    }
    public void start()
    {
        this.forEach(Thread::start);
    }
    public void stop()
    {
        this.forEach(e->{
            e.interrupt();
        });
    }
    public static void main(String[] args) {
        Letters letters = new Letters("ABCD");
        for (Thread t : letters)
            System.out.println(t.getName() + " starting");letters.start();
        try { Thread.sleep(5000); }catch(InterruptedException ignore) { }
        letters.stop();System.out.println("\nProgram completed.");
    }
}
