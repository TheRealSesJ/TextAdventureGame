//uses the character interface

package com.sesj.GameObjects.Enemies;
import com.sesj.GameObjects.Weapons.*;
import com.sesj.Interfaces.*;


public class Enemy implements Entity{

  private int hp; 
  private Weapon weapon;
  private int armor;
  
  private String name;
  
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
    return toString()
    +"\nWeapon: "+this.weapon.toString()
    +"\nattack: "+ this.weapon.getAttack()
    +"\nspeed: "+ this.weapon.getSpeed()
    +"\naccuracy: "+ this.weapon.getAccuracy()
    +"\nranged: "+ this.weapon.isRanged()
    +"\n"
    +"\nHp: "+this.hp
    +"\nArmor: "+this.armor
    +"\n";
  }
}