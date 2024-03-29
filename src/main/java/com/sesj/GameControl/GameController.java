//this is static game code that *is* player initiated

//---------*IMPORTANT-------------
//names of methods here must be the same as the command that runs them due to reflection constraints

package com.sesj.GameControl;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import com.sesj.Exceptions.*;
import com.sesj.GameObjects.*;
import com.sesj.Interfaces.CombatEntity;
import com.sesj.UtilityObjects.Coordinate;
import com.sesj.World.WorldManager;

//prints to console, only object allowed to do so
public class GameController{
  //ease of use for printing
  private static final PrintStream p = new PrintStream(System.out);
  private static final Random RAND = new Random();

  
//maps from scanInput()
  
  public static boolean scan(String[] input, Player player) throws MissingParameterException, NumberFormatException,
          MovementOutOfRangeException, MovementOutOfBoundsException, NotScannableException {
      if(input.length==1){
        for(int i=0; i<WorldManager.getWorld().getEnemies(player.getPosition()).size(); i++){
          p.println(WorldManager.getWorld().getEnemies(player.getPosition()).get(i).getStats());
        }
        p.println(WorldManager.getWorld().getLocation(player.getPosition()).getStats()); //TODO stats of the point location, not the scene (point hashmap rework)
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
          Coordinate pointOffset = new Coordinate(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
          Scene location = WorldManager.getWorld().getLocation(player.getPosition(), Integer.parseInt(input[1]), Integer.parseInt(input[2]));
          //check for null item or item scan boost
          boolean scan = false;
          try{
            scan = player.getItem().getScanBoost();
          } catch (NullPointerException e){}
//--------------if scannable scan else throw exception
          if(location.isScannable() || scan){
            for(int i=0; i<WorldManager.getWorld().getEnemies(player.getPosition(), pointOffset).size(); i++){
              p.println(WorldManager.getWorld().getEnemies(player.getPosition(), pointOffset).get(i).getStats());
            }
            p.println(location.getStats());
          } else {
            throw new NotScannableException();
          }
//---------------------------------------------------
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

  public static boolean enter(String[] input, Player player) throws MovementOutOfBoundsException {
    if(!WorldManager.enterDungeon(player.getPosition())){
      throw new MovementOutOfBoundsException();
    }
    player.setPosition(new Coordinate(2,2));
    //World.setDungeonMap(null);
    p.println("<<<<DUNGEON ENTERED>>>>");
    return true;
  }

  public static boolean exit(String[] input, Player player) throws MovementOutOfBoundsException {
    if(!WorldManager.exitDungeon()){
      throw new MovementOutOfBoundsException();
    }
    player.setPosition(new Coordinate(2,2));
    //World.setDungeonMap(null);
    p.println("<<<<DUNGEON EXITED>>>>");
    return true;
  }
  

  public static class CombatController{
    //fight
    public static boolean fight(String[] input, Player player){ //TODO fix the flipped accuracy
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition()); //TODO scene could inform the way interactions are handled with all GameObject types
      Enemy enemy = WorldManager.getWorld().getEnemies(player.getPosition()).get(0);
      //calculate who goes first

      //player turn
      if(player.getWeapon().getSpeed()>=enemy.getWeapon().getSpeed()){
        Utils.combatTurn(player, enemy);
        if(enemy.getHp()<=0) {
          p.println("\nEnemy Vanquished!\n");
          WorldManager.getWorld().destroy(enemy); //TODO fix this
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
        WorldManager.getWorld().destroy(enemy); //TODO fix this
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
        p.println("you grabbed "+player.getItem().getId());
        p.println(""+old.getId()+" was dropped");
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
        p.println("you grabbed "+player.getConsumable().getId());
        if(old!=null) p.println(""+old.getId()+" was dropped");
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
        p.println("you grabbed "+player.getWeapon().getId());
        p.println(""+old.getId()+" was dropped");
        return true;
    }

    public static boolean use(String[] input, Player player) throws NullGameObjectException {
      Scene scene = WorldManager.getWorld().getLocation(player.getPosition());
      try{
        player.getConsumable().getStats();
      } catch (NullPointerException e){
        throw new NullGameObjectException();
      }
      String name = player.getConsumable().getId();
      boolean used = player.consume();
      if(used) p.println("Consumable "+name+" used!");
      else p.println("Consumable "+name+" could not be used, effect is already active");
      return used;
    }

    public static boolean interact(String[] input, Player player) throws NPCInterfaceException { //launches player into npc mode
      if(WorldManager.getWorld().getNPCs(player.getPosition()).isEmpty()){
        return false;
      } else {
        throw new NPCInterfaceException();
      }
    }

  }

  //not referencable through reflection
  public static class Utils {
    //combat turn
    public static void combatTurn(CombatEntity attacker, CombatEntity defender){ //TODO ADD COMBAT DAMAGE NUMBERS; TODO FIX ACCURACY CALCULATION
      p.println("\n-----------COMBAT----------\n");
      p.println("\n"+attacker.getName()+" Move!\n");

      //weapons have 1 to 100 critical chance (accuracy)
      int attackRoll = RAND.nextInt(100)+1;
      int minCrit = 100-attacker.getWeapon().getAccuracy();
      if(defender.getWeapon().isRanged() && RAND.nextBoolean()) { //end with dodge if ranged
        minCrit+=20; //if enemy is ranged and attack is not dodged they are harder to crit
        p.println("\n"+defender.getName()+" EVADED!\n");
        p.println("\n"+defender+" hp: "+defender.getHp()+"/"+defender.getMaxHp()+"\n");

        p.println("\n-----------COMBAT----------\n");
        return;

      }
      p.println("Minimum roll to crit: "+minCrit);
      p.println("Attack roll: "+attackRoll);

      //check if attack has critical based on roll
      if(attackRoll<minCrit){
        int dmg = attacker.getWeapon().getAttack()-defender.getArmor();
        p.println("\nAttack landed with "+attacker.getWeapon().getId()+" on "+ defender.getName() +" for "+(dmg)+" damage!\n");
        defender.updateHp(Math.min(-1*dmg, 0));
      } else {
        int dmg = (int) (1.5*attacker.getWeapon().getAttack())-defender.getArmor();
        p.println("\n>>CRITICAL<< Attack landed with "+attacker.getWeapon().getId()+" on "+ defender.getName() +" for "+(dmg)+" damage!\n");
        defender.updateHp(Math.min(-1*dmg, 0));
      }
      if(attacker.getWeapon().getConsumable()!= null){
        defender.buff(new Buff(attacker.getWeapon().getConsumable()));
      }
      p.println("\n"+defender.getName()+" hp: "+defender.getHp()+"/"+defender.getMaxHp()+"\n");

      p.println("\n-----------COMBAT----------\n");
    }

    //sends a movable coordinate location of the entity as an array of ints
    public static int[] getRandomMove(CombatEntity combatEntity){
      ArrayList<int[]> locations = new ArrayList<int[]>();
      for(int i=-1;i<2;i++){
        for(int j=-1;j<2;j++){
          try{
            Scene loc = WorldManager.getWorld().getLocation(combatEntity.getPosition(), i, j);
            if(!(i==0 && j==0) && (loc.isTraversable() || combatEntity.canTraverse())){
              locations.add(new int[]{i, j});
            }
          }
          catch(IndexOutOfBoundsException e){}
        }
      }
      if(locations.size()==0) return new int[]{0,0}; //null movement
      return locations.get(locations.size()==1? 0 : RAND.nextInt(locations.size()-1));
    }
  }

  public static class NPCInteractions{
    public static boolean menu(String[] input, Player player){
      switch (WorldManager.getWorld().getNPCs(player.getPosition()).get(0).getType()){
        case BLACKSMITH:{
          p.println("\nWeapon and Item Upgrades: \n");
          p.println(player.getItem().getStats());
          p.println("\n\t---Increase all combat stats by 1---\n" +
                  "\tCOST: 7 xp\n" +
                  "\tBUY OPTION #: 1");
          p.println(player.getWeapon().getStats());
          p.println("\n\t---Increase all stats by 1---\n" +
                  "\tCOST: 15 xp\n"+
                  "\tBUY OPTION #: 2");
        } case SHOPKEEPER: {

        } case MERCHANT: {

        }
      }
      return false;
    }

    public static boolean buy(String[] input, Player player) throws MissingParameterException, NullGameObjectException, InsufficientXpException {
      if(WorldManager.getWorld().getNPCs(player.getPosition()).isEmpty()) throw new NullGameObjectException(); //if no npc exit
      if(input.length<2) throw new MissingParameterException(); //if missing parameters exit
      NPC npc = WorldManager.getWorld().getNPCs(player.getPosition()).get(0);
      switch (npc.getType()) {
        case BLACKSMITH: {
          if (Integer.parseInt(input[1]) == 1) { //TODO maybe make item less subjective than "case 1" and "15 xp"
            if(player.getXp()<7) throw new InsufficientXpException(); //checks for insufficient xp and cancels buy request if true
            player.getItem().upgrade(1.4);
            p.println("\nYou bought option #"+input[1]);
            p.println(player.getItem().getId()+" Upgraded!\n");
          }
          if(Integer.parseInt(input[1]) == 2){
            if(player.getXp()<15) throw new InsufficientXpException(); //checks for insufficient xp and cancels buy request if true
            player.getWeapon().upgrade(1.4);
            p.println("\nYou bought option #"+input[1]);
            p.println(player.getWeapon().getId()+" Upgraded!\n");
          }
        }
        case SHOPKEEPER: {

        }
        case MERCHANT: {

        }
      }

      return true;
    }

    public static boolean leave(String[] input, Player player) throws NPCInterfaceException {
      p.println("\nleaving...\n");
      throw new NPCInterfaceException();
    }
  }




  //minimap
  public static boolean minimap(String[] input, Player player){
    String[][] minimap = new String[9][9];
    for(int i=8; i>=0; i--){
      for(int j=0; j<9; j++){
        try{
          Scene loc = WorldManager.getWorld().getLocation(player.getPosition(), j/3-1, i/3-1);
          Coordinate coordinate = new Coordinate(j/3-1, i/3-1);
          boolean isEnemy = WorldManager.getWorld().getEnemies(player.getPosition(), coordinate)!= null &&
                  !WorldManager.getWorld().getEnemies(player.getPosition(), coordinate).isEmpty();
          if(i==4 && j==4){
            p.print("@ ");
          } else if(((double) (j-1)/3) == (j-1)/3 && ((double) (i-1)/3) == (i-1)/3 && isEnemy && loc.isScannable()){
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

//help function

  public static boolean help(String[] input, Player player){
    p.println(
            """

                    Command List:    (replace arguments in () with integer input parameters)

                    Normal Commands:
                      scan:          gives the user information about what is in the current scene
                      scan (x) (y):  gives the user information about what is in the scene at (x,y) relative to the player
                      interact:      if player is in a scene with an NPC, player enters NPC menu
                      minimap:       displays the minimap (does not use a turn)
                      enter:         if player is on a du+ngeon tile enter the dungeon
                      exit:          if player is inside a dungeon exit the dungeon and return to the world center (2,2)
                      stats:         displays the stats of the player (does not use a turn)
                      end_game:      ends the game

                    World Commands:
                      move (x) (y):  moves the player to the scene at (x,y) relative to the player
                      grab_item:     equips the item in the current scene to the player, the player drops its old item
                      grab_weapon:   equips the weapon in the current scene to the player, the player drops its old weapon
                      grab_cons:     equips the consumable in the current scene to the player, the player drops its old consumable
                      use:           uses the players consumable and removes it from the player

                    Combat Commands:
                      fight:         takes a combat turn against the enemy
                      run:           attempts to flee the scene to a nearby scene

                    Github Link: https://github.com/TheRealSesJ/TextAdventureGame
                    """
             );
    return false;
  }




  //dev commands, will refactor at some point so the user cannot use them
  public static boolean addXp(String[] input, Player  player) throws MissingParameterException {
    if(input.length==1) throw new MissingParameterException();
    player.updateXp(Integer.parseInt(input[1]));
    return true;
  }

  public static boolean addHp(String[] input, Player player) throws MissingParameterException {
    if(input.length==1) throw new MissingParameterException();
    player.updateHp(Integer.parseInt(input[1]));
    return true;
  }
}