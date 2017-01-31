/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
/**
 *
 * @author jbray_49
 */
public class Panel extends JFrame{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel3_1;
    private JButton train;
    private JButton fight;
    private JButton exit;
    private JButton about;
    private JTextField author;
    private JTextArea description;
    private ImageIcon coverPhoto;
    private JLabel label;
    private Trainer trainerFrame;
    private Fight fightFrame;
 	private JFrame quickFrame;
    private JTextArea quickText;
    private BufferedReader quickRead;
    private JScrollPane qviewscroll;
    
    public Panel(Codemon CM){
        /*!!!!!*/
        trainerFrame = new Trainer();
        trainerFrame.setVisible(false);
        
        /*!!!!!*/
        fightFrame = new Fight();
        fightFrame.setVisible(false);
        
        panel = new JPanel();
        panel.setVisible(true);
        panel.setLayout (new GridLayout(2,1));
        
        setSize(WIDTH, HEIGHT);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        quickFrame = new JFrame();
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quickText = new JTextArea();
        quickText.setEditable(false);
        
        /***************** Top containing photo or description *****************/
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        description = new JTextArea("\n\n\n" + "    Welcome to the Codemon Center"
                + "\n\n" + "   Click to Train button to develope new Codemons to submit for battle" + "\n"
                + "  The first button allows you to submit Codemon for tests and PVP" + "\n  The About button contains info on this assigment "
                + "\n  and Exit closes the Codemon Center");
        description.setEditable(false);
        panel2.add(description, BorderLayout.CENTER);
        
        panel.add(panel2);
        
        /***************** Bottom containing buttons and author tag *****************/
        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        
        panel3_1 = new JPanel();
        panel3_1.setLayout(new GridLayout(2,2));
        train = new JButton("Train");
        train.setPreferredSize(new Dimension(20,20));
        train.addActionListener(new TrainListener());
        
        fight = new JButton("Fight");
        fight.setPreferredSize(new Dimension(20,20));
        fight.addActionListener(new FightListener());
        
        exit = new JButton ("Exit");
        exit.setPreferredSize(new Dimension(20,20));
        exit.addActionListener(new ExitListener());
        
        about = new JButton ("About");
        about.setPreferredSize(new Dimension(20,20));
        about.addActionListener(new AboutListener());
        
        author = new JTextField("CIS*2750F15- Author: Braydon Johnson");
        author.setHorizontalAlignment(JTextField.CENTER);
        author.setEditable(false);
        
        panel3_1.add(train);
        panel3_1.add(fight);
        panel3_1.add(exit);
        panel3_1.add(about);
        
        panel3.add(panel3_1, BorderLayout.CENTER);
        panel3.add(author, BorderLayout.SOUTH);
        
        panel.add(panel3);
        
        add(panel);
    }
    
    /*public JButton getTrainButton(){
        return train;
    }
    
    public JButton getFightButton(){
        return fight;
    }
    
    public JPanel getPanel(){
        return panel;
    }
    
    public void setTrue(){
        panel.setVisible(true);
    }
    
    public void setFalse(){
        panel.setVisible(false);
    }*/
    
    public class ExitListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    public class AboutListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
        try{
            quickRead = new BufferedReader(new FileReader("../README"));
            quickText.read(quickRead, null);
            qviewscroll = new JScrollPane(quickText);
        	qviewscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	qviewscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            quickFrame.getContentPane().removeAll();
            quickFrame.add(qviewscroll);
            quickFrame.setVisible(true);
            } catch (FileNotFoundException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    public class TrainListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            trainerFrame.setVisible(true);
        }
    }
    
    public class FightListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fightFrame.setVisible(true);
        }
    }
}
