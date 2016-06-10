public class Runner extends GameObject
{

	public static final double GRAV = 7400;
	
	private double prevX;
	private double prevY;
	private double prevYv;
	private boolean jumping;
	
	public Runner(double w, double h, ID id, Animation animation)
	{
		super(w, h, id, animation);
	}
	
	@Override
	public void tick(double deltaTime)
	{
		if(prevYv > 0 && yV == 0)
		{
			jumping = true;
		}
		prevX = x;
		prevY = y;
		prevYv = yV;
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
	
	public void jump()
	{
		if(jumping)
		{
			jumping = false;
			yV = -1600;
		}
	}
	
	public boolean canJump()
	{
		return jumping;
	}
	
}
