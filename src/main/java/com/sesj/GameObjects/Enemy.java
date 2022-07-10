//uses the character interface

package com.sesj.GameObjects;
import com.sesj.GameObjects.Weapon;
import com.sesj.Interfaces.*;


public class Enemy implements Entity{

  private int hp; 
  private final Weapon weapon;
  private final int armor;
  
  private final String name;
  
  public Enemy(int hp, Weapon weapon, int armor, String name){
    this.hp = hp;
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

  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
    return this.armor;
  }

  public String toString() { return name; }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    return "\n"+this+"\n"
    +"\nWeapon: "+this.weapon.toString()
    +"\n\tattack: "+ this.weapon.getAttack()
    +"\n\tspeed: "+ this.weapon.getSpeed()
    +"\n\taccuracy: "+ this.weapon.getAccuracy()
    +"\n\tranged: "+ this.weapon.isRanged()
    +"\n"
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
    +"\n";
  }
}