/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.Color;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author jbray_49
 */
public class Fight extends JFrame{
    private File sfile;
    private File rfile;
    private File cfile;
    private File erfile;//
    private File f;
    private Boolean bool;
    private String file_name;
    private String file2_name;
    private File config_file;
    private File[] folder;
    private File[] erfolder;
    private File[] qfolder;
    private Codemon CM;
    private DefaultComboBoxModel codemonModel;
    private DefaultComboBoxModel codemon2Model;
    private BufferedReader quickRead;
    private JFileChooser directoryChooser;
    private BufferedReader br;
    private DefaultListModel qlistModel;
    private DefaultListModel rlistModel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private String s_path;
    private String c_path;
    private String r_path;
    private String er_path;
    private String line;
    private JPanel fight;
    private String qselected;
    private String rselected;
    private LayoutManager layout;
    private JMenu train;
    private JMenu config;
    private JMenu reports;
    private JMenu help;
    private JList q_list;
    private JList r_list;
    private int ilimit=0;
    private int vs=2;
    private int returnVal;
    private String[] cNames;
    private JComboBox c1Select;
    private JComboBox c2Select;
    
    private JPanel fight2;
    private JPanel fight2_1; //Contains button menu
    
    private JPanel fight3;
    
    private JPanel fight3_1;
    private JPanel fight3_1_1;
    private JPanel fight3_1_1_1;
    private JPanel fight3_1_1_2;
    private JPanel fight3_1_2;
    
    private JPanel fight3_2;
	
    JFrame quickFrame;
    JTextArea quickText;
    JScrollPane qviewscroll;

    /*!!!!!*/
    public Fight(){
        CM = new Codemon();
        get_Config();
        directoryChooser = new JFileChooser("../");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        qlist();
        rlist();
        createC1Model();
        createC2Model();
        fight = new JPanel();
        fight.addMouseListener(new updateMouseListener());
        fight.setVisible(true);
        layout = new BorderLayout();
        fight.setLayout(layout);
        
        setSize(WIDTH, HEIGHT);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /*quick view*/
        quickFrame = new JFrame();
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setSize(WIDTH, HEIGHT);
        quickFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quickText = new JTextArea();
        quickText.setEditable(false);
        /*quickView.add(qviewscroll);
        quickFrame.add(quickView);*/


        /***************** Menu Bar *****************/
        JMenuBar bar = new JMenuBar();
        
        config = new JMenu("Config");
        JMenuItem sourceD = new JMenuItem("Source Directory");
        sourceD.addActionListener(new sourceActionListener());
        JMenuItem codemonD = new JMenuItem("Codemon Directory");
        codemonD.addActionListener(new codemonActionListener());
        JMenuItem reportsD = new JMenuItem("Reports Directory");
        reportsD.addActionListener(new reportActionListener());
        JMenuItem iterations = new JMenuItem("Iterations Limit");
        iterations.addActionListener(new iterationActionListener());
        JMenuItem vs1 = new JMenuItem("vs. 1");
        vs1.addActionListener(new vs1ActionListener());
        JMenuItem vs2 = new JMenuItem("vs. 2");
        vs2.addActionListener(new vs2ActionListener());
        JMenuItem vs3 = new JMenuItem("vs. 3");
        vs3.addActionListener(new vs3ActionListener());
        
        config.add(sourceD);
        config.add(codemonD);
        config.add(reportsD);
        config.add(iterations);
        config.add(vs1);
        config.add(vs2);
        config.add(vs3);
        
        reports = new JMenu("Reports");
        JMenuItem view = new JMenuItem("View");
        view.addActionListener(new viewreportActionListener());
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(new deleteActionListener());
        JMenuItem fetch = new JMenuItem("Fetch All");
        fetch.addActionListener(new getreportActionListener());
        JMenuItem vis = new JMenuItem("Visualize");
        vis.addActionListener(new visualizeActionListener());
        
        reports.add(view);
        reports.add(delete);
        reports.add(fetch);
        reports.add(vis);
        
        help = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new helpActionListener());
        
        help.add(helpItem);
                
        bar.add(config);
        bar.add(reports);
        bar.add(help);
        
        fight.add(bar,BorderLayout.NORTH);
        
