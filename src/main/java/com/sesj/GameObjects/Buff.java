package com.sesj.GameObjects;

import com.sesj.Interfaces.Entity;
import com.sesj.Interfaces.GameObject;

public class Buff implements GameObject {
    private int hp;
    private int armor;
    private int duration;
    private String name;


    public Buff(int hp, int armor, int duration, String name){
         this.hp = hp;
         this.armor = armor;
         this.duration= duration;
         this.name = name;
    }

    public Buff(Consumable cons){ //implementation with
        this.hp = cons.getHp();
        this.armor = cons.getArmor();
        this.duration= cons.getDuration();
        this.name = cons.getName();
    }


    public void tick(Entity target){
        this.duration--;
    }

    public int getDuration(){ return this.duration; }

    public int getHp(){ return this.hp; }

    public int getArmor(){ return this.armor; }

    public String getName(){ return this.name; }

    public String getStats(){
        return "\nStatus Effect: "+this.name+" Duration: " + this.duration
                +"\n\n\tHp: "+this.hp
                +"\n\tArmor: "+this.armor;
    }

    @Override
    public boolean equals(Object obj) { return (((Buff) obj).name.equals(this.name)); }
}
