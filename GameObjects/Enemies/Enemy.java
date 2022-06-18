//uses the character interface

package GameObjects.Enemies;
import GameObjects.Weapons.*;
import Interfaces.*;


public class Enemy implements Entity{

  private int hp; 
  private Weapon weapon;
  private int armor;
  
  protected String name="Indeterminant Enemy";
  
  public Enemy(int hp, Weapon weapon, int armor){
    this.hp = hp;
    this.weapon = weapon; 
    this.armor=armor;
  }

  public Weapon getWeapon(){
    return this.weapon;
  }

  public int getHp(){
    return hp;
  }

  public void updateHp(int update){
    this.hp+=update;
  }

  public int getArmor(){
    return this.armor;
  }

  public String toString() { return name; }

  //returns the enemy's stats as a string, intended to give the user information
  public String getStats(){
    return toString()
    +"\nWeapon: "+this.weapon.toString()
    +"\nattack: "+ this.weapon.getAttack()
    +"\nspeed: "+ this.weapon.getSpeed()
    +"\naccuracy: "+ this.weapon.getAccuracy()
    +"\nranged: "+ this.weapon.isRanged()
    +"\n"
    +"\nHp: "+this.hp
    +"\nArmor: "+this.armor
    +"\n";
  }
}