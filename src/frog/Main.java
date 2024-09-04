package frog;

import java.awt.Dimension;

import javax.swing.JFrame;

import frog.DrawingSurface;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * Main class, creates new DrawingSurface window.
 * @author Justin Hwang, Mikaela Kwan, Jeremy Mills
 *
 */
public class Main {
	
	public static void main(String args[]) {
		
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		window.setTitle("Frog Dungeon");
	
		window.setSize(800, 600);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
	
		window.setVisible(true);
	}
}
