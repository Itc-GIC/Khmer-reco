package edu.itc.sphinx;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFormatPrinter implements IDevice {
    
    private final String FORMAT = "%35s: %s\n";

    @Override
    public void play(File file) {
        try {
            System.out.printf(FORMAT, 
                    file.getName(), getFormat(file));

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get audioFormat
     * @param file
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private AudioFormat getFormat(File file) throws UnsupportedAudioFileException, IOException {
        return AudioSystem
                .getAudioFileFormat(file)
                .getFormat();
    }
}
