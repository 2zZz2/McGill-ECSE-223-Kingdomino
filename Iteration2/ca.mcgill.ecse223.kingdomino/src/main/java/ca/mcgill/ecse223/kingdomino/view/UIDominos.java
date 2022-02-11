package ca.mcgill.ecse223.kingdomino.view;
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;

 import javax.swing.ImageIcon;

 public class UIDominos {

 	private static ArrayList<DominoPanel> dominoList = new ArrayList<>();


 	public static void createAllUIDominoes() {
 		try {
 			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
 			String line = "";
 			String delimiters = "[:\\+()]";
 			while ((line = br.readLine()) != null) {
 				String[] dominoString = line.split(delimiters);
 				int dominoId = Integer.decode(dominoString[0]);
 				ImageIcon leftTerrain = getTerrainType(dominoString[1]);
 				ImageIcon rightTerrain = getTerrainType(dominoString[2]);
 				int numCrown = 0;
 				if (dominoString.length > 3) {
 					numCrown = Integer.decode(dominoString[3]);
 				}
 				DominoPanel panel = new DominoPanel("right", leftTerrain, rightTerrain);
 				panel.setId(dominoId);
 				panel.setCrowns(numCrown);
 				dominoList.add(panel);
 			}
 			br.close();
 		} catch (IOException e) {
 			e.printStackTrace();
 			throw new java.lang.IllegalArgumentException(
 					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
 		}
 	}


 	private static ImageIcon getTerrainType(String string){
 		switch (string) {
 		case "W":
 			return new ImageIcon(AssetManager.getWheatField());
 		case "F":
 			return new ImageIcon(AssetManager.getForestTile());
 		case "M":
 			return new ImageIcon(AssetManager.getMountainTile());
 		case "G":
 			return new ImageIcon(AssetManager.getGrassTile());
 		case "S":
 			return new ImageIcon(AssetManager.getSwampTile());
 		case "L":
 			return new ImageIcon(AssetManager.getWaterTile());
 		default:
 			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + string);
 		}
 	}

 	public static DominoPanel getUIDominoById(int id) {
 		return dominoList.get(id-1);
 	}


 }

