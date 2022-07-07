package com.sesj.GameObjects;
import com.sesj.GameObjects.Enemy;
import com.sesj.GameObjects.Item;
//import GameObjects.Weapons.*;

public class Scene {

  private final String icon;
  private final String name;

  private Enemy enemy;
  private Item item;

  //properties of scenes;
  private final boolean scannable;
  private final boolean escapable;
  private final boolean traversable;
  
  public Scene(boolean scannable, boolean escapable, boolean traversable, String icon, String name){
    this.scannable=scannable;
    this.escapable=escapable;
    this.traversable =traversable;
    this.icon = icon;
    this.name=name;
  }
  
  public String getName(){ return name; }

  public String toString(){ return this.name; }

  public String getIcon(){ return icon; }

  public Enemy getEnemy(){
    Enemy e = this.enemy;
    if(e==null){
      return null;
    }
    return this.enemy;
  }

  public boolean setEnemy(Enemy enemy){
  //these two null checks make sure a new real enemy is not replacing another
    if(this.enemy==null || enemy==null){
      this.enemy = enemy;
      return true;
    }
    return false;
  }

  public Item getItem(){ 
    Item e = this.item;
    if(e==null){
      return null;
    }
    return this.item;
  }

  public boolean setItem(Item item){ 
    if(this.item==null || item==null){
      this.item = item;
      return true;
    }
    return false;
  }

  public String getInfo(){
    String data = "";
    try{ data += this.enemy.getStats()+""; } 
    catch(NullPointerException e){ data += "\nno enemy\n";}
    try{ data += this.item.getStats()+""; } 
    catch(NullPointerException e){ data += "\nno item\n";}
    return this.name+":\n"+data;
  }

  public boolean isEscapable(){
    return this.escapable;
  }

  public boolean isScannable(){
    return this.scannable;
  }

  public boolean isTraversable(){
    return this.traversable;
  }
}