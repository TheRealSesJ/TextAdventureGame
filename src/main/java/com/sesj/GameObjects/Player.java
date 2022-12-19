package com.sesj.GameObjects;

import com.sesj.Interfaces.*;
import com.sesj.Exceptions.*;
import com.sesj.World.WorldManager;

import java.awt.*;
import java.io.InterruptedIOException;
import java.util.ArrayList;

public class Player implements CombatEntity, GameObject {
  //world position
  private Point position;

  private Weapon weapon;
  private Item item; //starts empty
  private Consumable consumable; //starts empty
  private ArrayList<Buff> buffs = new ArrayList<>();
  private int movement;
  private int hp;
  private final int MAX_HP;
  private int armor;
  private int baseArmor;
  private int xp;
  
  public Player(int hp, Weapon weapon, Item item, int armor, int movement, int initialX, int initialY){
    this.hp = hp;
    this.MAX_HP = hp;
    this.weapon = weapon;
    this.item = item;
    this.armor = armor;
    this.baseArmor = armor;
    this.movement = movement;
    this.position = new Point(initialX, initialY);
  }

  //takes into account the ammount of tiles the player is allowed to move, as well as whether the area being entered is traversable. *also takes item buffs into consideration*
  //illegal acess exception used for case where tile is not traversible
  public boolean translate(int xTiles, int yTiles) throws NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException {
    try{
      if(!WorldManager.getWorld().getLocation(this.position, xTiles, yTiles).isTraversable()){
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

  public void setPosition(Point pos){
    this.position = pos;
  }

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

  public String getId() { return "Player"; }


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

  public int getMaxHp(){ return this.MAX_HP+this.item.getHpBoost(); }
  
  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
      return this.armor+this.item.getArmorBoost();
  }

  public void setArmor(int armor){ this.armor = armor; }

  public int getMovement(){
    return this.movement;
  }

  public int getXp(){ return this.xp; }

  public void updateXp(int xp){
    this.xp += xp;
  }

  public boolean buff(Buff buff){
    for(Buff e: this.buffs){
      if(e.equals(buff)) return false;
    }
    this.buffs.add(buff);
    this.consumable=null;
    return true;
  }

  public String getName() { return "Player"; }

  public boolean consume(){
    if(this.consumable.getDuration()==-1){
      this.hp+=this.consumable.getHp();
      this.armor+=this.consumable.getArmor();
      this.baseArmor+=this.consumable.getArmor();
      if(this.hp>this.MAX_HP) hp = MAX_HP;
      return true;
    } else {
      return buff(new Buff(this.consumable));
    }
  }

  public void tick() throws InterruptedIOException {
    System.out.println("I tick");
    this.weapon.reset();
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        if(this.buffs.get(i).getDuration()>0){
          if((this.hp<this.MAX_HP)) this.hp+=this.buffs.get(i).getHp();
          if(this.buffs.get(i).getArmor()!=0) this.armor = this.buffs.get(i).getArmor();
          this.buffs.get(i).tick(this);
        } else {
          this.armor= baseArmor;
          this.buffs.remove(i);
        }
      }
    }
    if(this.hp<=0) throw new InterruptedIOException();
  }

  //returns the players stats as a string, intended to give the user information
  public String getStats(){
    String returnStr = this.weapon.getStats()
    +"\n"
    +this.item.getStats()
    +"\n"
    +"\nPlayer: (effected by "+this.item.getId()+")"
    +"\n\n\tHp: "+this.hp+"/"+this.getMaxHp()+ "+("+this.item.getHpBoost()+")"
    +"\n\tArmor: "+this.armor+ "+("+this.item.getArmorBoost()+")"
            +"\n\tXp: "+this.xp
    +"\n";
    if(this.consumable!=null){
      returnStr+=this.consumable.getStats()+"\n";
    }
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        returnStr+=this.buffs.get(i).getStats()+"\n";
      }
    }

    return returnStr;
  }


  
}