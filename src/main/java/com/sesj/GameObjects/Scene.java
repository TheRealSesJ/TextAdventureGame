package com.sesj.GameObjects;
import com.sesj.Interfaces.GameObject;
//import GameObjects.Weapons.*;

public class Scene implements GameObject {

  private final String icon;
  private final String id;

  private Enemy enemy;
  private Item item;
  private Weapon weapon;
  private Consumable consumable;

  //properties of scenes;
  private final boolean scannable;
  private final boolean escapable;
  private final boolean traversable;
  
  public Scene(boolean scannable, boolean escapable, boolean traversable, String icon, String id){
    this.scannable=scannable;
    this.escapable=escapable;
    this.traversable =traversable;
    this.icon = icon;
    this.id = id;
  }

  public String getId(){ return this.id; }

  public String getIcon(){ return icon; }

  public Item getItem(){ 
    Item e = this.item;
    if(e==null){
      return null;
    }
    return this.item;
  }

  public boolean setItem(Item item){
    if(this.item==null){
      this.item = item;
      return true;
    }
    if(item==null){
      this.item = null;
      return true;
    }
    return false;
  }

  public Weapon getWeapon(){
    Weapon e = this.weapon;
    if(e==null){
      return null;
    }
    return this.weapon;
  }

  public boolean setWeapon(Weapon weapon){
    if(this.weapon==null){
      this.weapon = weapon;
      return true;
    }
    if(weapon==null){
      this.weapon = null;
      return true;
    }
    return false;
  }

  public Consumable getConsumable(){
    Consumable e = this.consumable;
    if(e==null){
      return null;
    }
    return this.consumable;
  }

  public boolean setConsumable(Consumable cons){
    if(this.consumable==null){
      this.consumable = cons;
      return true;
    }
    if(cons==null){
      this.consumable = null;
      return true;
    }
    return false;
  }

  public String getStats(){
    String data = "";
    try{ data += "\n"+this.item.getStats()+"\n"; }
    catch(NullPointerException e){}
    try{ data += "\n"+this.weapon.getStats()+"\n"; }
    catch(NullPointerException e){}
    try{ data += "\n"+this.consumable.getStats()+"\n"; }
    catch(NullPointerException e){}
    return "\n"+this.id.toUpperCase()+":\n"+data;
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