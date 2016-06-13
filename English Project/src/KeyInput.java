import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	private GameObjectList list;
	private boolean pDown = false;

	public KeyInput(GameObjectList list) {
		this.list = list;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Runner player = list.getRunner();

		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			player.setxAccel(400);
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			player.setxAccel(-400);
		if (key == KeyEvent.VK_SPACE)
			player.jump();
		if (key == KeyEvent.VK_P && !pDown)
		{
			pDown = true;
			RunnerMain.paused = !RunnerMain.paused;
		}
		RunnerMain.keyPressed = true;
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		Runner player = list.getRunner();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
			player.setxAccel(0);
		if (key == KeyEvent.VK_P)
		{
			pDown = false;
		}
		RunnerMain.keyPressed = false;

	}


}