package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DominoPanel extends JPanel {

	private String panelDirection;

	private JButton leftTile;
	private JButton rightTile;
	private DominoPanel curPanel;
	private int id;
	private int crowns;
	private boolean selection = false;

	public DominoPanel(String direction, ImageIcon left, ImageIcon right) {
		super();
		panelDirection = direction;
		leftTile = new JButton(left);
		rightTile = new JButton(right);
		addToPanel();
		curPanel = this;
	}

	//
	private void addToPanel() {
		this.setLayout(null);
		this.add(leftTile);

		// set Bounds depending on direction
		if (panelDirection.equalsIgnoreCase("right")) {
			leftTile.setBounds(0, 0, 83, 53);
			rightTile.setBounds(83, 0, 83, 53);
		} else if (panelDirection.equalsIgnoreCase("left")) {
			leftTile.setBounds(83, 0, 83, 53);
			rightTile.setBounds(0, 0, 83, 53);
		} else if (panelDirection.equalsIgnoreCase("up")) {
			leftTile.setBounds(0, 53, 83, 53);
			rightTile.setBounds(0, 0, 83, 53);
		} else if (panelDirection.equalsIgnoreCase("down")) {
			leftTile.setBounds(0, 0, 83, 53);
			rightTile.setBounds(0, 53, 83, 53);
		}

		JPopupMenu options = new JPopupMenu();
		JMenuItem rotateCw = new JMenuItem("rotate clockwise");
		rotateCw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curPanel.rotatePanel("clockwise");
			}
		});

		JMenuItem rotateCcw = new JMenuItem("rotate counter clockwise");
		rotateCcw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curPanel.rotatePanel("counter clockwise");
			}
		});

		JMenuItem selectDomino = new JMenuItem("select this domino");
		selectDomino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoController.selectDominoUI(id, curPanel);
			}
		});

		JMenuItem discard = new JMenuItem("discard this domino");
		discard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curPanel.discardDomino();
			}
		});

		options.add(rotateCw);
		options.add(rotateCcw);
		options.add(selectDomino);
		options.add(discard);
		leftTile.setComponentPopupMenu(options);
		rightTile.setComponentPopupMenu(options);

		this.add(rightTile);

	}

	public void setHandler() {
		if (selection) {
			leftTile.setTransferHandler(new ValueExportTransferHandler("test"));
			leftTile.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					KingdominoGamePage.curPanel = curPanel;
					KingdominoGamePage.buttonDirection = panelDirection;
					JButton button = (JButton) e.getSource();
					TransferHandler handle = button.getTransferHandler();
					handle.exportAsDrag(button, e, TransferHandler.COPY);

				}
			});
		}
	}
	

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}

	protected void discardDomino() {
		KingdominoController.discardDominoUI(id);
		curPanel.setVisible(false);

	}

	public void selectDomino() {
		KingdominoController.selectDominoUI(id, this);
	}

	public void rotatePanel(String rotationDirection) {

		if (rotationDirection.equals("clockwise")) {
			// Clockwise rotation
			switch (panelDirection) {
			case "right":
				panelDirection = "down";
				this.setSize(new Dimension(83, 106));
				rightTile.setLocation(0, 53);
				repaint();
				break;
			case "left":
				panelDirection = "up";
				this.setSize(new Dimension(83, 106));
				rightTile.setLocation(0, 0);
				leftTile.setLocation(0, 53);
				repaint();
				break;
			case "up":
				panelDirection = "right";
				this.setSize(new Dimension(166, 53));
				rightTile.setLocation(83, 0);
				leftTile.setLocation(0, 0);
				repaint();
				break;
			case "down":
				panelDirection = "left";
				this.setSize(new Dimension(166, 53));
				rightTile.setLocation(0, 0);
				leftTile.setLocation(83, 0);
				repaint();
				break;
			}
		} else {
			switch (panelDirection) {
			case "right":
				panelDirection = "up";
				this.setSize(new Dimension(83, 106));
				rightTile.setLocation(0, 0);
				leftTile.setLocation(0, 53);
				repaint();
				break;
			case "left":
				panelDirection = "down";
				this.setSize(new Dimension(83, 106));
				rightTile.setLocation(0, 53);
				leftTile.setLocation(0, 0);
				repaint();
				break;
			case "up":
				panelDirection = "left";
				this.setSize(new Dimension(166, 53));
				rightTile.setLocation(0, 0);
				leftTile.setLocation(83, 0);
				repaint();
				break;
			case "down":
				panelDirection = "right";
				this.setSize(new Dimension(166, 53));
				rightTile.setLocation(83, 0);
				leftTile.setLocation(0, 0);
				repaint();
				break;
			}
		}
	}

	// getters and setters
	public String getPanelDirection() {
		return panelDirection;
	}

	public JButton getLeftTile() {
		return leftTile;
	}

	public JButton getRightTile() {
		return rightTile;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCrowns() {
		return crowns;
	}

	public void setCrowns(int crowns) {
		this.crowns = crowns;
	}

	// Extra classes for drag and drop
	public class ValueExportTransferHandler extends TransferHandler {
		public final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
		private String value;

		public ValueExportTransferHandler(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return DnDConstants.ACTION_COPY_OR_MOVE;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			Transferable t = new StringSelection(getValue());
			return t;
		}

		@Override
		protected void exportDone(JComponent source, Transferable data, int action) {
			super.exportDone(source, data, action);
			// Decide what to do after the drop has been accepted
		}

	}

}
