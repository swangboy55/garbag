import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;

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
	private static ArrayList<String> BGMs = new ArrayList<String>();
	private static BufferedImage pause;
	public static boolean keyPressed;
	private static boolean gameStarted = true;
	public static boolean paused = false;
	private static Clip curClip;
	
//	private static boolean doIntroduction;
	
	static
	{
		BufferedImage scarletExp = null, tewwgExp = null, huckExp = null, gatzExp = null, credits = null;
		try
		{
			pause = ImageIO.read(new File("res/paused.png"));
			scarletExp = ImageIO.read(new File("res/explanation/scarletExp.png"));
			tewwgExp = ImageIO.read(new File("res/explanation/tewwgExp.png"));
			huckExp = ImageIO.read(new File("res/explanation/huckExp.png"));
			gatzExp = ImageIO.read(new File("res/explanation/gatsbyExp.png"));
			credits = ImageIO.read(new File("res/credits.png"));
			curClip = AudioSystem.getClip();
		}
		catch(Exception e){}
		explanations.add(scarletExp);
		explanations.add(tewwgExp);
		explanations.add(huckExp);
		explanations.add(gatzExp);
		explanations.add(credits);
		BGMs.add("res/music/Forest.wav");
		BGMs.add("res/music/god.wav");
		BGMs.add("res/music/huck.wav");
		BGMs.add("res/music/gatsby.wav");
		
		
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
			
			Level level1 = new Level(camera, 20, hester, hesterFall, hesterGrass, "res/backgrounds/scarlet1.png", "res/backgrounds/scarlet2.png", 2000, 80, 100);

			level1.addObstacle(pearl, 350 - 50, 1400, 50, 50);
			level1.addObstacle(scaffold, 350 - 70, 1400, 70, 70);
			level1.addObstacle(scarlet, 0, 400, 60, 60);
			
			mapCtl.addLevel(level1);
		}
		{
			Animation janie, janieFall, janieGrass;
			Animation mule, rag, hurricane;
			janie = new Animation(90);
			janieFall = new Animation(Integer.MAX_VALUE);
			janieGrass = new Animation(Integer.MAX_VALUE);
			mule = new Animation(Integer.MAX_VALUE);
			rag = new Animation(Integer.MAX_VALUE);
			hurricane = new Animation(Integer.MAX_VALUE);
			
			janie.addClip("res/images/jan1.png");
			janie.addClip("res/images/jan2.png");
			janie.addClip("res/images/jan3.png");
			
			janieFall.addClip("res/images/jan1.png");
			
			janieGrass.addClip("res/backgrounds/grassground.png");
			
			mule.addClip("res/obstacles/mule.png");
			rag.addClip("res/obstacles/cloth.png");
			hurricane.addClip("res/obstacles/hurricaneharbor.png");
			
			Level level2 = new Level(camera, 25, janie, janieFall, janieGrass, "res/backgrounds/tewwg1.png", "res/backgrounds/tewwg2.png", 1500, 40, 100);
			
			level2.addObstacle(mule, 350 - 60, 0, 70, 60);
			level2.addObstacle(rag, 0, 400, 60, 60);
			level2.addObstacle(hurricane, 0, 400, 70, 70);
			
			mapCtl.addLevel(level2);
		}
		{
			Animation huck, huckFall, huckWater;
			Animation whiskey, crown, school;
			huck = new Animation(90);
			huckFall = new Animation(Integer.MAX_VALUE);
			huckWater = new Animation(Integer.MAX_VALUE);
			whiskey = new Animation(Integer.MAX_VALUE);
			crown = new Animation(Integer.MAX_VALUE);
			school = new Animation(Integer.MAX_VALUE);
			
			huck.addClip("res/images/huc1.png");
			huck.addClip("res/images/huc2.png");
			huck.addClip("res/images/huc3.png");
			huck.addClip("res/images/huc2.png");
			
			huckFall.addClip("res/images/huc3.png");
			
			huckWater.addClip("res/backgrounds/huckground.png");

			whiskey.addClip("res/obstacles/whisky.png");
			crown.addClip("res/obstacles/crown.png");
			school.addClip("res/obstacles/school.png");
			
			Level level3 = new Level(camera, 30, huck, huckFall, huckWater, "res/backgrounds/huck1.png", "res/backgrounds/huck2.png", 1000, 20, 100);

			level3.addObstacle(whiskey, 350 - 60, 0, 50, 60);
			level3.addObstacle(crown, 350 - 32, 0, 32, 32);
			level3.addObstacle(school, 350 - 70, 0, 70, 70);
			
			mapCtl.addLevel(level3);
		}
		{
			Animation gatsby, gatsbyFall, gatsbyRoad;
			Animation shirt, clock, car, light, dollar;
			gatsby = new Animation(90);
			gatsbyFall = new Animation(Integer.MAX_VALUE);
			gatsbyRoad = new Animation(Integer.MAX_VALUE);
			shirt = new Animation(Integer.MAX_VALUE);
			clock = new Animation(Integer.MAX_VALUE);
			car = new Animation(Integer.MAX_VALUE);
			light = new Animation(Integer.MAX_VALUE);
			dollar = new Animation(Integer.MAX_VALUE);
			
			gatsby.addClip("res/images/gat1.png");
			gatsby.addClip("res/images/gat2.png");
			gatsby.addClip("res/images/gat3.png");
			
			gatsbyFall.addClip("res/images/gat1.png");
			
			gatsbyRoad.addClip("res/backgrounds/gatzground.png");
			
			shirt.addClip("res/obstacles/gatzshirt.png");
			clock.addClip("res/obstacles/clcockkckckk.png");
			car.addClip("res/obstacles/choochoo.png");
			light.addClip("res/obstacles/light.png");
			dollar.addClip("res/obstacles/hollahollagetdolla.png");
			
			Level level4 = new Level(camera, 35, gatsby, gatsbyFall, gatsbyRoad, "res/backgrounds/city1.png", "res/backgrounds/city2.png", 500, 4, 100);
			
			level4.addObstacle(shirt, 0, 300, 40, 40);
			level4.addObstacle(clock, 350 - 60, 0, 60, 60);
			level4.addObstacle(car, 350 - 40, 0, 70, 40);
			level4.addObstacle(light, 350 - 70, 0, 50, 70);
			level4.addObstacle(dollar, 0, 300, 70, 30);
			mapCtl.addLevel(level4);
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
			if(mapCtl.getCurLevel() != 3)
			{
				curClip.stop();
				curClip.close();
			}
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			if(gameStarted)
			{
				g.drawImage(explanations.get(mapCtl.getCurLevel()), 0, 0, null);
			}
			else
			{
				g.drawImage(explanations.get(mapCtl.getCurLevel() + 1), 0, 0, null);
			}
			if(keyPressed)
			{
				
				if(mapCtl.getCurLevel() == 3)
				{
					System.exit(0);
				}
				
				keyPressed = false;
				gradientAmt = 2;
				if(gameStarted)
				{
					try
					{
						AudioInputStream temp = AudioSystem.getAudioInputStream(new File(BGMs.get(0)));
						curClip.open(temp);
						temp.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					mapCtl.initLevel(0);
					gameStarted = false;
				}
				else
				{
					try
					{
						AudioInputStream temp = AudioSystem.getAudioInputStream(new File(BGMs.get(mapCtl.getCurLevel() + 1)));
						curClip.open(temp);
						temp.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					mapCtl.initLevel(mapCtl.getCurLevel() + 1);
				}
				curClip.loop(Clip.LOOP_CONTINUOUSLY);
				curClip.start();
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
