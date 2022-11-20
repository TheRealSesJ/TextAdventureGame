package com.sesj;

import com.sesj.Exceptions.ConfigException;
import com.sesj.GameObjects.*;
import com.sesj.StaticData.GameParameters;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class World {
    private final Scene[][] worldMap;

    public World(Scene[][] map, ArrayList<Enemy> enemies, ArrayList<Item> items,
                 ArrayList<Weapon> weapons, ArrayList<Consumable> consumables) {
        worldMap = map;
        int size = worldMap.length;
        Random rand = new Random();

        //enemies
        while(enemies.size()>0){
            int last = enemies.size()-1;
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if((worldMap[y][x].isTraversable() || enemies.get(last).canTraverse()) && worldMap[y][x].setEnemy(enemies.get(last))){
                enemies.get(last).initPos(new Point(x, y));
                enemies.remove(last);
            }
        }

        //items
        while(items.size()>0){
            int last = items.size()-1;
            if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setItem(items.get(last))){
                items.remove(last);
            }
        }

        //weapons
        while(weapons.size()>0){
            int last = weapons.size()-1;
            if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setWeapon(weapons.get(last))){
                weapons.remove(last);
            }
        }

        //consumables
        while(consumables.size()>0){
            int last = consumables.size()-1;
            if(worldMap[rand.nextInt(size)][rand.nextInt(size)].setConsumable(consumables.get(last))){
                consumables.remove(last);
            }
        }

    }

    public Scene getLocation(Point pos){
        return worldMap[(int) pos.getY()][(int) pos.getX()];
    }

    public Scene getLocation(Point pos, int x, int y){
        int xPos = (int) pos.getX();
        int yPos = (int) pos.getY();
        return worldMap[yPos+y][xPos+x];
    }

    public int size(){
        return worldMap.length;
    }

    public Scene[][] getArray(){
        return worldMap;
    }

    public void tick(){
        for(Scene[] row : worldMap){
            for(Scene scene : row){
                if(scene.getEnemy()!=null) scene.getEnemy().tick();
            }
        }
    }
}
