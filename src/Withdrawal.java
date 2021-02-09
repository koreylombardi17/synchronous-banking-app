public class Withdrawal extends Transaction{

    public Withdrawal(String name, Buffer sharedBuffer) {
        super(name, sharedBuffer);
    }

    @Override
    public void run() {

        try {
            // Will run in infinite loop until user stops application
            while(true) {

                // Put thread to sleep for 2 milliseconds
                Thread.sleep(getRandom().nextInt(2));

                // Random amount between 1$-50$ to deposit into account
                getSharedBuffer().withdraw(getRandom().nextInt(50) + 1, this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
