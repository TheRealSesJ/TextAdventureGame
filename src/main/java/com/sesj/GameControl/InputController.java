//main method

//java -jar build\libs\tag.jar
//run command

package com.sesj.GameControl;

//import com.sesj.GameObjects.Scenes.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.sesj.Exceptions.*;
import com.sesj.GameObjects.Player;
import com.sesj.Interfaces.GameObject;
import com.sesj.StaticData.EntityGenerator;
import com.sesj.StaticData.GameParameters;
import com.sesj.World.WorldManager;

import java.io.*;


public class InputController{
  public static final PrintStream p = new PrintStream(System.out);
  public static final Scanner SCAN = new Scanner(System.in);
  public static Method previousCommand;
  public static String[] previousArgs;
  public static final String[] placeholder = {""};
  public static boolean npcMode = false;

//the game!!!
  
  public static void main(String[] args) throws ConfigException {

    //load the config, if missing throw an error, game inside try-catch
    p.println("""
            ______     ____      ______
           /_____/    / __ \\    | |
             | |     / |__| \\   | |  ___
             | |     | |  | |   | |    |
             |_|     |_|  |_|   |_|____|
            """);
    GameParameters.load();
    EntityGenerator.load();
    WorldManager.build();
    Player player = GameParameters.getPlayer();
    p.println("Welcome to text adventure!");

      //game loop
    while(true){
  //----------------------------take turn
      try{
          //print new turn information
        GameController.minimap(placeholder, player);
        if(WorldManager.isDungeon(player.getPosition())){
          p.println("\n<<DUNGEON LOCATED>>\n");
        }
        if(WorldManager.getWorld().getEnemies(player.getPosition()).isEmpty()){
          if(!WorldManager.getWorld().getNPCs(player.getPosition()).isEmpty()){ //give player info about the entities
            displayObjects(WorldManager.getWorld().getNPCs(player.getPosition()));
          }
          if(npcMode){ //do "safe" turns
            takeTurn("\nNPC interaction: (menu) (buy) (leave)\n", player, GameController.NPCInteractions.class);
          } else {
            takeTurn("\nWorld interaction: (\"help\" for options)\n", player, GameController.WorldController.class);
          }
        } else { //if enemy is not null do a combat turn
          displayObjects(WorldManager.getWorld().getEnemies(player.getPosition()));
          takeTurn("\nCombat interaction: (\"help\" for options)\n", player, GameController.CombatController.class);
          //------check for game over condition
          if(player.getHp()<=0){
            break;
          }
        }
          //------------------end sequence
        p.println("\nNext turn: (Press ENTER)");
        SCAN.nextLine();
        p.println("-------------------------------------\n");
        //--------------tick the entities
        //GameController.Utils.enemyTurn();
        player.tick();
        WorldManager.getWorld().tick(player); //pass in player to influence enemy tick functions
        player.updateXp(WorldManager.getWorld().xpConsume()); //TODO might reconfigure this timing (deferral)
      }
  //----------------handles game end with exception from end_game input
      catch(InterruptedIOException e){
        break;
      } catch(NPCInterfaceException ignored){} //only thrown by WorldController class and it is handled
    }


//----end of the game----
    p.println("\nGAME OVER\n");

    p.println("Press any button to exit:");
    SCAN.nextLine();

  }

  //input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player, Class<?> type) throws InterruptedIOException, NPCInterfaceException {
    String[] inputArr = input.split("\s+");
    //check if world input
    Method inputAction;
    try {
      //catch three special cases
      if(inputArr[0].equals("end_game")){
        throw new InterruptedIOException();
      }
      if(inputArr[0].equals("r") && previousCommand!=null && previousArgs!=null && (previousCommand.getDeclaringClass().equals(type) || previousCommand.getDeclaringClass().equals(GameController.class))){
        p.println("invoking: " + previousCommand.getName());
        return (boolean) previousCommand.invoke(null, previousArgs, player);
      }

      inputAction = GameController.class.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
      //set previous
      previousCommand = inputAction;
      previousArgs = inputArr;

      return (boolean) inputAction.invoke(null, inputArr, player);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return false;
    } catch(InvocationTargetException e){ //exception from invoked method
      p.println(e.getCause().getMessage());
      return false;
    } catch(NoSuchMethodException e) { //for commands which are not in game parameters but in inner classes
      try {
        if(type.equals(GameController.CombatController.class)){
          inputAction = type.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
          //set previous
          previousCommand = inputAction;
          previousArgs = inputArr;

          return (boolean) inputAction.invoke(null, inputArr, player);
        } else if (type.equals(GameController.WorldController.class)){
          inputAction = type.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
          //set previous
          previousCommand = inputAction;
          previousArgs = inputArr;

          return (boolean) inputAction.invoke(null, inputArr, player);
        } else if (type.equals(GameController.NPCInteractions.class)){
          inputAction = type.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
          //set previous
          previousCommand = inputAction;
          previousArgs = inputArr;

          return (boolean) inputAction.invoke(null, inputArr, player);
        }
      } catch (NoSuchMethodException ex) {
        return false;
      } catch (InvocationTargetException ex) {
        //handle npc interaction, switch mode, interact makes true, leave makes false;
        if(ex.getCause().getClass().equals(NPCInterfaceException.class)){
          npcMode = !npcMode;
          return true;
        }
        //ex.printStackTrace(); //include for debugging
        p.println("Input undefined: " + ex.getCause().getMessage());
        return false;
      } catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
    }
    return false;
  }

  //takes a turn and if fails (false) repeats
  public static void takeTurn(String introMessage, Player player, Class<?> area) throws InterruptedIOException, NPCInterfaceException {
    p.println(introMessage);
    while(!scanInput(SCAN.nextLine(), player, area)){
      p.println("\nplease enter an allowed action\n");
    }
  }

  //give player information about Entities in this location
  public static void displayObjects(ArrayList<?> list){
    for (int i=0; i<list.size(); i++) {
      GameObject e = (GameObject) list.get(i);
      p.println("\n<<AN "+e.getClass().getSimpleName().toUpperCase()+" HAS APPEARED>>\n");
      p.println(e.getStats());
    }
  }
  
}