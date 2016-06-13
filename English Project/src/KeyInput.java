import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	private GameObjectList list;
	private boolean pDown = false;
	private boolean rdown = false;
	private boolean ldown = false;
	private double accelSpeed;

	public KeyInput(GameObjectList list) 
	{
		this.list = list;
		accelSpeed = 800;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Runner player = list.getRunner();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			player.setxAccel(accelSpeed);
			rdown = true;
		}
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			player.setxAccel(-accelSpeed);
			ldown = true;
		}
		if(key == KeyEvent.VK_UP)
		{
			accelSpeed += 100;
			if(accelSpeed > 4000)
			{
				accelSpeed = 4000;
			}
		}
		if(key == KeyEvent.VK_DOWN)
		{
			accelSpeed -= 100;
			if(accelSpeed < 100)
			{
				accelSpeed = 100;
			}
		}
		if (key == KeyEvent.VK_SPACE)
		{
			player.jump();
			RunnerMain.keyPressed = true;	
		}
		
		if (key == KeyEvent.VK_P && !pDown)
		{
			pDown = true;
			RunnerMain.paused = !RunnerMain.paused;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		Runner player = list.getRunner();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			if(!ldown)
			{
				player.setxAccel(0);
			}
			else
			{
				player.setxAccel(-accelSpeed);
			}
			rdown = false;
		}
		
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			if(!rdown)
			{
				player.setxAccel(0);
			}
			else
			{
				player.setxAccel(accelSpeed);
			}
			ldown = false;
		}
		if (key == KeyEvent.VK_P)
		{
			pDown = false;
		}
		if (key == KeyEvent.VK_SPACE)
		{
			RunnerMain.keyPressed = false;
		}

	}


}