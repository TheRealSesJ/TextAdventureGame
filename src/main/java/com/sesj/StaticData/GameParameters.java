package com.sesj.StaticData;

import java.util.Arrays;

import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;
import com.sesj.GameObjects.Weapons.*;
import com.sesj.Scenes.*;

public class GameParameters{

  //init
  public static void load(){

  }

  //player stats
    public static final Weapon playerWeapon = newSword();
    
    public static final int playerMovement = 1;
    public static final int playerHp = 30;
    public static final int playerArmor = 1;
  


  
  //load world from config

  public static Scene[][] getWorld(){
    String[][] worldStr = ConfigLoader.loadWorld();
    Scene[][] world = new Scene[worldStr.length][worldStr[0].length];
    for(int i=0; i<5; i++){
      for(int j=0; j<5;j++){
        Scene scene;
        switch(worldStr[i][j]){
          case("desert"): 
            scene = new Desert();
            break;
          case("forest"): 
            scene = new Forest();
            break;
          case("lake"): 
            scene = new Lake();
            break;
          case("mountains"): 
            scene = new Mountains();
            break;
          default:
            scene = new Scene("empty");
        }
        world[i][j] = scene;
      }
    }
    return world;
  }

  //procedural generation


  //all enemies array
  public static final Enemy[] enemies = {newWitch(), newWitch(), newWitch(), newWildBeast(), newWildBeast(), newWildBeast(), newWildBeast()};

  //all items array
    public static final Item[] items = {newHolySandals(), newHolySandals(), newPowerAmulet(), newPowerAmulet(), newChainmail()};

  



  //GameObjects
    //enemy constructors
        //hp //weapon //armor //name
  
      //wild beast
      public static Enemy newWildBeast(){
        return new Enemy(25, newClaws(), 5, "Wild Beast");
      }
    
      //witch
      public static Enemy newWitch(){
        return new Enemy(15, newStick(), 1, "Witch");
      }
    

    //item attributes
      //item constructors
        //attack boost //speed boost //accuracy boost //ranged boost //---weapon
        //move boost //traverse boost //hp boost //armor boost //---player
        //name //---data
      //PowerAmulet
      public static Item newPowerAmulet(){
        return new Item(4, 0, 0, true, 0, false, 0, 0, "Power Amulet");
      }

      //HolySandals
      public static Item newHolySandals(){
        return new Item(0, 1, 0, false, 2, true, 0, 0, "Holy Sandals");
      }

      //Chainmail
      public static Item newChainmail(){
        return new Item(0, -2, 0, false, 0, false, 0, 5, "Chainmail");
      }

  
    //weapon attributes
      //weapon constructors
        //attack //speed (0-10) //accuracy (0-100) //ranged (bool)
  
      //bow
      public static Weapon newBow(){
        return new Weapon(5, 3, 70, true, "Bow");
      }
  
      //stick
      public static Weapon newStick(){
        return new Weapon(3, 10, 65, false, "Stick");
      }
    
      //sword
      public static Weapon newSword(){
        return new Weapon(10, 5, 70, false, "Sword");
      }
  
      //claws
      public static Weapon newClaws(){
        return new Weapon(7, 6, 80, false, "Claws");
      }





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
} 