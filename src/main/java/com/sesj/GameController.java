//this is static game code that *is* player initiated
package com.sesj;

import java.util.*;
import java.io.PrintStream;
import com.sesj.Scenes.*;
import com.sesj.StaticData.GameParameters;
import com.sesj.GameObjects.Enemies.*;
import com.sesj.GameObjects.Items.*;

//prints to console, only object allowed to do so
public class GameController{
  //ease of use for printing
  private static final PrintStream p = new PrintStream(System.out);
  private static final Random rand = new Random();

  
//maps from scanInput()
  
  public static boolean scan(String[] input, Player player){
    if(input.length<3){
      p.println(World.getLocation(player).getInfo());
    } else {
      try{
        Integer.parseInt(input[1]);
        Integer.parseInt(input[2]);
      } catch(NumberFormatException e){
        p.println("provide integers in fields 2 and 3");
        return false;
      } 
      try{
        if(Integer.parseInt(input[1])>GameParameters.playerMovement || Integer.parseInt(input[2])>GameParameters.playerMovement){
          throw new IndexOutOfBoundsException();
        }
        p.println(World.getLocation(player, Integer.parseInt(input[1]), Integer.parseInt(input[2])).getInfo()); 
      }
      catch (IndexOutOfBoundsException e){
      p.println("invalid input, out of bounds");
      return false;
      }
    }
    return true;
  }

  public static boolean interact(String[] input, Player player){
    if(input.length<2){
      p.println("specify \"item\" or \"enemy\"");
      return false;
    }
    if(input[1].equals("enemy")){
      if(World.getLocation(player).getEnemy()==null){
        p.println("there is no enemy to interact with");
        return false;
      }
      p.println(World.getLocation(player).getEnemy().getStats());
    } else if(input[1].equals("item")){
      if(World.getLocation(player).getItem()==null){
        p.println("there is no item to interact with");
        return false;
      }
      p.println(World.getLocation(player).getItem().getStats());
    } else {
      p.println("specify \"item\" or \"enemy\"");
      return false;
    }
    return true;   
  }

  public static boolean translate(String[] input, Player player){
    if(input.length<3){
      p.println("invalid input, make sure all fields are specified");
      return false;
    }
    try{
      Integer.parseInt(input[1]);
      Integer.parseInt(input[2]);
    } catch(NumberFormatException e){
      p.println("provide integers in fields 2 and 3");
      return false;
    } 
    try{player.translate(Integer.parseInt(input[1]), Integer.parseInt(input[2]));}
    catch(IllegalAccessException e){
      p.println("You cannot go there yet");
      return false; 
    } catch(IndexOutOfBoundsException e){
      p.println("invalid input, out of bounds");
      return false;
    }
    
    displayMinimap(player);
    p.println("player moved "+input[1]+" units right and "+input[2]+" units up");
    return true;
  }

  //displays the players stats to the screen 
  //always returns false so turn does not pass
  public static boolean displayStats(Player player){
    p.println(player.getStats());
    return false;
  }
  
  public static boolean grabItem(Player player){
    Scene scene = World.getLocation(player);
    if(scene.getItem()==null){
      p.println("there is no item here");
      return false; 
    }
    if(player.getItem()!=null){
      Item old = player.equip(scene.getItem());
      scene.setItem(old);
      p.println("you grabbed "+player.getItem().toString());
      p.println(""+old.toString()+" was dropped");
      return true;
    }
    player.equip(scene.getItem());
    p.println("you grabbed "+player.getItem().toString());
    scene.setItem(null);
    return true;
  }


  //map from scanCombatInput
  
  public static boolean combat(Player player){
    Scene scene = World.getLocation(player);
    Enemy enemy = scene.getEnemy();
      //calculate who goes first

      //player turn
      if(player.getWeapon().getSpeed()>=enemy.getWeapon().getSpeed()){
      playerTurn(player, enemy);
      if(enemy.getHp()<=0) {
        p.println("\nEnemy Vanquished!\n");
        scene.setEnemy(null);
        return true;
      } //end sequence if enemy is dead
      enemyTurn(player, enemy);
    }
      //enemy turn
    else {
      enemyTurn(player, enemy);
      if(player.getHp()<=0) return true; //end sequence if player is dead
      playerTurn(player, enemy);
    }

    //check to remove enemy after sequence if dead
    if(enemy.getHp()<=0) {
        p.println("\nEnemy Vanquished!\n");
        scene.setEnemy(null);
    } //end sequence if enemy is dead
    return true;
  }

  public static boolean run(Player player){
    Scene scene = World.getLocation(player);
    //get a random moveable location and pass into translate
    int[] locs = getRandomMove(player);
    String[] input = {"",""+locs[0],""+locs[1]};
    //check for escapable location
    if(!scene.isEscapable()){
      p.println("there is nowhere to run...");
      return false;
    } else {
      translate(input, player);
      p.println("you escaped!");
      return true;
    }
  }



//combat turns


  //player turn
  public static void playerTurn(Player player, Enemy enemy){
    p.println("\nPlayer Move!\n");
        //weapons have 1 to 100 attack chance, finds wether or not attack landed 
        int attackRoll = rand.nextInt(90)+1; //<-----------higher chance of landing attack for player
        if(enemy.getWeapon().isRanged()){ //if enemy is ranged they are harder to hit, same goes for player, lowers roll
          attackRoll-=20;
        }
        if(attackRoll<=player.getWeapon().getAccuracy()){
          p.println("\nAttack landed with "+player.getWeapon().toString()+" on "+enemy.toString()+"!");
//---------------------->armor will reduce damage taken<---------------------------
          int dmg = -1*player.getWeapon().getAttack()+enemy.getArmor();
          enemy.updateHp(dmg>0? 0: dmg);
          p.println("\nEnemy hp remaining: "+enemy.getHp()+"\n");
        } else {
          p.println("\nAttack failed on "+enemy.toString()+"!\n");
        }
  }


  //enemy turn, mostly same as player except inverted who is playing
  public static void enemyTurn(Player player, Enemy enemy){
    p.println("\nEnemy Move!\n");
        int attackRoll = rand.nextInt(100)+1;
        if(player.getWeapon().isRanged()){
          attackRoll-=20;
        }
        if(attackRoll<=enemy.getWeapon().getAccuracy()){
          p.println("\nYou were attacked with "+enemy.getWeapon().toString()+"!");

//----------------------armor will reduce damage taken----------------------------
        int dmg = -1*enemy.getWeapon().getAttack()+player.getArmor();
        player.updateHp(dmg>0? 0: dmg);
          p.println("\nYour hp remaining: "+player.getHp()+"\n");
        } else {
          p.println("\nYou dodged the attack!\n");
        }
  }
  



//minimap commands
  public static boolean displayMinimap(Player player){
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
          World.getLocation(player, i-1, j-1);
          if(!(i==0 && j==0)){
            int[] coords = {i,j};
            locations.add(coords);
          }
        }
        catch(IndexOutOfBoundsException e){}
      }
    }
    return locations.get(rand.nextInt(locations.size()-1));
  }


//help function

  public static boolean getHelp(){
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