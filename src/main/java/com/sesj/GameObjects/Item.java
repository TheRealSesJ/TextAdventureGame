//planning on interface that includes the item

package com.sesj.GameObjects;

import com.sesj.Interfaces.ArtObject;
import com.sesj.Interfaces.GameObject;

public class Item implements GameObject, ArtObject {

  private final String art;
  //weapon related
  private int attackBoost;
  private int speedBoost;
  private int accuracyBoost;
  private final boolean rangeBoost;

  //player related
  private int moveBoost;
  private final boolean traverseBoost;
  private final boolean scanBoost;
  private int hpBoost;
  private int armorBoost;

  private final String id;

  public Item(int attack, int speed, int accuracy, boolean range, int movement, boolean traversal, boolean scan, int hp, int armor, String art, String id){
    this.attackBoost=attack;
    this.speedBoost=speed;
    this.accuracyBoost=accuracy;
    this.rangeBoost=range;
    this.moveBoost=movement;
    this.traverseBoost=traversal;
    this.scanBoost = scan;
    this.hpBoost=hp;
    this.armorBoost=armor;
    this.art=art;
    this.id = id;
  }

  public void upgrade(double multiplier){
    //this.attackBoost=(getAttackBoost()!=0)? 0 :Math.max((int)(item.getAttackBoost()*multiplier), item.getAttackBoost()+1);
    this.attackBoost=updateStat(this.attackBoost, multiplier);
    this.speedBoost=updateStat(this.speedBoost, multiplier);
    this.accuracyBoost=updateStat(this.accuracyBoost, multiplier);
    this.moveBoost=updateStat(this.moveBoost, multiplier);
    this.hpBoost=updateStat(this.hpBoost, multiplier);
    this.armorBoost=updateStat(this.armorBoost, multiplier);
  }

  private int updateStat(int stat, double multiplier){
    System.out.println(stat+" "+multiplier);
    return (stat==0)? 0 :Math.max((int)(stat*multiplier), stat+1);
  }

  
  public String getId(){ return id; }

  //weapon related getters
  public int getAttackBoost(){
    return this.attackBoost;
  }

  public int getSpeedBoost(){
    return this.speedBoost;
  }

  public int getAccuracyBoost(){
    return this.accuracyBoost;
  }

  public boolean getRangeBoost(){
    return this.rangeBoost;
  }

  //player related getters
  public int getMoveBoost(){
    return this.moveBoost;
  }

  public boolean getTraverseBoost(){
    return this.traverseBoost;
  }

  public boolean getScanBoost(){
    return this.scanBoost;
  }

  public int getHpBoost(){
    return this.hpBoost;
  }

  public int getArmorBoost(){
    return this.armorBoost;
  }

  public String getArt(){ return packageArt(this.art); }

  public String getStats(){
    String stats = "\nItem: "+this.id +"\n"
            +"------------------------------->" //should be 32 spaces
    +"\n    Weapon Related:"
    +"\n        attack boost: "+ this.attackBoost
    +"\n        speed: "+ this.speedBoost
    +"\n        accuracy: "+ this.accuracyBoost
    +"\n        ranged: "+ this.rangeBoost
    +"\n"
    +"\n    Player Related: "
    +"\n        move boost: "+ this.moveBoost
    +"\n        traversal boost: "+ this.traverseBoost
    +"\n        scan boost: "+ this.scanBoost
    +"\n        hp boost: " + this.hpBoost
    +"\n        armor boost: " + this.armorBoost
    +"\n";
    return appendArt(stats, packageArt(art));
  }
}