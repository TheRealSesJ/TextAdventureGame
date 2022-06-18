package GameObjects.Enemies;
import StaticData.*;

public class WildBeast extends Enemy{
  public WildBeast(){
    super(GameParameters.wildBeastHp, GameParameters.wildBeastWeapon, GameParameters.wildBeastArmor);
    name = "Wild Beast";
  }
}