//uses the character interface

package com.sesj.GameObjects;
import com.sesj.Interfaces.*;
import com.sesj.UtilityObjects.Coordinate;
import com.sesj.World.WorldManager;

import java.util.ArrayList;


public class Enemy implements CombatEntity, AIEntity, GameObject, ArtObject {


  private final int xp;
  private Coordinate position;

  private int hp;
  private final int MAX_HP;
  private final Weapon weapon;
  private int armor;
  private final int baseArmor;
  private final boolean canMove;
  private ArrayList<Buff> buffs = new ArrayList<>();
  private final String namePool;
  private String name;

  private final String art;
  private final String id;
  
  public Enemy(int hp, Weapon weapon, int armor, boolean canMove, String namePool, int xp, String art, String id){
    this.hp = hp;
    this.MAX_HP = hp;
    this.weapon = weapon; 
    this.armor=armor;
    this.baseArmor = armor;
    this.canMove = canMove;
    this.namePool = namePool;
    this.xp = xp;
    this.art=art;
    this.id = id;
  }

  public Weapon getWeapon(){
    return this.weapon;
  }

  public int getXp(){ return this.xp; }

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

  public String getArt(){ return packageArt(art);}

  public String getId() { return id; }

  public String getName(){ return this.name + " the "+this.id; }

  public void setName(String name){ this.name = name; }

  public String getNamePool(){ return this.namePool; }

  public Coordinate getPosition(){ return this.position; }

  public void setPosition(Coordinate position){
    this.position = position;
  }

  public boolean canTraverse(){ return false; }

  public void updateCoords(Coordinate offset){
    this.position.translate(offset.getX(), offset.getY());
  }


  public void tick(Player player){ //pass in player to influence movement
    //iterate over buffs
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        if(this.buffs.get(i).getDuration()>0){
          if((this.hp<this.MAX_HP)) this.hp+=this.buffs.get(i).getHp(); //TODO this could cause problems with slightly overhealing max hp
          if(this.buffs.get(i).getArmor()!=0) this.armor = this.buffs.get(i).getArmor();
          this.buffs.get(i).tick(this);
        } else {
          this.armor= baseArmor;
          this.buffs.remove(i); //TODO compiler says could cause problems with loop and removal...
        }
      }
    }
    //do movement
    move(player);
    //destroy this enemy if its hp is at 0 or below
    if(this.hp<=0) WorldManager.getWorld().destroy(this);
    System.out.println(id + " " + this + " @" + position + " has ticked!");
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



  public void move(Player player){ //TODO change the motives for why this enemy chooses to move
    ArrayList<Coordinate> locations = new ArrayList<>();
    for(int i=-1;i<2;i++){
      for(int j=-1;j<2;j++){
        try{
          Scene loc = WorldManager.getWorld().getLocation(position, i, j);
          if(!(i==0 && j==0) && (loc.isTraversable() || this.canTraverse())){
            locations.add(new Coordinate(position.getX()+i, position.getY()+j));
          }
        }
        catch(IndexOutOfBoundsException e){}
      }
    }

    if(locations.size()==0 || Math.random()>=0.5 || this.position.equals(player.getPosition())) return; //factor in player position
    else { //if cleared, do a movement TODO this is the only area where a GameObject has authority over the world, risky
      Coordinate move = locations.get(locations.size() == 1 ? 0 : (int) (Math.random() * locations.size() - 1));
      WorldManager.getWorld().getEnemies(position).remove(this);
      WorldManager.getWorld().getEnemies(move).add(this);
      setPosition(move);
      System.out.println(this.id +this+" has moved");
    }
  }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    String returnStr = "\nEnemy: "+this.getName().toUpperCase() +"\n"
            +"------------------------------->\n" //32 spaces
            +"\n    Hp: "+this.hp
            +"\n    Armor: "+this.armor
            +"\n    Can Move: "+this.canMove
            +"\n    Position: "+this.position
            +"\n    XP: "+this.xp;
    returnStr = appendArt(returnStr, packageArt(art));
    returnStr+=this.weapon.getStats()
    +"\n";
    if(this.buffs.size()!=0){
      for(int i = 0; i<this.buffs.size(); i++){
        returnStr+=this.buffs.get(i).getStats()+"\n";
      }
    }
    return returnStr;
  }


}