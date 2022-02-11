package ca.mcgill.ecse223.kingdomino.view;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class AssetManager {
    static private BufferedImage startBackgroundImage;
    static private BufferedImage startButtonImage;
    static private BufferedImage loadButtonImage;
    static private BufferedImage chestButtonImage;
    static private BufferedImage ruleButtonImage;
    static private BufferedImage soundButtonImage;
    static private BufferedImage kingButtonImage;
    static private BufferedImage gameBackgroundImage;
    static private BufferedImage backOfTile;
    static private BufferedImage buttonImage;
    static private BufferedImage castleImage;
    static private BufferedImage computerButton;
    static private BufferedImage pressedComputerButton;
    static private BufferedImage dynastyButton;
    static private  BufferedImage pressedDynastyButton;
    static private BufferedImage fourPlayerButton;
    static private BufferedImage pressedFourPlayerButton;
    static private BufferedImage goldKing;
    static private BufferedImage blueKing;
    static private BufferedImage redKing;
    static private BufferedImage hintButton;
    static private BufferedImage hintsSettingsButton;
    static private BufferedImage pressedHintsSettingsButton;
    static private BufferedImage magnifierImage;
    static private BufferedImage multiplayerImage;
    static private BufferedImage pressedMultiplayerImage;
    static private BufferedImage pressedButtonImage;
    static private BufferedImage pressedTileImage;
    static private BufferedImage settingsLabel;
    static private BufferedImage soundEffectsButton;
    static private BufferedImage pressedSoundEffectsButton;
    static private BufferedImage threePlayerButton;
    static private BufferedImage pressedThreePlayerButton;
    static private BufferedImage twoPlayerButton;
    static private BufferedImage pressedTwoPlayerButton;
    static private Font pixelType;
    static private BufferedImage smallBoard;
    static private BufferedImage greenKing;
    static private BufferedImage forestTile;
    static private BufferedImage waterTile;
    static private BufferedImage wheatField;
    static private BufferedImage mountainTile;
    static private BufferedImage swampTile;
    static private BufferedImage grassTile;
    static private BufferedImage noSoundButton;
    static private BufferedImage rules;
    static private BufferedImage hintsSettingButton;
    static private BufferedImage sound;
    static private BufferedImage pressedSound;
    static private BufferedImage firstPlayerColor;
    static private BufferedImage secondPlayerColor;
    static private BufferedImage thirdPlayerColor;
    static private BufferedImage fourthPlayerColor;
    static private BufferedImage kingIconGreen;
    static private BufferedImage kingIconBlue;
    static private BufferedImage kingIconGold;
    static private BufferedImage kingIconRed;
    static private BufferedImage playerNameBox;
    static private String soundName;  
    static private AudioInputStream audioInputStream;

    static public void preloadAllAssets() throws IOException, FontFormatException {
        startBackgroundImage = ImageIO.read(new File("./Assets/Pictures/firstScreenBackground.png"));
        startButtonImage = ImageIO.read(new File("./Assets/Pictures/startButton.png"));
        loadButtonImage = ImageIO.read(new File("./Assets/Pictures/loadButton.png"));
        chestButtonImage = ImageIO.read(new File("./Assets/Pictures/crest.png"));
        ruleButtonImage = ImageIO.read(new File("./Assets/Pictures/rulebook.png"));
        soundButtonImage = ImageIO.read(new File("./Assets/Pictures/sound.png"));
        kingButtonImage = ImageIO.read(new File("./Assets/Pictures/kingFace.png"));
        gameBackgroundImage = ImageIO.read(new File("./Assets/Pictures/background.png"));
        backOfTile = ImageIO.read(new File("./Assets/Pictures/backOfTile.png"));
        buttonImage = ImageIO.read(new File("./Assets/Pictures/button2.png"));
        castleImage = ImageIO.read(new File("./Assets/Pictures/castleTile.png"));
        computerButton = ImageIO.read(new File("./Assets/Pictures/computerButton.png"));
        dynastyButton = ImageIO.read(new File("./Assets/Pictures/dynastyButton.png"));
        fourPlayerButton = ImageIO.read(new File("./Assets/Pictures/fourPlayerButton.png"));
        goldKing = ImageIO.read(new File("./Assets/Pictures/goldKing.png"));
        hintButton = ImageIO.read(new File("./Assets/Pictures/hintButton.png"));
        hintsSettingsButton = ImageIO.read(new File("./Assets/Pictures/hintsSettingButton.png"));
        magnifierImage = ImageIO.read(new File("./Assets/Pictures/magnifier.png"));
        multiplayerImage = ImageIO.read(new File("./Assets/Pictures/multiplayerButton.png"));
        pressedButtonImage = ImageIO.read(new File("./Assets/Pictures/pressedButton.png"));
        pressedTileImage = ImageIO.read(new File("./Assets/Pictures/pressedTile.png"));
        settingsLabel = ImageIO.read(new File("./Assets/Pictures/settingsLabel.png"));
        soundEffectsButton = ImageIO.read(new File("./Assets/Pictures/soundEffectsButton.png"));
        threePlayerButton = ImageIO.read(new File("./Assets/Pictures/threePlayerButton.png"));
        twoPlayerButton = ImageIO.read(new File("./Assets/Pictures/twoPlayerButton.png"));
        pixelType = Font.createFont(Font.TRUETYPE_FONT,new File("./Assets/Fonts/Pixeltype.ttf"));
        smallBoard = ImageIO.read(new File("./Assets/Pictures/smallBoard.png"));
        greenKing = ImageIO.read(new File("./Assets/Pictures/resizedGreen2.png"));
        waterTile = ImageIO.read(new File("./Assets/Pictures/water.png"));
        wheatField = ImageIO.read(new File("./Assets/Pictures/wheatfield.png"));
        forestTile = ImageIO.read(new File("./Assets/Pictures/forest.png"));
        blueKing = ImageIO.read(new File("./Assets/Pictures/kingBlue.png"));
        redKing = ImageIO.read(new File("./Assets/Pictures/kingRed.png"));
        noSoundButton = ImageIO.read(new File("./Assets/Pictures/noSoundButton.png"));
        pressedComputerButton =  ImageIO.read(new File("./Assets/Pictures/pressedComputerButton.png"));
        pressedFourPlayerButton = ImageIO.read(new File("./Assets/Pictures/pressedFourPlayerButton.png"));
        pressedHintsSettingsButton = ImageIO.read(new File("./Assets/Pictures/pressedHintsButton.png"));
        pressedMultiplayerImage = ImageIO.read(new File("./Assets/Pictures/pressedMultiPlayerButton.png"));
        pressedSoundEffectsButton = ImageIO.read(new File("./Assets/Pictures/pressedSoundEffectsButton.png"));
        pressedThreePlayerButton = ImageIO.read(new File("./Assets/Pictures/pressedThreeplayerButton.png"));
        pressedTwoPlayerButton = ImageIO.read(new File("./Assets/Pictures/pressedTwoplayerButton.png"));
        swampTile = ImageIO.read(new File("./Assets/Pictures/swamp.png"));
        grassTile = ImageIO.read(new File("./Assets/Pictures/grass.png"));
        mountainTile = ImageIO.read(new File("./Assets/Pictures/mountain.png"));
        rules = ImageIO.read(new File("./Assets/Pictures/rules.png"));
        pressedDynastyButton = ImageIO.read(new File("./Assets/Pictures/pressedDynastyButton.png"));
        hintsSettingButton = ImageIO.read(new File("./Assets/Pictures/hintsSettingButton.png"));
        sound = ImageIO.read(new File("./Assets/Pictures/sound.png"));
        pressedSound = ImageIO.read(new File("./Assets/Pictures/noSoundButton.png"));
        firstPlayerColor = ImageIO.read(new File("./Assets/Pictures/playerMenuTwoGreen.png"));
	    secondPlayerColor = ImageIO.read(new File("./Assets/Pictures/playerMenuTwoBlue.png"));
	    thirdPlayerColor = ImageIO.read(new File("./Assets/Pictures/playerMenuFourGold.png"));
	    fourthPlayerColor = ImageIO.read(new File("./Assets/Pictures/playerMenuFourRedColor.png"));
	    kingIconGreen = ImageIO.read(new File("./Assets/Pictures/kingGreenIcon.png"));
	    kingIconBlue = ImageIO.read(new File("./Assets/Pictures/kingBlueIcon.png"));
	    kingIconGold = ImageIO.read(new File("./Assets/Pictures/kingGoldIcon.png"));
	    kingIconRed = ImageIO.read(new File("./Assets/Pictures/kingRedIcon.png"));
	    playerNameBox = ImageIO.read(new File("./Assets/Pictures/playerNameBox.png"));
	    soundName = "./Assets/Sounds/backgroundMusic.wav";

		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	   
	    
	    
    }
    public static AudioInputStream getSoundName() {
    	return audioInputStream;
    }
    public static BufferedImage getPlayerNameBox() {
    	return playerNameBox;
    }
    public static BufferedImage getFirstPlayerIcon() {
    	return kingIconGreen;
    }
    public static BufferedImage getSecondPlayerIcon() {
    	return kingIconBlue;
    }
    public static BufferedImage getThirdPlayerIcon() {
    	return kingIconGold;
    }
    public static BufferedImage getFourthPlayerIcon() {
    	return kingIconRed;
    }
    
    public static BufferedImage getFirstPlayerColor() {
    	return firstPlayerColor;
    }
    public static BufferedImage getSecondPlayerColor() {
    	return secondPlayerColor;
    }
    public static BufferedImage getThirdPlayerColor() {
    	return thirdPlayerColor;
    }
    public static BufferedImage getFourthPlayerColor() {
    	return fourthPlayerColor;
    }
    
    public static BufferedImage getHintsSettingButton() {
    	return hintsSettingButton;
    }
    
    public static BufferedImage getSoundButton() {
    	return sound;
    }
    
    public static BufferedImage getPressedSoundButton() {
    	return noSoundButton;
    }
    
    public static BufferedImage getPressedDynastyButton() {
        return pressedDynastyButton;
    }

    public static BufferedImage getMountainTile() {
        return mountainTile;
    }

    public static BufferedImage getSwampTile() {
        return swampTile;
    }

    public static BufferedImage getGrassTile() {
        return grassTile;
    }

    public static BufferedImage getRules() {
        return rules;
    }

    public static BufferedImage getPressedComputerButton() {
        return pressedComputerButton;
    }

    public static BufferedImage getPressedFourPlayerButton() {
        return pressedFourPlayerButton;
    }

    public static BufferedImage getBlueKing() {
        return blueKing;
    }

    public static BufferedImage getRedKing() {
        return redKing;
    }

    public static BufferedImage getPressedHintsSettingsButton() {
        return pressedHintsSettingsButton;
    }

    public static BufferedImage getPressedMultiplayerImage() {
        return pressedMultiplayerImage;
    }

    public static BufferedImage getPressedSoundEffectsButton() {
        return pressedSoundEffectsButton;
    }

    public static BufferedImage getPressedThreePlayerButton() {
        return pressedThreePlayerButton;
    }

    public static BufferedImage getPressedTwoPlayerButton() {
        return pressedTwoPlayerButton;
    }

    public static BufferedImage getNoSoundButton() {
        return noSoundButton;
    }

    public static BufferedImage getForestTile() {
        return forestTile;
    }

    public static BufferedImage getWaterTile() {
        return waterTile;
    }

    public static BufferedImage getWheatField() {
        return wheatField;
    }

    public static BufferedImage getGreenKing() {
        return greenKing;
    }

    public static BufferedImage getSmallBoard() {
        return smallBoard;
    }

    public static BufferedImage getStartBackgroundImage() {
        return startBackgroundImage;
    }

    public static Font getPixelType() {
        return pixelType;
    }

    public static BufferedImage getStartButtonImage() {
        return startButtonImage;
    }

    public static BufferedImage getLoadButtonImage() {
        return loadButtonImage;
    }

    public static BufferedImage getChestButtonImage() {
        return chestButtonImage;
    }

    public static BufferedImage getRuleButtonImage() {
        return ruleButtonImage;
    }

    public static BufferedImage getSoundButtonImage() {
        return soundButtonImage;
    }

    public static BufferedImage getKingButtonImage() {
        return kingButtonImage;
    }

    public static BufferedImage getGameBackgroundImage() {
        return gameBackgroundImage;
    }

    public static BufferedImage getBackOfTile() {
        return backOfTile;
    }

    public static BufferedImage getButtonImage() {
        return buttonImage;
    }

    public static BufferedImage getCastleImage() {
        return castleImage;
    }

    public static BufferedImage getComputerButton() {
        return computerButton;
    }

    public static BufferedImage getDynastyButton() {
        return dynastyButton;
    }

    public static BufferedImage getFourPlayerButton() {
        return fourPlayerButton;
    }

    public static BufferedImage getGoldKing() {
        return goldKing;
    }

    public static BufferedImage getHintButton() {
        return hintButton;
    }

    public static BufferedImage getHintsSettingsButton() {
        return hintsSettingsButton;
    }

    public static BufferedImage getMagnifierImage() {
        return magnifierImage;
    }

    public static BufferedImage getMultiplayerImage() {
        return multiplayerImage;
    }

    public static BufferedImage getPressedButtonImage() {
        return pressedButtonImage;
    }

    public static BufferedImage getPressedTileImage() {
        return pressedTileImage;
    }

    public static BufferedImage getSettingsLabel() {
        return settingsLabel;
    }

    public static BufferedImage getSoundEffectsButton() {
        return soundEffectsButton;
    }

    public static BufferedImage getThreePlayerButton() {
        return threePlayerButton;
    }

    public static BufferedImage getTwoPlayerButton() {
        return twoPlayerButton;
    }
}
