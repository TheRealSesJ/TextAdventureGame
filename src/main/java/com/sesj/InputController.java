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
import com.sesj.StaticData.ConfigLoader;
import com.sesj.StaticData.GameParameters;

import java.io.*;

public class InputController{
  
  public static final PrintStream p = new PrintStream(System.out);
  public static final Scanner SCAN = new Scanner(System.in);

//the game!!!
  
  public static void main(String[] args){

    //playing boolean
    boolean playing = true;

    //load the config, if missing throw an error, game inside try-catch
    try{

      ConfigLoader.load();
      World.build();

      
      Player player = GameParameters.getPlayer();
      p.println("Welcome to text adventure!");
      GameController.minimap(new String[]{""}, player);


      //game loop
      while(playing){
        
  //----------------------------take turn
        try{
          while(!scanInput(SCAN.nextLine(), player, "world")){
            p.println("\nplease enter an action\n");
          }
          p.println("\nNext turn:\n");

  //----------------------check for combat, if yes convert to *combat turns*
          while(World.getLocation(player).getEnemy()!=null){
            p.println("\nAn enemy approaches...\n");
            p.println(World.getLocation(player).getEnemy().getStats());

            while(!scanInput(SCAN.nextLine(), player, "combat")){
            p.println("\nplease enter an action\n");
            }
    
  //------check for game over condition
            if(player.getHp()<=0){
              playing = false;
              break;
            }
            
            p.println("\nNext turn:\n");
          }
        }
  //----------------handles game end with exception from end_game input
        catch(InterruptedIOException e){
          playing = false;
          break;
        }

  //-----check for game over condition again
        if(player.getHp()<=0){
          playing = false;
          break;
        }

      }
    } catch (ConfigException e){
      p.println(e.getMessage());
    } finally{


//----end of the game----
      p.println("\nGAME OVER\n");

      p.println("Press any button to exit:");
      SCAN.nextLine();
    }
  }

  //input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player, String type) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
    //check if world input
    Method inputAction;
    try {
      inputAction = GameController.class.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
      return (boolean) inputAction.invoke(null, inputArr, player);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return false;
    } catch(InvocationTargetException e){ //exception from invoked method
      p.println(e.getCause().getMessage());
      return false;
    } catch(NoSuchMethodException e) { //for commands which are not in game parameters
      if(inputArr[0].equals("end_game")){
        throw new InterruptedIOException();
      }
      try {
        if(type.equals("combat")){
          inputAction = GameController.CombatController.class.getDeclaredMethod((inputArr[0]), Player.class);
          return (boolean) inputAction.invoke(null, player);
        } else if (type.equals("world")){
          inputAction = GameController.WorldController.class.getDeclaredMethod((inputArr[0]), String[].class, Player.class);
          return (boolean) inputAction.invoke(null, inputArr, player);
        }
      } catch (NoSuchMethodException ex) {
        return false;
      } catch (InvocationTargetException invocationTargetException) {
        p.println(e.getMessage());
        return false;
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      }
    }
    return false;
  }

  
}