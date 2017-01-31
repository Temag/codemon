import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.regex.*;
import java.util.Arrays;
import java.lang.StringBuilder;

public class Visualize extends JFrame implements ActionListener{

	private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private JPanel visualize;
    private String fpath;
    private int[] colors;
    private BufferedReader quick_read;
    private String reader;
    private File file;
    private char temp;
    private Timer timer;
    private String tstring;
    private int pcurrent;
    private int tcurrent;
    private int mem1;
    private int mem2;
    private int mem3;
    private int mem4;
    private int p1_1;
	private int p1_2;
	private int p1_3;
	private int p1_4;
	private int p2_1;
	private int p2_2;
	private int p2_3;
	private int p2_4;
	private int p3_1;
	private int p3_2;
	private int p3_3;
	private int p3_4;
	private int p4_1;
	private int p4_2;
	private int p4_3;
	private int p4_4;
	private MyPanel vpanel;
	private Pattern p;
	private Matcher m;
	private StringBuilder sb;
    
    
	public Visualize(String path){
		colors = new int[16];
		p1_1 = 0xff0000;
		p1_2 = 0xee0000;
		p1_3 = 0xcd0000;
		p1_4 = 0x8b00000;
		p2_1 = 0x5f9ea0;
		p2_2 = 0x98f5ff;
		p2_3 = 0x8ee5ee;
		p2_4 = 0x7ac5cd;
		p3_1 = 0x7fff00;
		p3_2 = 0x76ee00;
		p3_3 = 0x66cd00;
		p3_4 = 0x458b00;
		p4_1 = 0xdb7093;
		p4_2 = 0xff82ab;
		p4_3 = 0xee799f;
		p4_4 = 0xcd6889;
		p = Pattern.compile("'[0-9]+");
		timer = new Timer(250, this);
		fpath = path;
		setSize(1027, 582);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		vpanel = new MyPanel();
		
		JButton slower = new JButton("Slower");
		slower.addActionListener(new slowActionListener());
		slower.setBounds(80,80,80,80);
		JButton run = new JButton("Run");
		run.addActionListener(new playActionListener());
		run.setBounds(80,80,80,80);
		JButton faster = new JButton("Faster");
		faster.addActionListener(new fastActionListener());
		faster.setBounds(80,80,80,80);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.add(slower);
		panel.add(run);
		panel.add(faster);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,4));
		
		JLabel p1 = new JLabel("Player 1");
		p1.setForeground(new Color(p1_1));
		JLabel p2 = new JLabel("Player 2");
		p2.setForeground(new Color(p2_1));
		JLabel p3 = new JLabel("Player 3");
		p3.setForeground(new Color(p3_1));
		JLabel p4 = new JLabel("Player 4");
		p4.setForeground(new Color(p4_1));
		
		panel2.add(p1);
		
		panel2.add(p2);
		
		panel2.add(p3);
		
		panel2.add(p4);
		
		
		add(panel2, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
		add(vpanel, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e){
		int yc;
		int xc;
		int player_color;
		temp = reader.charAt(7);
		pcurrent = (int)temp-'0';
		temp = reader.charAt(9);
		tcurrent = (int)temp - '0';
		
		tstring = reader.substring(56, 60);
		if(tstring.startsWith(" ") == false)
		{
			mem1 = Integer.valueOf(tstring);
			xc = (mem1%128)*8 + 2;
			yc = (mem1/64)*8 + 2;
			player_color = getPlayerColour(pcurrent, tcurrent);
			vpanel.repaint();
		}
		
		tstring = reader.substring(62, 66);
		if(!tstring.startsWith(" "))
		{
			mem2 = Integer.valueOf(tstring);
			player_color = getPlayerColour(pcurrent, tcurrent);
			vpanel.updateArray(mem2, player_color);
			vpanel.repaint();
			//add(vpanel, BorderLayout.CENTER);
			
		}
		
		tstring = reader.substring(68, 72);
		if(!tstring.startsWith(" "))
		{
			mem3 = Integer.valueOf(tstring);
			xc = (mem3%128)*8 + 2;
			yc = (mem3/64)*8 + 2;
			player_color = getPlayerColour(pcurrent, tcurrent);
			vpanel.repaint();
		}
		
		tstring = reader.substring(74, 78);
		if(!tstring.startsWith(" "))
		{
			mem4 = Integer.valueOf(tstring);
			xc = (mem4%128)*8 + 2;
			yc = (mem4/64)*8 + 2;
			player_color = getPlayerColour(pcurrent, tcurrent);
			vpanel.repaint();
		}
		try{
			reader = quick_read.readLine();
			if(!reader.startsWith("["))
			{
				timer.restart();
				timer.stop();
			}
		}catch(IOException exc){
		}
		
	}
	
	public int getPlayerColour(int player, int thread)
	{
		if(player==1)
		{
			if(thread == 0)
			{
				return p1_1;
			}
			if(thread == 1)
			{
				return p1_2;
			}
			
			if(thread == 2)
			{
				return p1_3;
			}
			
			if(thread == 3)
			{
				return p1_4;
			}
		}
		
		if(player==2)
		{
			if(thread == 0)
			{
				return p2_1;
			}
			if(thread == 1)
			{
				return p2_2;
			}
			
			if(thread == 2)
			{
				return p2_3;
			}
			
			if(thread == 3)
			{
				return p2_4;
			}
		}
		
		if(player==3)
		{
			if(thread == 0)
			{
				return p3_1;
			}
			if(thread == 1)
			{
				return p3_2;
			}
			
			if(thread == 2)
			{
				return p3_3;
			}
			
			if(thread == 3)
			{
				return p3_4;
			}
		}
		
		if(player==4)
		{
			if(thread == 0)
			{
				return p4_1;
			}
			if(thread == 1)
			{
				return p4_2;
			}
			
			if(thread == 2)
			{
				return p4_3;
			}
			
			if(thread == 3)
			{
				return p4_4;
			}
		}
		return 0;
	}
	public void runVisualize(){
		file = new File(fpath);
		try{
			quick_read = new BufferedReader(new FileReader(fpath));
			try{
				reader = quick_read.readLine();
				if(reader != null)
				{
					while(reader.contains("->") == false)
					{
						reader = quick_read.readLine();
					}
					timer.start();
				}
			}catch(IOException ex){
			}
		}catch(FileNotFoundException ex){
		}
	}
	
	public class fastActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int tm = timer.getDelay();
            timer.stop();
            timer.setDelay(tm/2);
            timer.start();
        }
    }
    
    public class slowActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int tm = timer.getDelay();
            timer.stop();
            timer.setDelay(tm*2);
            timer.start();
        }
    }
    
    public class playActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(timer.isRunning())
            {
            	timer.stop();
            }
            else
            {
            	timer.start();
            }
        }
    }
}

class MyPanel extends JPanel{
	private int width = 6;
	private int height = 6;
	private Color[] color_array;
	public MyPanel(){
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		color_array = new Color[9182];
		Arrays.fill(color_array, 0, 9182, Color.BLACK);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int x = 2;
		int y = 0;
		int count = 0;
		Color color = Color.BLACK;
		for(int i=0; i<64; i++){
			x=2;
			for(int j=0; j<128; j++){
				paintSquare(g, x, y, color_array[count]);
				x+=8;
				count++;
			}
			y+=8;
		}
	}
	
	public void updateArray(int index, int player_color)
	{
		color_array[index] = new Color(player_color);
	}
	
	public void paintSquare(Graphics g, int x, int y, Color color)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}