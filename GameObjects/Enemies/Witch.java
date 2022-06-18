package GameObjects.Enemies;
import StaticData.*;

public class Witch extends Enemy{
  public Witch(){
    super(GameParameters.witchHp, GameParameters.witchWeapon, GameParameters.witchArmor);
    name = "Witch";
  }
}