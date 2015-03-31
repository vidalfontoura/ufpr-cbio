package org.ufpr.cbio.poc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;

@SuppressWarnings("serial")
public class Scenario extends JPanel {

	private int WIDTH = 430;
	private int HEIGHT = 430;
	
	private int SIZE = 10;
	
	private int MAX_X, MAX_Y;
	
	private Protein protein;
	
	public Scenario(Protein protein) {
		
		setProtein(protein);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        
		repaint();
	}
	
	public void setProtein(Protein protein) {
		this.protein = protein;
		
		if(protein != null && protein.getResidues() != null) {
			
			MAX_X = protein.getResidues().get(0).getPoint().getX();
	    	MAX_Y = protein.getResidues().get(0).getPoint().getY();
			
	    	for (int i = 1; i < protein.getResidues().size(); i++) {
	    		
				if(protein.getResidues().get(i).getPoint().getX() > MAX_X) MAX_X = protein.getResidues().get(i).getPoint().getX();
				if(protein.getResidues().get(i).getPoint().getY() > MAX_Y) MAX_Y = protein.getResidues().get(i).getPoint().getY();
				
				if(MAX_X > MAX_Y && MAX_X > 23) {
					WIDTH = MAX_X * 20 + 30;
					HEIGHT = MAX_X * 20 + 30;
				} else if(MAX_Y > MAX_X && MAX_Y > 23) {
					WIDTH = MAX_Y * 20 + 30;
					HEIGHT = MAX_Y * 20 + 30;
				}
	    	}
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
		}
		repaint();
	}
	
	public Protein getProtein() {
		return protein;
	}
	
	@Override
    public void paint(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g.create(0, 0, WIDTH, HEIGHT);
		
		g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        
        g2d.setColor(Color.BLACK);
        
        g2d.translate(0, HEIGHT);
        g2d.scale(1.0, -1.0);
        
        //--- linhas centrais vermelhas
        g2d.setColor(Color.RED);
        g2d.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
        g2d.drawLine(0, HEIGHT/2, WIDTH, HEIGHT/2);
        g2d.drawRect(20, 20, 390, 390);
        g2d.setColor(Color.BLACK);
        //---
        
//        g2d.translate((WIDTH/2)-((MAX_X*20+30)/2), (HEIGHT/2)-((MAX_Y*20+30)/2));//centraliza
        g2d.translate(20, HEIGHT-((MAX_Y*20+50)));//jogar no canto superior esquerdo
        
        drawScenario(g2d);
    }
	
	public void drawScenario(Graphics2D g2d) {
		
		if(protein == null || protein.getResidues() == null) return;
		
		int x = 0, y = 0, j = 4;
		
		for (int i = 0; i < protein.getResidues().size(); i++) {
			
			Residue r = protein.getResidues().get(i);
			
			if(i == 0) {
				j = 4;
			}			
			else if(r.getPoint().getX() == x && r.getPoint().getY() == y+1) j = 0;//TOP
			else if(r.getPoint().getX() == x+1 && r.getPoint().getY() == y) j = 3;//RIGHT
			else if(r.getPoint().getX() == x && r.getPoint().getY() == y-1) j = 2;//DOWN
			else if(r.getPoint().getX() == x-1 && r.getPoint().getY() == y) j = 1;//LEFT
			
			drawNodeAt(g2d, r.getPoint().getX(), r.getPoint().getY(), r.getResidueType().toString(), j);
			
			x = r.getPoint().getX();
			y = r.getPoint().getY();
		}
	}
	
	private void drawNodeAt(Graphics2D g2d, int x, int y, String type, int orientation) {

		Graphics2D g = (Graphics2D) g2d.create();
		
		x = x*SIZE*2;
		y = y*SIZE*2;
		
		switch(orientation) {
			case 0://TOP
				g.drawLine(x+(SIZE/2), y, x+(SIZE/2), y+(-SIZE+1));
				break;
			case 1://RIGHT
				g.drawLine(x+SIZE, y+(SIZE/2), x+(SIZE*2-1), y+(SIZE/2));
				break;
			case 2://DOWN
				g.drawLine(x+(SIZE/2), y+(SIZE), x+(SIZE/2), y+(SIZE*2));
				break;
			case 3://LEFT
				g.drawLine(x, y+(SIZE/2), x-(SIZE), y+(SIZE/2));
				break;
		}
		
		if(type.equals("P"))
			g.setColor(Color.GRAY);
		else
			g.setColor(Color.BLACK);
		
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(Color.BLACK);
	}
}
