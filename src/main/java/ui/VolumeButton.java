package ui;

import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.VolumeButton.*;


public class VolumeButton extends PauseButton {

  private BufferedImage[] imgs;
  private BufferedImage slider;
  private int index = 0;
  private boolean mouseOver, mousePressed;
  private int buttonX, minX, maxX;

  public VolumeButton(int x, int y, int width, int height) {
    super(x + width/2, y, VOLUME_WIDTH, height);
    bounds.x -= VOLUME_WIDTH / 2;
    buttonX = x + width / 2;
    this.x = x;
    this.width = width;
    minX = x + VOLUME_WIDTH / 2;
    maxX = x + width - VOLUME_WIDTH / 2;
    loadImgs();
  }

  private void loadImgs() {
    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
    imgs = new BufferedImage[3];
    for (int i = 0; i < imgs.length; i++) {
      imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }
    slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
  }

  public void update() {
    index = 0;
    if (mouseOver) {
      index = 1;
    }
    if (mousePressed) {
      index = 2;
    }
  }

  public void draw(Graphics g) {

    g.drawImage(slider, x, y, width, height, null);
    g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);

  }

  public void changeX(int x) {
//    if(x < this.minX) {
//      this.buttonX = this.minX;
//    } else if(x > this.maxX) {
//      this.buttonX = this.maxX;
//    } else {
//      this.buttonX = x;
//    }

    this.buttonX = clamp(x, this.minX, this.maxX);

    bounds.x = buttonX - VOLUME_WIDTH / 2;
  }

  /* private void printVolume() {
    System.out.println("volume: " + getVolume());
  }

  // How to convert buttonX to a scale 0 - 100

  private int getVolume() {
    return (int) (((float) (buttonX - minX) / (maxX - minX)) * 100);
  }
  */

  private int clamp(int value, int min, int max) {
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    }

    return value;
  }

  public void resetBools() {
    mouseOver = false;
    mousePressed = false;
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public void setMouseOver(boolean mouseOver) {
    this.mouseOver = mouseOver;
  }

  public boolean isMousePressed() {
    return mousePressed;
  }

  public void setMousePressed(boolean mousePressed) {
    this.mousePressed = mousePressed;
  }
}

