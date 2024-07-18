package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class LevelCompletedOverlay {

  private Playing playing;
  private UrmButton menu, next;
  private BufferedImage img;
  private int bgX, bgY, bgW, bgH;

  public LevelCompletedOverlay(Playing playing) {
    this.playing = playing;
    initImg();
    initButtons();
  }

  private void initButtons() {
    int menuX = (int) (330 * Game.SCALE);
    int nextX = (int) (445 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);
    next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
    menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
  }

  private void initImg() {
    img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
    bgW = (int) (img.getWidth() * Game.SCALE);
    bgH = (int) (img.getHeight() * Game.SCALE);
    bgX = Game.GAME_WIDTH / 2 - bgW / 2;
    bgY = (int) (75 * Game.SCALE);
  }

  public void draw(Graphics g) {
    g.drawImage(img, bgX, bgY, bgW, bgH, null);
    next.draw(g);
    menu.draw(g);
  }

  public void update() {
    next.update();
    menu.update();
  }

  public boolean isIn(UrmButton b, MouseEvent e) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    next.setMouseOver(false);
    menu.setMouseOver(false);

    if(isIn(menu, e)) {
      menu.setMouseOver(true);
    } else if (isIn(next, e)) {
      next.setMouseOver(true);
    }
  }

  public void mouseReleased(MouseEvent e) {
    if(isIn(menu, e)) {
      if(menu.isMousePressed()) {
        playing.resetAll();
        GameState.state = GameState.MENU;
      }
    } else if (isIn(next, e)) {
      if(next.isMousePressed()) {
        playing.loadNextLevel();
      }
    }
    menu.resetBools();
    next.resetBools();
  }

  public void mousePressed(MouseEvent e) {
    if(isIn(menu, e)) {
      menu.setMousePressed(true);
    } else if (isIn(next, e)) {
      next.setMousePressed(true);
    }
  }

}
