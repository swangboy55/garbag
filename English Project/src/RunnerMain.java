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
	private static BufferedImage pause;
	public static boolean keyPressed;
	private static boolean gameStarted = true;
	public static boolean paused = false;
	
	
//	private static boolean doIntroduction;
	
	static
	{
		BufferedImage scarletExp = null, tewwgExp = null, huckExp = null, gatzExp = null;
		try
		{
			scarletExp = ImageIO.read(new File("res/explanation/scarletExp.png"));
			pause = ImageIO.read(new File("res/paused.png"));
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
		{
			Animation hester, hesterFall, hesterGrass;
			Animation pearl, scaffold, scarlet;
			hester = new Animation(90);
			hesterFall = new Animation(Integer.MAX_VALUE);
			hesterGrass = new Animation(Integer.MAX_VALUE);
			pearl = new Animation(Integer.MAX_VALUE);
			scaffold = new Animation(Integer.MAX_VALUE);
			scarlet = new Animation(Integer.MAX_VALUE);
			
			hester.addClip("res/images/hes1.png");
			hester.addClip("res/images/hes2.png");
			hester.addClip("res/images/hes3.png");
			
			hesterFall.addClip("res/images/hes1.png");
			
			hesterGrass.addClip("res/backgrounds/grassground.png"); 
			
			pearl.addClip("res/obstacles/pearl.png");
			scaffold.addClip("res/obstacles/scaffold.png");
			scarlet.addClip("res/obstacles/scarlet.png");
			
			Level testLevel = new Level(camera, 20, hester, hesterFall, hesterGrass, "res/backgrounds/scarlet1.png", "res/backgrounds/scarlet2.png", 500, 10);

			testLevel.addObstacle(pearl, 350 - 50, 1400, 50, 50);
			testLevel.addObstacle(scaffold, 350 - 70, 1400, 70, 70);
			testLevel.addObstacle(scarlet, 0, 1400, 60, 60);
			
			mapCtl.addLevel(testLevel);
		}
		{
			Animation janie, janieFall, janieGrass;
			Animation mule;
			janie = new Animation(90);
			janieFall = new Animation(Integer.MAX_VALUE);
			janieGrass = new Animation(Integer.MAX_VALUE);
			mule = new Animation(Integer.MAX_VALUE);
			
			janie.addClip("res/images/jan1.png");
			janie.addClip("res/images/jan2.png");
			janie.addClip("res/images/jan3.png");
			
			janieFall.addClip("res/images/jan1.png");
			
			janieGrass.addClip("res/backgrounds/grassground.png");
			
			mule.addClip("res/obstacles/mule.png");
			
			Level testLevel = new Level(camera, 25, janie, janieFall, janieGrass, "res/backgrounds/tewwg1.png", "res/backgrounds/tewwg2.png", 500, 7);
			
			testLevel.addObstacle(mule, 0, 1400, 70, 60);
			
			mapCtl.addLevel(testLevel);
		}
		{
			Animation hester, hesterFall, hesterGrass, shirt;
			hester = new Animation(90);
			hesterFall = new Animation(Integer.MAX_VALUE);
			hesterGrass = new Animation(Integer.MAX_VALUE);
			shirt = new Animation(Integer.MAX_VALUE);
			
			hester.addClip("res/images/huc1.png");
			hester.addClip("res/images/huc2.png");
			hester.addClip("res/images/huc3.png");
			hester.addClip("res/images/huc2.png");
			
			hesterFall.addClip("res/images/huc1.png");
			
			hesterGrass.addClip("res/backgrounds/huckground.png");
			
			shirt.addClip("res/obstacles/gatzshirt.png");
			
			Level testLevel = new Level(camera, 1, hester, hesterFall, hesterGrass, "res/backgrounds/huck1.png", "res/backgrounds/huck2.png", 500, 10);
			
		//	testLevel.addObstacle(shirt, 0, 1400);
			
			mapCtl.addLevel(testLevel);
		}
		{
			Animation hester, hesterFall, hesterGrass, shirt;
			hester = new Animation(90);
			hesterFall = new Animation(Integer.MAX_VALUE);
			hesterGrass = new Animation(Integer.MAX_VALUE);
			shirt = new Animation(Integer.MAX_VALUE);
			
			hester.addClip("res/images/gat1.png");
			hester.addClip("res/images/gat2.png");
			hester.addClip("res/images/gat3.png");
			
			hesterFall.addClip("res/images/gat1.png");
			
			hesterGrass.addClip("res/backgrounds/gatzground.png");
			
			shirt.addClip("res/obstacles/gatzshirt.png");
			
			Level testLevel = new Level(camera, 1, hester, hesterFall, hesterGrass, "res/backgrounds/city1.png", "res/backgrounds/city2.png", 500, 10);
			
		//	testLevel.addObstacle(shirt, 0, 1400);
			
			mapCtl.addLevel(testLevel);
		}
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
				if(gameStarted)
				{
					mapCtl.initLevel(0);
					gameStarted = false;
				}
				else
				{
					mapCtl.initLevel(mapCtl.getCurLevel() + 1);
				}
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
	
	
	
	private void renderPaused(Window wind)
	{
		Graphics g = wind.getBufferGraphics();
		
		g.setColor(Color.GREEN);
		
		g.drawImage(pause, 0, 0, null);
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
				if(!paused)
				{
					camera.tickCamera(tickTime);
					objectList.tickAll(tickTime);
					mapCtl.manageMap();
				}
				window.clear();
				if(paused)
				{
					mapCtl.updateAndRenderBG(0, window);
				}
				else
				{
					mapCtl.updateAndRenderBG(tickTime, window);
				}
				objectList.renderAll(window, camera);
				if(paused)
				{
					renderPaused(window);
				}
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
