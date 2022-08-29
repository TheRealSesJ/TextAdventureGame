package com.sesj.GameObjects;

import com.sesj.Interfaces.GameObject;

public class Consumable implements GameObject {
    final int hp;
    final int armor;
    final int duration; //when buffs are added
    final String name;




    public Consumable(int hp, int armor, int duration, String name){
        this.hp = hp;
        this.armor = armor;
        this.duration = duration;
        this.name = name;
    }

    public String getStats(){
        return "\nConsumable: "+this.name+" Duration: " + (duration==-1? "single use" : String.valueOf(duration))
                +"\n\n\tHp: "+this.hp
                +"\n\tArmor: "+this.armor;
    }

    public int getDuration(){ return duration; }

    public int getArmor(){ return armor; }

    public int getHp(){ return hp; }

    @Override
    public String toString(){ return name; }

}
