package music.internal;

import music.Hello;
import org.junit.Test;

public class TestHello {
    Hello hello = new Hello();

    @Test
    public void playBach() {
        hello.playBachInventio13();
    }

    @Test
    public void playMusicString() {
        hello.playMusicString();
    }

    @Test
    public void playPercussion() {
        hello.playPercussion();
    }

    @Test
    public void playPercussion2() {
        hello.playPercussion2();
    }
}
