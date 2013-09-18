import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Graf extends JPanel 
{
	
	private ArrayList <Point> points = new ArrayList <Point>();
	private int sizeX, sizeY;
	private JPanel graf;
	
	public Graf ()
	{
		FyllArray();
		sizeX = points.get(points.size()-1).x;
		sizeY = maxY(); //points.get(points.size()-1).y;
		
		graf = new TegnGraf ();
		graf.setPreferredSize(new Dimension (sizeX, sizeY));
		
		JScrollPane scroll = new JScrollPane (graf);
		scroll.setPreferredSize(new Dimension (400, 400));
		
		add (scroll,  BorderLayout.CENTER);
	}
	
	//tegner grafen og griden
		public class TegnGraf extends JPanel 
		{
			
			public void paintComponent (Graphics g)
			{
				super.paintComponent(g);
				tegnGrid (g);
				tegnGraf(g);
				
				
			}
			
			public void tegnGraf (Graphics g)
			{
				Graphics2D g2d = (Graphics2D)g;
				int preX = points.get(0).x;
				int preY = sizeY-points.get(0).y;
				
				
				for (int i=1; i<points.size()-1;i++)
				{	
					
					//tegner tallene
					g.setColor(Color.blue);
					//if(i%15 == 0)
					{
						g.drawString(Integer.toString(points.get(i).y), 0, sizeY-points.get(i).y);
						g.drawString(Integer.toString(points.get(i).x), points.get(i).x, sizeY-10);
					}
						
					//tekner kurven
					g2d.setStroke(new BasicStroke (2));
					g2d.setColor(Color.red);
					g2d.drawLine( preX, preY, points.get(i).x, sizeY-points.get(i).y);
					
					preX = points.get(i).x;
					preY = sizeY-points.get(i).y;
				}
				g2d.drawLine( preX, preY, points.get(points.size()-1).x, sizeY-points.get(points.size()-1).y);
				
			}
			
			
			public void tegnGrid (Graphics g)
			{
				
				//horisontal linje
				for (int i = 0; i< sizeX/20; i++)
				{
					g.drawLine(i*20, 0, i*20, points.get(points.size()-1).y);
				}
				
				//vertikal linje
				for (int i = 0; i< sizeY/20; i++)
				{
					g.drawLine(0, i*20, points.get(points.size()-1).x, i*20);
				}
			}
		}
		
		private int maxY ()
		{
			int y = 0;
			
			for (int i = 0; i< points.size();i++)
			{
				if(points.get(i).y > y)
					y=points.get(i).y;
			}
			
			return y;
		}
		
		//lager punktene i arrayet
		public void FyllArray ()
		{
			/*for(int i=0; i<720;i++)
				points.add(new Point (i*6, i*5));*/
			points.add(new Point (1000, 4776));
			points.add(new Point (2000, 12602));
			points.add(new Point (3000, 3724));
			points.add(new Point (4000, 5709));
			points.add(new Point (5000, 6757));
			points.add(new Point (6000, 5865));
			
			
		}
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame ("Forbruk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new Graf();
		newContentPane.setOpaque(true); 
        frame.setContentPane(newContentPane);
        frame.setResizable(false);
        
        frame.pack();
        frame.setVisible(true);

	}

}