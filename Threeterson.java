import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Threeterson implements Lock {
    private final ReentrantLock printLock;
    private final Output output;



    private final int[] level = {-1, -1, -1};
    private final int[] victim = {-1, -1, -1};

    public Threeterson(Output output) {
        this.printLock = new ReentrantLock();
        this.output = output;
    
        
    }
    
    @Override
    public void lock() {
        int id = getThreadId();
        for (int l = 0; l < 3; l++) {
            printLock.lock();
            try {
                level[id] = l;
                output.println(Thread.currentThread().getName() + " at L" + l);
                victim[l] = id;
                output.println(Thread.currentThread().getName() + " is the victim of L" + l);
            } finally {
                printLock.unlock();
            }
            // Busy-wait loop with yielding to avoid excessive CPU usage
            int backoff = 1;
            while (thereExists(id, l) && victim[l] == id) {
                try {
                    TimeUnit.MILLISECONDS.sleep(backoff + (int) (Math.random() * backoff));
                    backoff = Math.min(1000, backoff * 2 + (int) (Math.random() * 10)); // Added slight randomness
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    
        printLock.lock();
        try {
            output.println(Thread.currentThread().getName() + " has the lock");
        } finally {
            printLock.unlock();
        }
    }
    
    @Override
    public void unlock() {
        int id = getThreadId();
    
        // Ensure logging is thread-safe
        printLock.lock();
        try {
            level[id] = -1;
            output.println(Thread.currentThread().getName() + " unlocked the lock");
        } finally {
            printLock.unlock();
        }
    }
    
    private int getThreadId() {
        return Integer.parseInt(Thread.currentThread().getName().split("-")[1] )%3;
    }
    
    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private boolean thereExists(int id, int l) {
        for (int i = 0; i < 3; i++) {
            if (i != id && level[i] >= l && victim[l] == id) {
                return true;
            }
        }
        return false;
    }
}