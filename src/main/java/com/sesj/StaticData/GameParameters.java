package com.sesj.StaticData;


import com.sesj.Exceptions.ConfigNullValueException;
import com.sesj.Exceptions.MissingConfigException;
import com.sesj.GameObjects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

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

  public static Scene[][] getWorld(String map) throws ConfigNullValueException {
    String[][] worldStr = loadWorld(map);
    int size = worldStr.length;
    Scene[][] world = new Scene[size][size];
    for(int i=0; i<size; i++) {
      for (int j = 0; j < size; j++) {
        world[i][j] = getScene(worldStr[i][j]);
      }
    }
    return world;
  }

  //procedural generation
  public static Enemy[] getEnemyArray() throws ConfigNullValueException {
    String[] enemiesStr = loadSpawnables("enemies");
    int size = enemiesStr.length;
    Enemy[] enemies = new Enemy[size];
    for(int i=0; i<size;i++){
      enemies[i] = getEnemy(enemiesStr[i]);
    }
    return enemies;
  }


  public static Item[] getItemArray() throws ConfigNullValueException {
    String[] itemsStr = loadSpawnables("items");
    int size = itemsStr.length;
    Item[] items = new Item[size];
    for(int i=0; i<size;i++){
        items[i] = getItem(itemsStr[i]);
    }
    return items;
  }

  public static Consumable[] getConsumableArray() throws ConfigNullValueException {
    String[] itemsStr = loadSpawnables("consumables");
    int size = itemsStr.length;
    Consumable[] items = new Consumable[size];
    for(int i=0; i<size;i++){
      items[i] = getConsumable(itemsStr[i]);
    }
    return items;
  }

  public static Weapon[] getWeaponArray() throws ConfigNullValueException {
    String[] itemsStr = loadSpawnables("weapons");
    int size = itemsStr.length;
    Weapon[] weapons = new Weapon[size];
    for(int i=0; i<size;i++){
      weapons[i] = getWeapon(itemsStr[i]);
    }
    return weapons;
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


    public static String[][] loadWorld(String map) throws ConfigNullValueException {
      try{
        JSONObject world = (JSONObject) file.get(map);
        int size = ((JSONArray) world.get("row1")).size();
        String[][] worldArr = new String[size][size];
        for(int i=0; i<size; i++){
          for(int j =0; j<size; j++){
            worldArr[i][j] = (String) ((JSONArray) world.get("row"+(size-i))).get(j);
          }
        }
        return worldArr;
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at the world or dungeon array declaration");
      }

    }

    public static String[] loadSpawnables(String name) throws ConfigNullValueException {
      try{
        JSONArray list = (JSONArray) file.get(name);
        String[] arr = new String[list.size()];
        for(int i=0; i<list.size(); i++){
          arr[i] = (String) list.get(i);
        }
        return arr;
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at the "+name+" array declaration");
      }
    }
}