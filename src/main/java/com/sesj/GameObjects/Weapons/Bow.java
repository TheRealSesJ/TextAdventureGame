package com.sesj.GameObjects.Weapons;
import com.sesj.StaticData.*;

public class Bow extends Weapon{
  public Bow(){
    super(GameParameters.bowAttack, GameParameters.bowSpeed, GameParameters.bowAccuracy, GameParameters.bowRanged);
    this.name = "bow";
  }
}