// Bank account entity
public class Account {

    // Account balance (whole dollars)
    private int balance;

    public Account() {
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount){
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }
}
