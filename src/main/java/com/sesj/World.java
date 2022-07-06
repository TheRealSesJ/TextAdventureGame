package com.sesj;

import com.sesj.Scenes.*;
import com.sesj.Exceptions.ConfigException;
import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import com.sesj.StaticData.*;

public class World {
  private static Scene[][] worldMap;
  private static int size;
  private static ArrayList<Enemy> enemies;
  private static ArrayList<Item> items;



  public static void build() throws ConfigException{
    worldMap = GameParameters.getWorld();
    size = worldMap.length;
    enemies = new ArrayList<Enemy>(Arrays.asList(GameParameters.getEnemies()));
    items = new ArrayList<Item>(Arrays.asList(GameParameters.getItems()));
    populateEnemies();
    populateItems();
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
  
}