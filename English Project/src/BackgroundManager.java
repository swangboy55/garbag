import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class BackgroundManager 
{
	private Camera camera;
	public static double BG1SLOW = 0.5;
	public static double BG2SLOW = 0.8;
	private BufferedImage bg1, bg2;
	
	private double bgx1;
	private double bgx2;
	
	public BackgroundManager(Camera camera, String path1, String path2)
	{
		this.camera = camera;
		try
		{
			bg1 = ImageIO.read(new File(path1));
			bg2 = ImageIO.read(new File(path2));
		}
		catch(Exception e)
		{
			bg1 = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
			bg2 = bg1;
		}
	}
	
	public void reset()
	{
		bgx1 = 0;
		bgx2 = 0;
	}
	
	public void moveBackgrounds(double deltaTime)
	{
		bgx1 += (camera.getxV() * deltaTime) * BG1SLOW;
		bgx2 += (camera.getxV() * deltaTime) * BG2SLOW;
		
		if(bgx2 + (bg2.getWidth() * 2) < camera.getX() + camera.getW())
		{
			bgx2 += 800;
		}
		
		if(bgx1 + (bg1.getWidth() * 2) < camera.getX() + camera.getW())
		{
			bgx1 += 800;
		}
	}
	
	public void renderBackgrounds(Window window)
	{
		Graphics g = window.getBufferGraphics();
		
		g.drawImage(bg2, (int)(bgx2 - camera.getX()), 0, null);
		g.drawImage(bg2, (int)(bgx2 - camera.getX()) + bg2.getWidth(), 0, null);
		g.drawImage(bg1, (int)(bgx1 - camera.getX()), 0, null);
		g.drawImage(bg1, (int)(bgx1 - camera.getX()) + bg1.getWidth(), 0, null);
	}
}
