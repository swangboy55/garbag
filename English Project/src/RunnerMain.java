public class RunnerMain 
{
	public static boolean running;
	private GameObjectList objectList;
	private Camera camera;
	private Window window;
	private KeyInput keyInput;
	private MapControl mapCtl;
	
	public static void main(String[] args)
	{
		new RunnerMain().begin();
	}
	
	//init levels method
	public void initLevels()
	{
		Animation hester, hesterFall, dog, shirt;
		hester = new Animation(120);
		hesterFall = new Animation(Integer.MAX_VALUE);
		dog = new Animation(Integer.MAX_VALUE);
		shirt = new Animation(Integer.MAX_VALUE);
		
		hester.addClip("res/images/hes1.png");
		hester.addClip("res/images/hes2.png");
		hester.addClip("res/images/hes3.png");
		
		hesterFall.addClip("res/images/hes2.png");
		
		dog.addClip("res/dog.jpg");
		
		shirt.addClip("res/gatzshirt.png");
		
		Level testLevel = new Level(camera, 20, hester, hesterFall, dog, "res/city.png", "res/city2.png");
		
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
		
		mainLoop();
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
			camera.inheritObjectXMove();
			camera.tickCamera(tickTime);
			objectList.tickAll(tickTime);
			mapCtl.manageMap();
			window.clear();
			
			mapCtl.updateAndRenderBG(tickTime, window);
			
			objectList.renderAll(window, camera);
			Score.render(window, mapCtl);
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
