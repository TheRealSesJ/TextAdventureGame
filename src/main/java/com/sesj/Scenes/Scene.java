package com.sesj.Scenes;
import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;
//import GameObjects.Weapons.*;

public class Scene {

  private String biome; 

  private Enemy enemy;
  private Item item;

  //properties of scenes;
  protected boolean scannable = true;
  protected boolean escapable = true;
  protected boolean traversible = true;
  
  public Scene(String biome){
    this.biome=biome;
  }
  
  public String getBiome(){ return biome; }

  public String toString(){ return this.biome; }

  public String getIcon(){ return "!"; }

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
    return this.biome+":\n"+data;
  }

  public boolean isEscapable(){
    return this.escapable;
  }

  public boolean isTraversible(){
    return this.traversible;
  }
}