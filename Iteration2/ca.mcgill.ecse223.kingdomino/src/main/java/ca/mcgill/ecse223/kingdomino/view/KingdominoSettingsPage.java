package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.*;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class KingdominoSettingsPage extends JFrame {

    private JLabel settingsLabel;
    private JLabel playersLabel;
    private JLabel modeLabel;
    private JFrame settingsFrame = this;
    private JToggleButton twoPlayerButton;
    private JToggleButton threePlayerButton;
    private JToggleButton fourPlayerButton;
    private JToggleButton computerButton;
    private JToggleButton dynastyButton;
    private JToggleButton multiPlayerButton;
    private JToggleButton hintsButton;
    private JToggleButton soundEffectsButton;
    private JToggleButton soundButton;
    private JButton rulesButton;
    private JButton exit;
    private int isSelected;
    Clip clip;

    public KingdominoSettingsPage() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
        Container c = getContentPane();
        c.setPreferredSize(new Dimension(1280, 720));

        // Setup the labels
        settingsLabel = new JLabel(new ImageIcon(AssetManager.getSettingsLabel()));
        settingsLabel.setSize(new Dimension(550, 80));
        settingsLabel.setLocation(365, 45);
        c.add(settingsLabel);


        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(AssetManager.getPixelType());
        playersLabel = new JLabel("PLAYERS");
        playersLabel.setLocation(84, 120);
        playersLabel.setFont(new Font("pixelType", Font.BOLD, 38));
        playersLabel.setForeground(new Color(252, 217, 51));
        playersLabel.setSize(new Dimension(550, 80));
        c.add(playersLabel);


        //Mode label
        modeLabel = new JLabel("MODE");
        modeLabel.setLocation(84, 308);
        modeLabel.setFont(new Font("pixelType", Font.BOLD, 38));
        modeLabel.setForeground(new Color(252, 217, 51));
        modeLabel.setSize(new Dimension(550, 80));
        c.add(modeLabel);


        twoPlayerButton = new JToggleButton(new ImageIcon(AssetManager.getTwoPlayerButton()));
        twoPlayerButton.setSize(new Dimension(305, 135));
        twoPlayerButton.setLocation(80, 170);
        c.add(twoPlayerButton);

        threePlayerButton = new JToggleButton(new ImageIcon(AssetManager.getThreePlayerButton()));
        threePlayerButton.setSize(new Dimension(305, 135));
        threePlayerButton.setLocation(480, 170);
        c.add(threePlayerButton);

        fourPlayerButton = new JToggleButton(new ImageIcon(AssetManager.getFourPlayerButton()));
        fourPlayerButton.setSize(new Dimension(305, 135));
        fourPlayerButton.setLocation(880, 170);
        c.add(fourPlayerButton);

        computerButton = new JToggleButton(new ImageIcon(AssetManager.getComputerButton()));
        computerButton.setSize(new Dimension(305, 135));
        computerButton.setLocation(80, 360);
        c.add(computerButton);

        dynastyButton = new JToggleButton(new ImageIcon(AssetManager.getDynastyButton()));
        dynastyButton.setSize(new Dimension(305, 135));
        dynastyButton.setLocation(480, 360);
        c.add(dynastyButton);

        multiPlayerButton = new JToggleButton(new ImageIcon(AssetManager.getMultiplayerImage()));
        multiPlayerButton.setSize(new Dimension(305, 135));
        multiPlayerButton.setLocation(880, 360);
        c.add(multiPlayerButton);

        soundButton = new JToggleButton(new ImageIcon(AssetManager.getSoundButtonImage()));
        soundButton.setSize(new Dimension(75, 75));
        soundButton.setLocation(40, 625);
        soundButton.setOpaque(false);
        soundButton.setContentAreaFilled(false);
        soundButton.setBorderPainted(false);
        c.add(soundButton);

        rulesButton = new JButton(new ImageIcon(AssetManager.getRuleButtonImage()));
        rulesButton.setSize(new Dimension(75, 75));
        rulesButton.setLocation(40, 545);
        rulesButton.setOpaque(false);
        rulesButton.setContentAreaFilled(false);
        rulesButton.setBorderPainted(false);
        c.add(rulesButton);

        hintsButton = new JToggleButton(new ImageIcon(AssetManager.getHintsSettingsButton()));
        hintsButton.setSize(new Dimension(355, 115));
        hintsButton.setLocation(295, 570);
        hintsButton.setOpaque(false);
        hintsButton.setContentAreaFilled(false);
        hintsButton.setBorderPainted(false);
        c.add(hintsButton);

        soundEffectsButton = new JToggleButton(new ImageIcon(AssetManager.getSoundEffectsButton()));
        soundEffectsButton.setSize(new Dimension(355, 115));
        soundEffectsButton.setLocation(700, 570);
        soundEffectsButton.setOpaque(false);
        soundEffectsButton.setContentAreaFilled(false);
        soundEffectsButton.setBorderPainted(false);
        c.add(soundEffectsButton);
        
        exit = new JButton("X");
		exit.setSize(new Dimension(30, 30));
		exit.setForeground(Color.red);
		exit.setLocation(1220, 80);
		c.add(exit);
		

        // Button actions
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoStartPage.settingsFrame.setVisible(false);
			}
		});

        // Disable/enable the sound
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            	 AbstractButton button = (AbstractButton) e.getSource();
            	 boolean isPressed = button.getModel().isSelected();
            	 
            	 try {
            		 clip = AudioSystem.getClip();
                 if(isPressed){
                	 
					
						
						 button.setIcon(new ImageIcon(AssetManager.getSoundButton()));
                    
                     clip.open(AssetManager.getSoundName());
                     clip.start(); 
                     clip.loop(clip.LOOP_CONTINUOUSLY);
					
                 }else{
                	 clip.open(AssetManager.getSoundName());
                	  clip.stop();
                	  clip.close();
                     button.setIcon(new ImageIcon(AssetManager.getPressedSoundButton()));
                 }
                 } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            }
        });

        // Opens the rules
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KingdominoRules().setVisible(true);
            }
        });

        // Player selection
        
        
        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO set different images for clicked/unclicked
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                	if(isSelected>0) {
                		button.getModel().setSelected(false);
                		button.setIcon(new ImageIcon(AssetManager.getTwoPlayerButton()));
                		isSelected--;
                	}else {
                		isSelected++;
                    button.setIcon(new ImageIcon(AssetManager.getPressedTwoPlayerButton()));
                	}
                   
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getTwoPlayerButton()));
                }
            }
        });


        threePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                	
                	if(isSelected>0) {
                		button.getModel().setSelected(false);
                		button.setIcon(new ImageIcon(AssetManager.getThreePlayerButton()));
                		isSelected--;
                	}else {
                		isSelected++;
                    button.setIcon(new ImageIcon(AssetManager.getPressedThreePlayerButton()));
                	}
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getThreePlayerButton()));
                }
            }
        });

        fourPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                	if(isSelected>0) {
                		button.getModel().setSelected(false);
                		button.setIcon(new ImageIcon(AssetManager.getFourPlayerButton()));
                		isSelected--;
                	}else {
                		isSelected++;
                    button.setIcon(new ImageIcon(AssetManager.getPressedFourPlayerButton()));
                	}
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getFourPlayerButton()));
                }
            }
        });

        // Mode selection
        computerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                    button.setIcon(new ImageIcon(AssetManager.getPressedComputerButton()));
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getComputerButton()));
                }
            }
        });

        dynastyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                    button.setIcon(new ImageIcon(AssetManager.getPressedDynastyButton()));
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getDynastyButton()));
                }
            }
        });


        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                    button.setIcon(new ImageIcon(AssetManager.getPressedMultiplayerImage()));
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getMultiplayerImage()));
                }
            }
        });

        hintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                    button.setIcon(new ImageIcon(AssetManager.getPressedHintsSettingsButton()));
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getHintsSettingButton()));
                }
            }
        });


        soundEffectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton) e.getSource();
                Boolean isPressed = button.getModel().isSelected();
                if(isPressed){
                    button.setIcon(new ImageIcon(AssetManager.getPressedSoundEffectsButton()));
                }else{
                    button.setIcon(new ImageIcon(AssetManager.getSoundEffectsButton()));
                }
            }
        });

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

    private void refreshComponents() {

    }

}
