package com.sesj.GameObjects.Weapons;
import com.sesj.StaticData.*;

public class Stick extends Weapon{
  public Stick(){
    super(GameParameters.stickAttack, GameParameters.stickSpeed, GameParameters.stickAccuracy, GameParameters.stickRanged);
    this.name = "stick";
  }
}