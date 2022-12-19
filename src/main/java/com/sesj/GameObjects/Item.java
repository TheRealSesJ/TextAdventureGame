//planning on interface that includes the item

package com.sesj.GameObjects;

import com.sesj.Interfaces.GameObject;

public class Item implements GameObject {
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

  public Item(int attack, int speed, int accuracy, boolean range, int movement, boolean traversal, boolean scan, int hp, int armor, String id){
    this.attackBoost=attack;
    this.speedBoost=speed;
    this.accuracyBoost=accuracy;
    this.rangeBoost=range;
    this.moveBoost=movement;
    this.traverseBoost=traversal;
    this.scanBoost = scan;
    this.hpBoost=hp;
    this.armorBoost=armor;
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

  public String getStats(){
    return "\nItem: "+this.id +"\n"
    +"\n\tWeapon Related:"
    +"\n\t\tattack boost: "+ this.attackBoost
    +"\n\t\tspeed: "+ this.speedBoost
    +"\n\t\taccuracy: "+ this.accuracyBoost
    +"\n\t\tranged: "+ this.rangeBoost
    +"\n"
    +"\n\tPlayer Related: "
    +"\n\t\tmove boost: "+ this.moveBoost
    +"\n\t\ttraversal boost: "+ this.traverseBoost
    +"\n\t\tscan boost: "+ this.scanBoost
    +"\n\t\thp boost:" + this.hpBoost
    +"\n\t\tarmor boost: " + this.armorBoost
    +"\n";
  }
}