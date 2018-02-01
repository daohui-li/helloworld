package music;

import org.junit.Test;

public class TestHello {
    Hello hello = new Hello();

    @Test
    public void playScale() {
        hello.playScale();
    }

    @Test
    public void playCello() {
        hello.playCello();
    }
}
