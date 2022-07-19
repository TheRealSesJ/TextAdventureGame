//uses the character interface

package com.sesj.GameObjects;
import com.sesj.Interfaces.*;


public class Enemy implements Entity{

  private int xPos;
  private int yPos;

  private int hp;
  private int maxHp;
  private final Weapon weapon;
  private final int armor;
  
  private final String name;
  
  public Enemy(int hp, Weapon weapon, int armor, String name){
    this.hp = hp;
    this.maxHp = hp;
    this.weapon = weapon; 
    this.armor=armor;
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

  public int getXPos(){ return xPos; }

  public int getYPos(){ return yPos; }

  public void initPos(int xPos, int yPos){
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public boolean canTraverse(){ return false; }

  public void updateCoords(int x, int y){
    this.xPos+=x;
    this.yPos+=y;
  }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    return "\n"+this+"\n"
    +this.weapon.getStats()
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
            +"\nPosition: "+xPos+" "+yPos
    +"\n";
  }


}