        /***************** fight2 contains fight3 and fight2_1 *****************/
        fight2 = new JPanel();
        fight2.setLayout(new BorderLayout());
        
        /***************** fight2_1 contains button menu *****************/
        fight2_1 = new JPanel();
        fight2_1.setLayout(new GridLayout(1,7));
        
        Icon run_test_icon = new ImageIcon("../buttons/run.png");
        JButton run_test_button = new JButton(run_test_icon);
        run_test_button.setToolTipText("Run Test");
        run_test_button.addActionListener(new testActionListener());
        fight2_1.add(run_test_button);
        
        Icon run_self_icon = new ImageIcon("../buttons/runself.png");
        JButton run_self_button = new JButton(run_self_icon);
        run_self_button.addActionListener(new selfActionListener());
        run_self_button.setToolTipText("Run Self");
        fight2_1.add(run_self_button);
        
        Icon run_pvp_icon = new ImageIcon("../buttons/pvp.png");
        JButton run_pvp_button = new JButton(run_pvp_icon);
        run_pvp_button.addActionListener(new vsActionListener());
        run_pvp_button.setToolTipText("Run PvP");
        fight2_1.add(run_pvp_button);
        
        JButton visualize_button = new JButton();
        visualize_button.setToolTipText("Visualize");
        visualize_button.addActionListener(new visualizeActionListener());
        fight2_1.add(visualize_button);
        
        Icon delete_icon = new ImageIcon("../buttons/trash.png");
        JButton delete_button = new JButton(delete_icon);
        delete_button.setToolTipText("Delete");
        delete_button.addActionListener(new deleteActionListener());
        fight2_1.add(delete_button);
        
        /***************** fight3 contains fight 3_1 *****************/
        fight3 = new JPanel();
        fight3.setLayout(new GridLayout(1,2));
        
        /***************** fight3_1 *****************/
        fight3_1 = new JPanel();
        fight3_1.setLayout(new BorderLayout());
        
        /***************** fight3_1_1 contains Codemon Select *****************/
        fight3_1_1 = new JPanel();
        fight3_1_1.setLayout(new GridLayout(2,1));
        TitledBorder cselect_border = new TitledBorder("I Choose You...");
        fight3_1_1.setBorder(cselect_border);
        
        /***************** fight3_1_1_1 contains Codemon1 Select *****************/
        fight3_1_1_1 = new JPanel();
        fight3_1_1_1.setLayout(new BorderLayout());
        JLabel codemon1 = new JLabel("Codemon1:");
        fight3_1_1_1.add(codemon1, BorderLayout.WEST);
        c1Select = new JComboBox(codemonModel); //Quick View has to be populate first
        fight3_1_1_1.add(c1Select, BorderLayout.CENTER);
        
        /***************** fight3_1_1_2 contains Codemon2 Select *****************/
        fight3_1_1_2 = new JPanel();
        fight3_1_1_2.setLayout(new BorderLayout());
        JLabel codemon2 = new JLabel("Codemon2:");
        fight3_1_1_2.add(codemon2, BorderLayout.WEST);
        c2Select = new JComboBox(codemon2Model); //Quick View has to be populate first
        fight3_1_1_2.add(c2Select, BorderLayout.CENTER);
        
        /***************** fight3_1_2 contains Quick View *****************/
        fight3_1_2 = new JPanel();
        fight3_1_2.setLayout(new BorderLayout());
        TitledBorder qView_border = new TitledBorder("Quick View");
        fight3_1_2.setBorder(qView_border);
        q_list = new JList(qlistModel);
        q_list.addListSelectionListener(new qviewListListener());
        q_list.addMouseListener(new qviewMouseListener());
        JScrollPane qscroll = new JScrollPane(q_list);        
        qscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        qscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        fight3_1_2.add(qscroll, BorderLayout.CENTER);
        
        /***************** fight3_2 *****************/
        fight3_2 = new JPanel();
        fight3_2.setLayout(new BorderLayout());
        TitledBorder reports_border = new TitledBorder("Reports");
        fight3_2.setBorder(reports_border);
        r_list = new JList(rlistModel);
        r_list.addListSelectionListener(new reportListListener());
        r_list.addMouseListener(new reportsMouseListener());
        JScrollPane rscroll = new JScrollPane(r_list);
        rscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fight3_2.add(rscroll, BorderLayout.CENTER);
        
