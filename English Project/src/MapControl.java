import java.util.ArrayList;

public class MapControl
{
	private GameObjectList objList;
	private Camera camera;
	private GameObject ground1;
	private GameObject ground2;
	private GameObject frontGround;
	private Runner runner;
	private ArrayList<GameObject> obstacleList;
	
	//resources
	private Animation runnerAnim, groundAnim, fallingAnim;
	private BackgroundManager background;
	

	private int obstacleSpawnMode;
	private long obstacleSpawnTime;
	private long randomRange;
	private double obstacleSpacing;
	private double spacingRange;
	private int curLevel;
	private int obstaclesGenerated;
	private int obstaclesCleared;
	private double spacingChallengeRate;
	
	private ArrayList<Level> levels;
	
	private long lastSpawnTime;
	private long nextRandomSpawnTime;
	
	private double nextRandomSpacing;
	
	public static final int SPACED = 1;
	public static final int TIMED = 2;
	
	
	public MapControl(GameObjectList list, Camera camera)
	{
		curLevel = 0;
		levels = new ArrayList<Level>();
		obstacleSpawnTime = 600;
		randomRange = 400;
		obstacleSpawnMode = TIMED;
		objList = list;
		this.camera = camera;
		obstacleSpacing = 600;
		obstacleList = new ArrayList<GameObject>();
	}
	
	public int getObstaclesCleared()
	{
		return obstaclesCleared;
	}
	
	public Runner getRunner()
	{
		return runner;
	}
	
	public void setObstacleSpawnMode(int mode)
	{
		obstacleSpawnMode = mode;
	}
	
	public void setObstacleSpawnTime(long baseTime, long randRange)
	{
		obstacleSpawnTime = baseTime;
		randomRange = randRange;
	}
	
	public void setSpacingChallengeRate(double rate)
	{
		spacingChallengeRate = rate;
	}
	
	public void initLevel(int index)
	{
		curLevel = index;
		camera.setX(0);
		objList.clearMap();
		obstacleList.clear();
		obstaclesGenerated = 0;
		obstaclesCleared = 0;
		levels.get(index).loadLevel(this);
		
		ground1 = new GameObject(800, 50, ID.GROUND, groundAnim, 0);
		ground2 = new GameObject(800, 50, ID.GROUND, groundAnim, 0);
		runner = new Runner(50, 70, ID.RUNNER, runnerAnim, fallingAnim);
		
		ground1.setY(350);
		ground2.setY(350);
		ground2.setX(800);
		
		frontGround = ground2;
		
		runner.setY(210);
		runner.setX(100);
		runner.setxV(600);

		objList.addObject(ground1);
		objList.addObject(ground2);
		objList.addObject(runner);
		camera.setFollowObj(runner);
		
		lastSpawnTime = System.currentTimeMillis();
		genNewRandomSpawnTime();
		genNewRandomSpacing();
	}
	
	private GameObject generateRandomObstacle()
	{
		return levels.get(curLevel).generateRandomObstacle();
	}
	
	private void genNewRandomSpawnTime()
	{
		nextRandomSpawnTime = (long)(Math.random() * (double)randomRange);
	}
	
	private void genNewRandomSpacing()
	{
		nextRandomSpacing = (double)(Math.random() * spacingRange);
		spacingRange -= spacingChallengeRate;
		obstacleSpacing -= spacingChallengeRate;
		if(spacingRange <= 0)
		{
			spacingRange = spacingChallengeRate;
		}
		if(obstacleSpacing <= 0)
		{
			obstacleSpacing = spacingChallengeRate;
		}
	}
	
	public void spacedObstacleSpawn()
	{
		if(obstacleList.size() == 0)
		{
			GameObject newObstacle = generateRandomObstacle();
			newObstacle.setX(camera.getX() + camera.getW());
			objList.addObject(newObstacle);
			obstacleList.add(newObstacle);
			obstaclesGenerated++;
			genNewRandomSpacing();
		}
		else if((camera.getX() + camera.getW() -
				obstacleList.get(obstacleList.size() - 1).getX()) >= obstacleSpacing + nextRandomSpacing)
		{
			GameObject newObstacle = generateRandomObstacle();
			newObstacle.setX(camera.getX() + camera.getW());
			objList.addObject(newObstacle);
			obstacleList.add(newObstacle);
			obstaclesGenerated++;
			genNewRandomSpacing();
		}
	}
	
	public void timedObstacleSpawn()
	{
		if(System.currentTimeMillis() - lastSpawnTime > obstacleSpawnTime + nextRandomSpawnTime)
		{
			lastSpawnTime = System.currentTimeMillis();

			GameObject newObstacle = generateRandomObstacle();
			newObstacle.setX(camera.getX() + camera.getW());
			objList.addObject(newObstacle);
			obstacleList.add(newObstacle);
			obstaclesGenerated++;
			
			genNewRandomSpawnTime();
		}
	}
	
	public void manageMap()
	{
		if(runner.getDeathState())
		{
			initLevel(curLevel);
			return;
		}
		if(obstaclesGenerated < levels.get(curLevel).numObstacles())
		{
			if(obstacleSpawnMode == MapControl.SPACED)
			{
				spacedObstacleSpawn();
			}
			else if(obstacleSpawnMode == MapControl.TIMED)
			{
				timedObstacleSpawn();
			}
		}
		for(int a=0;a<obstacleList.size();a++)
		{
			if(obstacleList.get(a).getX() + obstacleList.get(a).getW() < camera.getX())
			{
				objList.removeObject(obstacleList.get(a));
				obstacleList.remove(a);
				a--;
				obstaclesCleared++;
			}
		}
		if(obstaclesCleared == levels.get(curLevel).numObstacles())
		{
			camera.setFollowObj(null);
			endOfLevel();
			return;
		}
		if(camera.getX() + camera.getW() > frontGround.getX() + frontGround.getW())
		{
			if(frontGround == ground1)
			{
				frontGround = ground2;
				frontGround.setX(ground1.getX() + 800);
			}
			else
			{
				frontGround = ground1;
				frontGround.setX(ground2.getX() + 800);
			}
		}
	}
	
	void endOfLevel()
	{
		if(runner.getX() > camera.getX() + camera.getW())
		{
			RunnerMain.initGradient();
		}
	}
	
	public void updateAndRenderBG(double deltaTime, Window window)
	{
		background.moveBackgrounds(deltaTime);
		background.renderBackgrounds(window);
	}
	
	public void setObstacleSpacing(double space, double random)
	{
		obstacleSpacing = space;
		spacingRange = random;
	}
	
	public void setRunnerAnim(Animation runnerAnim)
	{
		this.runnerAnim = runnerAnim;
	}

	public void setGroundAnim(Animation groundAnim) 
	{
		this.groundAnim = groundAnim;
	}
	
	public void setFallingAnim(Animation fallingAnim) 
	{
		this.fallingAnim = fallingAnim;
	}
	
	public void setBackground(BackgroundManager background) 
	{
		this.background = background;
	}
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
}
