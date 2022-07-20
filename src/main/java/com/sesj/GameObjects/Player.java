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
  private int movement;
  private int hp;
  private int maxHp;
  private int armor;
  
  public Player(int hp, Weapon weapon, int armor, int movement, int initialX, int initialY){
    this.hp = hp;
    this.maxHp = hp;
    this.weapon = weapon;
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
      if(this.item==null || (movement+this.item.getMoveBoost()<Math.abs(xTiles) || movement+this.item.getMoveBoost()<Math.abs(yTiles))){
        throw new MovementOutOfRangeException();
      }
    }
    this.position.translate(xTiles, yTiles);
    return true;
  }

  public Point getPosition(){ return this.position; }

  public boolean canTraverse(){
    if(this.item==null) return false;
    return this.item.getTraverseBoost();
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

  public int getMaxHp(){
    if(item!=null){
      return this.maxHp+this.item.getHpBoost();
    }
    return this.maxHp;
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

  public void tick(){}

  //returns the players stats as a string, intended to give the user information
  public String getStats(){
    String display ="";
    if(item!=null){
      display +=
              this.weapon.getStats()
    +"\n"
    +this.item.getStats()
    +"\n"
    +"\nPlayer: (effected by "+this.item.toString()+")"
    +"\n\tHp: "+this.hp+ "+("+this.item.getHpBoost()+")"
    +"\n\tArmor: "+this.armor+ "+("+this.item.getArmorBoost()+")"
    +"\n";
    } else{
      display += 
    this.weapon.getStats()
    +"\n"
    +"\nPlayer:"
    +"\n\tHp: "+this.hp
    +"\n\tArmor: "+this.armor
            +"\n\tPosition: "+this.position
    +"\n";
    }

    return display;
  }
  
}