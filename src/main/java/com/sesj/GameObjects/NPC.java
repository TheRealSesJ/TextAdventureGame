package com.sesj.GameObjects;

import com.sesj.Interfaces.AIEntity;
import com.sesj.Interfaces.GameObject;

import java.awt.*;

public class NPC implements GameObject, AIEntity {
    private final String id;
    private String name;
    private final String namePool;
    private final String message;
    private Point position;

    public NPC(String message, String namePool, String id){
        this.message = message;
        this.namePool = namePool;
        this.id=id;
    }

    @Override
    public String getNamePool(){ return this.namePool; }

    @Override
    public void setName(String name) { this.name = name;}

    @Override
    public Point getPosition() { return this.position; }

    @Override
    public void tick() {
        System.out.println(id + " " + this + " @" + position + " has ticked!");
    }

    @Override
    public String getName() { return this.name + " the "+this.id; } //TODO add name pool

    @Override
    public void setPosition(Point position) { this.position = position; }

    @Override
    public String getStats() {
        return "\nNPC: "+this.getName() +"\n"
                +"\n\tData:"

                +"\n";
    }

    @Override
    public String getId() {
        return this.id;
    }
}
