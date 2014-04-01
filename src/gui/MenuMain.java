package gui;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

import constant.Constant;

import util.ExtensionFileFilter;
import util.Setting;
import util.Template;

public class MenuMain extends JMenuBar implements ActionListener{
	
	//file menu
	private JMenuItem exitItem;
	private JMenuItem openItem;
	
	//edit menu
	private JMenuItem englishItem, khmerItem, frenchItem;
	private JMenuItem templateItem[];
	
	//help menu
	private JMenuItem manualItem;
	private JMenuItem aboutItem;
	
	private ResourceBundle bundle;
	private Locale locale = Setting.instance.getLocale();
	
	public MenuMain(){
		bundle = ResourceBundle.getBundle("i18n.language",locale);
		
		JMenu fileMenu = new JMenu(bundle.getString("file"));
	    JMenu editMenu = new JMenu(bundle.getString("edit"));
	    JMenu helpMenu = new JMenu(bundle.getString("help"));
	    
	    openItem = new JMenuItem(bundle.getString("open"));
	    openItem.addActionListener(this);
	    exitItem = new JMenuItem(bundle.getString("exit"));
	    exitItem.addActionListener(this);
	    
//	    JMenu recentOpenMenu = new JMenu(bundle.getString("recentOpen"));
//	    recentOpenMenu.addActionListener(this);
	    
	    fileMenu.add(openItem);
//	    fileMenu.add(recentOpenMenu);
	    fileMenu.add(exitItem);
	    
	    
	    englishItem = new JMenuItem("English");
	    englishItem.addActionListener(this);
	    khmerItem = new JMenuItem("Khmer");
	    khmerItem.addActionListener(this);
	    frenchItem = new JMenuItem("French");
	    frenchItem.addActionListener(this);
	    
	    JMenu language = new JMenu(bundle.getString("language"));
	    language.add(khmerItem);
	    language.add(englishItem);
	    language.add(frenchItem);
	    
	    JMenu template = new JMenu(bundle.getString("template"));
	    templateItem = new JMenuItem[Constant.templateName.length];
        for(int i=0; i<Constant.templateName.length; i++){
            templateItem[i] = new JMenuItem(Constant.templateName[i]);
            template.add(templateItem[i]);
            templateItem[i].addActionListener(this);
        }
        
	    editMenu.add(language);
	    editMenu.add(template);
	    
	    manualItem = new JMenuItem(bundle.getString("manual"));
        manualItem.addActionListener(this);
        aboutItem = new JMenuItem(bundle.getString("about"));
        aboutItem.addActionListener(this);
        
        helpMenu.add(manualItem);
        helpMenu.add(aboutItem);
        
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openItem){
			ContentMain.instance.openAndReco();
		}else if(e.getSource() == exitItem){
			Setting.instance.saveSetting();
			System.exit(0);
		}else if(e.getSource() == khmerItem){
			changeLanguage(Constant.KHMER_LOCALE);
		}else if(e.getSource() == englishItem){
			changeLanguage(Constant.ENGLISH_LOCALE);
		}else if(e.getSource() == frenchItem){
			changeLanguage(Constant.FRENCH_LOCALE);
		}else if(e.getSource()==templateItem[Constant.SKINLF_TEMPLATE]){
            changeTemplate(Constant.SKINLF_TEMPLATE);
        }else if(e.getSource()==templateItem[Constant.MOTIF_TEMPLATE]){
            changeTemplate(Constant.MOTIF_TEMPLATE);
        }else if(e.getSource()==templateItem[Constant.METAL_TEMPLATE]){
            changeTemplate(Constant.METAL_TEMPLATE);
        }else if(e.getSource()==templateItem[Constant.NIMBUS_TEMPLATE]){
            changeTemplate(Constant.NIMBUS_TEMPLATE);
        }
		
	}
	
	private void changeLanguage(int localeConstant){
		Windows windows = (Windows) SwingUtilities.getWindowAncestor(this);
		windows.setVisible(false);
		Setting.instance.setLocale(localeConstant);
		windows = new Windows();
	}
	
	private void changeTemplate(int template){
		Windows windows = (Windows) SwingUtilities.getWindowAncestor(this);
		windows.setVisible(false);
		Setting.instance.setTemplate(template);
		try {
			Template.setTemplate(template);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		windows = new Windows();
	}

}
