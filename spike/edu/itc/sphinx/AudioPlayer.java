package edu.itc.sphinx;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer implements IDevice {
    
    private final int BUFFER_SIZE = 128000;
    
    private AudioInputStream stream = null;
    private SourceDataLine playbackLine = null;
    

    @Override
    public void play(File file) {
        try {
            startStreaming(file);
            
            int read = 0;
            byte[] data = new byte[BUFFER_SIZE];
            
            while(read != -1) {
                read = stream.read(data, 0, data.length);
                if(read > 0) {
                    playbackLine.write(data, 0, read);
                }
            }
            
            finishStreaming();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Start streaming file
     * @param file
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void startStreaming(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stream = AudioSystem.getAudioInputStream(file);
        playbackLine = getDataLine();

        playbackLine.open();
        playbackLine.start();
    }
    
    /**
     * Get dataline for playback
     * @return
     * @throws LineUnavailableException
     */
    private SourceDataLine getDataLine() throws LineUnavailableException {
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        return (SourceDataLine) AudioSystem.getLine(info);
    }
    
    /**
     * Finish streaming and close the playback dataline
     */
    private void finishStreaming() {
        playbackLine.drain();
        playbackLine.close();
    }
}
