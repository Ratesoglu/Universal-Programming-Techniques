public class Main {

    public static void main(String[] args) throws InterruptedException{
        StringTask task = new StringTask("A", 70000);
        System.out.println("Task " + task.getState());
        task.start();
        if (args.length > 0 && args[0].equals("abort")) {
          Thread.sleep(1000);
          task.abort();

        }
        while (!task.isDone()) {
            Thread.sleep(500);
            switch(task.getState()) {
                case RUNNING:
                    System.out.print("R.");
                    break;
                case ABORTED:
                    System.out.println(" ... aborted.");
                    break;
                case READY:
                    System.out.println(" ... ready.");
                    break;
                default:
                    System.out.println("unknown state");
            }
        }
        System.out.println("Task " + task.getState());
        System.out.println(task.getResult().length());
    }
}
class StringTask implements Runnable {

    public enum TaskState {
        CREATED, RUNNING, ABORTED, READY
    }
    public TaskState myState;
    public String text;
    public String resultText;
    public Thread myThread;
    public int operations;
    public StringTask() {
        myState = TaskState.CREATED;
    }
    public StringTask(String a, int i) {
        this();
        text = a;
        operations = i;
        resultText = "";
    }
    public boolean isDone() {
        if (myState == TaskState.READY || myState == TaskState.ABORTED) return true;
        return false;
    }
    public void abort() {
        while (operations != 0) {
            resultText += text;
            operations--;
        }
    }
    @Override
    public void run() {
        while (operations != 0) {
            resultText += text;
            operations--;
        }
        myState = TaskState.READY;
    }
    public TaskState getState() {
        return myState;
    }
    public String getResult() {
        return resultText;
    }
    public void start() {
        myThread = new Thread(this);
        myState = TaskState.RUNNING;
        myThread.start();

    }
}
