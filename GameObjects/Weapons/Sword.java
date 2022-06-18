package GameObjects.Weapons;
import StaticData.*;

public class Sword extends Weapon{
  public Sword(){
    super(GameParameters.swordAttack, GameParameters.swordSpeed, GameParameters.swordAccuracy, GameParameters.swordRanged);
    this.name = "sword";
  }
}