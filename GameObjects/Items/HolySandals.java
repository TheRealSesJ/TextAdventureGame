package GameObjects.Items;
import StaticData.*;

public class HolySandals extends Item{
  //gets all of its stats from the static GameParameters class
  public HolySandals(){
    super(GameParameters.holySandalsAttackBoost, GameParameters.holySandalsSpeedBoost, GameParameters.holySandalsAccuracyBoost, GameParameters.holySandalsRangeBoost, GameParameters.holySandalsMoveBoost, GameParameters.holySandalsTraverseBoost, GameParameters.holySandalsHpBoost, GameParameters.holySandalsArmorBoost);
    this.name = "Holy Sandals";
  }
}