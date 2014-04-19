package control_event;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Recorder extends Thread{
	
	private File audioFile ;
	private AudioFormat audioFormat;
	private TargetDataLine targetDataLine;
	private final String fileTmpPath = "~tmp";
	private String tmpFileDir = "resource/tmp_file_dir/";
	
	
	//add 201013
	public Recorder(String fileName) throws LineUnavailableException{
		/*int Order = 0;
		while((audioFile = new File(tmpFileDir+fileTmpPath+Order+".wav")).exists()){
			Order++;
		}*/			
		
		audioFile = new File(fileName);
		
		System.out.println("File path :"+fileName);
		audioFormat = getAudioFormat();
		System.out.println("test 1 ");
		DataLine.Info dataLineInfo =new DataLine.Info(TargetDataLine.class,audioFormat);
		System.out.println("test 2 ");
		
		targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
	}
	//add 201013
	
	public Recorder() throws LineUnavailableException{
		int Order = 0;
		while((audioFile = new File(tmpFileDir+fileTmpPath+Order+".wav")).exists()){
			Order++;
		}
		audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo =new DataLine.Info(TargetDataLine.class,audioFormat);
		targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
	}
	
	/*hour code
	 * 
	 * public Recorder() throws LineUnavailableException{
		int Order = 0;
		while((audioFile = new File(tmpFileDir+fileTmpPath+Order+".wav")).exists()){
			Order++;
		}
		audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo =new DataLine.Info(TargetDataLine.class,audioFormat);
		targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
	}*/
	
	public void run(){
		 
		AudioFileFormat.Type fileType = AudioFileFormat.Type.AU;
		
		try{
			targetDataLine.open(getAudioFormat());
			targetDataLine.start();
			AudioSystem.write(new AudioInputStream(targetDataLine),fileType,audioFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void deleteTmpFile(){
		audioFile.deleteOnExit();
	}
	
	public void stopRecord() {
		targetDataLine.stop();
	    targetDataLine.close();
	}
	
	private AudioFormat getAudioFormat(){
	    try{
	        return getAudio().getFormat();
	    } catch(Exception e) {
	        AudioFormat.Encoding technique = AudioFormat.Encoding.PCM_SIGNED;
	        float sampleRate = 16000.0F; // 8000,11025,16000,22050,44100
	        int sampleSizeInBits = 16; // 8,16
	        int channels = 1; // 1(mono),2(stereo)
	        int frameSize = 2; // bytes per frame
	        float frameRate = 16000.0F; // frames per second
	        boolean bigEndian = true; // true(big-endian),false(little-endian)
	        
	        return new AudioFormat(technique, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
	    }
	}
	
	public AudioInputStream getAudio() throws UnsupportedAudioFileException, IOException{
		return AudioSystem.getAudioInputStream(audioFile);
	}
	
	public File getAudioFile(){
		return audioFile;
	}

}
