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
	//init method
	public void begin()
	{
		running = true;
		objectList = new GameObjectList();
//		Animation hesterAnimation = new Animation(60);
		camera = new Camera(800, 400);
//		hesterAnimation.addClip("res/6eMtaHG.png");
		keyInput = new KeyInput(objectList);
		window = new Window(800, 400, keyInput);
//		runner = new Runner(50, 70, ID.RUNNER, hesterAnimation);
//		GameObject ground = new GameObject(800, 50, ID.GROUND, "D:\\JPEG\\maxresdefault.jpg");
//		ground.setY(350);
//		
//		objectList.addObject(ground);
//		
//		
//		
//		runner.setX(50);
//		runner.setY(280);
//		runner.setxV(400);
//		camera.setFollowObj(runner);
//		objectList.addObject(runner);
		mapCtl = new MapControl(objectList, camera);
		mapCtl.initMap();
		window.setVisible(true);
		mainLoop();
	}
	
	public void mainLoop()
	{
		long lastTick = System.currentTimeMillis();
		double tickTime;
		while(running)
		{
			tickTime = (double)(System.currentTimeMillis() - lastTick) / 1000.0;
			lastTick = System.currentTimeMillis();
			objectList.tickAll(tickTime);
			camera.inheritObjectXMove();
			mapCtl.manageMap();
			window.clear();
			
			
			objectList.renderAll(window, camera);
			
			window.swap();
			try
			{
				Thread.sleep(16);
			} 
			catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
