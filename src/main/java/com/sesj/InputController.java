//main method

//java -jar build\libs\tag.jar
//run command

package com.sesj;

//import com.sesj.GameObjects.Scenes.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.sesj.Exceptions.*;
import com.sesj.GameObjects.Player;
import com.sesj.StaticData.GameParameters;

import java.io.*;

public class InputController{
  
  public static final PrintStream p = new PrintStream(System.out);
  public static final Scanner SCAN = new Scanner(System.in);
  public static Method previousCommand;
  public static String[] previousArgs;

//the game!!!
  
  public static void main(String[] args){

    //playing boolean
    boolean playing = true;

    //load the config, if missing throw an error, game inside try-catch
    try{

      GameParameters.configLoad();
      WorldManager.build();

      
      Player player = GameParameters.getPlayer();
      p.println("Welcome to text adventure!");


      //game loop
      while(playing){
        
  //----------------------------take turn
        try{
          GameController.minimap(new String[]{""}, player);
          if(WorldManager.getWorld().getLocation(player.getPosition()).getEnemy()==null){
            while(!scanInput(SCAN.nextLine(), player, GameController.WorldController.class)){
              p.println("\nplease enter an allowed action\n");
            }
            //----------------------do enemy turns
            GameController.Utils.enemyTurn();
            p.println("\nNext turn: (Press ENTER)");
            SCAN.nextLine();
            p.println("<------------------------------------->\n");
          } else { //if enemy is not null do a combat turn
            p.println("\n<<AN ENEMY HAS APPEARED>>\n");
            p.println("\nPlayer Hp: "+player.getHp()+"\n");
            p.println(WorldManager.getWorld().getLocation(player.getPosition()).getEnemy().getStats());

            while(!scanInput(SCAN.nextLine(), player, GameController.CombatController.class)){
              p.println("\nplease enter an allowed action\n");
            }

            //------check for game over condition
            if(player.getHp()<=0){
              playing = false;
              break;
            }

            p.println("\nNext turn: (Press ENTER)");
            SCAN.nextLine();
            p.println("-------------------------------------\n");
          }
        }
  //----------------handles game end with exception from end_game input
        catch(InterruptedIOException e){
          playing = false;
          break;
        }

  //-----check for game over condition again
        if(player.getHp()<=0) {
          playing = false;
          break;
        }

//--------------tick the entities
        player.tick();
        WorldManager.getWorld().tick();


      }
    } catch (ConfigException e){
      e.printStackTrace();
    }

//----end of the game----
      p.println("\nGAME OVER\n");

      p.println("Press any button to exit:");
      SCAN.nextLine();

  }

  //input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player, Class<?> type) throws InterruptedIOException{
    String[] inputArr = input.split("\s+");
    //check if world input
    Method inputAction;
    try {
      //catch two special cases
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
        }
      } catch (NoSuchMethodException ex) {
        return false;
      } catch (InvocationTargetException ex) {
        //ex.printStackTrace(); //include for debugging
          p.println("Input undefined: " + ex.getCause().getMessage());
        return false;
      } catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
    }
    return false;
  }

  
}