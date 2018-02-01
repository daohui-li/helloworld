package music;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

import java.util.Arrays;
import java.util.HashMap;

public class Hello {

    private Player player;

    public Hello() {
        player = new Player();
    }

    public void playScale() {
        // letter without number default to 5
        player.play("C4 D4 E4 F4 G4 A4 B4 C D E F G A B C6 D6 E6 F6 G6 A6 B6 C7w");
    }

    public void playCello() {
        // V: voice
        // I: instrument
        // |: indicating measures
        // duration: 'q' quarter, 'qq' half note (or 'h'), 'w' whole note
        //   '.' for dotted duration, 'R' for rest
        Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq .R");
        Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ .R");
        player.play(p1, p2);
    }

    public void playBachInventio13() {
        Pattern p = new Pattern("E5s A5s C6s B5s E5s B5s D6s C6i E6i G#5i\n" +
                "E6i | A5s E5s A5s C6s B5s E5s B5s D6s C6i A5i Ri");
        player.play(p);
    }

    public void playMusicString() {
        // T: Tempo, Followed by beats per minutes or a predefined Constant
        // V: voice track; V9 is for percussion
        // I: Instrument
        // [A-G], music note; [#bn], sharp, flat, natural; octave
        // [whqistxo]: duration, whole, half, quarter, etc
        // '+': indicates the notes are played together
        // Root note plus (maj|min|aug) plus duration; inverse begins with '^'
        // K: key signature; e.g., KAbmin
        Pattern p = new Pattern("T[Adagio] V0 I[Piano] C5q F#5q\n" +
                "CmajQ V1 I[Flute] C3q+E3q E3q+G3q Ri\n" +
                "C2majI");
        player.play(p);
    }

    public void playPercussion() {
        Rhythm rhythm = new Rhythm();
        rhythm.setLayers(Arrays.asList(
                "O..oO...O..oOO..",
                "..*...*...*...*.",
                "^^^^^^^^^^^^^^^^",
                "...............!"));
        HashMap<Character, String> rhythmKit = new HashMap<>();
        rhythmKit.put('O', "[BASS_DRUM]i");
        rhythmKit.put('o', "Rs [BASS_DRUM]s");
        rhythmKit.put('*', "[ACOUSTIC_SNARE]i");
        rhythmKit.put('^', "[PEDAL_HI_HAT]s Rs");
        rhythmKit.put('!', "[CRASH_CYMBAL_1]s Rs");
        rhythmKit.put('.', "Ri");
        rhythm.setRhythmKit(rhythmKit);

        Pattern pattern = rhythm.getPattern();
        pattern.repeat(3);
        pattern.setTempo(100);
        player.play(pattern);
    }

    public void playPercussion2() {
        Rhythm rhythm = new Rhythm()
                .addLayer("O..oO...O..oOO..") // This is Layer 0
                .addLayer("..S...S...S...S.")
                .addLayer("````````````````")
                .addLayer("...............+") // This is Layer 3
                .addOneTimeAltLayer(3, 3, "...+...+...+...+") // Replace Layer 3 with this string on the 4th (count from 0) measure
                .setLength(4); // Set the length of the rhythm to 4 measures
        // use default rhythm symbol mapping
        player.play(rhythm.getPattern().repeat(2)); // Play 2 instances of the 4-measure-long rhythm
    }
}

