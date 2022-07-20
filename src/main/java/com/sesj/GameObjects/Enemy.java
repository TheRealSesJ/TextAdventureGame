//uses the character interface

package com.sesj.GameObjects;
import com.sesj.Interfaces.*;

import java.awt.*;


public class Enemy implements Entity, GameObject {

  private Point position;

  private int hp;
  private int maxHp;
  private final Weapon weapon;
  private final int armor;
  private final boolean canMove;
  
  private final String name;
  
  public Enemy(int hp, Weapon weapon, int armor, boolean canMove, String name){
    this.hp = hp;
    this.maxHp = hp;
    this.weapon = weapon; 
    this.armor=armor;
    this.canMove = canMove;
    this.name = name; 
  }

  public Weapon getWeapon(){
    return this.weapon;
  }

  public int getHp(){
    return hp;
  }

  public int getMaxHp(){
    return maxHp;
  }

  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
    return this.armor;
  }

  public String toString() { return name; }

  public Point getPosition(){ return this.position; }

  public void initPos(Point position){
    this.position = position;
  }

  public boolean canTraverse(){ return false; }

  public void updateCoords(int x, int y){
    this.position.translate(x, y);
  }

  public void tick(){}

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    return "\n Enemy: "+this+"\n"
    +this.weapon.getStats()
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
            +"\n\tCan Move: "+this.canMove
            +"\nPosition: "+this.position
    +"\n";
  }


}