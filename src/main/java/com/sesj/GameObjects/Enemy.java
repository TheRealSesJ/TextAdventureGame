//uses the character interface

package com.sesj.GameObjects;
import com.sesj.Interfaces.*;
import com.sesj.World.WorldManager;

import java.awt.*;
import java.util.ArrayList;


public class Enemy implements Entity, GameObject {

  private Point position;

  private int hp;
  private final int MAX_HP;
  private final Weapon weapon;
  private int armor;
  private final int baseArmor;
  private final boolean canMove;
  private ArrayList<Buff> buffs = new ArrayList<>();
  
  private final String name;
  
  public Enemy(int hp, Weapon weapon, int armor, boolean canMove, String name){
    this.hp = hp;
    this.MAX_HP = hp;
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
    return MAX_HP;
  }

  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
    return this.armor;
  }

  public String getName() { return name; }

  public Point getPosition(){ return this.position; }

  public void initPos(Point position){
    this.position = position;
  }

  public boolean canTraverse(){ return false; }

  public void updateCoords(Point offset){
    this.position.translate((int) offset.getX(), (int) offset.getY());
  }


  public void tick(){
    //iterate over buffs
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        if(this.buffs.get(i).getDuration()>0){
          if((this.hp<this.MAX_HP)) this.hp+=this.buffs.get(i).getHp(); //TODO this could cause problems with slightly overhealing max hp
          if(this.buffs.get(i).getArmor()!=0) this.armor = this.buffs.get(i).getArmor();
          this.buffs.get(i).tick(this);
        } else {
          this.armor= baseArmor;
          this.buffs.remove(i);
        }
      }
    }
    //do movement
    move();
    //destroy this enemy if its hp is at 0 or below
    if(this.hp<=0) WorldManager.getWorld().destroy(this);
    System.out.println(name+ this + " @" + position + " has ticked!");
  }


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



  public void move(){ //TODO change the motives for why this enemy chooses to move
    ArrayList<Point> locations = new ArrayList<>();
    for(int i=-1;i<2;i++){
      for(int j=-1;j<2;j++){
        try{
          Scene loc = WorldManager.getWorld().getLocation(position, i, j);
          if(!(i==0 && j==0) && (loc.isTraversable() || this.canTraverse())){
            locations.add(new Point((int) position.getX()+i, (int) position.getY()+j));
          }
        }
        catch(IndexOutOfBoundsException e){}
      }
    }

    if(locations.size()==0 || Math.random()>=0.5){

    } else { //if cleared, do a movement TODO this is the only area where a GameObject has authority over the world, risky
      Point move = locations.get(locations.size() == 1 ? 0 : (int) (Math.random() * locations.size() - 1));
      WorldManager.getWorld().getEnemies(position).remove(this);
      WorldManager.getWorld().getEnemies(move).add(this);
      initPos(move);
      System.out.println(this.name+this+" has moved");
    }
  }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    String returnStr = "\nEnemy: "+this.name+"\n"
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