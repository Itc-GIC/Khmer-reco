package edu.itc.sphinx.demo;

import edu.itc.sphinx.*;

/**
 * The Demo.
 * <p/>
 * <pre><code>
 * public static void main(String[] args){                   
 *     new Demo(
 *         new AudioFormatPrinter(), 
 *         new AudioPlayer()
 *     ).run();
 * }
 * </code></pre>
 */
public class Demo {
    
    private IDevice[] devices;
    
    /**
     * Instantiate Demo with devices.
     * @param devices 
     */
    public Demo(IDevice... devices){
        this.devices = devices;
    }
    
    /**
     * Run the demo.
     */
    public void run() {
        run("resource/tmp_file_dir");
    }
    
    /**
     * Run the demo and specify the location of the wav files
     * @param location of wav files
     */
    public void run(String location) {
        AudioManager manager = new AudioManager(location, "*.wav");
        for(IDevice device : devices) {
            manager.addDevice(device);
        }
        
        manager.run();
    }
}
