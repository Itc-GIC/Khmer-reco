package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import constant.Constant;
import control_event.Recorder;

public class ControlPanel extends JPanel{
	
	private JLabel itcIconLabel;
	private JLabel gicIconLabel;
	private JProgressBar progressBar = new JProgressBar();
	
	public static ControlPanel instance = new ControlPanel();
	
	private ControlPanel(){
		this.setLayout(new BorderLayout());
//		setLayout(new BoxLayout(this,Boxlayout.));
		
		JPanel iconGroup = new JPanel();
		iconGroup.setLayout(new GridLayout(1, 2));
		itcIconLabel = new JLabel(new ImageIcon(Constant.IMAGE_PATH+"itc.gif"));
		gicIconLabel = new JLabel(new ImageIcon(Constant.IMAGE_PATH+"gic.png"));
		
		iconGroup.add(itcIconLabel);
		iconGroup.add(gicIconLabel);
//		iconGroup.add(stopBut);
		JLabel credit = new JLabel("Power by GIC Pro 11");
		progressBar.setPreferredSize(new Dimension(400, 10));
		JPanel progressPanel = new JPanel();
//		progressPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
//		progressPanel.setAlignmentY(FlowLayout.CENTER);
		progressPanel.add(progressBar);
		
		add(progressPanel,BorderLayout.CENTER);
		add(credit,BorderLayout.EAST);
		add(iconGroup,BorderLayout.WEST);
		
		this.setBorder(BorderFactory.createEmptyBorder(10,10,0,10)); 
//		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setVisible(true);
	}
	
	public void startProgressBar(){
		progressBar.setIndeterminate(true);
	}
	
	public void stopProgressBar(){
		progressBar.setIndeterminate(false);
	}
}
