//uses the character interface

package com.sesj.GameObjects;
import com.sesj.Interfaces.*;

import java.awt.*;
import java.util.ArrayList;


public class Enemy implements Entity, GameObject {

  private Point position;

  private int hp;
  private int maxHp;
  private final Weapon weapon;
  private int armor;
  private final int baseArmor;
  private final boolean canMove;
  private ArrayList<Buff> buffs = new ArrayList<>();
  
  private final String name;
  
  public Enemy(int hp, Weapon weapon, int armor, boolean canMove, String name){
    this.hp = hp;
    this.maxHp = hp;
    this.weapon = weapon; 
    this.armor=armor;
    this.baseArmor = armor;
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

  public void tick(){
    System.out.println(name + " @" + position + " has ticked!");
    //iterate over buffs
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        if(this.buffs.get(i).getDuration()>0){
          if((this.hp<this.maxHp)) this.hp+=this.buffs.get(i).getHp();
          if(this.buffs.get(i).getArmor()!=0) this.armor = this.buffs.get(i).getArmor();
          this.buffs.get(i).tick(this);
        } else {
          this.armor= baseArmor;
          this.buffs.remove(i);
        }
      }
    }
  }

  @Override
  public boolean buff(Buff buff){
    for(Buff e: this.buffs){
      if(e.equals(buff)){
        buffs.remove(e);
        buffs.add(buff);
        return true;
      }
    }
    this.buffs.add(buff);
    return true;
  }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    String returnStr = "\nEnemy: "+this+"\n"
    +this.weapon.getStats()
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
            +"\n\tCan Move: "+this.canMove
            +"\nPosition: "+this.position
    +"\n";
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        returnStr+=this.buffs.get(i).getStats()+"\n";
      }
    }
    return returnStr;
  }


}