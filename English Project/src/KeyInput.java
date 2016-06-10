import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	private GameObjectList list;

	public KeyInput(GameObjectList list) {
		this.list = list;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Runner player = list.getRunner();

		if (key == KeyEvent.VK_D)
			player.setxAccel(100);
		if (key == KeyEvent.VK_A)
			player.setxAccel(-100);
		if (key == KeyEvent.VK_SPACE)
			player.jump();
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		Runner player = list.getRunner();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_A)
			player.setxAccel(0);


	}


}