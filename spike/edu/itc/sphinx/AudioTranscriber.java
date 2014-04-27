package edu.itc.sphinx;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class AudioTranscriber extends DeviceBase {
    
    private ConfigurationManager config;
    private Recognizer recognizer;
    private AudioFileDataSource dataSource;
    private AudioFormat audioFormat;
    
    public AudioTranscriber() {
        this("/recognition/config_with_language_grammar.xml");
    }
    
    /**
     * Create audioTranscriber by filename
     * @param filename
     */
    public AudioTranscriber(String filename) {
        this(AudioTranscriber.class.getResource(filename));
    }
    
    /**
     * Create audioTranscriber by URL
     * @param url
     */
    public AudioTranscriber(URL url) {
        config = new ConfigurationManager(url);
        
        recognizer = (Recognizer) config.lookup("recognizer");
        dataSource = (AudioFileDataSource) config.lookup("audioFileDataSource");
        recognizer.allocate();        
    }

    @Override
    public void play(File file) {
        AudioInputStream inputStream = null;
        try {
            inputStream = getInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
            
        dataSource.setInputStream(inputStream, null);
        System.out.println("  Result: " + getResultHypothesis());
    }
    
    /**
     * Get result hypothesis as <code>String</code>
     * @return
     */
    private String getResultHypothesis() {
        StringBuffer resultText = new StringBuffer();
        Result result;
        
        while((result = recognizer.recognize()) != null) {
            resultText
                .append(' ')
                .append(result.getBestFinalResultNoFiller());
        }
        return resultText.toString();
    }
    
    /**
     * Get audio input stream from <code>audioFile</code>
     * @param audioFile
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private AudioInputStream getInputStream(File audioFile) throws UnsupportedAudioFileException, IOException {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
        return AudioSystem.getAudioInputStream(getAudioFormat(), inputStream);
    }
    
    /**
     * Get default audio format
     * @return audioFormat
     */
    public AudioFormat getAudioFormat() {
        if(audioFormat == null) {
            audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,    // encoding
                16000.0F,                           // sampleRate
                16,                                 // sampleSizeInBits
                1,                                  // channels (stereo)
                2,                                  // frameSize
                16000.0F,                           // frameRate
                false                               // little-endian
            );
        }
        return audioFormat;
    }
    
    @Override
    public void deallocate() {
        recognizer.deallocate();
    }
}