			r_list.setCellRenderer(new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				Component x = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				for(int i=0; i<erfolder.length; i++)
				{
					if(value.toString().equals(erfolder[i].getName()))
					{
						setForeground(Color.red);
					}	
				}
				return x;
			}
		});
        
        /***************** Add Components of fight3_1 *****************/
        fight3_1_1.add(fight3_1_1_1);
        fight3_1_1.add(fight3_1_1_2);
        fight3_1.add(fight3_1_1, BorderLayout.NORTH);
        fight3_1.add(fight3_1_2, BorderLayout.CENTER);
        
        /***************** Add Components of fight3 *****************/
        fight3.add(fight3_1);
        fight3.add(fight3_2);
        
        /***************** Add Components of fight2 *****************/
        fight2.add(fight2_1, BorderLayout.NORTH);
        fight2.add(fight3, BorderLayout.CENTER);
        
        fight.add(fight2, BorderLayout.CENTER);
        
        add(fight);
    }
    
    /***************** List Functions *****************/
    
    /*
    qlist:
    Loads the files contained in the given directories and adds
    them to the quick view listmodel.
    Arguments: None
    Returns: None
    */
    public void qlist(){
        sfile = new File(s_path);
        qfolder = sfile.listFiles();
        qlistModel = new DefaultListModel();
        for(int i=0; i<qfolder.length; i++)
        {
            if(qfolder[i].getName().endsWith(".cm"))
            {
                qlistModel.addElement(qfolder[i].getName());   
            }
        }
    }
    
    /*
    updateqlist:
    Function called to update the quick view list by clearing the list and reloading the files
    in the provided directory and adding them to the list
    Arguments: None
    Returns: None
    */
    public void updateqlist(){
        qlistModel.clear();
        sfile = new File(s_path);
        qfolder = sfile.listFiles();
        for(int i=0; i<qfolder.length; i++)
        {
               if(qfolder[i].getName().endsWith(".cm"))
               {
                    qlistModel.addElement(qfolder[i].getName());   
               }
        }
    }  
    
    /*
    rlist:
    Loads the files in the provided directory and adds
    them to the report list
    Arguments: None
    Returns: None
    */
    public void rlist(){
        rfile = new File(r_path);
        folder = rfile.listFiles();
        rlistModel = new DefaultListModel();
        for(int i=0; i<folder.length; i++)
        {
            if(!folder[i].getName().equals(".DS_Store"))
               {
                    rlistModel.addElement(folder[i].getName());   
               }   
        }

        erfile = new File(er_path);
        erfolder = erfile.listFiles();
        for(int i=0; i<erfolder.length; i++)
        {
            if(!erfolder[i].getName().equals(".DS_Store"))
            {
                rlistModel.addElement(erfolder[i].getName());   
            }   
        }
    }
    

    /*
    updaterList:
    Clears the report list and reloads the files in the provided directory
    and adds them to the list
    Arguments: None
    Returns: None
    */
    public void updaterList(){
        rlistModel.clear();
        rfile = new File(r_path);
        folder = rfile.listFiles();
        for(int i=0; i<folder.length; i++)
        {
            if(!folder[i].getName().equals(".DS_Store"))
            {
                rlistModel.addElement(folder[i].getName());   
            }   
        }
        erfile = new File(er_path);
        erfolder = erfile.listFiles();
        for(int i=0; i<erfolder.length; i++)
        {
            if(!erfolder[i].getName().equals(".DS_Store"))
            {
                rlistModel.addElement(erfolder[i].getName());   
            }   
        }
    }
    
    /*
    createC1Model:
    Loads the files in the given pathway and adds them to a Combobox
    model to populate the Codemon1 ComboBox
    Arguments: None
    Returns: None
    */
    public void createC1Model(){    
        cfile = new File(c_path);
        folder = cfile.listFiles();
        codemonModel = new DefaultComboBoxModel();
        for(int i=0; i<folder.length; i++)
        {
            if(folder[i].getName().endsWith(".codemon"))
            {
                codemonModel.addElement(folder[i].getName());
            }
        }
    }
    
    /*
    updateC1Model:
    Removes all elements from the Codemon1 ComboBox, reloads the files
    from the given pathway and populates the ComboBox
    Arguments: None
    Returns: None
    */
    public void updateC1Model(){
        codemonModel.removeAllElements();
        cfile = new File(c_path);
        folder = cfile.listFiles();
        for(int i=0; i<folder.length; i++)
        {
            if(folder[i].getName().endsWith(".codemon"))
            {
                codemonModel.addElement(folder[i].getName());
            }
        }
    }
    

    /*
    createC2Model:
    Loads the files in the given pathway and adds them to a Combobox
    model to populate the Codemon2 ComboBox
    Arguments: None
    Returns: None
    */
    public void createC2Model(){    
        cfile = new File(c_path);
        folder = cfile.listFiles();
        codemon2Model = new DefaultComboBoxModel();
        codemon2Model.addElement(null);
        for(int i=0; i<folder.length; i++)
        {
            if(folder[i].getName().endsWith(".codemon"))
            {
                codemon2Model.addElement(folder[i].getName());
            }
        }
    }
    
    /*
    updateC2Model:
    Removes all elements from the Codemon2 ComboBox, reloads the files
    from the given pathway and populates the ComboBox
    Arguments: None
    Returns: None
    */
    public void updateC2Model(){
        codemon2Model.removeAllElements();
        cfile = new File(c_path);
        folder = cfile.listFiles();
        codemon2Model.addElement(null);
        for(int i=0; i<folder.length; i++)
        {
            if(folder[i].getName().endsWith(".codemon"))
            {
                codemon2Model.addElement(folder[i].getName());
            }
        }
    }
    

    /*
    update:
    Called to update all lists and direcory pathways
    Arguments: None
    Returns: None
    */
    public void update(){
        get_Config();
            updateqlist();
            updaterList();
            updateC1Model();
            updateC2Model();
    }
    
    /***************** Configure saved data *****************/
    
    /*
    get_Config:
    Calls four other functions to fully configure all directory pathways
    Arguments: None
    Returns: None
    */
    public void get_Config(){
    get_cPath();
    get_sPath();
    get_rPath();
    get_erPath();
    }
    
    /*
    get_cPath:
    Using a relative pathway from the working directory this function
    reads a text file to find where the Codemon Directory is located.
    Saves it the variable c_path for future use.
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
    Using a relative pathway from the working directory this function
    reads a text file to find where the Source Directory is located.
    Saves it to the variable s_path for future use.
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

    /*
    get_rPath:
    Using a relative pathway from the working directory this function
    reads a text file to find where the Reports Directory is located.
    Saves it to the variable r_path for future use.
    Arguments: None
    Returns: None
    */
    public void get_rPath()
    {
        config_file = new File("../Config/rdirectory.txt");
        try {
            br = new BufferedReader(new FileReader(config_file));
            try {
                r_path = br.readLine();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    get_erPath:
    Using a relative pathway from the working directory this function
    reads a text file to find where the Empty Reports Directory is located.
    Saves it to the variable er_path for future use.
    Arguments: None
    Returns: None
    */
    public void get_erPath()
    {
        config_file = new File("../Config/erdirectory.txt");
        try {
            br = new BufferedReader(new FileReader(config_file));
            try {
                er_path = br.readLine();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    viewReport:
    Loads the contents of a report file int a BufferedReader, which the text area reads from.
    Opens a JFrame with the contents of the the report file loaded into the text area
    Exceptions: IO and FileNotFound, both caught
    Arguments: None
    Returns: None
    */
    public void viewReport(){
        try {
            quickRead = new BufferedReader(new FileReader(r_path+"/"+rselected));
            quickText.read(quickRead, null);
            qviewscroll = new JScrollPane(quickText);
        	qviewscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	qviewscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        	quickFrame.getContentPane().removeAll();
            quickFrame.add(qviewscroll);
            quickFrame.setVisible(true);
            quickRead.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    viewCodemon:
    Loads the contents of a report file int a BufferedReader, which the text area reads from.
    Opens a JFrame with the contents of the the report file loaded into the text area
    Exceptions: IO and FileNotFound, both caught
    Arguments: None
    Returns: None
    */
    public void viewCodemon(){
        try {
            quickRead = new BufferedReader(new FileReader(s_path+"/"+qselected));
            quickText.read(quickRead, null);
            qviewscroll = new JScrollPane(quickText);
        	qviewscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	qviewscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        	quickFrame.getContentPane().removeAll();
            quickFrame.add(qviewscroll);
            quickFrame.setVisible(true);
            quickRead.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    delete:
    Deletes the currently selected report
    Exceptions: IO, caught
    Arguments: None
    Returns: None
    */
    public void delete(){
        try {
            Files.delete(Paths.get(r_path + "/" + rselected));
            update();
        } catch (IOException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEreport(){
        try {
            Files.delete(Paths.get(er_path + "/" + rselected));
            update();
        } catch (IOException ex) {
            Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /***************** Action Listeners *****************/
    
    public class testActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //submit for test run with iteration limit and selected codemon
            file_name = (String)c1Select.getSelectedItem();
            file2_name = (String)c2Select.getSelectedItem();
            if(file_name != null && file2_name == null)
            {
                int s = CM.runTest(file_name, c_path + "/" + file_name, r_path, ilimit);
                try{
                    File f = new File(er_path + "/" + String.valueOf(s));
                    bool = f.createNewFile();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
            updaterList();
        }
    }
    
    public class selfActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //submit for self game with iteration and 2 selected codemon
            file_name = (String)c1Select.getSelectedItem();
            file2_name = (String)c2Select.getSelectedItem();
            if(file_name != null && file2_name != null)
            {
                int s = CM.runSelf(file_name, file2_name, c_path + "/" + file_name, c_path + "/" + file2_name, r_path, ilimit);
                try{
                    File f = new File(er_path + "/" + String.valueOf(s));
                    bool = f.createNewFile();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            updaterList();
        }
    }
    
    public class vsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //send pvp request with vs value and selected codemon
            file_name = (String)c1Select.getSelectedItem();
            file2_name = (String)c2Select.getSelectedItem();
            if(file_name != null && file2_name == null)
            {
                CM.runPVP(file_name, c_path + "/" + file_name, r_path, ilimit, vs);
            }
            updaterList();
        }
    }
    
    public class vs1ActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            vs = 2;
        }
    }
    
    public class vs2ActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            vs = 3;
        }
    }
    
    public class vs3ActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            vs = 4;
        }
    }
    
    public class iterationActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String inputValue = JOptionPane.showInputDialog("Please input the number of iterations");
            ilimit = Integer.parseInt(inputValue);
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
                updateqlist();
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
                updateC1Model();
                updateC2Model();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    public class reportActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            returnVal = directoryChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                r_path = directoryChooser.getSelectedFile().getAbsolutePath();
                try {
                FileWriter writer = new FileWriter("../Config/rdirectory.txt");
                writer.write(r_path);
                writer.close();
                updaterList();
            } catch (IOException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    public class viewreportActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int a =1;
            for(int i = 0; i<erfolder.length; i++)
            {
                if(erfolder[i].getName().equals(rselected))
                {
                    int s = CM.getReport(r_path + "/" + rselected, rselected);
                    if(s  > 0)
                    {
                        deleteEreport();
                        //viewReport();
                        a=0;
                    }
                    else if(s == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Report Unavailable!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        delete();
                        a=0;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Network Connection Error!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        delete();
                        a=0;
                    }
                    //delete_unfetched();
                    break;
                }
            }
            if(a==1)
            {
                viewReport();
            }
        }
    }
    
    public class deleteActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(rselected != null)
            {
                delete();
            }
        }
    }
    
    public class helpActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            
            quickText.setText("Welcome to the fight window, in this window you have can select" + "\n"  +
            "different Codemon to test out or fight against ago people's Codemon.\n\n" +
            "Under the config menu you will find the options for setting up a fight:\n" +
            "- You can select the directories your codemon can be found in\n" +
            "- The number of turns the fight will run for\n- The number of people you" +
            " wish to battle in a PvP match\n\n" +
            "The buttons along the top are your options for battles:\n" +
            "- The first button submits a Codemon for a test for the given number\nof turns\n" +
            "- The second submits two of your own Codemon for a test battle for a given \nnumber of turns\n" +
            "- The third submits one of your codemon for a PvP match, the number of \nopponents is specified in the config menu\n" +
            "- The last button deletes the selected report \nNOTE! an empty report cannot be deleted!\n\n" +
            "The reports menu allows you to view, delete and fetch the reports shown.\n" +
            "It is also possible to view and fetch reports by double clicking \non the desired report.\n\n" +
            "Finally the quick view panel allows you the ability to check on your codemon:\n" +
            "- By double clicking the desired codemon a quick view window will popup\n" +
            "allowing you to view the instructions, however you cannot edit from \nthis window.");
            quickFrame.getContentPane().removeAll();
            quickFrame.add(quickText);
            quickFrame.setVisible(true);
        }
    }

    public class getreportActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int a =1;
            for(int i = 0; i<erfolder.length; i++)
            {
                rselected = erfolder[i].getName();

                int s = CM.getReport(r_path + "/" + rselected, rselected);
                if(s  > 0)
                {
                    deleteEreport();
                    //viewReport();
                    a=0;
                }
                else if(s == 0)
                {
                    JOptionPane.showMessageDialog(null, "Report Unavailable!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    delete();
                    a=0;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Network Connection Error!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    delete();
                    a=0;
                }
                //delete_unfetched();
            }
        }
    }
    public class visualizeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
        	int a =1;
            for(int i = 0; i<erfolder.length; i++)
            {
                if(erfolder[i].getName().equals(rselected))
                {
                    int s = CM.getReport(r_path + "/" + rselected, rselected);
                    if(s  > 0)
                    {
                        deleteEreport();
                        //viewReport();
                        a=0;
                    }
                    else if(s == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Report Unavailable!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        delete();
                        a=0;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Network Connection Error!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        delete();
                        a=0;
                    }
                    //delete_unfetched();
                    break;
                }
            }
            if(a==1)
            {
            	String new_path = r_path + "/" + rselected;
                Visualize visualizer = new Visualize(new_path);
            	visualizer.setVisible(true);
            	visualizer.runVisualize();
            }
        }
    }
    
    
    /***************** ListSelectionListener *****************/
    
    public class qviewListListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList element = (JList) e.getSource(); //To change body of generated methods, choose Tools | Templates.
            qselected = (String) element.getSelectedValue();
        }
        
    }
    
    public class reportListListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList element = (JList) e.getSource(); //To change body of generated methods, choose Tools | Templates.
            rselected = (String) element.getSelectedValue();
        }
        
    }
    
    /***************** Mouse Listeners *****************/
    
    public class qviewMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2)
            {
                viewCodemon();
            }
        }

        public void mouseExited(MouseEvent e) {

        }       

        public void mouseEntered(MouseEvent e) {

    	}

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
              
        }
    }
    
    public class reportsMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2)
            {
                int a =1;
				for(int i = 0; i<erfolder.length; i++)
				{
					if(erfolder[i].getName().equals(rselected))
					{
						int s = CM.getReport(r_path + "/" + rselected, rselected);
						if(s  > 0)
						{
							deleteEreport();
							//viewReport();
							a=0;
						}
						else if(s == 0)
						{
							JOptionPane.showMessageDialog(null, "Report Unavailable!", "Alert", JOptionPane.INFORMATION_MESSAGE);
							delete();
							a=0;
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Network Connection Error!", "Alert", JOptionPane.INFORMATION_MESSAGE);
							delete();
							a=0;
						}
						//delete_unfetched();
						break;
					}
				}
				if(a==1)
				{
					viewReport();
				}
        	}
        }

        public void mouseExited(MouseEvent e) {

        }       

        public void mouseEntered(MouseEvent e) {

    }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
              
        }
    }
    
    public class updateMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            get_Config();
            updateqlist();
            updaterList();
            updateC1Model();
            updateC2Model();
            
        }

        public void mouseExited(MouseEvent e) {

        }       

        public void mouseEntered(MouseEvent e) {

    	}

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
              
        }
    }
}
