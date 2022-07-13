package com.sesj.GameObjects;

import com.sesj.Interfaces.*;
import com.sesj.Exceptions.*;
import com.sesj.World;

public class Player implements Entity{
  //world position
  private int xPos;
  private int yPos;

  private Weapon weapon;
  private Item item; //starts empty
  private int movement;
  private int hp;
  private int armor;
  
  public Player(int hp, Weapon weapon, int armor, int movement, int initialX, int initialY){
    this.hp = hp;
    this.weapon = weapon;
    this.armor = armor;
    this.movement = movement;
    this.xPos = initialX;
    this.yPos = initialY;
  }

  //takes into account the ammount of tiles the player is allowed to move, as well as whether or not the area being entered is traversible. *also takes item buffs into consideration*
  //illegal acess exception used for case where tile is not traversible
  public boolean translate(int xTiles, int yTiles) throws NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException {
    try{
      if(!World.getLocation(this, xTiles, yTiles).isTraversable()){
        if(this.item==null || !this.item.getTraverseBoost()){
          throw new NotTraversableException();
        }
      }
    //convert index out of bounds into movement out of bounds for specificity
    }catch(IndexOutOfBoundsException e){
      throw new MovementOutOfBoundsException();
    }
    if(movement<Math.abs(xTiles) || movement<Math.abs(yTiles)){
      if(this.item==null || (movement+this.item.getMoveBoost()<Math.abs(xTiles) || movement+this.item.getMoveBoost()<Math.abs(yTiles))){
        throw new MovementOutOfRangeException();
      }
    }
    yPos+=yTiles;
    xPos+=xTiles;
    return true;
  }

  public int getXPos(){
    return xPos;
  }

  public int getYPos(){
    return yPos;
  }

  //gets weapon
  public Weapon getWeapon(){
    return this.weapon;
  }

  public Item getItem(){
    return this.item;
  }

  public String toString() { return "Player"; }

  //adds item effects, returns old weapon and removes effects from it
  public Weapon equip(Weapon weapon){
    Weapon old = this.weapon;
    this.weapon = weapon;
    if(this.item!=null){
      this.weapon.effect(this.item);
       return old.unEffect(item);
    }
    return old;
  }

  
  //removes old item effects, adds new item effects, returns old item
  public Item equip(Item item){
    
    if(this.item!=null){
      this.weapon.unEffect(this.item);
      Item old=this.item;
      this.item= item;
      this.weapon.effect(this.item);
      return old;
    } else {
//return null if item never existed in the first place
      this.item = item;
      this.weapon.effect(this.item);
      return null;
    }
  }

  public Weapon equipWeapon(Weapon weapon){
    if(this.item!=null){
      this.weapon.unEffect(this.item);
      Weapon old=this.weapon;
      this.weapon= weapon;
      this.weapon.effect(this.item);
      return old;
    } else {
//return old and skip uneffect step if item never existed in the first place
      Weapon old = this.weapon;
      this.weapon= weapon;
      return old;
    }
  }

//health related methods
  public int getHp(){
    return hp;
  }
  
  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
    if(item!=null){
      return this.armor+this.item.getArmorBoost();
    }
    return this.armor;
  }

  public int getMovement(){
    return this.movement;
  }

  //returns the players stats as a string, intended to give the user information
  public String getStats(){
    String display ="";
    if(item!=null){
      display += 
    "Weapon: "+this.weapon.toString() +" (affected by "+this.item.toString()+")"
    +"\n\tattack: "+ this.weapon.getAttack() + "+("+this.item.getAttackBoost()+")"
    +"\n\tspeed: "+ this.weapon.getSpeed() + "+("+this.item.getSpeedBoost()+")"
    +"\n\taccuracy: "+ this.weapon.getAccuracy() + "+("+this.item.getAccuracyBoost()+")"
    +"\n\tranged: "+ this.weapon.isRanged() + "+("+this.item.getRangeBoost()+")"
    +"\n"
    +"\nItem: "+this.item.toString() 
    +"\n\tmove boost: "+ this.item.getMoveBoost()
    +"\n\ttraversal boost: "+ this.item.getTraverseBoost()
    +"\n\tscan boost: " +this.item.getScanBoost()
    +"\n"
    +"\nPlayer: (effected by "+this.item.toString()+")"
    +"\n\tHp: "+this.hp+ "+("+this.item.getHpBoost()+")"
    +"\n\tArmor: "+this.armor+ "+("+this.item.getArmorBoost()+")"
    +"\n";
    } else{
      display += 
    "Weapon: "+this.weapon.toString()
    +"\n\tattack: "+ this.weapon.getAttack()
    +"\n\tspeed: "+ this.weapon.getSpeed()
    +"\n\taccuracy: "+ this.weapon.getAccuracy()
    +"\n\tranged: "+ this.weapon.isRanged()
    +"\n"
    +"\nPlayer:"
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
            +"\n\tPosition: "+this.xPos +" "+this.yPos
    +"\n";
    }

    return display;
  }
  
}