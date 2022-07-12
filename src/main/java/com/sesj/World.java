package com.sesj;

import com.sesj.GameObjects.*;
import com.sesj.Exceptions.ConfigException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

import com.sesj.Interfaces.Entity;
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
    enemies = new ArrayList<Enemy>(Arrays.asList(GameParameters.getEnemyArray()));
    items = new ArrayList<Item>(Arrays.asList(GameParameters.getItemArray()));
    weapons = new ArrayList<Weapon>(Arrays.asList(GameParameters.getWeaponArray()));
    populateEnemies();
    populateItems();
    populateWeapons();
  }

  public static Scene getLocation(Entity e){
    return worldMap[e.getYPos()][e.getXPos()];
  }

  public static Scene getLocation(Entity e, int xOffset, int yOffset){
    return worldMap[e.getYPos() + yOffset][e.getXPos() + xOffset];
  }





  private static void populateEnemies(){
    Random rand = new Random();
    while(enemies.size()>0){
      int last = enemies.size()-1;
      int x = rand.nextInt(size);
      int y = rand.nextInt(size);
      if(worldMap[y][x].setEnemy(enemies.get(last))){
        enemies.get(last).initPos(x, y);
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