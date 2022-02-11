package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class KingdominoBrowsingPage extends JFrame{
	private JComboBox<String> option;
	private JButton browse;
	private JLabel message1;
	private JLabel message2;
	private JLabel message3;
	private JTextField idField;
	private JPanel allDominos;
	private JButton exit;
	private JFrame browsingFrame = this;
	private JLabel instruction;
	
	private String msg;
	private String msg2;
	private String msg3;
	
	public KingdominoBrowsingPage() {
        initComponents();
        refreshData();
    }
	public void initComponents() {
		setContentPane(new JLabel(new ImageIcon(AssetManager.getStartBackgroundImage())));
		Container c = getContentPane();
		c.setPreferredSize(new Dimension(1280,720));
		setLayout(null);
		
		option = new JComboBox<String>();
		option.addItem("All");
		option.addItem("By ID");
		option.addItem("By landscape");
		option.setLocation(610, 350);
		option.setSize(new Dimension(60,20));
		option.setSelectedIndex(-1);
		c.add(option);
		
		browse = new JButton("Browse");
		browse.setFont(new Font("Dialog",Font.PLAIN,10));
		browse.setSize(new Dimension(130, 40));
        browse.setLocation(585,420);
        browse.setForeground(new Color(252, 193, 51));
        browse.setBackground(Color.cyan);
        browse.setOpaque(true);
        browse.setBorderPainted(false);
        c.add(browse);
        browse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				browseActionPerformed(evt);
			}
		});
		
        message1 = new JLabel();
        message1.setForeground(new Color(252, 193, 51));
        message1.setSize(new Dimension(1280, 100));
        message1.setLocation(0, 430);
		c.add(message1);
		
		message2 = new JLabel();
        message2.setForeground(new Color(252, 193, 51));
        message2.setSize(new Dimension(1280, 100));
        message2.setLocation(0, 450);
		c.add(message2);
		
		message3 = new JLabel();
        message3.setForeground(new Color(252, 193, 51));
        message3.setSize(new Dimension(1280, 100));
        message3.setLocation(0, 470);
		c.add(message3);
		
		instruction = new JLabel("Please select how to browse.");
		instruction.setForeground(new Color(252, 193, 51));
		instruction.setLocation(550,300);
		instruction.setSize(new Dimension(300,40));
		c.add(instruction);
		
		idField = new JTextField();
		idField.setLocation(615, 380);
		idField.setSize(new Dimension(50,30));
		c.add(idField);
		
		exit = new JButton("X");
		exit.setSize(new Dimension(30, 30));
		exit.setForeground(Color.red);
		exit.setLocation(1220, 80);
		c.add(exit);
		

        // Button actions
		
		exit.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				browsingFrame.setVisible(false);
			}
		});
       
		pack();
		
	}
	public void refreshData() {
		message1.setText(msg);
		message2.setText(msg2);
		message3.setText(msg3);
		idField.setText("");
		option.setSelectedIndex(-1);
		
	}
	private void browseActionPerformed(ActionEvent evt) {
		msg = "";
		int index = option.getSelectedIndex();
		if(index == 0) {
			String all = "1:W+W" +"2:W+W" +"3:F+F"+ "4:F+F" +"5:F+F" +"6:L+L" +"7:L+L"+ "8:L+L" + "9:L+L" + 
"10:G+G" + 
					"11:G+G" + 
					"12:S+S" + 
					"13:W+F" + 
					"14:W+L" + 
					"15:W+G" + 
					"16:W+S" + 
					"17:F+L" + 
					"18:F+G" + 
					"19:F+W(1)" + 
					"20:L+W(1)" + 
					"21:G+W(1)" + 
					"22:S+W(1)" + 
					"23:M+W(1)" + 
					"24:W+F(1)" + 
					"25:W+F(1)" ;
			msg = all.trim();
			String all2 = "26:W+F(1)" + 
					"27:W+F(1)" + 
					"28:L+F(1)" + 
					"29:G+F(1)" + 
					"30:W+L(1)" + 
					"31:W+L(1)" + 
					"32:F+L(1)" + 
					"33:F+L(1)" + 
					"34:F+L(1)" + 
					"35:F+L(1)" + 
					"36:W+G(1)" + 
					"37:L+G(1)" + 
					"38:W+S(1)" + 
					"39:G+S(1)" + 
					"40:W+M(1)" + 
					"41:W+G(2)" + 
					"42:L+G(2)" + 
					"43:W+S(2)" + 
					"44:G+S(2)"
					;
			msg2 = all2.trim();
			String all3 = "45:W+M(2)" + 
					"46:S+M(2)" + 
					"47:S+M(2)" + 
					"48:W+M(3)";
			msg3 = all3.trim();
			refreshData();
		}
		if(index == 1) {
			msg = "";
			int id = 1;
			try {
				id = Integer.parseInt(idField.getText());
			}
			catch (NumberFormatException e) {
				msg = "Id needs to be a numerical value!";
			}
			TerrainType leftType = KingdominoController.getdominoByID(id).getLeftTile();
			TerrainType rightType = KingdominoController.getdominoByID(id).getRightTile();
			int crownLeft = KingdominoController.getdominoByID(id).getLeftCrown();
			int crownRight = KingdominoController.getdominoByID(id).getRightCrown();
			msg = "The left tile of this domino is "+leftType+", the right tile of this domino is "+rightType+". The left tile has "+crownLeft+"crowns, and the right tile has "+crownRight+" Crowns";
		}
		if(index == 2) {
			ArrayList<Domino> selectedByTerrainType = KingdominoController.getDominoByTerrainType(idField.getText());
			ArrayList<Integer> ids = new ArrayList<Integer>(); 
			for (Domino d: selectedByTerrainType) {
				ids.add(d.getId());
			}
			msg = ids.toString();
		}
	}


	
}
