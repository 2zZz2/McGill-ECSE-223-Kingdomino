package ca.mcgill.ecse223.kingdomino.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

public class KingdominoVerifyErrorMenu extends JFrame{
	private JLabel errorMessage;
	public static String messageVerify = "ERROR";
	
	  public KingdominoVerifyErrorMenu() {
	        initComponents();
	    }

	    private void initComponents() {
	    	  setLayout(null);
	    	 //this.setResizable(false);
	        setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
	        Container c = getContentPane();
	        c.setPreferredSize(new Dimension(950, 450));
	      
	        
	       errorMessage = new JLabel(messageVerify,JLabel.CENTER);
	       errorMessage.setFont(new Font("pixelType", Font.BOLD, 70));
		   //errorMessage.setPreferredSize(new Dimension(550,400));
		   errorMessage.setSize(new Dimension(600, 70));
		   errorMessage.setLocation (40,150);
		   errorMessage.setForeground(new Color(252, 193, 51));
		   c.add(errorMessage);
		   pack();

	    // Action that is performed upon the window closing
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                KingdominoStartPage.getFrame().setVisible(true);
                e.getWindow().dispose();
            }
        });
	    }
	  public static void setErrorMessage(String error) {
		  messageVerify = error;
	  }
}
