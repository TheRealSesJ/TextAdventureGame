package com.sesj.StaticData;


import com.sesj.Exceptions.ConfigNullValueException;
import com.sesj.Exceptions.MissingConfigException;
import com.sesj.GameObjects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class GameParameters{

  public static Scene[][] getWorld() throws ConfigNullValueException {
    String[][] worldStr = ConfigLoader.loadWorld();
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
    String[] enemiesStr = ConfigLoader.loadSpawnables("enemies");
    int size = enemiesStr.length;
    Enemy[] enemies = new Enemy[size];
    for(int i=0; i<size;i++){
      enemies[i] = getEnemy(enemiesStr[i]);
    }
    return enemies;
  }


  public static Item[] getItemArray() throws ConfigNullValueException {
    String[] itemsStr = ConfigLoader.loadSpawnables("items");
    int size = itemsStr.length;
    Item[] items = new Item[size];
    for(int i=0; i<size;i++){
      items[i] = getItem(itemsStr[i]);
    }
    return items;
  }

  public static Weapon[] getWeaponArray() throws ConfigNullValueException {
    String[] itemsStr = ConfigLoader.loadSpawnables("weapons");
    int size = itemsStr.length;
    Weapon[] weapons = new Weapon[size];
    for(int i=0; i<size;i++){
      weapons[i] = getWeapon(itemsStr[i]);
    }
    return weapons;
  }

  public static Scene getScene(String name) throws ConfigNullValueException {
    String[] args = ConfigLoader.loadSceneStats(name);
    try{
      return new Scene(
              Boolean.parseBoolean(args[0]),
              Boolean.parseBoolean(args[1]),
              Boolean.parseBoolean(args[2]),
              args[3],
              args[4]);
    } catch(NumberFormatException e) {
      throw new ConfigNullValueException("in scene stats");
    }
  }

  public static Enemy getEnemy(String name) throws ConfigNullValueException {
    String[] args = ConfigLoader.loadEnemyStats(name);
    try{
      return new Enemy(
              Integer.parseInt(args[0]), //hp
              getWeapon(args[1]), //weapon
              Integer.parseInt(args[2]), //armor
              args[3]); //name
    } catch(NumberFormatException e) {
      throw new ConfigNullValueException("in enemy stats");
    }
  }

  public static Item getItem(String name) throws ConfigNullValueException {
    String[] args = ConfigLoader.loadItemStats(name);
    try{
      return new Item(
              Integer.parseInt(args[0]), //attack boost
              Integer.parseInt(args[1]), //speed boost
              Integer.parseInt(args[2]), //accuracy boost
              Boolean.parseBoolean(args[3]), //range boost TODO FIX
              Integer.parseInt(args[4]), //movement boost
              Boolean.parseBoolean(args[5]), //traversal boost
              Boolean.parseBoolean(args[6]), //scan boost
              Integer.parseInt(args[7]), //hp boost
              Integer.parseInt(args[8]), //armor boost
              args[9]); //name
    } catch(NumberFormatException e) {
      throw new ConfigNullValueException("in item stats");
    }
  }

  public static Weapon getWeapon(String name) throws ConfigNullValueException {
    String[] args = ConfigLoader.loadWeaponStats(name);
    try{
      return new Weapon(
      Integer.parseInt(args[0]), //attack 
      Integer.parseInt(args[1]), //speed 
      Integer.parseInt(args[2]), //accuracy 
      Boolean.parseBoolean(args[3]), //range
      args[4]); //name;
    } catch(NumberFormatException e) {
      throw new ConfigNullValueException("in weapon stats");
    }
  }

  public static Player getPlayer() throws ConfigNullValueException {
    String[] args = ConfigLoader.loadPlayerStats();
    try{
      return new Player(
              Integer.parseInt(args[0]), //hp
              getWeapon(args[1]), //weapon
              Integer.parseInt(args[2]), //armor
              Integer.parseInt(args[3]),
              Integer.parseInt(args[4]),
              Integer.parseInt(args[5])); //movement
    } catch(NumberFormatException e) {
      throw new ConfigNullValueException("in player stats");
    }

  }

  public static class ConfigLoader {

    //import the json
    private static JSONObject file;


    public static void configLoad() throws MissingConfigException {
      try{
        JSONParser parser = new JSONParser();
        file = (JSONObject)parser.parse(new FileReader("Config\\Config.json"));
      } catch (Exception e){
        throw new MissingConfigException();
      }
    }

    public static String[][] loadWorld() throws ConfigNullValueException {
      try{
        JSONObject world = (JSONObject) file.get("world");
        int size = ((JSONArray) world.get("row1")).size();
        String[][] worldArr = new String[size][size];
        for(int i=0; i<size; i++){
          for(int j =0; j<size; j++){
            worldArr[i][j] = (String) ((JSONArray) world.get("row"+(size-i))).get(j);
          }
        }
        return worldArr;
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at the world array declaration");
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

    public static String[] loadEnemyStats(String name) throws ConfigNullValueException {
      try{
        JSONObject enemy = (JSONObject) ((JSONObject) file.get("enemy_stats")).get(name);
        return new String[]{
                (String) enemy.get("hp"),
                (String) enemy.get("weapon"),
                (String) enemy.get("armor"),
                (String) enemy.get("name")};
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at enemy stats or reference");
      }
    }

    public static String[] loadItemStats(String name) throws ConfigNullValueException {
      try{
        JSONObject item = (JSONObject) ((JSONObject) file.get("item_stats")).get(name);
        return new String[]{
                (String) item.get("attack"),
                (String) item.get("speed"),
                (String) item.get("accuracy"),
                (String) item.get("range"),
                (String) item.get("movement"),
                (String) item.get("traversal"),
                (String) item.get("scan"),
                (String) item.get("hp"),
                (String) item.get("armor"),
                (String) item.get("name")};
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at item stats declaration or reference");
      }
    }

    public static String[] loadWeaponStats(String name) throws ConfigNullValueException {
      try{
        JSONObject weapon = (JSONObject) ((JSONObject) file.get("weapon_stats")).get(name);
        return new String[]{
                (String) weapon.get("attack"),
                (String) weapon.get("speed"),
                (String) weapon.get("accuracy"),
                (String) weapon.get("range"),
                (String) weapon.get("name")};
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at weapon stats declaration or reference");
      }
    }

    public static String[] loadSceneStats(String name) throws ConfigNullValueException {
      try{
        JSONObject scene = (JSONObject) ((JSONObject) file.get("scene_stats")).get(name);
        return new String[]{
                (String) scene.get("scannable"),
                (String) scene.get("escapable"),
                (String) scene.get("traversable"),
                (String) scene.get("icon"),
                (String) scene.get("name")};
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at scene stats declaration or reference");
      }
    }

    public static String[] loadPlayerStats() throws ConfigNullValueException {
      try{
        JSONObject player = (JSONObject) file.get("player_stats");
        return new String[]{
                (String) player.get("hp"),
                (String) player.get("weapon"),
                (String) player.get("armor"),
                (String) player.get("movement"),
                (String) player.get("initialX"),
                (String) player.get("initialY")};
      } catch (NullPointerException e){
        throw new ConfigNullValueException("at player stats declaration");
      }
    }






  }
}