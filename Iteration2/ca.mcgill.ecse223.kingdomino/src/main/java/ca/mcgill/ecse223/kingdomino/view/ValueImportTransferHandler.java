package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.TransferHandler;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

public class ValueImportTransferHandler extends TransferHandler {

	private int currentplayer;
	private ArrayList <JButton[][]> playerBoards;
	
	public final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;

	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
		return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport support) {
		boolean accept = false;
		if (canImport(support)) {
			try {
				Transferable t = support.getTransferable();
				Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
				if (value instanceof String) {
					Component component = support.getComponent();
					if (component instanceof JButton) {
						
						currentplayer= KingdominoApplication.gamePage.getCurrentPlayer();
						playerBoards = KingdominoApplication.gamePage.getPlayerBoards();
						JButton temp;
						JButton boardButton = (JButton) component;
						// Find the button on the grid
						// Determine if the domino could be placed there
						int[] buttonCoordinates = KingdominoApplication.gamePage.getButtonCoordinates(component, playerBoards.get(currentplayer));

						// Map the coordinates to game coordinates
						int yGameCoordinates = 4 - buttonCoordinates[0];
						int xGameCoordinates = buttonCoordinates[1] - 4;

						// Validate placement with controller call

						// Change the buttons on the grid if it fits
						boolean place = KingdominoController.placeDominoUI(xGameCoordinates, yGameCoordinates,
								KingdominoGamePage.buttonDirection);
						if (place) {
							switch (KingdominoGamePage.buttonDirection) {
							case "right":
								boardButton.setIcon(KingdominoGamePage.curPanel.getLeftTile().getIcon());
								boardButton.setTransferHandler(null);
								temp = KingdominoApplication.gamePage.getButton(playerBoards.get(currentplayer), buttonCoordinates[0],
										buttonCoordinates[1] + 1);
								temp.setIcon(KingdominoGamePage.curPanel.getRightTile().getIcon());
								// Remove the ability to place something on this button
								temp.setTransferHandler(null);
								break;
							case "left":
								boardButton.setIcon(KingdominoGamePage.curPanel.getLeftTile().getIcon());
								boardButton.setTransferHandler(null);
								temp = KingdominoApplication.gamePage.getButton(playerBoards.get(currentplayer), buttonCoordinates[0],
										buttonCoordinates[1] - 1);
								temp.setIcon(KingdominoGamePage.curPanel.getRightTile().getIcon());
								temp.setTransferHandler(null);
								break;
							case "down":
								boardButton.setIcon(KingdominoGamePage.curPanel.getLeftTile().getIcon());
								boardButton.setTransferHandler(null);
								temp = KingdominoApplication.gamePage.getButton(playerBoards.get(currentplayer), buttonCoordinates[0] + 1,
										buttonCoordinates[1]);
								temp.setIcon(KingdominoGamePage.curPanel.getRightTile().getIcon());
								temp.setTransferHandler(null);
								break;
							case "up":
								boardButton.setIcon(KingdominoGamePage.curPanel.getLeftTile().getIcon());
								boardButton.setTransferHandler(null);
								temp = KingdominoApplication.gamePage.getButton(playerBoards.get(currentplayer), buttonCoordinates[0] - 1,
										buttonCoordinates[1]);
								temp.setIcon(KingdominoGamePage.curPanel.getRightTile().getIcon());
								temp.setTransferHandler(null);
								break;
							}
						}
						accept = true;
					}
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		return accept;
	}
}