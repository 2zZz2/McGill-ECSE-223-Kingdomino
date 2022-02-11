package ca.mcgill.ecse223.kingdomino.application;

import java.awt.FontFormatException;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.view.AssetManager;
import ca.mcgill.ecse223.kingdomino.view.KingdominoEndScreen;
import ca.mcgill.ecse223.kingdomino.view.KingdominoGamePage;
import ca.mcgill.ecse223.kingdomino.view.KingdominoSettingsPage;
import ca.mcgill.ecse223.kingdomino.view.KingdominoStartPage;
import ca.mcgill.ecse223.kingdomino.view.UIDominos;

public class KingdominoApplication {

	private static Kingdomino kingdomino;
	private static Gameplay gameplay;
	public static KingdominoGamePage gamePage;
	public static KingdominoSettingsPage settingsPage;
	public static KingdominoStartPage startPage;
	public static KingdominoEndScreen endPage;

	public static void main(String[] args) {
		try {
			AssetManager.preloadAllAssets();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UIDominos.createAllUIDominoes();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				settingsPage = new KingdominoSettingsPage();
				startPage = new KingdominoStartPage();
				startPage.setVisible(true);
			}
		});
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}

	public static void setGameplay(Gameplay gp) {
		gameplay = gp;
	}

	public static Gameplay getGameplay() {
		if (gameplay == null) {
			gameplay = new Gameplay();
		}
		return gameplay;
	}
}
