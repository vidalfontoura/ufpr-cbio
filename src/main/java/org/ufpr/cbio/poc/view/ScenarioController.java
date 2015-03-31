package org.ufpr.cbio.poc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ScenarioController extends JPanel{
	
	public ScenarioController() {
		setPreferredSize(new Dimension(100, 400));
		setVisible(true);
		
		setBackground(Color.GREEN);
		
		setUpButtons();
	}

	private void setUpButtons() {
	}
}
