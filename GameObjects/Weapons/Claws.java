package GameObjects.Weapons;
import StaticData.*;

public class Claws extends Weapon{
  public Claws(){
    super(GameParameters.clawsAttack, GameParameters.clawsSpeed, GameParameters.clawsAccuracy, GameParameters.clawsRanged);
    this.name = "claws";
  }
}