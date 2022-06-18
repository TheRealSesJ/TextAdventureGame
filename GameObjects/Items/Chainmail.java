package GameObjects.Items;
import StaticData.*;

public class Chainmail extends Item{
  //gets all of its stats from the static GameParameters class
  public Chainmail(){
    super(GameParameters.chainmailAttackBoost, GameParameters.chainmailSpeedBoost, GameParameters.chainmailAccuracyBoost, GameParameters.chainmailRangeBoost, GameParameters.chainmailMoveBoost, GameParameters.chainmailTraverseBoost, GameParameters.chainmailHpBoost, GameParameters.chainmailArmorBoost);
    this.name = "Chainmail";
  }
}