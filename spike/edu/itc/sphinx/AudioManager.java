package edu.itc.sphinx;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AudioManager {
    private String location;
    private String glob;
    private List<IDevice> devices = new ArrayList<IDevice>();
    
    /**
     * Create audioManager
     */
    public AudioManager() {
        this(".", "*.wav");
    }
    
    /**
     * Create audioManager by given location and glob
     * @param location
     * @param glob
     */
    public AudioManager(String location, String glob) {
        this.location = location;
        this.glob = glob;
    }
    
    /**
     * Add device to play during running phase
     * @param device
     */
    public void addDevice(IDevice device) {
        devices.add(device);
    }

    /**
     * Run all devices against found audio files
     */
    public void run() {
        try {

            for(Path path : getDS()) {
                for(IDevice device : this.devices) {
                    device.play(path.toFile());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get directoryStream from location and glob
     * @return DirectoryStream
     * @throws IOException
     */
    private DirectoryStream<Path> getDS() throws IOException {
        return Files
                .newDirectoryStream(getPath(), this.glob);
    }
    
    /**
     * Get location path
     * @return Path
     */
    private Path getPath() {
        return FileSystems
                .getDefault()
                .getPath(this.location);
    }
}
