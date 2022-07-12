package com.sesj.StaticData;


import com.sesj.Exceptions.MissingConfigException;
import com.sesj.GameObjects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class GameParameters{

  public static Scene[][] getWorld(){
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
  public static Enemy[] getEnemies(){
    String[] enemiesStr = ConfigLoader.loadSpawnables("enemies");
    int size = enemiesStr.length;
    Enemy[] enemies = new Enemy[size];
    for(int i=0; i<size;i++){
      enemies[i] = getEnemy(enemiesStr[i]);
    }
    return enemies;
  }


  public static Item[] getItems(){
    String[] itemsStr = ConfigLoader.loadSpawnables("items");
    int size = itemsStr.length;
    Item[] items = new Item[size];
    for(int i=0; i<size;i++){
      items[i] = getItem(itemsStr[i]);
    }
    return items;
  }

  public static Weapon[] getWeapons(){
    String[] itemsStr = ConfigLoader.loadSpawnables("weapons");
    int size = itemsStr.length;
    Weapon[] weapons = new Weapon[size];
    for(int i=0; i<size;i++){
      weapons[i] = getWeapon(itemsStr[i]);
    }
    return weapons;
  }

  public static Scene getScene(String name){
    String[] args = ConfigLoader.loadSceneStats(name);
    try{
      return new Scene(
              Boolean.parseBoolean(args[0]),
              Boolean.parseBoolean(args[1]),
              Boolean.parseBoolean(args[2]),
              args[3],
              args[4]);
    } catch(NumberFormatException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Enemy getEnemy(String name){
    String[] args = ConfigLoader.loadEnemyStats(name);
    try{
      return new Enemy(
              Integer.parseInt(args[0]), //hp
              getWeapon(args[1]), //weapon
              Integer.parseInt(args[2]), //armor
              args[3]); //name
    } catch(NumberFormatException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Item getItem(String name){
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
      e.printStackTrace();
      return null;
    }
  }

  public static Weapon getWeapon(String name){
    String[] args = ConfigLoader.loadWeaponStats(name);
    try{
      return new Weapon(
      Integer.parseInt(args[0]), //attack 
      Integer.parseInt(args[1]), //speed 
      Integer.parseInt(args[2]), //accuracy 
      Boolean.parseBoolean(args[3]), //range
      args[4]); //name;
    } catch(NumberFormatException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Player getPlayer(){
    String[] args = ConfigLoader.loadPlayerStats();
    try{
      return new Player(
              Integer.parseInt(args[0]), //hp
              getWeapon(args[1]), //weapon
              Integer.parseInt(args[2]), //armor
              Integer.parseInt(args[3])); //movement
    } catch(NumberFormatException e) {
      e.printStackTrace();
      return null;
    }

  }

  public static class ConfigLoader {

    //import the json
    private static JSONObject file;


    public static void load() throws MissingConfigException {
      try{
        JSONParser parser = new JSONParser();
        file = (JSONObject)parser.parse(new FileReader("Config\\Config.json"));
      } catch (Exception e){
        throw new MissingConfigException();
      }
    }

    public static String[][] loadWorld(){
        JSONObject world = (JSONObject) file.get("world");
        int size = ((JSONArray) world.get("row1")).size();
        String[][] worldArr = new String[size][size];
        for(int i=0; i<size; i++){
          for(int j =0; j<size; j++){
            worldArr[i][j] = (String) ((JSONArray) world.get("row"+(size-i))).get(j);
          }
        }
        return worldArr;
    }

    public static String[] loadSpawnables(String name){
      JSONArray list = (JSONArray) file.get(name);
      String[] arr = new String[list.size()];
      for(int i=0; i<list.size(); i++){
        arr[i] = (String) list.get(i);
      }
      return arr;
    }

    public static String[] loadEnemyStats(String name){
      JSONObject enemy = (JSONObject) ((JSONObject) file.get("enemy_stats")).get(name);
      return new String[]{
        (String) enemy.get("hp"),
        (String) enemy.get("weapon"),
        (String) enemy.get("armor"),
        (String) enemy.get("name")};
    }

    public static String[] loadItemStats(String name){
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
    }

    public static String[] loadWeaponStats(String name){
      JSONObject weapon = (JSONObject) ((JSONObject) file.get("weapon_stats")).get(name);
      return new String[]{
        (String) weapon.get("attack"),
        (String) weapon.get("speed"),
        (String) weapon.get("accuracy"),
        (String) weapon.get("range"),
        (String) weapon.get("name")};
    }

    public static String[] loadSceneStats(String name) {
      JSONObject scene = (JSONObject) ((JSONObject) file.get("scene_stats")).get(name);
      return new String[]{
              (String) scene.get("scannable"),
              (String) scene.get("escapable"),
              (String) scene.get("traversable"),
              (String) scene.get("icon"),
              (String) scene.get("name")};
    }

    public static String[] loadPlayerStats(){
      JSONObject player = (JSONObject) file.get("player_stats");
      return new String[]{
              (String) player.get("hp"),
              (String) player.get("weapon"),
              (String) player.get("armor"),
              (String) player.get("movement")};
    }






  }
}