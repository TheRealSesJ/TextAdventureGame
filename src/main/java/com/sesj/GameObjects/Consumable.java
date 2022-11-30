package com.sesj.GameObjects;

import com.sesj.Interfaces.GameObject;

public class Consumable implements GameObject {
    private final int hp;
    private final int armor;
    private final int duration; //when buffs are added
    private final String id;




    public Consumable(int hp, int armor, int duration, String id){
        this.hp = hp;
        this.armor = armor;
        this.duration = duration;
        this.id = id;
    }

    public String getStats(){
        return "\nConsumable: "+this.id +" Duration: " + (duration==-1? "single use" : String.valueOf(duration))
                +"\n\n\tHp: "+this.hp
                +"\n\tArmor: "+this.armor;
    }

    public int getDuration(){ return duration; }

    public int getArmor(){ return armor; }

    public int getHp(){ return hp; }

    public String getId(){ return id; }

}
