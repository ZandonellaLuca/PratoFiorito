package Audio;

import ConsoleColors.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Classe Sound fornita dal docente,
 * serve a riprodurre un file audio .wav in background.
 */
public class Sound {

    private static Clip clip;

    /**
     * Metodo che genera una Clip e gli fornisce un AudioInput,
     * successivamente fa partire la Clip, controllando per eventuali errori.
     *
     * @param fileName il percorso + nome del file.
     */
    public static synchronized void play(final String fileName)
    {
        // Note: use .wav files
        new Thread(new Runnable() {
            public void run() {
                try {
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
                    clip.open(inputStream);
                    clip.start();
                }catch (Exception e) {
                    System.out.println(
                            Color.RED + "Play sound error: " +
                            e.getMessage() + " for " + fileName + Color.RESET
                    );
                }
            }
        }).start();
    }

    /**
     * Metodo che sfrutta lo stesso Clip precedente per fermare,
     * l'audio in background controllando per un eventuale errore.
     */
    public static synchronized void stop() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    clip.stop();
                } catch (Exception e) {
                    System.out.println(
                            Color.RED + "Stop sound error: " +
                            e.getMessage() + Color.RESET
                    );
                }
            }
        }).start();
    }
}