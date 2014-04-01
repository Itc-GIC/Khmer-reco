package control_event;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player{
	
    private AudioInputStream audio;
    private Clip clip;
    private File file;
     
    /********************************************************/
    /////////////////////// constructor
    public Player(String voicePath){
    	try {
    		file = new File(voicePath);
			this.audio = AudioSystem.getAudioInputStream(file);
			init();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Player(AudioInputStream audio){
    	this.audio = audio;
    	init();
    }
    
    public Player(File file) throws UnsupportedAudioFileException, IOException{
    	this.audio = AudioSystem.getAudioInputStream(file);
    	init();
    }
    
    public void init(){
    	try {
			clip = AudioSystem.getClip();
			clip.open(this.audio);
			clip.addLineListener(new LineListener() {
				
				@Override
				public void update(LineEvent arg0) {
					if(arg0.getType() == LineEvent.Type.START){
					}else if(arg0.getType() == LineEvent.Type.STOP){
						clip.loop(Clip.LOOP_CONTINUOUSLY);
						clip.stop();
					}
				}
			});
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
	//////////////////////// methode for player
	public void play(){
		clip.setFramePosition(0);
		clip.start();
		clip.loop(0);
	}
	
	public void stopPlay(){
		clip.stop();
    }
	
	public void pause(){
		clip.stop();
	}
	
	public boolean isPlay(){
		return clip.isActive();
	}

	public AudioFormat getFormatAudio(){
    	return audio.getFormat();
    }
	
	public AudioInputStream getAudio(){
		try {
			return AudioSystem.getAudioInputStream(this.file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/********************************************************/
	/////////////////////// test part //////////////////////////
    public static void main(String[] args){
    	Player p = new Player("D:/test.wav");
//    	p.start();
    	p.play();
    	getEnter();			// tap enter when you want to stop
    	p.stopPlay();
    	getEnter();
    	p.play();
    	getEnter();			// tap enter when you want to stop
    	p.stopPlay();
    }
    
    //////////////function for test (have no meaning) don't care about it
    public static void getEnter(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}