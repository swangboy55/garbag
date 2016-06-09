public class Runner extends GameObject
{

	public static final double GRAV = 981;
	
	private double prevX;
	private double prevY;
	
	public Runner(double w, double h, ID id, Animation animation)
	{
		super(w, h, id, animation);
	}
	
	@Override
	public void tick(double deltaTime)
	{
		prevX = x;
		prevY = y;
		super.tick(deltaTime);
		yV += GRAV * deltaTime;
		
	}
	
	public void changeAnimation(Animation animation)
	{
		sprite = animation;
	}
	
	public double getPrevX()
	{
		return prevX;
	}
	
	public double getPrevY()
	{
		return prevY;
	}
	
	@Override
	public void setX(double x)
	{
		super.setX(x);
		prevX = x;
	}
	
	@Override
	public void setY(double y)
	{
		super.setY(y);
		prevY = y;
	}
	
}
