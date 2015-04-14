package GamePKG;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.Color;

//import sun.audio.*;


public class MySounds {

	protected static final String AudioPlayer = null;
	
	public Clip PacBeginning  = loadClip("/Sounds/pacman_beginning.wav");
	public Clip PacChomp      = loadClip("/Sounds/pacman_chomp.wav");
	
	/**
	 * Create the frame.
	 */
	public MySounds() {
		
	} // MySounds constructor

	public Clip loadClip(String filename)
	{
		Clip clip = null;
		
		try
		{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open( audioIn );
		}// try
		catch (Exception e)
		{
			e.printStackTrace();
		}// catch
		
		return(clip);
		
	} // Clip
	
	public void playClip( int index )
	{
		if (index == 1)
		   {
		   stopClip(1);
		   PacBeginning.start();
		   }
		else
			if (index == 2)
			   {
			   if (!PacChomp.isRunning())
			      {
			      stopClip(2);
			      PacChomp.start();
			      }
			   }
	} // playClip
	
	public void stopClip( int index )
	{
		if (index == 1)
		   {
		   if (PacBeginning.isRunning() )
			   PacBeginning.stop();
		   PacBeginning.setFramePosition(0);
		   }
		else if (index == 2)
		   {
		   if (PacChomp.isRunning() )
			   PacChomp.stop();
		   PacChomp.setFramePosition(0);
		   }
	} // stopClip
	
} // MySounds class
