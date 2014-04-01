package recognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import control_event.Recorder;

import ErrorException.NotHearException;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class Recognition {

	private Recognizer recognizer;
	private AudioFileDataSource datasource;
	public static Recognition instance = new Recognition();
	
	// load all the trained file (dictionary, language model, acoustic model)
	private Recognition(){
		ConfigurationManager cm = new ConfigurationManager(Recognition.class.getResource("config_with_language_grammar.xml"));
		
		// allocate the recognizer
        System.out.println("Loading...");
        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
        
        datasource = (AudioFileDataSource) cm.lookup("audioFileDataSource");
         
	}
	
	// can be called mony time after the object is located
	public String translateSpeech(AudioInputStream sound) throws Exception{
        
		
		//edit 191013
        sound = AudioSystem.getAudioInputStream(getAudioFormat(), sound);
        //*** close avoid change format sound input
        
        
        datasource.setInputStream(sound, "recorded");
        
        Result result = recognizer.recognize();
        String resultText = "";
        
        if (result != null) {
            resultText= result.getBestFinalResultNoFiller();
        } else {
//            throw new NotHearException("Can't hear the voice");
        	return "Can't hear the voice";
        }
		return resultText;
	}
	
	private AudioFormat getAudioFormat(){
		/*AudioFormat.Encoding technique = AudioFormat.Encoding.PCM_SIGNED;*/
		
		System.out.println(" Start");
		
		AudioFormat.Encoding technique = AudioFormat.Encoding.PCM_SIGNED;
		
		
		float sampleRate = 16000.0F;
		//8000,11025,16000,22050,44100
		int sampleSizeInBits = 8;
		//8,16
		int channels = 1;
		int frameSize = 2;
		float frameRate = 16000;
		//1,2
		
		System.out.println(" end");
		boolean bigEndian = true;
		//true,false
		return new AudioFormat(technique,sampleRate,sampleSizeInBits,channels,frameSize,frameRate,bigEndian);
	}
	/**/
	public static void main(String [] args){
			
		
		try {
			
			System.out.println("reading wav file...");
			
			//String dir = "C:/Users/Jeudy7/Desktop/Doc_ITC_teaching_250913/sphinx_4_project/reco/resource/tmp_file_dir/";
			String dir = "./resource/tmp_file_dir/";
			 File directory = new File(dir);
			 
		     //get all the files from a directory
		     File[] fList = directory.listFiles();
		     
		     
		     	     
		     List<String> list_fileName=new ArrayList<String>();
	     	     
		     Recognition reg = new Recognition();
		     
			 System.out.println(" list files");
		     for (File file : fList){
		         System.out.println("File read :"+file.getName());
		         
		         //System.out.println("number of file : "+file.list()));
		         
		         Recorder rec = new Recorder(dir+file.getName());
		        // Recorder r = null;
		         
		         System.out.println("text reco: "+reg.translateSpeech(rec.getAudio()));
		         
		         list_fileName.add(file.getName());		        		        
		     }
		     
		     
		    
		     /*
		     Iterator it=list_fileName.iterator();
		     System.out.println("Filenames in list : ");
		        while(it.hasNext())
		        {
		          String value=(String)it.next();

		          System.out.println("Value :"+value);
		        }
	*/
		     
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		

		
				
		/* hour
		try {
			String com;
			Recorder r = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Recognition reg = new Recognition();
			while(true){
				System.out.println("s: to start.   c: to stops.");
				if((com = br.readLine()).equals("s")){
					System.out.println("start");
					r = new Recorder();
					r.start();
				}else if(com.equals("c")){
					System.out.println("cancel");
					r.stopRecord();
					r.deleteTmpFile();
					System.out.println("text: "+reg.translateSpeech(r.getAudio()));
				}
			}

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
	}
	
	/**/	
	
}
