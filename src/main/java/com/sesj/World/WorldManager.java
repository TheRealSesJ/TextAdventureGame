package com.sesj.World;

import com.sesj.Exceptions.ConfigException;

import java.awt.*;

import com.sesj.StaticData.*;
import com.sesj.World.World;

public class WorldManager {
  private static World world;
  private static World[][] dungeonMaps;
  private static World dungeonMap;


  public static void build() throws ConfigException{
    world = GameParameters.getWorld("main_world");
    dungeonMaps = GameParameters.getDungeonLocations();
  }

  public static World getWorld(){
    if(dungeonMap!=null) return dungeonMap;
    return world;
  }

  public static boolean isDungeon(Point pos){
    int x = (int) pos.getX();
    int y = (int) pos.getY();
    return dungeonMaps[y][x] != null;
  }

  public static boolean enterDungeon(Point pos){
    int x = (int) pos.getX();
    int y = (int) pos.getY();
    if(isDungeon(pos)){
      dungeonMap = dungeonMaps[y][x];
      return true;
    }
    return false;
  }

  public static boolean exitDungeon(){
    if(dungeonMap!=null){
      dungeonMap=null;
      return true;
    } else {
      return false;
    }
  }


  
}