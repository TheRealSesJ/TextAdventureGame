package com.sesj.StaticData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class ArtLoader {
    private static File artFile;
    private static Scanner scan;
    private static HashMap<String, StringBuffer> artList = new HashMap<>();

    public static void load(){
        artFile = new File("Config/Art.txt");
        try {
            scan = new Scanner(artFile);
        } catch (FileNotFoundException e) { //TODO make an exception type to handle
            e.printStackTrace();
        }
        gatherArt();
    }

    public static void gatherArt(){
        String currentHeader="";
        while(scan.hasNext()){
            String currentString = scan.nextLine();
            if(currentString.startsWith("*[") && currentString.endsWith("]")){
                currentHeader = currentString.substring(2, currentString.length()-1);
                artList.put(currentHeader, new StringBuffer());
                //pool detected
            } else if(currentString.startsWith("&&")){
                //comment detected
            } else {
                artList.get(currentHeader).append(currentString).append("\n");
                //add the current line with a newline character
            }

        }
    }

    public static String getArt(String key){
        return artList.get(key).toString();
    }
}
