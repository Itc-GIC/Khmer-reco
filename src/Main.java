import util.Setting;
import util.Template;

import gui.Windows;


public class Main {

	public static void main(String[] args) {
		
		try {
			Template.setTemplate(Setting.instance.getTemplate());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Windows();
	}

}
