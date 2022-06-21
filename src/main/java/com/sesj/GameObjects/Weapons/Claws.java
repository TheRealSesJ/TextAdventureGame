package com.sesj.GameObjects.Weapons;
import com.sesj.StaticData.*;

public class Claws extends Weapon{
  public Claws(){
    super(GameParameters.clawsAttack, GameParameters.clawsSpeed, GameParameters.clawsAccuracy, GameParameters.clawsRanged);
    this.name = "claws";
  }
}