import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation 
{
	private long curTime;
	private int animationTime;
	private int curFrame;
	private ArrayList<BufferedImage> clips;
	
	public Animation(int time)
	{
		animationTime = time;
		clips = new ArrayList<BufferedImage>();
		curTime = System.currentTimeMillis();
		curFrame = 0;
	}
	
	public void addClip(BufferedImage clip)
	{
		clips.add(clip);
	}
	
	public void addClip(String directory)
	{
		BufferedImage clip;
		try
		{
			clip = ImageIO.read(new File(directory));
		}
		catch(Exception e)
		{
			return;
		}
		clips.add(clip);
	}
	
	public void nextFrame()
	{
		if(System.currentTimeMillis() - curTime > animationTime)
		{
			curTime = System.currentTimeMillis();
			curFrame++;
			if(curFrame == clips.size())
			{
				curFrame = 0;
			}
		}
	}
	
	public BufferedImage getCurrentImage()
	{
		if(clips.size() == 0)
		{
			return null;
		}
		return clips.get(curFrame);
	}
}
