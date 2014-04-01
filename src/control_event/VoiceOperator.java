package control_event;

import java.io.File;

import javax.sound.sampled.AudioInputStream;

import util.Save;

public class VoiceOperator {
	private Player player;
	private AudioInputStream audio;
	
	public VoiceOperator(AudioInputStream audio) {
		this.audio = audio;
		player = new Player(audio);
	}
	
	public VoiceOperator(String voicePath) {
		player = new Player(voicePath);
		audio = player.getAudio();
	}

	public void saveTotext(File file, String text){
		Save.saveToText(file, text);
		
	}
	
	public void saveToWave(File file){
		Save.saveToWave(file, audio);	
	}
	
	
	public void play(){
		if(audio != null){
			player.play();
		}
	}
	
	public void stopPlay(){
		player.stopPlay();
	}
	
	public void delete(String voicePath){
		
	}
	
	public void setAudio(String voicePath){
		player = new Player(voicePath);
		audio = player.getAudio();
	}
	
	public void setAudio(AudioInputStream audio){
		player = new Player(audio);
		this.audio = audio;
	}
	
	public AudioInputStream getAudio(){
		return audio;
	}

	public static void main(String[] args) {
		VoiceOperator v = new VoiceOperator("test/test.wav");
		v.play();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v.play();
	}

}
