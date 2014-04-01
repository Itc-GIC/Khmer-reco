package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import constant.Constant;
import control_event.Player;
import control_event.Recorder;

import recognition.Recognition;
import util.ExtensionFileFilter;
import util.Save;
import util.Setting;

public class ContentMain extends JPanel implements MouseListener{
	private final Dimension PREFERED_SIZE_SCROOL = new Dimension(700,300); 
	
	private final int textResultWidth = 70;
	private final int textResultHeight = 5;
	private final Border SPACE_ICON = new EmptyBorder(0,10,0,0);
	private final int SAVE_FROM_CURRENT= 1;
	private final int SAVE_FROM_HISTORY= 2;
	
	//Icon
	private final ImageIcon recordIcon = new ImageIcon(Constant.IMAGE_PATH+"record.png");
	private final ImageIcon recordOverIcon = new ImageIcon(Constant.IMAGE_PATH+"record-over.png");
	private final ImageIcon recordClickIcon = new ImageIcon(Constant.IMAGE_PATH+"record-click.png");
	
	private final ImageIcon playIcon = new ImageIcon(Constant.IMAGE_PATH+"play.png");
	private final ImageIcon playOverIcon = new ImageIcon(Constant.IMAGE_PATH+"play-over.png");
	private final ImageIcon playClickIcon = new ImageIcon(Constant.IMAGE_PATH+"play-click.png");
	
	private final ImageIcon stopIcon = new ImageIcon(Constant.IMAGE_PATH+"stop.png");
	private final ImageIcon stopOverIcon = new ImageIcon(Constant.IMAGE_PATH+"stop-over.png");
	private final ImageIcon stopClickIcon = new ImageIcon(Constant.IMAGE_PATH+"stop-click.png");
	
	private final ImageIcon saveIcon = new ImageIcon(Constant.IMAGE_PATH+"save.png");
	private final ImageIcon saveOverIcon = new ImageIcon(Constant.IMAGE_PATH+"save-over.png");
	private final ImageIcon saveClickIcon = new ImageIcon(Constant.IMAGE_PATH+"save-click.png");
	
	private final ImageIcon openIcon = new ImageIcon(Constant.IMAGE_PATH+"open.png");
	private final ImageIcon openOverIcon = new ImageIcon(Constant.IMAGE_PATH+"open-over.png");
	private final ImageIcon openClickIcon = new ImageIcon(Constant.IMAGE_PATH+"open-click.png");
	
	//singleton
	public static ContentMain instance = new ContentMain();
	
//	private JButton saveAllBut;
	private JLabel playBut, saveBut, recordBut, stopBut, openBut , saveAllBut;
	private JTextArea textResult, history;
	private Recognition reg;
	private Recorder recorder;
	private Player player;
	
	private String lastOpenDir=System.getProperty("user.dir");
	private boolean isRecording =false;
	
	private ResourceBundle bundle;
	private Locale locale = Setting.instance.getLocale();
	
	private ContentMain(){
		bundle = ResourceBundle.getBundle("i18n.language",locale);
		reg = Recognition.instance;
		showGraphic();
		
	}
	
