package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
  private GamePanel gamePanel;

  public KeyboardInputs(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        System.out.println("Its W");
        gamePanel.changeYDelta(-5);
        break;
      case KeyEvent.VK_A:
        System.out.println("Its A");
        gamePanel.changeXDelta(-5);
        break;
      case KeyEvent.VK_S:
        System.out.println("Its S");
        gamePanel.changeYDelta(+5);
        break;
      case KeyEvent.VK_D:
        System.out.println("Its D");
        gamePanel.changeXDelta(+5);
        break;
    }
  }
}
