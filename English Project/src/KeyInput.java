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
//		if (key == KeyEvent.VK_D)
//			player.setxV(250);
//		if (key == KeyEvent.VK_A)
//			player.setxV(-250);
//		if (key == KeyEvent.VK_SPACE && player.canJump())
//			player.jump();
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		Runner player = list.getRunner();
//		if (key == KeyEvent.VK_D)
//			player.setxV(0);
//		if (key == KeyEvent.VK_A)
//			player.setxV(0);


	}


}