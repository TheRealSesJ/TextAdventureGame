package com.sesj.GameObjects;

import com.sesj.Interfaces.CombatEntity;
import com.sesj.Interfaces.GameObject;

public class Buff implements GameObject {
    private final int hp;
    private final int armor;
    private int duration;
    private final String id;


    public Buff(int hp, int armor, int duration, String id){
         this.hp = hp;
         this.armor = armor;
         this.duration= duration;
         this.id = id;
    }

    public Buff(Consumable cons){ //implementation with
        this.hp = cons.getHp();
        this.armor = cons.getArmor();
        this.duration= cons.getDuration();
        this.id = cons.getId();
    }


    public void tick(CombatEntity target){
        this.duration--;
    }

    public int getDuration(){ return this.duration; }

    public int getHp(){ return this.hp; }

    public int getArmor(){ return this.armor; }

    public String getId(){ return this.id; }

    public String getStats(){
        return "\nStatus Effect: "+this.id +" Duration: " + this.duration
                +"\n\n\tHp: "+this.hp
                +"\n\tArmor: "+this.armor;
    }

    @Override
    public boolean equals(Object obj) { return (((Buff) obj).id.equals(this.id)); }
}
