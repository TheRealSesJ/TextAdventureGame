package StaticData;

import GameObjects.Enemies.*;
import GameObjects.Items.*;
import GameObjects.Weapons.*;
import Scenes.*;

public class GameParameters{

  
  
  //World Map
    public static final Scene[] row4 = 
  
      {new Forest(), new Mountains(), new Mountains(), new Mountains(), new Forest()};
  
    public static final Scene[] row3 = 
  
      {new Forest(), new Mountains(), new Mountains(), new Desert()   , new Forest()};
  
    public static final Scene[] row2 = 
  
      {new Forest(), new Forest()   , new Desert()   , new Desert()   , new Desert()};
  
    public static final Scene[] row1 = 
  
      {new Forest(), new Lake()     , new Lake()     , new Desert()   , new Desert()};
  
    public static final Scene[] row0 = 
  
      {new Forest(), new Lake()     , new Lake()     , new Desert()   , new Mountains()};

  public static final Scene[][] world = {row0, row1, row2, row3, row4};



  
  //GameObjects
    //enemy attributes
      //enemy
        //hp
        //weapon
  
      //wild beast
      public static final int wildBeastHp = 25;
      public static final Weapon wildBeastWeapon = new Claws();
      public static final int wildBeastArmor = 5;
    
      //witch
      public static final int witchHp = 15;
      public static final Weapon witchWeapon = new Stick();
      public static final int witchArmor = 1; 
      
    

    //item attributes
      //item
        //weapon boosts
          //attack boost
          //speed boost
          //accuracy boost
          //ranged boost
        //player boosts
          //move boost
          //traverse boost
          //hp boost
          //armor boost

      //PowerAmulet
      public static final int powerAmuletAttackBoost = 4;
      public static final int powerAmuletSpeedBoost = 0;
      public static final int powerAmuletAccuracyBoost = 0;
      public static final boolean powerAmuletRangeBoost=true;
      public static final int powerAmuletMoveBoost = 0;
      public static final boolean powerAmuletTraverseBoost = false;
      public static final int powerAmuletHpBoost = 0;
      public static final int powerAmuletArmorBoost = 0;

      //HolySandals
      public static final int holySandalsAttackBoost = 0;
      public static final int holySandalsSpeedBoost = 1;
      public static final int holySandalsAccuracyBoost = 0;
      public static final boolean holySandalsRangeBoost=false;
      public static final int holySandalsMoveBoost = 2;
      public static final boolean holySandalsTraverseBoost = true;
      public static final int holySandalsHpBoost = 0;
      public static final int holySandalsArmorBoost = 0;

      //Chainmail
      public static final int chainmailAttackBoost = 0;
      public static final int chainmailSpeedBoost = -2; //slows user down
      public static final int chainmailAccuracyBoost = 0;
      public static final boolean chainmailRangeBoost=false;
      public static final int chainmailMoveBoost = 0;
      public static final boolean chainmailTraverseBoost = false;
      public static final int chainmailHpBoost = 0;
      public static final int chainmailArmorBoost = 5;

  
    //weapon attributes
      //weapon
        //attack (no bounds)
        //speed 0-10 10 and 10 is a coinflip
        //accuracy 0-100 determines the chance of a hit
        //ranged boolean
  
      //bow
      public static final int bowAttack = 5;
      public static final int bowSpeed = 3;
      public static final int bowAccuracy = 70;
      public static final boolean bowRanged = true;
  
      //stick
      public static final int stickAttack = 3;
      public static final int stickSpeed = 10;
      public static final int stickAccuracy = 65;
      public static final boolean stickRanged = false;
    
      //sword
      public static final int swordAttack = 10;
      public static final int swordSpeed = 4;
      public static final int swordAccuracy = 70;
      public static final boolean swordRanged = false;
  
      //claws
      public static final int clawsAttack = 7;
      public static final int clawsSpeed = 6;
      public static final int clawsAccuracy = 80;
      public static final boolean clawsRanged = false;



  //scenes
    //scene
      //escapable (true)
      //remote scannable
      //traversible? (needs implementation)
      //lvl requirements (tbd)

    //mountains
      public static final boolean mountainsEscapable = false;

    //lake
      public static final boolean lakeTraversible = false;


//procedural generation


//all enemies array
  public static final Enemy[] enemies = {new Witch(), new Witch(), new Witch(), new WildBeast(), new WildBeast(), new WildBeast(), new WildBeast()};


//all items array
  public static final Item[] items = {new HolySandals(), new HolySandals(), new PowerAmulet(), new PowerAmulet(), new Chainmail()};
} 