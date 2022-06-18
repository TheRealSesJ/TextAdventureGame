//planning on interface that includes the item

package GameObjects.Items;

public class Item{
  //protected changeable attributes
  protected String name = "Indeterminant Item";
  
  //weapon related
  private int attackBoost;
  private int speedBoost;
  private int accuracyBoost;
  private boolean rangeBoost;

  //player related
  private int moveBoost;
  private boolean traverseBoost;
  private int hpBoost;
  private int armorBoost;  

  public Item(int att, int spd, int acc, boolean rng, int move, boolean tvs, int hp, int arm){
    this.attackBoost=att;
    this.speedBoost=spd;
    this.accuracyBoost=acc;
    this.rangeBoost=rng;
    this.moveBoost=move;
    this.traverseBoost=tvs;
    this.hpBoost=hp;
    this.armorBoost=arm;
  }

  
  public String toString(){ return name; }

  //weapon
  public int getAttackBoost(){
    return this.attackBoost;
  }

  public int getSpeedBoost(){
    return this.speedBoost;
  }

  public int getAccuracyBoost(){
    return this.accuracyBoost;
  }

  public boolean getRangeBoost(){
    return this.rangeBoost;
  }

  //player
  public int getMoveBoost(){
    return this.moveBoost;
  }

  public boolean getTraverseBoost(){
    return this.traverseBoost;
  }

  public int getHpBoost(){
    return this.hpBoost;
  }

  public int getArmorBoost(){
    return this.armorBoost;
  }

  public String getStats(){
    return toString()
    +"\nWeapon Related:"
    +"\nattack boost: "+ this.attackBoost
    +"\nspeed: "+ this.speedBoost
    +"\naccuracy: "+ this.accuracyBoost
    +"\nranged: "+ this.rangeBoost
    +"\n"
    +"\nPlayer Related: "
    +"\nmove boost: "+ this.moveBoost
    +"\ntraversal boost: "+ this.traverseBoost
    +"\nhp boost:" + this.hpBoost
    +"\narmor boost: " + this.armorBoost
    +"\n";
  }
}