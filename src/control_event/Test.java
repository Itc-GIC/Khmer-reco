package control_event;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Test {

	
	public static void main(String[] args) {
		
//		String path = "/Users/macbookpro/Desktop/save.wav";
//    	Player pl = new Player(path);
//    	pl.play(path);
//    	try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		pl.stopPlay();
//    	pl.play(path);
    	
    	
    	try {
    		Recorder r = new Recorder();
    		r.start();
			Thread.sleep(1000);
			r.stopRecord();
			
			File f = new File ("D:/test.wav");
	    	VoiceOperator vo = new VoiceOperator(r.getAudio());
//	    	vo.saveToWave(f, r.getAudio());
	    	r.deleteTmpFile();		// the file will be delete when the programm is closed
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	
//    	Player pl = new Player(r.getAudio());
//    	pl.play(r.getAudio());
//    	try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	r.stopRecord();
//    	pl.play(r.getAudio());	
 
	}

}
