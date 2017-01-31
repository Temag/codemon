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
import java.awt.LayoutManager;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.KeyStroke;
/**
 *
 * @author jbray_49
 */
public class Trainer extends JFrame{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private KeyStroke accelerator;
    private Codemon CM;
    private JPanel panel;
    private JMenu file;
    private JMenu compile;
    private JMenu config;
    private JMenu help;
    private BufferedReader br;
    private BufferedWriter writer;
    private JTextArea text;
    private File files;
    private JFileChooser fileChooser;
    private JFileChooser directoryChooser;
    private int returnVal;
    private JLabel bottom_label;
    private TitledBorder border;
    //private FileFilter filter;
    private String file_name = "New";
    private String file_path;
    private String path;
    private String modified = "Not Modified";
    private String reader;
    
    private File config_file;
    private String c_path = "../code";
    private String s_path = "../Source";
    
    private JFrame quickFrame;
    private JTextArea quickText;
    
    /*!!!!!*/
    public Trainer(){
        CM = new Codemon();
        get_Config();
        fileChooser = new JFileChooser(s_path);
        //fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Codemon File: .cm, .codemon", "cm", "codemon");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        directoryChooser = new JFileChooser("../");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        quickFrame = new JFrame();
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quickText = new JTextArea();
        quickText.setEditable(false);
        
        /***************** Main panel containing menu and all other panels *****************/
        panel = new JPanel();
        panel.setVisible(true);
        LayoutManager layout = new BorderLayout();
        panel.setLayout(layout);
        
        setSize(WIDTH, HEIGHT);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        JMenuBar bar = new JMenuBar();
        
        file = new JMenu("File");
        JMenuItem New = new JMenuItem("New");
        New.addActionListener(new new_fileActionListener());
        KeyStroke ctrln = KeyStroke.getKeyStroke("control shift N");
        New.setAccelerator(ctrln);
        
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new open_fileActionListener());
        KeyStroke ctrlo = KeyStroke.getKeyStroke("control O");
        open.setAccelerator(ctrlo);
        
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new save_fileActionListener());
        KeyStroke ctrls = KeyStroke.getKeyStroke("control S");
        save.setAccelerator(ctrls);
        
        JMenuItem save_as = new JMenuItem("Save As");
        save_as.addActionListener(new save_file_asActionListener());
        KeyStroke ctrlss = KeyStroke.getKeyStroke("control shift S");
        save_as.setAccelerator(ctrlss);
        
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new quitActionListener());
        KeyStroke ctrlq = KeyStroke.getKeyStroke("control shift Q");
        quit.setAccelerator(ctrlq);
        
        file.add(New);
        file.add(open);
        file.add(save);
        file.add(save_as);
        file.add(quit);
        bar.add(file);
        
        
        compile = new JMenu("Build");
        JMenuItem compile1 = new JMenuItem("Assemble");
        compile1.addActionListener(new runActionListener());
        KeyStroke ctrla = KeyStroke.getKeyStroke("control A");
        compile1.setAccelerator(ctrla);
        
        JMenuItem compile_run = new JMenuItem("Assemble and Launch");
        compile_run.addActionListener(new assemblerunActionListener());
        KeyStroke ctrlsa = KeyStroke.getKeyStroke("control shift A");
        compile_run.setAccelerator(ctrlsa);
        
        compile.add(compile1);
        compile.add(compile_run);
        bar.add(compile);
        
        
        config = new JMenu("Config");
        JMenuItem source_directory = new JMenuItem("Source Directory");
        source_directory.addActionListener(new sourceActionListener());
        KeyStroke ctrlw = KeyStroke.getKeyStroke("control W");
        source_directory.setAccelerator(ctrlw);
        
        JMenuItem codemon_directory = new JMenuItem("Codemon Directory");
        codemon_directory.addActionListener(new codemonActionListener());
        KeyStroke ctrle = KeyStroke.getKeyStroke("control E");
        codemon_directory.setAccelerator(ctrle);
        
        config.add(source_directory);
        config.add(codemon_directory);
        bar.add(config);
        
        
        help = new JMenu("Help");
        JMenuItem help1 = new JMenuItem("Help");
        help1.addActionListener(new helpActionListener());
        KeyStroke ctrlh = KeyStroke.getKeyStroke("control H");
        help1.setAccelerator(ctrlh);
        
        help.add(help1);        
        bar.add(help);
        
        panel.add(bar, BorderLayout.NORTH);
        
        /***************** Contains text area as well as button icons *****************/
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        text = new JTextArea();
        text.getDocument().addDocumentListener(new textListener());
        text.getDocument().putProperty("name", "Text Area");
        text.setEditable(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        border = new TitledBorder(file_name);
        scroll.setBorder(border);
        panel2.add(scroll, BorderLayout.CENTER);
        
        
        JPanel panel2_1 = new JPanel();
        panel2_1.setLayout(new GridLayout(1,7));
        
        Icon new_file_icon = new ImageIcon("../buttons/new_file.png");
        JButton new_file = new JButton(new_file_icon);
        new_file.setToolTipText("New File");
        new_file.addActionListener(new new_fileActionListener());
        panel2_1.add(new_file);
        
        Icon open_file_icon = new ImageIcon("../buttons/open_file.png");
        JButton open_file = new JButton(open_file_icon);
        open_file.setToolTipText("Open");
        open_file.addActionListener(new open_fileActionListener());
        panel2_1.add(open_file);
        
        Icon save_file_icon = new ImageIcon("../buttons/save_file.png");
        JButton save_file = new JButton(save_file_icon);
        save_file.setToolTipText("Save");
        save_file.addActionListener(new save_fileActionListener());
        panel2_1.add(save_file);
        
        Icon save_file_as_icon = new ImageIcon("../buttons/save_file_as.png");
        JButton save_file_as = new JButton(save_file_as_icon);
        save_file_as.setToolTipText("Save As");
        save_file_as.addActionListener(new save_file_asActionListener());
        panel2_1.add(save_file_as);
        
        Icon run_icon = new ImageIcon("../buttons/run_2.png");
        JButton run_program = new JButton(run_icon);
        run_program.addActionListener(new runActionListener());
        run_program.setToolTipText("Assemble");
        panel2_1.add(run_program);
        
        panel2.add(panel2_1, BorderLayout.NORTH);
        
        bottom_label = new JLabel("Current Project: " + file_name + " [" + modified + "]");
        bottom_label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(bottom_label, BorderLayout.SOUTH);
        panel.add(panel2, BorderLayout.CENTER);
        
        add(panel);
    }
    
    /*
    setFileTitle:
    Sets the file identifier in the corner of the text area
    Arguments: None
    Returns: None
    */
    public void setFileTitle(){
        border.setTitle(file_name);
        bottom_label.setText("Current Project: " + file_name + " [" + modified + "]");
        panel.revalidate();
        panel.repaint();
    }
    
    /*
    setModified:
    Sets and unsets the modified flag and updates the bottom panel label
    Arguments: None
    Returns: None
    */
    public void setModified(int modified){
        if(modified == 1){
            this.modified = "Modified";
        }
        else{
            this.modified = "Not Modified";
        }
        bottom_label.setText("Current Project: " + file_name + " [" + this.modified + "]");
        panel.revalidate();
        panel.repaint();
    }
    

    /*
    saveAs:
    Updates the directory pathwats and opens a file dialogue so the user may
    choose the file the wish to save their text too
    Exceptions: Exception, caught
    Arguments: None
    Returns: None
    */
    public void saveAs(){
        get_Config();
        fileChooser.setCurrentDirectory(new File(s_path));
        if(!(file_name.endsWith(".cm")))
        {
            fileChooser.setSelectedFile(new File(file_name + ".cm"));
        }
            returnVal = fileChooser.showSaveDialog(panel);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                path = fileChooser.getSelectedFile().getAbsolutePath();
                files = fileChooser.getSelectedFile();
                file_name = fileChooser.getSelectedFile().getName();
                setFileTitle();
            }
            
            try{
                writer = new BufferedWriter(new FileWriter(files));
                text.write(writer);
            }catch(Exception error){
                
            }
            setModified(0);
    }

    /*
    save:
    File has been previously selected so this function autmatically saves to that file
    Exceptions: Exception, caught
    Arguments: None
    Returns: None
    */
    public void save(){
        path = fileChooser.getSelectedFile().getAbsolutePath();
        files = fileChooser.getSelectedFile();
        file_name = fileChooser.getSelectedFile().getName();
        setFileTitle();
    
        try{
            writer = new BufferedWriter(new FileWriter(files));
            text.write(writer);
        }catch(Exception error){
        
        }
    }
    
    /*
    get_Config:
    Calls two other functions to load directory paths
    Arguments: None
    Returns: None
    */
    public void get_Config(){
        get_cPath();
        get_sPath();
    }
    
    /*
    get_cPath:
    Using a relative pathway from the working directory, reads a file to find where the Codemon
    Directory is located
    Exceptions: IO FileNotFound, both caught
    Arguments: None
    Returns: None
    */
    public void get_cPath()
    {
        config_file = new File("../Config/cdirectory.txt");
        try {
            br = new BufferedReader(new FileReader(config_file));
            try {
                c_path = br.readLine();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /*
    get_sPath:
    Using a relative pathway from the working directory, reads a file to find where the Source
    Directory is located
    Exceptions: IO FileNotFound, both caught
    Arguments: None
    Returns: None
    */
    public void get_sPath()
    {
        config_file = new File("../Config/sdirectory.txt");
        try {
            br = new BufferedReader(new FileReader(config_file));
            try {
                s_path = br.readLine();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /***************** ActionListeners *****************/
    
    public class helpActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            quickText.setText("Welcome to the Codemon training center where you are free to develope\nand modify your Codemon!\n\n" +
            "A few things that make this program special are:\n" +
            "- The build menu at the top allows you to assemble your Codemon into binary \nblobs for submission to the codex\n" +
            "- Pressing Assemble will build your Codemon\n- While Assemble and Run will build them and then open the Fight Club\n" +
            "- The config menu allows you to change the directory paths if your Source \nand Codemon files" + 
            "are in diffrent directories than the default directories\n" +
            "- The buttons are all very standard except the play button at the end\n this is the same as" +
            "the Assemble button in the Build menu");
            quickFrame.getContentPane().removeAll();
            quickFrame.add(quickText);
            quickFrame.setVisible(true);
        }
    }
    
    public class new_fileActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            /*Object[] options = {"Yes", "No", "Cancel"};
                JOptionPane.showOptionDialog(null, "Would you like to save before change before exiting", "save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); */ 
            text.setText("");
            file_name = "New";
            setFileTitle();
            setModified(0);
        }
    }
    
    public class open_fileActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            get_Config();
            fileChooser.setCurrentDirectory(new File(s_path));
            returnVal = fileChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                files = fileChooser.getSelectedFile();
                file_path = fileChooser.getSelectedFile().getAbsolutePath();
                file_name = files.getName();
                setFileTitle();
                
                try{
                    br = new BufferedReader(new FileReader(files));
                    text.read(br, null);
                }catch(Exception error){
                    
                }
                setModified(0);
            }
        }
    }
    
    public class save_fileActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            get_Config();
            if(file_name.equals("New")){
                saveAs();
            }
            else{
                save();
            }
            setModified(0);
        }
    }
    
    public class save_file_asActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            get_Config();
            fileChooser.setCurrentDirectory(new File(s_path));
            saveAs();
        }
    }
    
    public class runActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            /*!!!!!!*/          
            CM.parse(file_name, file_path, c_path + "/" + file_name.substring(0, file_name.length()-3) + ".codemon");
        }
    }

    public class assemblerunActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            /*!!!!!!*/          
            CM.parse(file_name, file_path, c_path + "/" + file_name.substring(0, file_name.length()-3) + ".codemon");
            Fight fightFrame = new Fight();
            fightFrame.setVisible(true);
        }
    }
    //
    public class quitActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(modified.equals("Not Modified")){
               System.exit(0); 
            }
            else{
                /*String ObjButtons[] = {"Yes","No"};
                int result = JOptionPane.showOptionDialog(null,"This document has been modified, would you like to save changes?",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(result==JOptionPane.YES_OPTION)
                {
                    save();
                }*/
            }
        }
    }
    
    public class sourceActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            returnVal = directoryChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                s_path = directoryChooser.getSelectedFile().getAbsolutePath();
                try {
                FileWriter writer = new FileWriter("../Config/sdirectory.txt");
                writer.write(s_path);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class codemonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            returnVal = directoryChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                c_path = directoryChooser.getSelectedFile().getAbsolutePath();
                try {
                FileWriter writer = new FileWriter("../Config/cdirectory.txt");
                writer.write(c_path);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    /***************** DocumentListener *****************/
    
    public class textListener implements DocumentListener{
        @Override
        public void insertUpdate(DocumentEvent e) {
            setModified(1);
        }
        public void removeUpdate(DocumentEvent e) {
            setModified(1);
        }
        public void changedUpdate(DocumentEvent e) {
            setModified(1);
        }
    }
    
}
