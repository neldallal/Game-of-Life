/*
 * TeamEgypt
 * Nadine Eldallal and Ebram Youssef
 * CSC 171 - Project 3 : GameOfLife
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GameOfLife implements ActionListener {
	
	//instance variables
	JTextField gridSize = new JTextField("20", 5);
	int dim = Integer.parseInt(gridSize.getText());
	int steps = 0;
	int delay;
	int[][] grid;
	protected Timer t;
	JButton resetButton = new JButton("RESET");
	JButton startButton = new JButton("START");
	JButton stopButton = new JButton("STOP");
	JLabel Steps = new JLabel("Steps" + steps);
	JSlider speedSlider = new JSlider();
	JLabel textLabel = new JLabel("Grid size: ");
    protected List<Rectangle> cells;
    Graphics g;
    Graphics2D g2d;
    myCanvas canvas = new myCanvas();
    Random rand = new Random();
    
    //constructor
    public GameOfLife() {
    	grid = new int[dim][dim];
    	
    	JFrame frame = new JFrame("Game of Life");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(canvas);	
		
		JPanel controlPanel = new JPanel();
		
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		controlPanel.setBackground(Color.white);

		controlPanel.setLayout(new FlowLayout());
		controlPanel.setPreferredSize(new Dimension(30,40));
		frame.add(controlPanel);
		
		controlPanel.add(Steps);
		
		controlPanel.add(resetButton);
		resetButton.addActionListener(this);
		
		controlPanel.add(startButton);
		startButton.addActionListener(this);
		
		controlPanel.add(stopButton);
		stopButton.addActionListener(this);
		
		speedSlider.setMaximum(2000);
        speedSlider.setMinimum(1);
        speedSlider.setValue(1000);
        delay = 1000;
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(1, new JLabel("1"));
        labels.put(1000, new JLabel("1000"));
        labels.put(2000, new JLabel("2000"));
        speedSlider.setLabelTable(labels);
        speedSlider.setPaintLabels(true);
		controlPanel.add(speedSlider);
		
		controlPanel.add(textLabel);
		controlPanel.add(gridSize);
        
		frame.pack();
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		t = new Timer(delay, new TimerCallBack());
	}
    
    protected void randomize() {
		
		//to fill array with random 0s or 1s
		for (int i=0; i<dim; ++i) {
			for (int j=0; j<dim; ++j) {
				grid[i][j] = rand.nextInt(2);

			}
		}
	}
	
    protected void evolve() { //GameOfLife rules
    	int[] di = {-1, -1, -1, 1, 1, 1, 0, 0};
		int[] dj = {-1, 1, 0, -1, 1, 0, -1, 1};
		
		int[][] next = new int[dim][dim];
		
		for (int x=0; x<dim; ++x) {
			for (int y=0; y<dim; ++y) {
				
				int neighbors = 0;
				
				for(int k=0; k<8; k++) {
					int nx = x + di[k];
					int ny = y + dj[k];
					if(valid(nx, ny) && grid[nx][ny] == 1)
						neighbors += 1;
				}
				
				if(neighbors == 3 || neighbors + grid[x][y] == 3)
					next[x][y] = 1;

				else
					next[x][y] = 0;
			}
		}
		grid = next;
		steps++;
		canvas.repaint();
    }
    
	public void resetGrid() {
		dim = Integer.parseInt(gridSize.getText());
        cells = Arrays.asList(new Rectangle[dim*dim]);
        grid = new int[dim][dim];
		for(int i=0; i<dim; i++)
		{
			for(int j=0; j<dim; j++)
			{
				grid[i][j] = 0;
			}
		}
	}
    
	public void start() {
		randomize();
		steps = 0;
		canvas.repaint();
		delay = speedSlider.getValue();
		t.setDelay(delay);
		t.restart();		
	}
	
	protected boolean valid(int x, int y) { // helper method for evolve()
		return (x >= 0 && x < dim && y >= 0 && y < dim);
	}
    
	public class TimerCallBack extends JComponent implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			evolve();
		}
	}
	
	class myCanvas extends JPanel{
	    
	    int xOffset;
	    int yOffset;
		
	    //creates array list to store cells with dimension 20x20
		public myCanvas() {
			cells = Arrays.asList(new Rectangle[dim*dim]);
			this.setSize(getPreferredSize());
		}
		
		//gets dimensions of canvas
	    public Dimension getPreferredSize() {
	        return new Dimension(700, 700);
	    }
	    
	    //clears cells before resizing window
	    @Override
	    public void invalidate() {
	        super.invalidate();
	    }
	    
	    
	    @Override
	    public void paintComponent(Graphics g) {
	    	
	    	Steps.setText("Steps: " + steps);
	    	int width = getWidth();
	        int height = getHeight();

	        int cellWidth = width / dim;
	        int cellHeight = height / dim;
	        
	        xOffset = (width - (dim * cellWidth)) / 2;
	        yOffset = (height - (dim * cellHeight)) / 2;
	        	        
	       //filling array list
	        for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    Rectangle cell = new Rectangle(
                            xOffset + (j * cellWidth),
                            yOffset + (i * cellHeight),
                            cellWidth,
                            cellHeight);
                    cells.set(i * dim + j, cell);
                }
	        }
	        
	    	// filling each generation's alive cells with randomly generated colors
	        super.paintComponent(g);
	        g2d = (Graphics2D) g.create();
			float R = rand.nextFloat();
		    float G = rand.nextFloat();
		    float B = rand.nextFloat();
		    Color randomColor = new Color(R, G, B);
	        for(int i=0; i<dim; i++)
	        {
	        	for(int j=0;j<dim; j++)
	        	{
	        		Rectangle cell = cells.get(j + i * dim);
	        		if(grid[i][j] == 1) {
	        			g2d.setColor(randomColor);
	        			g2d.fill(cell);
	        		}
	        		g2d.setColor(Color.gray);
	        		g2d.draw(cell);
	        	}
	        }
	    }
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			resetGrid();
			start();
			System.out.println("reset button");
		}
		else if (e.getSource() == startButton) {
			delay = speedSlider.getValue();
			t.setDelay(delay);
			t.start();
			System.out.println("start button");
		}
		else if (e.getSource() == stopButton) {
			t.stop();
			System.out.println("stop button");
		}
	}
	
	// main
	public static void main(String[] args)
	{
		GameOfLife gg = new GameOfLife();
		gg.start();
	}
}