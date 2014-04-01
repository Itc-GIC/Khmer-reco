package util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;



public class Save {
	
	public static void saveToWave(File file, AudioInputStream audio){
		 
		AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		
		   try{
		    AudioSystem.write(audio,fileType,file);
		   }catch (Exception e){
		    e.printStackTrace();
		   }
	}
	
	public static void saveToText(File file, String text){

		try {
			FileWriter fw = new FileWriter(file);
			PrintWriter pr = new PrintWriter(fw);
			pr.printf("%s%n", text);
			pr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
//		File f = new File ("/Users/macbookpro/Desktop/test.txt");
//		Save s = new Save(f);
//		SaveToText(f, "abcde khdffjf jskdfh\n kshfjkd sjhdfkjs");
//		ReadText("/Users/macbookpro/Desktop/test.txt");
		
	}
}
