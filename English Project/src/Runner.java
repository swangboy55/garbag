public class Runner extends GameObject
{

	public static final double GRAV = 7400;
	private double prevYv;
	private boolean jumping;
	private double xAccel;
	private Animation fallingAnim;
	private Animation runningAnim;
	
	public Runner(double w, double h, ID id, Animation running, Animation falling)
	{
		super(w, h, id, running);
		fallingAnim = falling;
		runningAnim = running;
	}
	
	public void setxAccel(double accel)
	{
		xAccel = accel;
	}
	
	@Override
	public void tick(double deltaTime)
	{
		if(prevYv > 0 && yV == 0)
		{
			jumping = true;
		}
		prevYv = yV;
		super.tick(deltaTime);
		yV += GRAV * deltaTime;
		xV += xAccel * deltaTime;
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
	public void render(Window wind, Camera cam)
	{
		if(prevY != y)
		{
			sprite = fallingAnim;
		}
		else
		{
			sprite = runningAnim;
		}
		super.render(wind, cam);
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
