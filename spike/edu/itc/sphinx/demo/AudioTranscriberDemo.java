package edu.itc.sphinx.demo;

import edu.itc.sphinx.*;

public class AudioTranscriberDemo {

    public static void main(String[] args) {
        new Demo(
            new AudioTranscriber(),
            new AudioPlayer()
        ).run();
    }
}