	private void showGraphic(){
		setLayout(new GridLayout(2,1,0,5));
		
		JPanel currentOperationPanel = new JPanel();
		recordBut = new JLabel(recordIcon);
		recordBut.setBorder(SPACE_ICON);
		recordBut.setToolTipText(bundle.getString("record"));
		recordBut.addMouseListener(this);
		
		
		playBut = new JLabel(playIcon);
		playBut.setBorder(SPACE_ICON);
		playBut.setToolTipText(bundle.getString("play"));
		playBut.addMouseListener(this);
		
		saveBut = new JLabel(saveIcon);
		saveBut.setBorder(SPACE_ICON);
		saveBut.setToolTipText(bundle.getString("save"));
		saveBut.addMouseListener(this);
		
		stopBut = new JLabel(stopIcon);
		stopBut.setBorder(SPACE_ICON);
		stopBut.setToolTipText(bundle.getString("stop"));
		stopBut.addMouseListener(this);
		
		openBut = new JLabel(openIcon);
		openBut.setBorder(SPACE_ICON);
		openBut.setToolTipText(bundle.getString("open"));
		openBut.addMouseListener(this);
		
		textResult = new JTextArea(textResultHeight,textResultWidth);
		JScrollPane scrollableDisplayResult = new JScrollPane(
				textResult, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		currentOperationPanel.add(recordBut);
		currentOperationPanel.add(playBut);
		currentOperationPanel.add(stopBut);
		currentOperationPanel.add(saveBut);
		currentOperationPanel.add(openBut);
		currentOperationPanel.add(scrollableDisplayResult);
		
		JPanel historyPanel = new JPanel();
		history = new JTextArea(textResultHeight,textResultWidth);
		JScrollPane scrollableHistory = new JScrollPane(
				history, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		scrollableHistory.setPreferredSize(PREFERED_SIZE_SCROOL);
		
//		JLabel historyLabel = new JLabel(bundle.getString("history"));
//		saveAllBut = new JButton(bundle.getString("save"));
		
		saveAllBut = new JLabel(saveIcon);
		saveAllBut.setToolTipText(bundle.getString("save"));
		saveAllBut.addMouseListener(this);
		
//		historyPanel.add(historyLabel);
//		historyPanel.add(saveAllBut);
		historyPanel.add(saveAllBut);
		historyPanel.add(scrollableHistory);
		
		add(currentOperationPanel);
		add(historyPanel);
		
		setVisible(true);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==recordBut){
			recordBut.setEnabled(false);
			recordBut.removeMouseListener(this);
			isRecording=true;
			if(recorder!=null){
				deleteTmpFile();
			}
			try {
				recorder = new Recorder();
				System.out.println("delete");
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			recorder.start();
			
		}else if (e.getSource()==playBut){
			if(player!=null){
				player.play();
				System.out.println("hello");
			}
		}else if (e.getSource()==stopBut){
			if(isRecording){
				recorder.stopRecord();
				player = new Player(recorder.getAudioFile().getAbsolutePath());
				recoVoice();
				recordBut.setEnabled(true);
				recordBut.addMouseListener(this);
				recordBut.setIcon(recordIcon);
				isRecording=false;
			}else if(player.isPlay()){
				player.stopPlay();
			}
//			recorder.deleteTmpFile();
		}else if (e.getSource()==saveBut){
			saveText(SAVE_FROM_CURRENT);
		}else if (e.getSource()==openBut){
			openAndReco();
		}else if (e.getSource()==saveAllBut){
			saveText(SAVE_FROM_HISTORY);
		}

	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==recordBut){
			recordBut.setIcon(recordClickIcon);
		}else if(e.getSource()==playBut){
			playBut.setIcon(playClickIcon);
		}else if(e.getSource()==stopBut){
			stopBut.setIcon(stopClickIcon);
		}else if(e.getSource()==saveBut){
			saveBut.setIcon(saveClickIcon);
		}else if(e.getSource()==openBut){
			openBut.setIcon(openClickIcon);
		}else if(e.getSource()==saveAllBut){
			saveAllBut.setIcon(saveClickIcon);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==recordBut){
			recordBut.setIcon(recordOverIcon);
		}else if(e.getSource()==playBut){
			playBut.setIcon(playOverIcon);
		}else if(e.getSource()==stopBut){
			stopBut.setIcon(stopOverIcon);
		}else if(e.getSource()==saveBut){
			saveBut.setIcon(saveOverIcon);
		}else if(e.getSource()==openBut){
			openBut.setIcon(openOverIcon);
		}else if(e.getSource()==saveAllBut){
			saveAllBut.setIcon(saveOverIcon);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==recordBut){
			recordBut.setIcon(recordOverIcon);
		}else if(e.getSource()==playBut){
			playBut.setIcon(playOverIcon);
		}else if(e.getSource()==stopBut){
			stopBut.setIcon(stopOverIcon);
		}else if(e.getSource()==saveBut){
			saveBut.setIcon(saveOverIcon);
		}else if(e.getSource()==openBut){
			openBut.setIcon(openOverIcon);
		}else if(e.getSource()==saveAllBut){
			saveAllBut.setIcon(saveOverIcon);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==recordBut){
			recordBut.setIcon(recordIcon);
		}else if(e.getSource()==playBut){
			playBut.setIcon(playIcon);
		}else if(e.getSource()==stopBut){
			stopBut.setIcon(stopIcon);
		}else if(e.getSource()==saveBut){
			saveBut.setIcon(saveIcon);
		}else if(e.getSource()==openBut){
			openBut.setIcon(openIcon);
		}else if(e.getSource()==saveAllBut){
			saveAllBut.setIcon(saveIcon);
		}
	}
	
	public void recoVoice(){
		Thread reco = new Thread(new Runnable() {
			@Override
			public void run() {
				ControlPanel controlPanel = ControlPanel.instance;
				controlPanel.startProgressBar();
				try {
					AudioInputStream audio = player.getAudio();
					String result = reg.translateSpeech(audio);
//					String result = "hello";
					String oldText = textResult.getText();
					if(!oldText.equals("")){
						history.setText(oldText+"\n"+history.getText());
					}
					textResult.setText(result);
					controlPanel.stopProgressBar();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		reco.start();
	}
	
	public void saveText(int textResultType){
		JFileChooser saveText = new JFileChooser(lastOpenDir); 
		int saveStatus = saveText.showSaveDialog(ContentMain.instance);
		if(saveStatus==JFileChooser.APPROVE_OPTION){
			File selectedFile = saveText.getSelectedFile();
			if(selectedFile.exists()){
				if(confirmOverride()){
					String path = selectedFile.getAbsolutePath();
					
					if(!path.toLowerCase().endsWith(".txt")){
						selectedFile = new File(path+".txt");
					}
//					System.out.println(selectedFile.getAbsolutePath());
					if(textResultType==SAVE_FROM_CURRENT)
						Save.saveToText(selectedFile, textResult.getText());
					else if(textResultType==SAVE_FROM_HISTORY)
						Save.saveToText(selectedFile, history.getText());
				}
			}else{
				String path = selectedFile.getAbsolutePath();
				
				if(!path.toLowerCase().endsWith(".txt")){
					selectedFile = new File(path+".txt");
				}
//				System.out.println(selectedFile.getAbsolutePath());
				if(textResultType==SAVE_FROM_CURRENT)
					Save.saveToText(selectedFile, textResult.getText());
				else if(textResultType==SAVE_FROM_HISTORY)
					Save.saveToText(selectedFile, history.getText());
			}
//			System.out.println(selectedFile.getParent());
			lastOpenDir=selectedFile.getParent();
		}
	}
	
	public void openAndReco(){
		JFileChooser saveText = new JFileChooser(lastOpenDir); 
		ExtensionFileFilter filter = new ExtensionFileFilter("WAV", new String[] { "WAV" });
		saveText.setFileFilter(filter);
		int openStatus = saveText.showOpenDialog(this);
		if(openStatus==JFileChooser.APPROVE_OPTION){
			File selectedFile = saveText.getSelectedFile();
			player = new Player(selectedFile.getAbsolutePath());
			recoVoice();
			lastOpenDir=selectedFile.getParent();
		}
	}

	public boolean confirmOverride(){
		int status = JOptionPane.showConfirmDialog(ContentMain.instance,
				bundle.getString("confrimOverride"),
				bundle.getString("fileConflict"),
				JOptionPane.YES_NO_OPTION);
		return status == JOptionPane.YES_OPTION;
	}
	
	public void deleteTmpFile(){
		if(recorder!=null)
			recorder.deleteTmpFile();
	}
	
//	private void disableAllBut(){
//		recordBut.setEnabled(false);
//		recordBut.removeMouseListener(this);
//		playBut.setEnabled(false);
//		playBut.removeMouseListener(this);
//		stopBut.setEnabled(false);
//		stopBut.removeMouseListener(this);
//		saveBut.setEnabled(false);
//		saveBut.removeMouseListener(this);
//		openBut.setEnabled(false);
//		openBut.removeMouseListener(this);
//		saveAllBut.setEnabled(false);
//		saveAllBut.removeMouseListener(this);
//	}
//	
//	private void enableAllBut(){
//		recordBut.setEnabled(true);
//		recordBut.addMouseListener(this);
//		playBut.setEnabled(true);
//		playBut.addMouseListener(this);
//		stopBut.setEnabled(true);
//		stopBut.addMouseListener(this);
//		saveBut.setEnabled(true);
//		saveBut.addMouseListener(this);
//		openBut.setEnabled(true);
//		openBut.addMouseListener(this);
//		saveAllBut.setEnabled(true);
//		saveAllBut.addMouseListener(this);
//	}
}
