package com.sesj;

import com.sesj.GameObjects.*;
import com.sesj.Exceptions.ConfigException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

import com.sesj.Interfaces.Entity;
import com.sesj.StaticData.*;

public class WorldManager {
  private static WorldMap worldMap;
  private static WorldMap[][] dungeonMaps;
  private static WorldMap dungeonMap;


  public static void build() throws ConfigException{
    worldMap = new WorldMap(GameParameters.getWorld("worldMap"),
            new ArrayList<Enemy>(Arrays.asList(GameParameters.getEnemyArray())),
                    new ArrayList<Item>(Arrays.asList(GameParameters.getItemArray())),
                            new ArrayList<Weapon>(Arrays.asList(GameParameters.getWeaponArray())),
                                    new ArrayList<Consumable>(Arrays.asList(GameParameters.getConsumableArray())));

  }

  public static WorldMap getWorld(){
    if(dungeonMap!=null) return dungeonMap;
    return worldMap;
  }
  
}