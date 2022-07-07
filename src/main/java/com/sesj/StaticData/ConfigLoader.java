package com.sesj.StaticData;

import java.io.*;

//json-simple imports
import org.json.simple.parser.JSONParser;

import com.sesj.Exceptions.MissingConfigException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigLoader {

  //import the json
  private static JSONObject file;


  public static void load() throws MissingConfigException{
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
