package com.sesj.GameObjects.Weapons;

import com.sesj.GameObjects.Items.Item;

public class Weapon {

  private int attack;
  private int speed;
  private int accuracy;
  private boolean ranged;
  
  private String name;
  
  public Weapon(int attack, int speed, int accuracy, boolean ranged, String name){
    this.attack = attack;
    this.speed = speed;
    this.accuracy = accuracy;
    this.ranged = ranged;
    this.name=name;
  }

  public int getAttack(){
    return attack;
  }

  public int getSpeed(){
    return speed;
  }
  
  public int getAccuracy(){
    return accuracy;
  }
  
  public boolean isRanged(){
    return ranged;
  }

  public String toString(){ return this.name; }

  public Weapon effect(Item item){
    this.attack+= item.getAttackBoost();
    this.speed += item.getSpeedBoost();
    this.accuracy+= item.getAccuracyBoost();
    this.ranged |= item.getRangeBoost();
    return this;
  }

  //needs a new name----------------------
  public Weapon unEffect(Item item){
    this.attack-= item.getAttackBoost();
    this.speed -= item.getSpeedBoost();
    this.accuracy-= item.getAccuracyBoost();
    //this part is slightly broken, needs work
    this.ranged = !item.getRangeBoost();
    return this;
  }

  
}