package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import util.Setting;

public class Windows extends JFrame{

	public Windows(){
		setTitle("Khmer Speech Recognition");
        this.setSize(870,600);
        this.setLocation(200, 300);
        setJMenuBar(new MenuMain());
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        ContentMain content1 = ContentMain.instance;
        content.add(content1,BorderLayout.CENTER);
        content.add(ControlPanel.instance,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	Setting.instance.saveSetting();
            	ContentMain.instance.deleteTmpFile();
            	System.exit(0);
            }
          });
        
        setVisible(true);
	}

}
