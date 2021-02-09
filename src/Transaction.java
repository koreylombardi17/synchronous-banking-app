import java.util.Random;

public abstract class Transaction implements Runnable{

    private String name;
    private Random random = new Random();
    private Buffer sharedBuffer;

    public Transaction(String name, Buffer sharedBuffer) {
        this.name = name;
        this.sharedBuffer = sharedBuffer;
    }

    public String getName() {
        return name;
    }

    public Random getRandom() {
        return random;
    }

    public Buffer getSharedBuffer() {
        return sharedBuffer;
    }
}
