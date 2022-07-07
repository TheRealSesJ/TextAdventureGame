package com.sesj;

import com.sesj.GameObjects.Weapons.*;
import com.sesj.GameObjects.Items.*;
import com.sesj.StaticData.*;
import com.sesj.Interfaces.*;
import com.sesj.Exceptions.*;

public class Player implements Entity{
  //world position
  private int xPos = 0;
  private int yPos = 0;

  private Weapon weapon = GameParameters.playerWeapon;
  private Item item; //starts empty

  private final int move = GameParameters.playerMovement;
  private int hp = GameParameters.playerHp;
  private final int armor=GameParameters.playerArmor;
  
  public Player(){ 
  }

  //takes into account the ammount of tiles the player is allowed to move, as well as whether or not the area being entered is traversible. *also takes item buffs into consideration*
  //illegal acess exception used for case where tile is not traversible
  public boolean translate(int xTiles, int yTiles) throws NotTraversibleException, MovementOutOfRangeException, MovementOutOfBoundsException {
    try{
      if(!World.getLocation(this, xTiles, yTiles).isTraversable()){
        if(this.item==null || !this.item.getTraverseBoost()){
          throw new NotTraversibleException();
        }
      }
    //convert index out of bounds into movement out of bounds for specificity
    }catch(IndexOutOfBoundsException e){
      throw new MovementOutOfBoundsException();
    }
    if(move<Math.abs(xTiles) || move<Math.abs(yTiles)){
      if(this.item==null || (move+this.item.getMoveBoost()<Math.abs(xTiles) || move+this.item.getMoveBoost()<Math.abs(yTiles))){
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
    return this.move;
  }

  //returns the players stats as a string, intended to give the user information
  public String getStats(){
    String display ="";
    if(item!=null){
      display += 
    "Weapon: "+this.weapon.toString() +" (affected by "+this.item.toString()+")"
    +"\nattack: "+ this.weapon.getAttack() + "+("+this.item.getAttackBoost()+")"
    +"\nspeed: "+ this.weapon.getSpeed() + "+("+this.item.getSpeedBoost()+")"
    +"\naccuracy: "+ this.weapon.getAccuracy() + "+("+this.item.getAccuracyBoost()+")"
    +"\nranged: "+ this.weapon.isRanged() + "+("+this.item.getRangeBoost()+")"
    +"\n"
    +"\nItem: "+this.item.toString() 
    +"\nmove boost: "+ this.item.getMoveBoost()
    +"\ntraversal boost: "+ this.item.getTraverseBoost()
    +"\n"
    +"\nPlayer: (effected by "+this.item.toString()+")"
    +"\nHp: "+this.hp+ "+("+this.item.getHpBoost()+")"
    +"\nArmor: "+this.armor+ "+("+this.item.getArmorBoost()+")"
    +"\n";
    } else{
      display += 
    "Weapon: "+this.weapon.toString()
    +"\nattack: "+ this.weapon.getAttack()
    +"\nspeed: "+ this.weapon.getSpeed()
    +"\naccuracy: "+ this.weapon.getAccuracy()
    +"\nranged: "+ this.weapon.isRanged()
    +"\n"
    +"\nPlayer:"
    +"\nHp: "+this.hp
    +"\nArmor: "+this.armor
    +"\n";
    }

    return display;
  }
  
}