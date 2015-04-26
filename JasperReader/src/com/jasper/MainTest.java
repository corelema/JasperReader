package com.jasper;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class MainTest extends JFrame {

	public MainTest(){
		CreateTree createTree = new CreateTree();
		//createTree.processFolder();
		add(createTree.getTree());
		createTree.getTree().expandRow(5);
		
		int row = 0;
	    while (row < createTree.getTree().getRowCount()) {
	    	createTree.getTree().expandRow(row);
	      row++;
	      }
		
	    
	    JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    this.setContentPane(pane);
	    pane.setViewportView(createTree.getTree());
	    
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TransUnion Tree");       
        this.pack();
        this.setVisible(true);
        this.setPreferredSize(new Dimension(800, 10000));
        this.setMinimumSize(new Dimension(800, 10000));
        this.setSize(new Dimension(800, 1000000));
        this.validate();
        
        try
        {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            this.paint(graphics2D);
            ImageIO.write(image,"jpeg", new File("C:\\Users\\Corentin\\Desktop\\test.jpeg"));
        }
        catch(Exception exception)
        {
            //code
        }
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainTest();
            }
        });
	}

}
