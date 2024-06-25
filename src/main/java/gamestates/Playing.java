package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {
  private Player player;
  private LevelManager levelManager;

  public Playing(Game game) {
    super(game);
    initClasses();
  }

  private void initClasses() {
    levelManager = new LevelManager(game);
    player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
    player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
  }

  @Override
  public void update() {
    levelManager.update();
    player.update();
  }

  @Override
  public void draw(Graphics g) {
    levelManager.draw(g);
    player.render(g);
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A:
        player.setLeft(true);
        break;
      case KeyEvent.VK_D:
        player.setRight(true);
        break;
      case KeyEvent.VK_SPACE:
        player.setJump(true);
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A:
        player.setLeft(false);
        break;
      case KeyEvent.VK_D:
        player.setRight(false);
        break;
      case KeyEvent.VK_SPACE:
        player.setJump(false);
        break;
      case KeyEvent.VK_BACK_SPACE:
        GameState.state = GameState.MENU;
        break;
    }
  }


  public void windowFocusLost() {
    player.resetDirBooleans();
  }

  public Player getPlayer() {
    return player;
  }
}
