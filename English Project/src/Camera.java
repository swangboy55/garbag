
public class Camera 
{
	
	private double x;
	private double y;
	private int w;
	private int h;
	private GameObject followObj;
	
	public Camera(int w, int h)
	{
		this.w = w;
		this.h = h;
		followObj = null;
	}
	
	public void setFollowObj(GameObject obj)
	{
		followObj = obj;
	}
	
	public boolean shouldRender(double x, double y, double w, double h)
	{
		if(x + w >= this.x && x <= (this.x + this.w))
		{
			if(y + h >= this.y && y <= (this.y + this.h))
			{
				return true;
			}
		}
		return false;
	}
	
	public void centerOnObject(GameObject go, double mapWidth, double mapHeight)
	{
		this.x = (go.getX() - (w / 2) + (go.getW() / 2));
		this.y = (go.getY() - (h / 2) + (go.getH() / 2));
		
		if(this.x < 0)
			this.x = 0;
		if(this.y < 0)
			this.y = 0;
		
		if(this.x + w >= mapWidth)
			this.x = mapWidth - w;
		if(this.y + h >= mapHeight)
			this.y = mapHeight - h;
		
	}
	
	public void inheritObjectVelocity()
	{
		if(followObj != null)
		{
			x += followObj.getxV();
			y += followObj.getyV();
		}
	}
	
	public double translateX(double x)
	{
		return x - this.x;
	}
	
	public double translateY(double y)
	{
		return y - this.y;
	}

//	public void 
}
