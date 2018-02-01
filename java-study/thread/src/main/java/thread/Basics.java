package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Basics {
    public static Thread runThread(final String text, boolean blocked) {
        Thread thread = new Thread(() -> System.out.println(text));
        thread.start();
        if (blocked) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return thread;
    }

    public static Thread bow(Friend friend1, Friend friend2) {
        Thread thread = new Thread(() -> { friend1.bow(friend2);});
        thread.start();

        return thread;
    }

    public static Thread bow(FriendWithLock friend1, FriendWithLock friend2) {
        Thread thread = new Thread(() -> { friend1.bow(friend2);});
        thread.start();

        return thread;
    }

    public static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void bow(Friend other) {
            System.out.println(name + " bows to " + other.name);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            other.bowBack(this);  // other bows back
        }

        public synchronized void bowBack(Friend other) {
            System.out.println(name + " bows back to " + other.name);
        }
    }

    public static class FriendWithLock {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public FriendWithLock(String name) {
            this.name = name;
        }

        public void bow(FriendWithLock other) {
            if (acquireBothLocks(other)) {
                try {
                    System.out.println(name + " bows to " + other.name);
                    try {  // intentionally add delay to mimic lock conflict
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                    other.bowBack(this);
                } finally {
                    lock.unlock();
                    other.lock.unlock();
                }
            } else {
                System.out.println(name + " is unable to bow " + other.name + "! Is " + other.name + " bowing to me?");
                System.out.println(name + " cancels the bow action");
            }
        }

        private void bowBack(FriendWithLock other) {
            System.out.println(name + " bows back to " + other.name);
        }

        private boolean acquireBothLocks(FriendWithLock other) {
            boolean myLocked = false;
            boolean otherLocked = false;
            boolean rc;
            try {  // acquire both locks
                myLocked = lock.tryLock();
                otherLocked = other.lock.tryLock();
            } finally {
                rc = myLocked && otherLocked;
                if (!rc) {  // unlock if both cannot acquired
                    if (myLocked) {
                        lock.unlock();
                    } else if (otherLocked){
                        other.lock.unlock();
                    }
                }
            }
            return rc;
        }
    }
}
