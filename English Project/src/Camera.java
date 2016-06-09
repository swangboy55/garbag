
public class Camera 
{
	
	private double x;
	private double y;
	private int w;
	private int h;
	private Runner followObj;
	
	public Camera(int w, int h)
	{
		this.w = w;
		this.h = h;
		followObj = null;
	}
	
	public void setFollowObj(Runner obj)
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
	
	public void inheritObjectXMove()
	{
		if(followObj != null)
		{
			x += followObj.getX() - followObj.getPrevX();
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

	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getW()
	{
		return (double)w;
	}
	
	public double getH()
	{
		return (double)h;
	}
}
