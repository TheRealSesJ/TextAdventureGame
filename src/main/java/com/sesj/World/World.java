package com.sesj.World;

import com.sesj.GameObjects.*;
import com.sesj.Interfaces.*;

import java.awt.*;
import java.util.*;

public class World {
    private final Scene[][] worldMap;
    private HashMap<Point, ArrayList<Enemy>> enemyMap = new HashMap<>();
    private HashMap<Point, ArrayList<NPC>> npcMap = new HashMap<>();
    private final ArrayList<AIEntity> ENTITY_CONTAINER = new ArrayList<>();
    private int xpBank = 0;

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
            if(enemyMap.get(new Point(x, y))==null){
                ArrayList<Enemy> arr = new ArrayList<>();
                arr.add(enemies.get(last));
                enemyMap.put(new Point(x, y), arr);
            } else {
                enemyMap.get(new Point(x, y)).add(enemies.get(last));
            }
            enemies.get(last).setPosition(new Point(x, y));
            ENTITY_CONTAINER.add(enemies.remove(last));
        }

        //npcs
        NPC blacksmith = new NPC("hello my friend", "blacksmith", "Blacksmith", TYPE.BLACKSMITH);
        ENTITY_CONTAINER.add(blacksmith); //TODO STATIC ENTITY GENERATION
        blacksmith.setPosition(new Point(2, 2));
        npcMap.put(new Point(2, 2), new ArrayList<>());
        npcMap.get(new Point(2, 2)).add(blacksmith);

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

    public ArrayList<Enemy> getEnemies(Point pos){
        enemyMap.computeIfAbsent(pos, k -> new ArrayList<>()); //TODO understand this lambda function
        return enemyMap.get(pos);
    }

    public ArrayList<Enemy> getEnemies(Point pos, Point offset){
        enemyMap.computeIfAbsent(new Point((int) (pos.getX()+offset.getX()), (int) (pos.getY()+offset.getY())), k -> new ArrayList<>());
        return enemyMap.get(new Point((int) (pos.getX()+offset.getX()), (int) (pos.getY()+offset.getY())));
    }

    public ArrayList<NPC> getNPCs(Point pos){
        npcMap.computeIfAbsent(pos, k -> new ArrayList<>()); //TODO understand this lambda function
        return npcMap.get(pos);
    }

    public ArrayList<NPC> getNPCs(Point pos, Point offset){
        npcMap.computeIfAbsent(new Point((int) (pos.getX()+offset.getX()), (int) (pos.getY()+offset.getY())), k -> new ArrayList<>());
        return npcMap.get(new Point((int) (pos.getX()+offset.getX()), (int) (pos.getY()+offset.getY())));
    }

    public ArrayList<AIEntity> getEntities(){
        return ENTITY_CONTAINER;
    }


    public Scene getLocation(Point pos){
        return worldMap[(int) pos.getY()][(int) pos.getX()];
    }

    public Scene getLocation(Point pos, Point offset){
        int xPos = (int) pos.getX();
        int yPos = (int) pos.getY();
        int xOffset = (int) offset.getX();
        int yOffset = (int) offset.getY();
        return worldMap[yPos+yOffset][xPos+xOffset];
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

    public void tick(Player player){ //TODO iterate over the listed objects
        for(int i=0; i<ENTITY_CONTAINER.size();i++){
            ENTITY_CONTAINER.get(i).tick(player);
        }
    }

    public void destroy(Enemy enemy){
        ENTITY_CONTAINER.remove(enemy);
        System.out.println("removing");
        enemyMap.get(enemy.getPosition()).remove(enemy);
        xpBank += enemy.getXp();
    }

    public int xpConsume(){
        int returnVal = xpBank;
        xpBank = 0;
        System.out.println(returnVal+" xp consumed!");
        return returnVal;
    }
}
