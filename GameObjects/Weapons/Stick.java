package GameObjects.Weapons;
import StaticData.*;

public class Stick extends Weapon{
  public Stick(){
    super(GameParameters.stickAttack, GameParameters.stickSpeed, GameParameters.stickAccuracy, GameParameters.stickRanged);
    this.name = "stick";
  }
}