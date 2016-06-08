public class Runner extends GameObject
{

	public Runner(double w, double h, ID id, Animation animation)
	{
		super(w, h, id, animation);
	}
	
	public void changeAnimation(Animation animation)
	{
		sprite = animation;
	}
}
