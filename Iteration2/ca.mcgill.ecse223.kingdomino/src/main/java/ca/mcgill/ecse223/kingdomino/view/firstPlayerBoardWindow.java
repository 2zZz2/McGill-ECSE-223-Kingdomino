package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;

public class firstPlayerBoardWindow extends JFrame {
	
	public ArrayList<JButton[][]> boardsList = KingdominoApplication.gamePage.getPlayerBoards();
	
	
	  public firstPlayerBoardWindow(int player) {
	        initComponents(player);
	    }

	    private void initComponents(int player) {
	    	
	    	 setLayout(null);
		        setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
		        Container c = getContentPane();
		        c.setPreferredSize(new Dimension(1200, 620));
	    	switch(player) {
	    	case 1:
	       
	        JPanel boardPanel = new JPanel();
			boardPanel.setLayout(new GridLayout(9, 9));
			boardPanel.setBounds(260, 0, 750, 500);

			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++) {
					boardPanel.add(boardsList.get(0)[k][j]);
				}
			}
	        c.add(boardPanel);
	        
	        
	    	case 2:
	 	       
		        JPanel boardPanel2 = new JPanel();
				boardPanel2.setLayout(new GridLayout(9, 9));
				boardPanel2.setBounds(260, 0, 750, 500);

				for (int k = 0; k < 9; k++) {
					for (int j = 0; j < 9; j++) {
						boardPanel2.add(boardsList.get(1)[k][j]);
					}
				}
		        c.add(boardPanel2);
	        
	    	case 3:
	 	       
		        JPanel boardPanel3 = new JPanel();
				boardPanel3.setLayout(new GridLayout(9, 9));
				boardPanel3.setBounds(260, 0, 750, 500);

				for (int k = 0; k < 9; k++) {
					for (int j = 0; j < 9; j++) {
						boardPanel3.add(boardsList.get(0)[k][j]);
					}
				}
		        c.add(boardPanel3);
		        
		        
	    	case 4:
	 	       
		        JPanel boardPanel4 = new JPanel();
				boardPanel4.setLayout(new GridLayout(9, 9));
				boardPanel4.setBounds(260, 0, 750, 500);

				for (int k = 0; k < 9; k++) {
					for (int j = 0; j < 9; j++) {
						boardPanel4.add(boardsList.get(0)[k][j]);
					}
				}
		        c.add(boardPanel4);
	        
	    	}
	        
	        pack();
	    }
	
}
