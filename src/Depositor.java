public class Depositor extends Transaction{

    public Depositor(String name, Buffer sharedBuffer) {
        super(name, sharedBuffer);
    }

    @Override
    public void run() {

        try {
            // Will run in infinite loop until user stops application
            while(true) {

                // Put thread to sleep for 25 milliseconds
                Thread.sleep(getRandom().nextInt(25));

                // Random amount between 1$-250$ to deposit into account
                getSharedBuffer().deposit(getRandom().nextInt(250) + 1, this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
