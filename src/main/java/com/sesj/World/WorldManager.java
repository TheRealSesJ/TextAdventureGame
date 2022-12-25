package com.sesj.World;

import com.sesj.Exceptions.ConfigException;

import java.util.ArrayList;

import com.sesj.Interfaces.AIEntity;
import com.sesj.StaticData.*;
import com.sesj.UtilityObjects.Coordinate;

public class WorldManager {
  private static World world;
  private static World[][] dungeonMaps;
  private static World dungeonMap;


  public static void build() throws ConfigException{
    world = GameParameters.getWorld("main_world");
    EntityGenerator.name(world.getEntities());
    dungeonMaps = GameParameters.getDungeonLocations();
  }

  public static World getWorld(){
    if(dungeonMap!=null) return dungeonMap;
    return world;
  }

  public static boolean isDungeon(Coordinate pos){
    int x = pos.getX();
    int y = pos.getY();
    return dungeonMaps[y][x] != null;
  }

  public static boolean enterDungeon(Coordinate pos){
    int x = pos.getX();
    int y = pos.getY();
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