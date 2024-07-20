package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

  private Playing playing;
  private BufferedImage[][] potionImgs, containerImgs;
  private ArrayList<Potion> potions;
  private ArrayList<GameContainer> containers;

  public ObjectManager(Playing playing) {
    this.playing = playing;
    loadImgs();

    potions = new ArrayList<>();
//    potions.add(new Potion(300, 300, RED_POTION));
//    potions.add(new Potion(400, 300, BLUE_POTION));

    containers = new ArrayList<>();
//    containers.add(new GameContainer(500, 300, BARREL));
//    containers.add(new GameContainer(600, 300, BARREL));
  }

  private void loadImgs() {
    BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
    potionImgs = new BufferedImage[2][7];

    for (int i = 0; i < potionImgs.length; i++) {
      for (int j = 0; j < potionImgs[i].length; j++) {
        potionImgs[i][j] = potionSprite.getSubimage(12 * j, 16 * i, 12, 16);

      }
    }

    BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
    containerImgs = new BufferedImage[2][8];

    for (int i = 0; i < containerImgs.length; i++) {
      for (int j = 0; j < containerImgs[i].length; j++) {
        containerImgs[i][j] = containerSprite.getSubimage(40 * j, 30 * i, 40, 30);

      }
    }
  }

  public void update() {

    for (Potion p : potions) {
      if(p.isActive()){
        p.update();
      }
    }

    for (GameContainer gc : containers) {
      if(gc.isActive()){
        gc.update();
      }
    }
  }

  public void draw(Graphics g, int xLvlOffset) {
    drawPotions(g,xLvlOffset);
    drawContainers(g, xLvlOffset);
  }

  private void drawContainers(Graphics g, int xLvlOffset) {
    for (GameContainer gc : containers) {
      if(gc.isActive()){
        int type = 0;
        if(gc.getObjType() == BARREL) {
          type = 1;
        }

        g.drawImage(containerImgs[type][gc.getAniIndex()],
            (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
            (int) (gc.getHitbox().y - gc.getyDrawOffset()),
            CONTAINER_WIDTH,
            CONTAINER_HEIGHT,
            null);
      }
    }
  }

  private void drawPotions(Graphics g, int xLvlOffset) {
    for (Potion p : potions) {
      if(p.isActive()){
        int type = 0;
        if(p.getObjType() == RED_POTION){
          type = 1;
        }
        g.drawImage(potionImgs[type][p.getAniIndex()],
            (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
            (int) (p.getHitbox().y - p.getyDrawOffset()),
            POTION_WIDTH,
            POTION_HEIGHT,
            null);
      }
    }
  }


}