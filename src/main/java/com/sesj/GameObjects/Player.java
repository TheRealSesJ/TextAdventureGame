package com.sesj.GameObjects;

import com.sesj.Interfaces.*;
import com.sesj.Exceptions.*;
import com.sesj.StaticData.GameParameters;
import com.sesj.World;

import java.awt.*;

public class Player implements Entity, GameObject{
  //world position
  private Point position;

  private Weapon weapon;
  private Item item; //starts empty
  private Consumable consumable; //starts empty
  private int movement;
  private int hp;
  private int maxHp;
  private int armor;
  
  public Player(int hp, Weapon weapon, Item item, int armor, int movement, int initialX, int initialY){
    this.hp = hp;
    this.maxHp = hp;
    this.weapon = weapon;
    this.item = item;
    this.armor = armor;
    this.movement = movement;
    this.position = new Point(initialX, initialY);
  }

  //takes into account the ammount of tiles the player is allowed to move, as well as whether or not the area being entered is traversible. *also takes item buffs into consideration*
  //illegal acess exception used for case where tile is not traversible
  public boolean translate(int xTiles, int yTiles) throws NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException {
    try{
      if(!World.getLocation(this.position, xTiles, yTiles).isTraversable()){
        if(this.item==null || !canTraverse()){
          throw new NotTraversableException();
        }
      }
    //convert index out of bounds into movement out of bounds for specificity
    }catch(IndexOutOfBoundsException e){
      throw new MovementOutOfBoundsException();
    }
    if(movement<Math.abs(xTiles) || movement<Math.abs(yTiles)){
      if(movement+this.item.getMoveBoost()<Math.abs(xTiles) || movement+this.item.getMoveBoost()<Math.abs(yTiles)){
        throw new MovementOutOfRangeException();
      }
    }
    this.position.translate(xTiles, yTiles);
    return true;
  }

  public Point getPosition(){ return this.position; }

  public boolean canTraverse(){
    return this.item.getTraverseBoost();
  }

  //gets weapon
  public Weapon getWeapon(){
    return this.weapon.effect(this.item);
  }

  public Item getItem(){
    return this.item;
  }

  public Consumable getConsumable(){
    return this.consumable;
  }

  public String toString() { return "Player"; }


  public Weapon equip(Weapon weapon){
    Weapon old = this.weapon;
    this.weapon = weapon;
    return old;
  }

  
  //returns old item
  public Item equip(Item item){
      Item old=this.item;
      this.item= item;
      return old;
  }

  public Consumable equip(Consumable consumable){
    Consumable old = this.consumable;
    this.consumable = consumable;
    return old;
  }

//health related methods
  public int getHp(){
    return hp;
  }

  public int getMaxHp(){ return this.maxHp+this.item.getHpBoost(); }
  
  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
      return this.armor+this.item.getArmorBoost();
  }

  public int getMovement(){
    return this.movement;
  }

  public void consume(){
    if(this.consumable.getDuration()==-1){
      this.hp+=this.consumable.getHp();
      if(this.hp>this.maxHp) hp = maxHp;
      this.consumable=null;
    } else {
        System.out.println("consoom"); // TODO implement the buff factory
    }

  }

  public void tick(){
    System.out.println("I tick");
      this.weapon.reset();
  }

  //returns the players stats as a string, intended to give the user information
  public String getStats(){
    String returnStr = this.weapon.getStats()
    +"\n"
    +this.item.getStats()
    +"\n"
    +"\nPlayer: (effected by "+this.item.toString()+")"
    +"\n\tHp: "+this.hp+"/"+this.getMaxHp()+ "+("+this.item.getHpBoost()+")"
    +"\n\tArmor: "+this.armor+ "+("+this.item.getArmorBoost()+")"
    +"\n";
    if(this.consumable!=null){
      returnStr+=this.consumable.getStats()+"\n";
    }
    return returnStr;
  }
  
}