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
    try {
      JSONObject world = (JSONObject) file.get("world");
      int size = ((JSONArray) world.get("row1")).size();
      String[][] worldArr = new String[size][size];
      for(int i=0; i<size; i++){
        for(int j =0; j<size; j++){
          worldArr[i][j] = (String) ((JSONArray) world.get("row"+(size-i))).get(j);
        }
      }
      return worldArr;
    } catch(Exception e) {
      e.printStackTrace();
      return null; //will break the program if this code fails
    }
  }

  public static String[] loadEnemies(){
    JSONArray enemyList = (JSONArray) file.get("enemies");
    String[] enemyArr = new String[enemyList.size()];
    for(int i=0; i<enemyList.size(); i++){
      enemyArr[i] = (String) enemyList.get(i);
    }
    return enemyArr;
  }

  public static String[] loadItems(){
    JSONArray itemList = (JSONArray) file.get("items");
    String[] itemArr = new String[itemList.size()];
    for(int i=0; i<itemList.size(); i++){
      itemArr[i] = (String) itemList.get(i);
    }
    return itemArr;
  }

  public static String[] loadEnemyStats(String name){
    JSONObject enemy = (JSONObject) ((JSONObject) file.get("enemy_stats")).get(name);
    String[] enemyArgs = {
      (String) enemy.get("hp"), 
      (String) enemy.get("weapon"), 
      (String) enemy.get("armor"), 
      (String) enemy.get("name")};
    return enemyArgs;
  }

  public static String[] loadItemStats(String name){
    JSONObject item = (JSONObject) ((JSONObject) file.get("item_stats")).get(name);
    String[] itemArgs = {
      (String) item.get("attack"), 
      (String) item.get("speed"), 
      (String) item.get("accuracy"), 
      (String) item.get("range"),
      (String) item.get("movement"),  
      (String) item.get("traversal"), 
      (String) item.get("hp"), 
      (String) item.get("armor"), 
      (String) item.get("name")};
    return itemArgs;
  }

  public static String[] loadWeaponStats(String name){
    JSONObject weapon = (JSONObject) ((JSONObject) file.get("weapon_stats")).get(name);
    String[] weaponArgs = {
      (String) weapon.get("attack"), 
      (String) weapon.get("speed"), 
      (String) weapon.get("accuracy"),
      (String) weapon.get("range"), 
      (String) weapon.get("name")};
    return weaponArgs;
  }
  

  



}
