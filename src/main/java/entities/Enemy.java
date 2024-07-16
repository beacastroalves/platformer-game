package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.EnemyConstants.DEAD;
import static utilz.Constants.EnemyConstants.GetEnemyDmg;
import static utilz.Constants.EnemyConstants.GetMaxHealth;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.RUNNING;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;
import static utilz.HelpMethods.IsSightClear;

public abstract class Enemy extends Entity {
  protected int aniIndex, enemyState, enemyType;
  protected int aniTick, aniSpeed = 25;
  protected boolean firstUpdate = true;
  protected boolean inAir;
  protected float fallSpeed;
  protected float gravity = 0.04f * Game.SCALE;
  protected float walkSpeed = 0.35f * Game.SCALE;
  protected int walkDir = LEFT;
  protected int tileY;
  protected float attackDistance = Game.TILES_SIZE;
  protected int maxHealth;
  protected int currentHealth;
  protected boolean active = true;
  protected boolean attackChecked;


  public Enemy(float x, float y, int width, int height, int enemyType) {
    super(x, y, width, height);
    this.enemyType = enemyType;
    initHitbox(x, y, width, height);
    maxHealth = GetMaxHealth(enemyType);
    currentHealth = maxHealth;
  }

  protected void firstUpdateCheck(int[][] lvlData) {
    if (!IsEntityOnFloor(hitbox, lvlData)) {
      inAir = true;
    }
    firstUpdate = false;
  }

  protected void updateInAir(int[][] lvlData) {
    if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
      hitbox.y += fallSpeed;
      fallSpeed += gravity;
    } else {
      inAir = false;
      hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
      tileY = (int) (hitbox.y / Game.TILES_SIZE);
    }
  }

  protected void move(int[][] lvlData) {
    float xSpeed = 0;

    if (walkDir == LEFT) {
      xSpeed = -walkSpeed;
    } else {
      xSpeed = walkSpeed;
    }
    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
      if (IsFloor(hitbox, xSpeed, lvlData)) {
        hitbox.x += xSpeed;
        return;
      }
    }
    changeWalkDir();
  }

  protected void turnTowardsPlayer(Player player) {
    if (player.hitbox.x > hitbox.x) {
      walkDir = RIGHT;
    } else {
      walkDir = LEFT;
    }
  }

  protected boolean canSeePlayer(int[][] lvlData, Player player) {
    int playerTileY = (int) player.getHitbox().y / Game.TILES_SIZE;
    if (playerTileY == tileY) {
      if (isPlayerInRange(player)) {
        if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isPlayerInRange(Player player) {
    int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
    return absValue <= attackDistance * 5;
  }

  protected boolean isPlayerCloseForAttack(Player player) {
    int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
    return absValue <= attackDistance;
  }

  protected void newState(int enemyState) {
    this.enemyState = enemyState;
    aniTick = 0;
    aniIndex = 0;
  }

  public void hurt(int amount) {
    currentHealth -= amount;
    if (currentHealth <= 0) {
      newState(DEAD);
    } else {
      newState(HIT);
    }
  }


  // checkEnemyHit
  protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
    if (attackBox.intersects(player.hitbox)) {
      player.changeHealth(-GetEnemyDmg(enemyType));
    }
    attackChecked = true;
  }

  protected void updateAnimationTick() {
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
        aniIndex = 0;

        switch (enemyState) {
          case ATTACK, HIT -> enemyState = IDLE;
          case DEAD -> active = false;
        }
      }
    }
  }

  public void update(int[][] lvlData) {
    updateMove(lvlData);
    updateAnimationTick();
  }

  public void updateMove(int[][] lvlData) {
    if (firstUpdate) {
      if (!IsEntityOnFloor(hitbox, lvlData)) {
        inAir = true;
      }
      firstUpdate = false;
    }

    if (inAir) {
      if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
        hitbox.y += fallSpeed;
        fallSpeed += gravity;
      } else {
        inAir = false;
        hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
      }
    } else {
      switch (enemyState) {
        case IDLE:
          enemyState = RUNNING;
          break;
        case RUNNING:
          float xSpeed = 0;

          if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
          } else {
            xSpeed = walkSpeed;
          }
          if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, xSpeed, lvlData)) {
              hitbox.x += xSpeed;
              return;
            }
          }
          changeWalkDir();
          break;
      }
    }
  }

  protected void changeWalkDir() {
    if (walkDir == LEFT) {
      walkDir = RIGHT;
    } else {
      walkDir = LEFT;
    }
  }

  public void resetEnemy() {
    hitbox.x = x;
    hitbox.y = y;
    firstUpdate = true;
    currentHealth = maxHealth;
    newState(IDLE);
    active = true;
    fallSpeed = 0;
  }

  public int getAniIndex() {
    return aniIndex;
  }

  public int getEnemyState() {
    return enemyState;
  }

  public boolean isActive() {
    return active;
  }

}
