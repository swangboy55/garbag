
public class MapControl
{
	private GameObjectList objList;
	private Camera camera;
	private double obstacleSpacing;
	private GameObject ground1;
	private GameObject ground2;
	private GameObject frontGround;
	private Runner runner;
	
	public MapControl(GameObjectList list, Camera camera)
	{
		objList = list;
		this.camera = camera;
		obstacleSpacing = 600;
	}
	
	public void initMap()
	{
		objList.clearMap();
		obstacleSpacing = 600;
		ground1 = new GameObject(800, 50, ID.GROUND, "D:/JPEG/maxresdefault.jpg");
		ground2 = new GameObject(800, 50, ID.GROUND, "D:/JPEG/maxresdefault.jpg");
		Animation runnerAnim = new Animation(75);
		runnerAnim.addClip("res/6eMtaHG.png");//changeme
		runner = new Runner(50, 70, ID.RUNNER, runnerAnim);
		
		ground1.setY(350);
		ground2.setY(350);
		ground2.setX(800);
		
		frontGround = ground2;
		
		runner.setY(280);
		runner.setX(50);
		runner.setxV(400);

		objList.addObject(ground1);
		objList.addObject(ground2);
		objList.addObject(runner);
		camera.setFollowObj(runner);
	}
	
	public void manageMap()
	{
//		if((camera.getX() + camera.getW()))
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
