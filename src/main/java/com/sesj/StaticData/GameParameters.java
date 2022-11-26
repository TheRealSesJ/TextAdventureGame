package com.sesj.StaticData;


import com.sesj.Exceptions.ConfigNullValueException;
import com.sesj.Exceptions.MissingConfigException;
import com.sesj.GameObjects.*;
import com.sesj.World.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class GameParameters{

  private static JSONObject file;

  public static void configLoad() throws MissingConfigException {
    try{
      JSONParser parser = new JSONParser();
      file = (JSONObject)parser.parse(new FileReader("Config\\Config.json"));
    } catch (Exception e){
      throw new MissingConfigException();
    }
  }

  public static World[][] getDungeonLocations() throws ConfigNullValueException {
    try{
      JSONObject map = (JSONObject) file.get("dungeon_placement_map");
      try{
        int size = ((JSONArray) map.get("row1")).size();
        World[][] dungeonArray = new World[size][size];
        for(int i=0; i<size; i++){
          for(int j =0; j<size; j++){
            if(((String) ((JSONArray) map.get("row"+(size-i))).get(j)).equals("null")){
              dungeonArray[i][j] = null;
            }else {
              dungeonArray[i][j] = getWorld((String) ((JSONArray) map.get("row" + (size - i))).get(j));

            }
          }
        }
        return dungeonArray;
      }catch (NullPointerException e){
        throw new ConfigNullValueException("at the dungeon placement map contents");
      }

    }catch (NullPointerException e){
      throw new ConfigNullValueException("at the dungeon placement map declaration");
    }

  }

  public static World getWorld(String worldID) throws ConfigNullValueException{
    try{
      JSONObject world = (JSONObject) file.get(worldID);

      //get the WorldMap
      Scene[][] worldArray;
      try{
        JSONObject worldMap = (JSONObject) world.get("map");
        int size = ((JSONArray) worldMap.get("row1")).size();
        worldArray = new Scene[size][size];
        for(int i=0; i<size; i++){
          for(int j =0; j<size; j++){
            worldArray[i][j] = getScene((String) ((JSONArray) worldMap.get("row"+(size-i))).get(j));
          }
        }
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at the world or dungeon array declaration");
      }

      //get the enemies
      String[] enemiesStr = loadSpawnables(world, "enemies");
      ArrayList<Enemy> enemies = new ArrayList<Enemy>();
      for(int i=0; i<enemiesStr.length;i++){
        enemies.add(getEnemy(enemiesStr[i]));
      }

      //get the items
      String[] itemsStr = loadSpawnables(world, "items");
      ArrayList<Item> items = new ArrayList<Item>();
      for(int i=0; i<itemsStr.length;i++){
        items.add(getItem(itemsStr[i]));
      }

      //get the weapons
      String[] weaponsStr = loadSpawnables(world, "weapons");
      ArrayList<Weapon> weapons = new ArrayList<Weapon>();
      for(int i=0; i<weaponsStr.length;i++){
        weapons.add(getWeapon(weaponsStr[i]));
      }

      //get the consumables
      String[] consStr = loadSpawnables(world, "consumables");
      ArrayList<Consumable> consumables = new ArrayList<Consumable>();
      for(int i=0; i<consStr.length;i++){
        consumables.add(getConsumable(consStr[i]));
      }


      return new World(worldArray, enemies, items, weapons, consumables);
    }catch (NullPointerException e){
      throw new ConfigNullValueException("at world container class");
    }

  }

  public static String[] loadSpawnables(JSONObject world, String name) throws ConfigNullValueException {
    try{
      JSONArray list = (JSONArray) world.get(name);
      String[] arr = new String[list.size()];
      for(int i=0; i<list.size(); i++){
        arr[i] = (String) list.get(i);
      }
      return arr;
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at the "+name+" array declaration");
    }
  }



  public static Scene getScene(String name) throws ConfigNullValueException {
    try{
      JSONObject scene = (JSONObject) ((JSONObject) file.get("scene_stats")).get(name);
      try{
        return new Scene(
                Boolean.parseBoolean((String) scene.get("scannable")),
                Boolean.parseBoolean((String) scene.get("escapable")),
                Boolean.parseBoolean((String) scene.get("traversable")),
                (String) scene.get("icon"),
                (String) scene.get("name"));
      } catch(NumberFormatException e) {
        throw new ConfigNullValueException("in scene stats");
      }
    } catch (NullPointerException e) {
      throw new ConfigNullValueException("at scene stats declaration or reference");
    }
  }

  public static Enemy getEnemy(String name) throws ConfigNullValueException {
    try{
      JSONObject enemy = (JSONObject) ((JSONObject) file.get("enemy_stats")).get(name);
      try{
        return new Enemy(
                Integer.parseInt((String) enemy.get("hp")), //hp
                getWeapon((String) enemy.get("weapon")), //weapon
                Integer.parseInt((String) enemy.get("armor")), //armor
                Boolean.parseBoolean((String) enemy.get("canMove")),
                (String) enemy.get("name")); //name
      } catch(NumberFormatException e) {
        throw new ConfigNullValueException("in enemy stats");
      }
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at enemy stats declaration or reference");
    }

  }

  public static Item getItem(String name) throws ConfigNullValueException {
    try{
      JSONObject item = (JSONObject) ((JSONObject) file.get("item_stats")).get(name);
      try{
        return new Item(
                Integer.parseInt((String) item.get("attack")), //attack boost
                Integer.parseInt((String) item.get("speed")), //speed boost
                Integer.parseInt((String) item.get("accuracy")), //accuracy boost
                Boolean.parseBoolean((String) item.get("range")), //range boost
                Integer.parseInt((String) item.get("movement")), //movement boost
                Boolean.parseBoolean((String) item.get("traversal")), //traversal boost
                Boolean.parseBoolean((String) item.get("scan")), //scan boost
                Integer.parseInt((String) item.get("hp")), //hp boost
                Integer.parseInt((String) item.get("armor")), //armor boost
                (String) item.get("name")); //name
      } catch(Exception e) {
        throw new ConfigNullValueException("in item stats");
      }
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at item stats declaration or reference");
    }

  }

  public static Weapon getWeapon(String name) throws ConfigNullValueException {
    try{
      JSONObject weapon = (JSONObject) ((JSONObject) file.get("weapon_stats")).get(name);
      try{
        return new Weapon(
                Integer.parseInt((String) weapon.get("attack")), //attack
                Integer.parseInt((String) weapon.get("speed")), //speed
                Integer.parseInt((String) weapon.get("accuracy")), //accuracy
                Boolean.parseBoolean((String) weapon.get("range")), //range
                getConsumable((String) weapon.get("consumable")), //status effect
                (String) weapon.get("name")); //name;
      } catch(NumberFormatException e) {
        throw new ConfigNullValueException("in weapon stats");
      }
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at weapon stats declaration or reference");
    }

  }

  public static Player getPlayer() throws ConfigNullValueException {
    try{
      JSONObject player = (JSONObject) file.get("player_stats");
      try{
        return new Player(
                Integer.parseInt((String) player.get("hp")), //hp
                getWeapon((String) player.get("weapon")), //weapon
                getItem((String) player.get("item")), //item
                Integer.parseInt((String) player.get("armor")),
                Integer.parseInt((String) player.get("movement")),
                Integer.parseInt((String) player.get("initialX")),
                Integer.parseInt((String) player.get("initialY"))); //movement
      } catch(NumberFormatException e) {
        throw new ConfigNullValueException("in player stats");
      }
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at player stats declaration");
    }

  }

  public static Consumable getConsumable(String name) throws ConfigNullValueException {
    try{
      JSONObject item = (JSONObject) ((JSONObject) file.get("consumable_stats")).get(name);
      try{
        return new Consumable(
                Integer.parseInt((String) item.get("hp")), //hp boost
                Integer.parseInt((String) item.get("armor")), //armor boost
                Integer.parseInt((String) item.get("duration")), //duration
                (String) item.get("name")); //name
      } catch(NumberFormatException e) {
        throw new ConfigNullValueException("in consumable stats");
      }
    } catch (NullPointerException e){
      throw new ConfigNullValueException("at consumable stats declaration or reference");
    }

  }
}