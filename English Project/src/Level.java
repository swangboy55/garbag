import java.util.ArrayList;

//resource holder for each book
public class Level 
{
	private class ObstacleGenerator
	{
		public Animation animation;
		public double ySpawnLocation;
		public double gravity;
		public int width;
		public int height;
		
		public GameObject generateObstacle()
		{
			GameObject obstacle = new GameObject(width, height, ID.OBSTACLE, animation, gravity);
			if(ySpawnLocation == -1)
			{
				obstacle.setY(270);
			}
			else
			{
				obstacle.setY(ySpawnLocation);
			}
			return obstacle;
		}
	}
	
	private int obstaclesToOvercome;
	private ArrayList<ObstacleGenerator> obstacles;
	private Animation runnerAnimation, groundAnimation, fallingAnimation;
	private BackgroundManager levelBackground;
	private double spacingChallenge, spacing, spacingRand;
	
	public Level(Camera cam, int obstacles, Animation runner, Animation falling, Animation ground, String bg1, String bg2, double s, double sc, double sr)
	{
		obstaclesToOvercome = obstacles;
		runnerAnimation = runner;
		groundAnimation = ground;
		fallingAnimation = falling;
		this.obstacles = new ArrayList<ObstacleGenerator>();
		levelBackground = new BackgroundManager(cam, bg1, bg2);
		spacing = s;
		spacingChallenge = sc;
		spacingRand = sr;
	}
	
	public int numObstacles()
	{
		return obstaclesToOvercome;
	}
	
	public void addObstacle(Animation animation, double ySpawn, double grav, int width, int height)
	{
		ObstacleGenerator newGenerator = new ObstacleGenerator();
		newGenerator.animation = animation;
		newGenerator.gravity = grav;
		newGenerator.ySpawnLocation = ySpawn;
		newGenerator.width = width;
		newGenerator.height = height;
		obstacles.add(newGenerator);
	}
	
	public GameObject generateRandomObstacle()
	{
		if(obstacles.size() == 0)
		{
			return null;
		}
		int ro = (int)(Math.random() * obstacles.size());
		return obstacles.get(ro).generateObstacle();
	}
	
	public void loadLevel(MapControl gameInstance)
	{
		levelBackground.reset();

		gameInstance.setRunnerAnim(runnerAnimation);
		gameInstance.setGroundAnim(groundAnimation);
		gameInstance.setFallingAnim(fallingAnimation);
		gameInstance.setBackground(levelBackground);
		gameInstance.setObstacleSpawnMode(MapControl.SPACED);
		gameInstance.setObstacleSpacing(spacing, spacingRand);
		gameInstance.setSpacingChallengeRate(spacingChallenge);
	}
	
	
}
