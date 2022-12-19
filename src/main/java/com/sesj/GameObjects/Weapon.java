package com.sesj.GameObjects;

import com.sesj.Interfaces.GameObject;

public class Weapon implements GameObject {

  private int level;

  private Consumable cons;
  private int attack;
  private int speed;
  private int accuracy;
  private boolean ranged;

  private int boostedAttack =0;
  private int boostedSpeed = 0;
  private int boostedAccuracy = 0;
  private boolean boostedRange = false;
  
  private final String id;
  
  public Weapon(int attack, int speed, int accuracy, boolean ranged, Consumable cons, String id){
    this.attack = attack;
    this.speed = speed;
    this.accuracy = accuracy;
    this.ranged = ranged;
    this.cons = cons;
    if(cons.getArmor()==0 && cons.getHp()==0) this.cons = null;
    this.id = id;
  }

  public void upgrade(double multiplier){
    this.attack = updateStat(this.attack, multiplier);
    this.speed = updateStat(this.speed, multiplier);
    this.accuracy = updateStat(this.accuracy, multiplier);
  }

  private int updateStat(int stat, double multiplier){
    System.out.println(stat+" "+multiplier);
    System.out.println((stat==0)? 0 :Math.max((int)(stat*multiplier), stat+1));
    return (stat==0)? 0 :Math.max((int)(stat*multiplier), stat+1);
  }

  public int getAttack(){
    return attack+boostedAttack;
  }

  public int getSpeed(){
    return speed+boostedSpeed;
  }

  public int getAccuracy(){
    return accuracy+boostedAccuracy;
  }

  public boolean isRanged(){
    return ranged||boostedRange;
  }

  public int getLevel(){ return this.level; }

  public void updateLevel(int level){ this.level += level;}

  public Consumable getConsumable(){ return this.cons; }

  public String getId(){ return this.id; }

  public Weapon effect(Item item){
    this.boostedAttack = item.getAttackBoost();
    this.boostedSpeed = item.getSpeedBoost();
    this.boostedAccuracy = item.getAccuracyBoost();
    this.boostedRange = item.getRangeBoost();
    return this;
  }


  public Weapon reset(){
    this.boostedAttack = 0;
    this.boostedSpeed = 0;
    this.boostedAccuracy = 0;
    this.boostedRange = false;
    return this;
  }

  public String getStats(){
    String returnStr = "\nWeapon: "+ this.id
            +"\n\tAttack: "+ this.getAttack()
            +"\n\tSpeed: "+ this.getSpeed()
            +"\n\tAccuracy: "+ this.getAccuracy()
            +"\n\tRanged: "+ this.isRanged()
            +"\n\tLevel: "+ this.level
            +"\n\tApplied Status Effect: ";
    if(cons!=null){
      returnStr += this.cons.getStats()
              +"\n";
    } else {
      returnStr += "none\n";
    } return returnStr;
  }

  
}