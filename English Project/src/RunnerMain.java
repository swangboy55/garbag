import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RunnerMain 
{
	public static boolean running;
	private GameObjectList objectList;
	private Camera camera;
	private Window window;
	private KeyInput keyInput;
	private MapControl mapCtl;
	private static boolean doGradient;
	private static double gradientAmt;
	private static ArrayList<BufferedImage> explanations = new ArrayList<BufferedImage>();
	public static boolean keyPressed;
//	private static boolean doIntroduction;
	
	static
	{
		BufferedImage scarletExp = null, tewwgExp = null, huckExp = null, gatzExp = null;
		try
		{
			scarletExp = ImageIO.read(new File("res/explanation/scarletExp.png"));
			tewwgExp = ImageIO.read(new File("res/explanation/tewwgExp.png"));
			huckExp = ImageIO.read(new File("res/explanation/huckExp.png"));
			gatzExp = ImageIO.read(new File("res/explanation/gatzExp.png"));
		}
		catch(Exception e){}
		explanations.add(scarletExp);
		explanations.add(tewwgExp);
		explanations.add(huckExp);
		explanations.add(gatzExp);
		keyPressed = false;
	}
	
	public static void main(String[] args)
	{
		doGradient = false;
		new RunnerMain().begin();
	}
	
//	public boolean introducingLevel()
//	{
//		return doIntroduction;
//	}
	
	//init levels method
	public void initLevels()
	{
		Animation hester, hesterFall, dog, shirt;
		hester = new Animation(90);
		hesterFall = new Animation(Integer.MAX_VALUE);
		dog = new Animation(Integer.MAX_VALUE);
		shirt = new Animation(Integer.MAX_VALUE);
		
		hester.addClip("res/images/jan1.png");
		hester.addClip("res/images/jan2.png");
		hester.addClip("res/images/jan3.png");
		
		hesterFall.addClip("res/images/jan1.png");
		
		dog.addClip("res/dog.jpg");
		
		shirt.addClip("res/gatzshirt.png");
		
		Level testLevel = new Level(camera, 1, hester, hesterFall, dog, "res/city.png", "res/city2.png");
		
		testLevel.addObstacle(shirt, 0, 1400);
		
		mapCtl.addLevel(testLevel);
	}

	//init class method
	public void begin()
	{
		running = true;
		objectList = new GameObjectList();
		camera = new Camera(800, 400);
		keyInput = new KeyInput(objectList);
		window = new Window(800, 400, keyInput);
		mapCtl = new MapControl(objectList, camera);
		
		initLevels();
		
		mapCtl.initLevel(0);
		window.setVisible(true);
		gradientAmt = 1;
		doGradient = true;
		
		mainLoop();
	}
	
	public static void initGradient()
	{
		doGradient = true;
		gradientAmt = 0;
	}
	
	private static void gradiate(double deltaTime, Window window, MapControl mapCtl)
	{
		Graphics g = window.getBufferGraphics();
		if(gradientAmt < 1)
		{
			Color blackGrad = new Color(0, 0, 0, (float)gradientAmt);
			g.setColor(blackGrad);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			gradientAmt += 3 * deltaTime;
			if(gradientAmt >= 1.0)
			{
				gradientAmt = 1;
			}
		}
		else if(gradientAmt < 2)
		{
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			g.drawImage(explanations.get(mapCtl.getCurLevel()), 0, 0, null);
			if(keyPressed)
			{
				keyPressed = false;
				gradientAmt = 2;
				mapCtl.initLevel(0);
			}
		}
		else if(gradientAmt < 3)
		{
			Color blackGrad = new Color(0, 0, 0, (float)(3.0 - gradientAmt));
			g.setColor(blackGrad);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			gradientAmt += 3 * deltaTime;
			if(gradientAmt >= 3.0)
			{
				gradientAmt = 3;
				doGradient = false;
			}
		}
	}
	
	public void mainLoop()
	{
		long lastTick = System.currentTimeMillis();
		double tickTime;
		Score.init(window);
		while(running)
		{
			tickTime = (double)(System.currentTimeMillis() - lastTick) / 1000.0;
			lastTick = System.currentTimeMillis();
			if(!RunnerMain.doGradient)
			{
				camera.inheritObjectXMove();
				camera.tickCamera(tickTime);
				objectList.tickAll(tickTime);
				mapCtl.manageMap();
				window.clear();
				
				mapCtl.updateAndRenderBG(tickTime, window);
				
				objectList.renderAll(window, camera);
				Score.render(window, mapCtl);
			}
			else
			{
				window.clear();
				mapCtl.updateAndRenderBG(tickTime, window);
				
				objectList.renderAll(window, camera);
				Score.render(window, mapCtl);
				gradiate(tickTime, window, mapCtl);
			}
			window.swap();
			try
			{
				Thread.sleep(1);
			} 
			catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
