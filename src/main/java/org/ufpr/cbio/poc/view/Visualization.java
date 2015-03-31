package org.ufpr.cbio.poc.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;

@SuppressWarnings("serial")
public class Visualization extends JFrame{
	
	private static Scenario scenario;
	private ScenarioController controls;
	
	public Visualization() {
		setName("Structure Visualization");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setResizable(false);
		
		scenario = new Scenario(null);
		//controls = new ScenarioController();
		
		JScrollPane scroll = new JScrollPane(null, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(448, 448));
		scroll.getViewport().add(scenario);
		
		add(scroll);
		//add(controls);
		
		pack();
		setVisible(true);
	}
	
	public void showStructure(Protein protein) {
		scenario.setProtein(protein);
	}
	
	public static void update() {
		scenario.repaint();
	}
}
