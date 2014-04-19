package edu.itc.sphinx.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFormatDemo {

    final String AUDIO_DIR = "resource/tmp_file_dir";
    final String FORMAT = "%35s: %s\n";
    
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException{
        new AudioFormatDemo().run();
    } 

    /**
     * Application runner
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public void run() throws IOException, UnsupportedAudioFileException {
        DirectoryStream<Path> ds = getDS();
        for(Path path : ds) {
            printAudioFormat(path.toFile());
        }
    }
    
    /**
     * Print audio file format
     * @param file
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private void printAudioFormat(File file) throws UnsupportedAudioFileException, IOException {
        AudioFormat audioFormat = 
                AudioSystem.getAudioFileFormat(file).getFormat();
        System.out.printf(FORMAT, file.getName(), audioFormat);
    }

    /**
     * Get directory stream path of audio files
     * @return directoryStream object
     * @throws IOException
     */
    private DirectoryStream<Path> getDS() throws IOException {
        Path directory = FileSystems.getDefault().getPath(AUDIO_DIR);
        return Files.newDirectoryStream(directory, "*.wav");
    }
}
