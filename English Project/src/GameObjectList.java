import java.util.ArrayList;


public class GameObjectList {

	private ArrayList<GameObject> list = new ArrayList<GameObject>();
	public GameObjectList()
	{
	}
	
	public void clearMap()
	{
		list.clear();
	}
	
	public int addObject(GameObject g)
	{
		list.add(g);
		return list.size()-1;
	}
	public void removeObject(int index)
	{
		list.remove(index);
	}
	
	public ArrayList<GameObject> getList()
	{
		return list;
	}
	
	public GameObject get(int index)
	{
		return list.get(index);
	}
	
	public void removeObject(GameObject g)
	{
		for(int i = 0; i < list.size(); i++){
			if(list.get(i) == g) {
				list.remove(i);
				break;
			}
		}
	}
	
	public Runner getRunner()
	{
		for(GameObject go : list)
		{
			if(go.getID() == ID.RUNNER)
				return (Runner) go;
		}
		return null;
	}

	public void renderAll(Window wind, Camera cam)
	{
		for(GameObject go : list)
		{
			go.render(wind, cam);
		}
	}
	
	public GameObject getGround()
	{
		for(GameObject go : list)
		{
			if(go.getID() == ID.GROUND)
			{
				return go;
			}
		}
		return null;
	}
	
	public void tickAll(double deltaTime)
	{
		for(GameObject go : list)
		{
			go.tick(deltaTime);
			if(go.getID() == ID.RUNNER)//add more later
			{
				for(GameObject g2 : list)
				{
					if(go == g2)
					{
						continue;
					}
					if(go.isColliding(g2))
					{
						double depthX = go.getPenetrationDepthX(g2);
						double depthY = go.getPenetrationDepthY(g2);
						if(Math.abs(depthX) < Math.abs(depthY))
						{
							go.correctPositionX(depthX);
						}
						else
						{
							go.correctPositionY(depthY);
						}
					}
				}
			}
		}
	}
	
	public int size()
	{
		return list.size();
	}
}
