package com.sesj.UtilityObjects;

import java.awt.*;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x =x;
        this.y =y;
    }

    public int getX(){ return this.x; }

    public int getY(){ return this.y; }

    public void translate(int x, int y){
        this.x+=x;
        this.y+=y;
    }

    @Override
    public String toString(){ return "("+this.x+","+this.y+")"; }

    @Override
    public boolean equals(Object obj){
        Coordinate coord = (Coordinate) obj;
        return coord.getX()==this.getX() && coord.getY()==this.getY();
    }

    @Override
    public int hashCode() {  //copied from Point, hashing algorithm //TODO understand this
        long bits =Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

}
