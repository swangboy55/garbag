import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Score 
{
	public static void init(Window window)
	{
		Graphics2D g = (Graphics2D)window.getBufferGraphics();
		g.setFont(new Font("Impact", Font.BOLD, 40));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
	}
	public static void render(Window window, MapControl mapCtl)
	{
		Graphics g = window.getBufferGraphics();
		drawOutlined(g, "Cleared: " + mapCtl.getObstaclesCleared());
	}
	
	private static void drawOutlined(Graphics g, String s)
	{
		g.setColor(Color.RED);
		g.drawString(s, 5, 51);
		g.drawString(s, 5, 49);
		g.drawString(s, 6, 50);
		g.drawString(s, 4, 50);
		
		g.setColor(Color.BLACK);
		g.drawString(s, 5, 50);
	}
}
