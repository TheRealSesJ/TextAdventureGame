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
    String[][] worldArr = new String[5][5];
    try {
      JSONObject world = (JSONObject) file.get("world");
      for(int i=0; i<5; i++){
        for(int j =0; j<5; j++){
          worldArr[i][j] = (String) ((JSONArray) world.get("row"+(5-i))).get(j);
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return worldArr;
  }
  



}
