package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;

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
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
      case KeyEvent.VK_A:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
        gamePanel.setMoving(false);
        break;
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {

    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        System.out.println("Its W");
        gamePanel.setDirection(UP);
        break;
      case KeyEvent.VK_A:
        System.out.println("Its A");
        gamePanel.setDirection(LEFT);
        break;
      case KeyEvent.VK_S:
        System.out.println("Its S");
        gamePanel.setDirection(DOWN);
        break;
      case KeyEvent.VK_D:
        System.out.println("Its D");
        gamePanel.setDirection(RIGHT);
        break;
    }
  }
}
