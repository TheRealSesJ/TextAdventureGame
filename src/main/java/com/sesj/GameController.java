//this is static game code that *is* player initiated

//---------*IMPORTANT-------------
//names of methods here must be the same as the command that runs them due to reflection constraints

package com.sesj;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.sesj.Exceptions.*;
import com.sesj.GameObjects.Enemy;
import com.sesj.GameObjects.Item;
import com.sesj.GameObjects.Player;
import com.sesj.Interfaces.Entity;
import com.sesj.GameObjects.Scene;

//prints to console, only object allowed to do so
public class GameController{
  //ease of use for printing
  private static final PrintStream p = new PrintStream(System.out);
  private static final Random RAND = new Random();

  
//maps from scanInput()
  
  public static boolean scan(String[] input, Player player) throws MissingParameterException, NumberFormatException,
          MovementOutOfRangeException, MovementOutOfBoundsException, NotScannableException {
      if(input.length==1){
        p.println(World.getLocation(player).getInfo());
      } else {
//look for missing input parameters throw exception
        try{input[2]=input[2];}
        catch (IndexOutOfBoundsException e){throw new MissingParameterException();}
//make sure scan is less than player move capabilities or else throw exception
          if(Math.abs(Integer.parseInt(input[1]))>player.getMovement() || Math.abs(Integer.parseInt(input[2]))>player.getMovement()){
            throw new MovementOutOfRangeException();
          }
//look for missing index in world array throw exception
          try{
            Scene location = World.getLocation(player, Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            //check for null item or item scan boost
            boolean scan = false;
            try{
              scan = player.getItem().getScanBoost();
            } catch (NullPointerException e){
            }
            //if scannable scan else throw exception
            if(location.isScannable() || scan){
              p.println(location.getInfo());
            } else {
              throw new NotScannableException();
            }

          } catch(IndexOutOfBoundsException e){
            throw new MovementOutOfBoundsException();
          }
          
      } 
    return true;
  }



//moves the player in one of 8 possible directions


//displays the players stats to the screen 
//always returns false so turn does not pass
  public static boolean stats(String[] input, Player player){
    p.println(player.getStats());
    return false;
  }
  

  public class CombatController{
    //fight
    public static boolean fight(Player player){
      Scene scene = World.getLocation(player);
      Enemy enemy = scene.getEnemy();
      //calculate who goes first

      //player turn
      if(player.getWeapon().getSpeed()>=enemy.getWeapon().getSpeed()){
        combatTurn(player, enemy);
        if(enemy.getHp()<=0) {
          p.println("\nEnemy Vanquished!\n");
          scene.setEnemy(null);
          return true;
        } //end sequence if enemy is dead
        combatTurn(enemy, player);
      }
      //enemy turn
      else {
        combatTurn(enemy, player);
        if(player.getHp()<=0) return true; //end sequence if player is dead
        combatTurn(player, enemy);
      }

      //check to remove enemy after sequence if dead
      if(enemy.getHp()<=0) {
        p.println("\nEnemy Vanquished!\n");
        scene.setEnemy(null);
      } //end sequence if enemy is dead
      return true;
    }
    //attempt flee combat sequence
    public static boolean run(Player player) throws NumberFormatException,
            NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException, MissingParameterException{
      Scene scene = World.getLocation(player);
      //get a random moveable location and pass into translate
      int[] locs = getRandomMove(player);
      String[] input = {"",""+locs[0],""+locs[1]};
      //check for escapable location
      if(!scene.isEscapable()){
        p.println("there is nowhere to run...");
        return false;
      } else {
        WorldController.move(input, player);
        p.println("you escaped!");
        return true;
      }
    }
    //combat turn
    public static void combatTurn(Entity attacker, Entity defender){
      p.println("\n"+attacker+" Move!\n");
      //weapons have 1 to 100 attack chance, regardless of whether attack landed
      int attackRoll = RAND.nextInt(100)+1;
      if(defender.getWeapon().isRanged()){ //if enemy is ranged they are harder to hit, lowers roll
        attackRoll+=20;
      }
      if(attackRoll<=attacker.getWeapon().getAccuracy()){
        p.println("\nAttack landed with "+attacker.getWeapon()+" on "+ defender +"!\n");
//---------------------->armor will reduce damage taken<---------------------------
        int dmg = -1*attacker.getWeapon().getAttack()+defender.getArmor();
        defender.updateHp(Math.min(dmg, 0));
        p.println("\n"+defender+" hp remaining: "+defender.getHp()+"\n");
      } else {
        p.println("\nAttack failed on "+ defender +"!\n");
      }
    }
  }

  public class WorldController{

    public static boolean move(String[] input, Player player) throws NumberFormatException,
            NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException, MissingParameterException{
      //convert IndexOutOfBounds into MissingParameterException
      try{
        player.translate(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
      } catch(IndexOutOfBoundsException e){
        throw new MissingParameterException();
      }

      minimap(input, player);
      p.println("player moved "+input[1]+" units right and "+input[2]+" units up");
      return true;
    }

    public static boolean grab_item(String[] input, Player player) throws NullGameObjectException{
      Scene scene = World.getLocation(player);
      try{
        scene.getItem();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
      try{
        Item old = player.equip(scene.getItem());
        scene.setItem(old);
        p.println("you grabbed "+player.getItem().toString());
        if(old!=null) p.println(""+old.toString()+" was dropped");
        return true;
      } catch(NullPointerException e){
        e.printStackTrace();
        return false;
      }
    }
  }
  


//minimap commands
  public static boolean minimap(String[] input, Player player){
    String[][] minimap = getMinimap(player);
    for(int i=8; i>=0; i--){
      for(int j=0; j<3; j++){
        p.print(minimap[i/3][j]+" ");
        if(i!=4 || j!=1) p.print(minimap[i/3][j]+" ");
          else p.print("@ ");
        p.print(minimap[i/3][j]+" ");
      }
      p.println();
    }
    return false;
    
  }

  public static String[][] getMinimap(Player player){
    String[][] minimap = new String[3][3];
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
          try{ minimap[i][j] = World.getLocation(player, j-1, i-1).getIcon(); } 
          catch (IndexOutOfBoundsException e){ minimap[i][j] = "!"; }
      }
    }
    return minimap;
  }


  
  
  
//sends a moveable coordinate location of the player as an array of ints
  public static int[] getRandomMove(Player player){
    ArrayList<int[]> locations = new ArrayList<int[]>();
    for(int i=-1;i<2;i++){
      for(int j=-1;j<2;j++){
        try{
          Scene loc = World.getLocation(player, i, j);
          if(!(i==0 && j==0) && loc.isTraversable()){
            int[] coords = {i,j};
            locations.add(coords);
          }
        }
        catch(IndexOutOfBoundsException e){}
      }
    }
    return locations.get(RAND.nextInt(locations.size()-1));
  }


//help function

  public static boolean help(String[] input, Player player){
    p.println(
      "Command List:\n"
      
      +"\nNormal Commands:\n" 
      +"\nscan: gives the user information about what is in the current scene"
      +"\nscan <x> <y>: gives the user information about what is in the scene at (x,y) relative to the player"
      +"\nmove <x> <y>: moves the player to the scene at (x,y) relative to the player"
      +"\ninteract <object>: gives the player information about the <enemy> or <item> in the current scene"
      +"\nminimap: displays the minimap (does not use a turn)"
      +"\nstats: displays the stats of the player (does not use a turn)"
      +"\nhelp: displays this command list (does not use a turn)"       
      +"\ngrab_item: equips the item in the current scene to the player, the player drops its old item"
      
      +"\nCombat Commands:\n"
      +"\nfight: takes a combat turn against the enemy"
      +"\nrun: attempts to flee the scene to a nearby scene"
      +"\nscan: gives the user information about what is in the current scene"
      +"\nscan <x> <y>: gives the user information about what is in the scene at (x,y) relative to the player"
      +"\ninteract <object>: gives the player information about the <enemy> or <item> in the current scene"
      +"\nminimap: displays the minimap (does not use a turn)"
      +"\nstats: displays the stats of the player (does not use a turn)"
      +"\nhelp: displays this command list (does not use a turn)"       
             
      +"\nall coordinates are assumed to be Cartesian ordered pairs\n"           
             );
    return false;
  }



}