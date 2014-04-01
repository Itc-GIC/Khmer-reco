package util;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

import constant.Constant;

public class Template {
	private final static Font templateFont = new Font("Khmer OS",Font.BOLD,14);
	public static void setTemplate(int template) throws UnsupportedLookAndFeelException, ClassNotFoundException, IllegalAccessException, InstantiationException, Exception {
		UIManager.put("Menu.font", templateFont);
		UIManager.put("MenuItem.font", templateFont);
		UIManager.put("Button.font", templateFont);
		UIManager.put("Label.font", templateFont);
		UIManager.put("TextArea.font", templateFont);
		
		if (template==Constant.SKINLF_TEMPLATE){
            Skin skin = SkinLookAndFeel.loadThemePack("resource/lib/themepack.zip");
            SkinLookAndFeel.setSkin(skin);
            UIManager.setLookAndFeel(new SkinLookAndFeel());
        }else if (template==Constant.METAL_TEMPLATE){
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        else if (template==Constant.MOTIF_TEMPLATE){
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }
        else if (template==Constant.NIMBUS_TEMPLATE){
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
    }
}
