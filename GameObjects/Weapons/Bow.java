package GameObjects.Weapons;
import StaticData.*;

public class Bow extends Weapon{
  public Bow(){
    super(GameParameters.bowAttack, GameParameters.bowSpeed, GameParameters.bowAccuracy, GameParameters.bowRanged);
    this.name = "bow";
  }
}