package GameObjects.Items;
import StaticData.*;

public class PowerAmulet extends Item{
  //gets all of its stats from the static GameParameters class
  public PowerAmulet(){
    super(GameParameters.powerAmuletAttackBoost, GameParameters.powerAmuletSpeedBoost, GameParameters.powerAmuletAccuracyBoost, GameParameters.powerAmuletRangeBoost, GameParameters.powerAmuletMoveBoost, GameParameters.powerAmuletTraverseBoost, GameParameters.powerAmuletHpBoost, GameParameters.powerAmuletArmorBoost);
    this.name = "Power Amulet";
  }
}