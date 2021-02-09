import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        // Create thread pool
        ExecutorService application = Executors.newFixedThreadPool(14);

        // Create shared buffer for deposit and withdraw threads to communicate
        Buffer sharedBuffer = new BufferImpl(new Account());

        // Print table format
        System.out.printf( "%-30s%-30s%-30s\n%-30s%-30s%-30s\n",
                "Deposit Threads",
                "Withdrawal Threads",
                "    Balance",
                "-------------------------",
                "------------------------",
                "--------------------------------------------------"
        );

        try {
            // 5 Depositor Threads
            for(int i = 1; i <= 5; i++) {

                // Spin up new Depositor threads
                application.execute(new Depositor("D" + i, sharedBuffer));
            }

            // 9 Withdrawal threads
            for (int i = 1; i <= 9; i++) {

                // Spin up new withdraw threads
                application.execute(new Withdrawal("W" + i, sharedBuffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
