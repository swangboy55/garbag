import java.util.ArrayList;

public class MapControl
{
	private GameObjectList objList;
	private Camera camera;
	private double obstacleSpacing;
	private GameObject ground1;
	private GameObject ground2;
	private GameObject frontGround;
	private Runner runner;
	private ArrayList<GameObject> obstacleList;
	
	private static Animation testObstacle;
	
	static
	{
		testObstacle = new Animation(Integer.MAX_VALUE);
		testObstacle.addClip("res/6eMtaHG.png");
	}
	
	public MapControl(GameObjectList list, Camera camera)
	{
		objList = list;
		this.camera = camera;
		obstacleSpacing = 600;
		obstacleList = new ArrayList<GameObject>();
	}
	
	public Runner getRunner()
	{
		return runner;
	}
	
	public void initMap()
	{
		objList.clearMap();
		obstacleSpacing = 600;
		ground1 = new GameObject(800, 50, ID.GROUND, "res/dog.jpg");
		ground2 = new GameObject(800, 50, ID.GROUND, "res/dog.jpg");
		Animation runnerAnim = new Animation(100);
		runnerAnim.addClip("res/images/hes1.png");
		runnerAnim.addClip("res/images/hes2.png");
		runnerAnim.addClip("res/images/hes3.png");
		Animation fallingAnim = new Animation(Integer.MAX_VALUE);
		fallingAnim.addClip("res/images/hes3.png");
		runner = new Runner(50, 70, ID.RUNNER, runnerAnim, fallingAnim);
		
		ground1.setY(350);
		ground2.setY(350);
		ground2.setX(800);
		
		frontGround = ground2;
		
		runner.setY(210);
		runner.setX(500);
		runner.setxV(600);

		objList.addObject(ground1);
		objList.addObject(ground2);
		objList.addObject(runner);
		camera.setFollowObj(runner);
	}
	
	public void manageMap()
	{
		if(obstacleList.size() == 0)
		{
			GameObject newObstacle = new GameObject(80, 80, ID.OBSTACLE, testObstacle);
			newObstacle.setX(camera.getX() + camera.getW());
			newObstacle.setY(ground1.getY() - 80);
			objList.addObject(newObstacle);
			obstacleList.add(newObstacle);
		}
		else if((camera.getX() + camera.getW() -
				obstacleList.get(obstacleList.size() - 1).getX()) >= obstacleSpacing)
		{
			GameObject newObstacle = new GameObject(80, 80, ID.OBSTACLE, testObstacle);
			newObstacle.setX(camera.getX() + camera.getW());
			newObstacle.setY(ground1.getY() - 80);
			objList.addObject(newObstacle);
			obstacleList.add(newObstacle);
		}
		
		for(int a=0;a<obstacleList.size();a++)
		{
			if(obstacleList.get(a).getX() + obstacleList.get(a).getW() < camera.getX())
			{
				objList.removeObject(obstacleList.get(a));
				obstacleList.remove(a);
				a--;
			}
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
	
	public void setObstacleSpacing(double space)
	{
		obstacleSpacing = space;
	}
}
