//this is static game code that *is* player initiated

//---------*IMPORTANT-------------
//names of methods here must be the same as the command that runs them due to reflection constraints

package com.sesj;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.sesj.Exceptions.*;
import com.sesj.GameObjects.*;
import com.sesj.Interfaces.Entity;

//prints to console, only object allowed to do so
public class GameController{
  //ease of use for printing
  private static final PrintStream p = new PrintStream(System.out);
  private static final Random RAND = new Random();

  
//maps from scanInput()
  
  public static boolean scan(String[] input, Player player) throws MissingParameterException, NumberFormatException,
          MovementOutOfRangeException, MovementOutOfBoundsException, NotScannableException {
      if(input.length==1){
        p.println(WorldManager.getWorld().getLocation(player.getPosition()).getStats());
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
            Scene location = WorldManager.getWorld().getLocation(player.getPosition(), Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            //check for null item or item scan boost
            boolean scan = false;
            try{
              scan = player.getItem().getScanBoost();
            } catch (NullPointerException e){
            }
            //if scannable scan else throw exception
            if(location.isScannable() || scan){
              p.println(location.getStats());
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

  public static boolean exit(String[] input, Player player) throws MovementOutOfBoundsException {
    if(!player.isInDungeon()){
      throw new MovementOutOfBoundsException();
    }
    //World.setDungeonMap(null);
    p.println("<<<<DUNGEON EXITED>>>>");
    return true;
  }
  

  public static class CombatController{
    //fight
    public static boolean fight(String[] input, Player player){ //TODO fix the flipped accuracy
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      Enemy enemy = scene.getEnemy();
      //calculate who goes first

      //player turn
      if(player.getWeapon().getSpeed()>=enemy.getWeapon().getSpeed()){
        Utils.combatTurn(player, enemy);
        if(enemy.getHp()<=0) {
          p.println("\nEnemy Vanquished!\n");
          scene.setEnemy(null);
          return true;
        } //end sequence if enemy is dead
        Utils.combatTurn(enemy, player);
      }
      //enemy turn
      else {
        Utils.combatTurn(enemy, player);
        if(player.getHp()<=0) return true; //end sequence if player is dead
        Utils.combatTurn(player, enemy);
      }

      //check to remove enemy after sequence if dead
      if(enemy.getHp()<=0) {
        p.println("\nEnemy Vanquished!\n");
        scene.setEnemy(null);
      } //end sequence if enemy is dead
      return true;
    }
    //attempt flee combat sequence
    public static boolean run(String[] input, Player player) throws NumberFormatException,
            NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException, MissingParameterException{
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      //get a random moveable location and pass into translate
      int[] locs = Utils.getRandomMove(player);
      input = new String[]{"",""+locs[0],""+locs[1]};
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
  }

  public static class WorldController{

    public static boolean move(String[] input, Player player) throws NumberFormatException,
            NotTraversableException, MovementOutOfRangeException, MovementOutOfBoundsException, MissingParameterException{
      //convert IndexOutOfBounds into MissingParameterException
      try{
        player.translate(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
      } catch(IndexOutOfBoundsException e){
        throw new MissingParameterException();
      }
      p.println("player moved "+input[1]+" units right and "+input[2]+" units up");
      return true;
    }

    public static boolean grab_item(String[] input, Player player) throws NullGameObjectException{
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      try{
        scene.getItem().getStats();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
        Item old = player.equip(scene.getItem());
        scene.setItem(null);
        scene.setItem(old);
        p.println("you grabbed "+player.getItem().toString());
        p.println(""+old+" was dropped");
        return true;
    }

    public static boolean grab_cons(String[] input, Player player) throws NullGameObjectException{
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      try{
        scene.getConsumable().getStats();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
      try{
        Consumable old = player.equip(scene.getConsumable());
        scene.setConsumable(null);
        scene.setConsumable(old);
        p.println("you grabbed "+player.getConsumable().toString());
        if(old!=null) p.println(""+old.toString()+" was dropped");
        return true;
      } catch(NullPointerException e){
        throw new NullGameObjectException();
      }
    }

    public static boolean grab_weapon(String[] input, Player player) throws NullGameObjectException{
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      try{
        scene.getWeapon().getStats();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
        Weapon old = player.equip(scene.getWeapon());
        scene.setWeapon(null);
        scene.setWeapon(old);
        p.println("you grabbed "+player.getWeapon().toString());
        p.println(""+old.toString()+" was dropped");
        return true;
    }

    public static boolean use(String[] input, Player player) throws NullGameObjectException {
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      try{
        player.getConsumable().getStats();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
      String name = player.getConsumable().toString();
      boolean used = player.consume();
      if(used) p.println("Consumable "+name+" used!");
      else p.println("Consumable "+name+" could not be used, effect is already active");
      return used;
    }

  }


  //minimap
  public static boolean minimap(String[] input, Player player){
    String[][] minimap = new String[9][9];
    for(int i=8; i>=0; i--){
      for(int j=0; j<9; j++){
        try{
          Scene loc = WorldManager.getWorld().getLocation(player.getPosition(), j/3-1, i/3-1);
          if(i==4 && j==4){
            p.print("@ ");
          } else if(((double) (j-1)/3) == (j-1)/3 && ((double) (i-1)/3) == (i-1)/3 && loc.getEnemy()!=null && loc.isScannable()){
            p.print("E ");
          } else {
            p.print(WorldManager.getWorld().getLocation(player.getPosition(), j/3-1, i/3-1).getIcon()+" ");
          }
        } catch (IndexOutOfBoundsException e){
          p.print("! ");
        }

      }
      p.println();
    }
    return false;
  }

  //not referencable through reflection
  public static class Utils {
    //combat turn
    public static void combatTurn(Entity attacker, Entity defender){
      p.println("\n-----------COMBAT----------\n");
      p.println("\n"+attacker+" Move!\n");

      //weapons have 1 to 100 critical chance (accuracy)
      int attackRoll = RAND.nextInt(100)+1;
      int minCrit = 100-attacker.getWeapon().getAccuracy();
      if(defender.getWeapon().isRanged() && RAND.nextBoolean()) { //end with dodge if ranged
        p.println("\n"+defender+" EVADED!\n");
        p.println("\n"+defender+" hp: "+defender.getHp()+"/"+defender.getMaxHp()+"\n");

        p.println("\n-----------COMBAT----------\n");
        return;

      } else {
        minCrit+=20; //if enemy is ranged and attack is not dodged they are harder to crit
      }
      p.println("Minimum roll to crit: "+minCrit);
      p.println("Attack roll: "+attackRoll);

      //check if attack has critical based on roll
      if(attackRoll<minCrit){
        p.println("\nAttack landed with "+attacker.getWeapon()+" on "+ defender +"!\n");
        int dmg = -1*attacker.getWeapon().getAttack()+defender.getArmor();
        defender.updateHp(Math.min(dmg, 0));
      } else {
        p.println("\n>>CRITICAL<< Attack landed with "+attacker.getWeapon()+" on "+ defender +"!\n");
        int dmg = (int) -1.5*attacker.getWeapon().getAttack()+defender.getArmor();
        defender.updateHp(Math.min(dmg, 0));
      }
      if(attacker.getWeapon().getConsumable()!= null){
        defender.buff(new Buff(attacker.getWeapon().getConsumable()));
      }
      p.println("\n"+defender+" hp: "+defender.getHp()+"/"+defender.getMaxHp()+"\n");

      p.println("\n-----------COMBAT----------\n");
    }

    //sends a movable coordinate location of the entity as an array of ints
    public static int[] getRandomMove(Entity entity){
      ArrayList<int[]> locations = new ArrayList<int[]>();
      for(int i=-1;i<2;i++){
        for(int j=-1;j<2;j++){
          try{
            Scene loc = WorldManager.getWorld().getLocation(entity.getPosition(), i, j);
            if(!(i==0 && j==0) && (loc.isTraversable() || entity.canTraverse())){
              locations.add(new int[]{i, j});
            }
          }
          catch(IndexOutOfBoundsException e){}
        }
      }
      return locations.get(locations.size()==1? 0 : RAND.nextInt(locations.size()-1));
    }

    public static void enemyTurn(){
      Scene[][] world = WorldManager.getWorld().getArray();
      for(Scene[] row : world){
        for(Scene scene : row){
          if(scene.getEnemy()!=null){
            Enemy enemy = scene.getEnemy();
            //movement
            int[] move = getRandomMove(enemy);
            Scene newScene = WorldManager.getWorld().getLocation(enemy.getPosition(), move[0], move[1]);
              if(RAND.nextInt(5)>2 && newScene.getEnemy()==null){
                enemy.updateCoords(move[0], move[1]);
                newScene.setEnemy(enemy);
                scene.setEnemy(null);
              }
          }
        }
      }
    }
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