package com.sesj.StaticData;


import com.sesj.Exceptions.ConfigException;
import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;
import com.sesj.GameObjects.Weapons.*;
import com.sesj.Scenes.*;

public class GameParameters{

  //player stats
    public static final Weapon playerWeapon = getWeapon("sword");
    
    public static final int playerMovement = 1;
    public static final int playerHp = 30;
    public static final int playerArmor = 1;
  


  
  //load world from config

  public static Scene[][] getWorld(){
    String[][] worldStr = ConfigLoader.loadWorld();
    int size = worldStr.length;
    Scene[][] world = new Scene[size][size];
    for(int i=0; i<size; i++){
      for(int j=0; j<size;j++){
        String[] args = ConfigLoader.loadSceneStats(worldStr[i][j]);
        world[i][j] = new Scene(
                Boolean.parseBoolean(args[0]),
                Boolean.parseBoolean(args[1]),
                Boolean.parseBoolean(args[2]),
                args[3],
                args[4]);
      }
    }
    try{
      return world;
    } catch(NumberFormatException e){
      e.printStackTrace();
      return null;
    }
  }

  //procedural generation
  public static Enemy[] getEnemies(){
    String[] enemiesStr = ConfigLoader.loadEnemies();
    int size = enemiesStr.length;
    Enemy[] enemies = new Enemy[size];
    for(int i=0; i<size;i++){
      String[] args = ConfigLoader.loadEnemyStats(enemiesStr[i]);
      enemies[i] = new Enemy(
        Integer.parseInt(args[0]), //hp
        getWeapon(args[1]), //weapon
        Integer.parseInt(args[2]), //armor
        args[3]); //name
    }
    try{
      return enemies;
    } catch(NumberFormatException e){
      e.printStackTrace();
      return null;
    }
  }


  public static Item[] getItems(){
    String[] itemsStr = ConfigLoader.loadItems();
    int size = itemsStr.length;
    Item[] items = new Item[size];
    for(int i=0; i<size;i++){
      String[] args = ConfigLoader.loadItemStats(itemsStr[i]);
      items[i] = new Item(
        Integer.parseInt(args[0]), //attack boost
        Integer.parseInt(args[1]), //speed boost
        Integer.parseInt(args[2]), //accuracy boost
        Boolean.parseBoolean(args[3]), //range boost TODO FIX
        Integer.parseInt(args[4]), //movement boost
        Boolean.parseBoolean(args[5]), //traversal boost
        Integer.parseInt(args[6]), //hp boost
        Integer.parseInt(args[7]), //armor boost
        args[8]); //name
    }
    try{
      return items;
    } catch(NumberFormatException e){
      e.printStackTrace();
      return null;
    }
  }


  public static Weapon getWeapon(String name){
    String[] args = ConfigLoader.loadWeaponStats(name);
    try{
      return new Weapon(
      Integer.parseInt(args[0]), //attack 
      Integer.parseInt(args[1]), //speed 
      Integer.parseInt(args[2]), //accuracy 
      Boolean.parseBoolean(args[3]), //range
      args[4]); //name;
    } catch(NumberFormatException e){
      e.printStackTrace();
      return null;
    }
    
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