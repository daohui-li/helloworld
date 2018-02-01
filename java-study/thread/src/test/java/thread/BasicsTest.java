package thread;

import org.junit.Test;
import thread.Basics.Friend;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicsTest {
    @Test
    public void testRunThread() throws InterruptedException {
        Thread thread = Basics.runThread("Hello, World!", true);
        assertFalse(thread.isAlive());

        thread = Basics.runThread("Hello, No Blocking", false);
        assertTrue(thread.isAlive());
        thread.join();  // wait until it is done, or the display will not be seen
        assertFalse(thread.isAlive());
    }

    @Test
    public void testDeadLock() throws InterruptedException {
        Friend friend1 = new Friend("John");
        Friend friend2 = new Friend("Larry");

        Thread thread1 = Basics.bow(friend1, friend2);
        Thread.sleep(100);
        assertFalse(thread1.isAlive());

        thread1 = Basics.bow(friend1, friend2);
        Thread thread2 = Basics.bow(friend2, friend1);
        Thread.sleep(100);
        assertTrue(thread1.isAlive());
        assertTrue(thread2.isAlive());  // deadlocked
    }

    @Test
    public void testDeadLockAvoid() throws InterruptedException {
        Basics.FriendWithLock friend1 = new Basics.FriendWithLock("John");
        Basics.FriendWithLock friend2 = new Basics.FriendWithLock("Larry");

        Thread thread1 = Basics.bow(friend1, friend2);
        Thread.sleep(100);
        assertFalse(thread1.isAlive());

        thread1 = Basics.bow(friend1, friend2);
        Thread thread2 = Basics.bow(friend2, friend1);
        Thread.sleep(100);
        assertFalse(thread1.isAlive());
        assertFalse(thread2.isAlive());  // deadloack avoided
    }
}
