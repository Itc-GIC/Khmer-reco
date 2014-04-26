package edu.itc.sphinx.demo;

import edu.itc.sphinx.*;

public class AudioPlayerDemo {

    public static void main(String[] args){
        new Demo(
            new AudioFormatPrinter(), 
            new AudioPlayer()
        ).run();
    }
}
