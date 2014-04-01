package util;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.stream.FileImageInputStream;
import javax.security.auth.callback.LanguageCallback;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import constant.Constant;

public class Setting {
	private final static String LANGUAGE = "language";
	private final static String RECENT_OPEN = "recent_open";
	private final static String TEMPLATE = "template";
	private final static String SEPERATOR = "=";
	private final static String DELIMIT_VALUE = ";";
	
	private HashMap<String, String[]> setting = new HashMap<String, String[]>();
	private File settingFile = new File(Constant.SETTING_FILEPATH);
	
	public static Setting instance = new Setting();
	private Setting(){
		initialSetting();
	}
	
//	public HashMap<String, String[]> getSetting(){
//		return setting;
//	}
	
	private void initialSetting(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(settingFile)));
			String element;
			while((element=br.readLine())!=null){
				addElement2Setting(element);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Locale getLocale(){
		int language;
		try{
			language = Integer.parseInt(setting.get(LANGUAGE)[0]);
		}catch(NumberFormatException e){
			language = Constant.KHMER_LOCALE;
		}
		
		if(language==Constant.FRENCH_LOCALE)
			return new Locale("fr","FR");
		else if (language==Constant.ENGLISH_LOCALE)
			return new Locale("en","US");
		else
			return new Locale("km","KH");
	}
	
	public void setLocale(int language){
		String[] languageString = new String[]{language+""};
		setting.put(LANGUAGE,languageString);
	}
	
	public int getTemplate(){
		return Integer.parseInt(setting.get(TEMPLATE)[0]);
	}
	
	public void setTemplate(int template){
		String[] templateString = new String[]{template+""};
		setting.put(TEMPLATE,templateString);
	}
	
	public void saveSetting(){
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(settingFile)));
			bw.write(mergeElement());
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String mergeElement(){

		String result="";
		Set<String> keysUnsort = setting.keySet();
		Set<String> keysSort = new TreeSet<String>();
		for(String key: keysUnsort)
			keysSort.add(key);
		
		String [] values;
		String value;
		for(String key : keysSort){
			values = setting.get(key);
			value = Arrays.toString(values).replace(", ", DELIMIT_VALUE).replaceAll("[\\[\\]]", "");
			result=result+key+SEPERATOR+value+"\n";
		}
//		System.out.println(result);
		return result;
	}
	
	private void addElement2Setting(String element){
		String[] temp = element.split(SEPERATOR);
		String key = temp[0];
		String values[] = temp[1].split(DELIMIT_VALUE);
		setting.put(key, values);
	}
	
	public static void main(String[] args){
//		System.out.println("language111="+instance.setting.get("recent_open")[0]);
		instance.mergeElement();
	}
}
