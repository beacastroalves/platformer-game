package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class GameOverOverlay {

  private Playing playing;

  public GameOverOverlay(Playing playing) {
    this.playing = playing;
  }

  public void draw(Graphics g) {
    g.setColor(new Color(0, 0, 0, 180));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

    g.setColor(Color.WHITE);
    g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
    g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      playing.resetAll();
      GameState.state = GameState.MENU;
    }
  }
}
