package com.sesj.GameObjects;

import com.sesj.Interfaces.ArtObject;
import com.sesj.Interfaces.GameObject;

public class Consumable implements GameObject, ArtObject {
    private final int hp;
    private final int armor;
    private final int duration; //when buffs are added
    private final String art;
    private final String id;
    private boolean onWeapon = false;


    public Consumable(int hp, int armor, int duration, String art, String id){
        this.hp = hp;
        this.armor = armor;
        this.duration = duration;
        this.art = art;
        this.id = id;
    }

    public String getStats(){
        String returnStr = "\nConsumable: "+this.id +"\n";
        if(!onWeapon) returnStr += "------------------------------->"; //should be 32 spaces
        returnStr += "\n    Duration: " + (duration==-1? "single use" : String.valueOf(duration))
                +"\n    Hp: "+this.hp
                +"\n    Armor: "+this.armor;
        return appendArt(returnStr, packageArt(art));
    }

    public int getDuration(){ return duration; }

    public int getArmor(){ return armor; }

    public int getHp(){ return hp; }

    public String getId(){ return id; }

    public String getArt(){ return packageArt(art); }

    public boolean isOnWeapon(){ return this.onWeapon; }

    public void attachToWeapon(){ this.onWeapon = true; }
}
