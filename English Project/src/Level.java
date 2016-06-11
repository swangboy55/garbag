import java.util.ArrayList;

//resource holder for each book
public class Level 
{
	private class ObstacleGenerator
	{
		public Animation animation;
		public double ySpawnLocation;
		public double gravity;
		
		public GameObject generateObstacle()
		{
			GameObject obstacle = new GameObject(40, 40, ID.OBSTACLE, animation, gravity);
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
	
	public Level(Camera cam, int obstacles, Animation runner, Animation falling, Animation ground, String bg1, String bg2)
	{
		obstaclesToOvercome = obstacles;
		runnerAnimation = runner;
		groundAnimation = ground;
		fallingAnimation = falling;
		this.obstacles = new ArrayList<ObstacleGenerator>();
		levelBackground = new BackgroundManager(cam, bg1, bg2);
	}
	
	public int numObstacles()
	{
		return obstaclesToOvercome;
	}
	
	public void addObstacle(Animation animation, double ySpawn, double grav)
	{
		ObstacleGenerator newGenerator = new ObstacleGenerator();
		newGenerator.animation = animation;
		newGenerator.gravity = grav;
		newGenerator.ySpawnLocation = ySpawn;
		obstacles.add(newGenerator);
	}
	
	public GameObject generateRandomObstacle()
	{
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
		gameInstance.setObstacleSpacing(500, 200);
		gameInstance.setSpacingChallengeRate(30);
	}
	
	
}
