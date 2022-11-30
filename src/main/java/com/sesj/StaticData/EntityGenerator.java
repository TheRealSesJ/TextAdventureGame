package com.sesj.StaticData;

import com.sesj.GameObjects.Enemy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EntityGenerator {
    private static File nameList;
    private static Scanner scan;
    private static ArrayList<ArrayList<String>> namePools = new ArrayList<>();

    public static void load(){
        nameList = new File("Config\\NameList.txt");
        try {
            scan = new Scanner(nameList);
        } catch (FileNotFoundException e) { //TODO make an exception type to handle
            e.printStackTrace();
        }
        gatherNames();
    }

    public static void gatherNames(){
        while(scan.hasNext()){
            String currentString = scan.next();
            if(currentString.startsWith("[")){
                namePools.add(new ArrayList<>(Arrays.asList(currentString)));
                currentString = scan.next();
            }
            namePools.get(namePools.size()-1).add(currentString);
        }
    }

    public static void name(ArrayList<Enemy> enemies){
        for(Enemy e : enemies){
            String namePool = e.getNamePool();
            for(ArrayList<String> arr : namePools){
                if(arr.get(0).substring(1, arr.get(0).length()-1).equals(namePool)){
                    e.setName(arr.get(1+(int) (Math.random()*arr.size()-1)));
                }
            }


        }

    }
}