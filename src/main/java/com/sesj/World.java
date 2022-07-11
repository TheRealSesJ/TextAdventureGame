package com.sesj;

import com.sesj.GameObjects.*;
import com.sesj.Exceptions.ConfigException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import com.sesj.StaticData.*;

public class World {
  private static Scene[][] worldMap;
  private static int size;
  private static ArrayList<Enemy> enemies;
  private static ArrayList<Item> items;
  private static ArrayList<Weapon> weapons;



  public static void build() throws ConfigException{
    worldMap = GameParameters.getWorld();
    size = worldMap.length;
    enemies = new ArrayList<Enemy>(Arrays.asList(GameParameters.getEnemies()));
    items = new ArrayList<Item>(Arrays.asList(GameParameters.getItems()));
    weapons = new ArrayList<Weapon>(Arrays.asList(GameParameters.getWeapons()));
    populateEnemies();
    populateItems();
    populateWeapons();
  }

  public static Scene getLocation(Player p){
    return worldMap[pY(p)][pX(p)];
  }

  public static Scene getLocation(Player p, int xOffset, int yOffset){
    return worldMap[pY(p) + yOffset][pX(p) + xOffset];
  }

  private static int pX(Player p){
    return p.getXPos()+size/2;
  }

  private static int pY(Player p){
    return p.getYPos()+size/2;
  }

  private static void populateEnemies(){
    Random rand = new Random();
    while(enemies.size()>0){
      int last = enemies.size()-1;
      if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setEnemy(enemies.get(last))){
        enemies.remove(last);
      }
    }
  }

  private static void populateItems(){
    Random rand = new Random();
    while(items.size()>0){
      int last = items.size()-1;
      if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setItem(items.get(last))){
        items.remove(last);
      }
    }
  }

  private static void populateWeapons(){
    Random rand = new Random();
    while(weapons.size()>0){
      int last = weapons.size()-1;
      if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setWeapon(weapons.get(last))){
        weapons.remove(last);
      }
    }
  }
  
}