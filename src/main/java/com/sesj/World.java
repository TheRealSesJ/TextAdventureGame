package com.sesj;

import com.sesj.Scenes.*;
import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import com.sesj.StaticData.*;

public class World {
  private static final int ySize = GameParameters.world.length; 
  private static final int xSize = GameParameters.world[0].length;
  private static final Scene[][] worldMap = GameParameters.world;
  private static ArrayList<Enemy> enemies =   
  new ArrayList<Enemy>(Arrays.asList(GameParameters.enemies));
  private static ArrayList<Item> items =   
  new ArrayList<Item>(Arrays.asList(GameParameters.items));

  public static void build(){
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
    return p.getXPos()+xSize/2;
  }

  private static int pY(Player p){
    return p.getYPos()+ySize/2;
  }

  private static void populateEnemies(){
    Random rand = new Random();
    while(enemies.size()>0){
      int last = enemies.size()-1;
      if(worldMap[rand.nextInt(5)][rand.nextInt(5)].setEnemy(enemies.get(last))){
        enemies.remove(last);
      }
    }
  }

  private static void populateItems(){
    Random rand = new Random();
    while(items.size()>0){
      int last = items.size()-1;
      if(worldMap[rand.nextInt(5)][rand.nextInt(5)].setItem(items.get(last))){
        items.remove(last);
      }
    }
  }
  
}