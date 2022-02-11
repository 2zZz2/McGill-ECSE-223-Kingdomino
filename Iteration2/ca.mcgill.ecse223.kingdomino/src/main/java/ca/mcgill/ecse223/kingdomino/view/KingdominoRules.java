package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class KingdominoRules extends JFrame {

    private JButton exit;
    public JFrame rulesFrame = this;

    public KingdominoRules() {
        initComponents();
    }

    private void initComponents() {
        setContentPane(new JLabel(new ImageIcon(AssetManager.getRules())));
        Container c = getContentPane();
        c.setPreferredSize(new Dimension(1280, 720));

        try {
            //BufferedImage button = ImageIO.read(new File("/Users/mac/Documents/GitHub/ecse223-group-project-08/Iteration2/ca.mcgill.ecse223.kingdomino/Assets/Pictures/exit.png"));
            exit = new JButton("X");
            exit.setSize(new Dimension(50, 50));
            exit.setForeground(Color.red);
            exit.setLocation(1200, 100);
            //exit.setOpaque(false);
            //exit.setContentAreaFilled(false);
            //exit.setBorderPainted(false);
            c.add(exit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                rulesFrame.setVisible(false);
            }
        });

        pack();
    }
}