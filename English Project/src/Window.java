import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Window extends Canvas 
{
	public static final int VB_SIZE = 29;
	public static final int HB_SIZE = 6;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage frameBuffer;
	private JFrame frame;
	private BufferStrategy strat;
	private Graphics g;
	private Graphics bufferGraphics;
	
	public Window(int width, int height, KeyInput listener)
	{
		this.setPreferredSize(new Dimension(width + HB_SIZE, height + VB_SIZE));
		this.addKeyListener(listener);
		
		frame = new JFrame("Meme");
		frame.setPreferredSize(new Dimension(width + HB_SIZE, height + VB_SIZE));
		frame.setMaximumSize(new Dimension(width + HB_SIZE, height + VB_SIZE));
		frame.setMinimumSize(new Dimension(width + HB_SIZE, height + VB_SIZE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setResizable(false);
		
		frame.pack();
		
		
		
		frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.createBufferStrategy(1);
		strat = getBufferStrategy();
		g = strat.getDrawGraphics();
		
		bufferGraphics = frameBuffer.getGraphics();
	}
	
	public BufferedImage getBufferedImage()
	{
		return frameBuffer;
	}
	
	public Graphics getBufferGraphics()
	{
		return bufferGraphics;
	}
	
	public void setVisible(boolean vis)
	{
		frame.setVisible(vis);
		frame.requestFocus();
	}
	
	public void clear()
	{
		bufferGraphics.setColor(Color.black);
		int w = getWidth(), h = getHeight();
		bufferGraphics.fillRect(0, 0, w, h);
	}
	
	public void swap()
	{
		g.drawImage(frameBuffer, 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
		strat.show();
	}

}
