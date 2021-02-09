import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferImpl implements Buffer{

    // User bank account
    private Account bankAccount;

    // Used to lock access to data to ensure synchronization
    private Lock accessLock = new ReentrantLock();

    // Condition keeping track of lock
    private Condition canWithdraw = accessLock.newCondition();

    // Inject bank account into constructor
    public BufferImpl(Account bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void withdraw(int amount, Transaction transaction) {

        // Accessing account, therefore lock until process is finished
        accessLock.lock();

        if (bankAccount.getBalance() < amount) {
            try {
                // Print transaction was blocked
                System.out.printf( "%-30s%-30s%-30s\n",
                        "",
                        "Thread " + transaction.getName() + " withdraws $" + amount,
                        "(******) WITHDRAW BLOCKED - INSUFFICIENT FUNDS!!!"
                );
                while(bankAccount.getBalance() < amount) {

                    // Not enough money in account to make withdraw, wait until more money has been deposited
                    canWithdraw.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Make withdraw
        bankAccount.withdraw(amount);

        // Print formatted transaction
        System.out.printf( "%-30s%-30s%-30s\n",
                "",
                "Thread " + transaction.getName() + " withdraws $" + amount,
                "(-)  Balance is $" + bankAccount.getBalance()
        );

        // Process finished, therefore unlock access to account's data
        accessLock.unlock();
    }

    @Override
    public void deposit(int amount, Transaction transaction) {

        // Accessing account, therefore lock until process is finished
        accessLock.lock();

        // Make deposit
        bankAccount.deposit(amount);

        // Print formatted transaction
        System.out.printf( "%-30s%-30s%-30s\n",
                "Thread " + transaction.getName() + " deposits $" + amount,
                "",
                "(+)  Balance is $" + bankAccount.getBalance());

        // Notify awaiting threads money has been deposited, withdraw can take place now
        canWithdraw.signal();

        // Process finished, therefore unlock access to account's data
        accessLock.unlock();
    }
}
