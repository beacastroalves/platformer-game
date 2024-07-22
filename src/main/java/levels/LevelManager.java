package levels;

import gamestates.GameState;
import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

  private Game game;
  private BufferedImage[] levelSprite;
  private ArrayList<Level> levels;
  private int lvlIndex = 0;
  public LevelManager(Game game) {
    this.game = game;
//    levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
    importOutsideSprites();
    levels = new ArrayList<>();
    buildAllLevels();
  }

  public void loadNextLevel() {
    lvlIndex++;
    if(lvlIndex >= levels.size()) {
      lvlIndex = 0;
      System.out.println("No more levels! Game Completed!");
      GameState.state = GameState.MENU;
    }

    Level newLevel = levels.get(lvlIndex);
    game.getPlaying().getEnemyManager().loadEnemies(newLevel);
    game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
    game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    game.getPlaying().getObjectManager().loadObject(newLevel);

  }

  private void buildAllLevels() {
    BufferedImage[] allLevels = LoadSave.GetAllLevels();
    for (BufferedImage img : allLevels) {
      levels.add(new Level(img));
    }
  }

  private void importOutsideSprites() {
    BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);

    levelSprite = new BufferedImage[48];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 12; j++) {
        int index = i * 12 + j;
        levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
      }
    }

  }

  public void draw(Graphics g, int lvlOffset) {
    for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
      for (int j = 0; j < levels.get(lvlIndex).getLevelData()[0].length; j++) {
        int index = levels.get(lvlIndex).getSpriteIndex(j, i);

        g.drawImage(levelSprite[index], Game.TILES_SIZE * j - lvlOffset, Game.TILES_SIZE * i, Game.TILES_SIZE, Game.TILES_SIZE, null);
      }
    }
  }

  public void update() {

  }

  public Level getCurrentLevel() {
    return levels.get(lvlIndex);
  }

  public int getAmountOfLevels() {
    return levels.size();
  }

}
