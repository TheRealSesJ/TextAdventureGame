//main method
package com.sesj;

//import com.sesj.Scenes.*;
import java.util.*;

import com.sesj.Exceptions.*;
import com.sesj.StaticData.ConfigLoader;

import java.io.*;

public class InputController{
  
  public static final PrintStream p = new PrintStream(System.out);
  public static final Scanner SCAN = new Scanner(System.in);

//the game!!!
  
  public static void main(String[] args) {

    //playing boolean
    boolean playing = true;
    
    p.println(System.getProperty("user.dir"));

    //load the condig, if missing throw an error
    try{
      ConfigLoader.load();
    } catch (MissingConfigException e){
      p.println("Unable to locate Config file");
      p.println("Press any button to exit:");
      SCAN.nextLine();
    }
    
    World.build();
    
    Player player = new Player();
    p.println("Welcome to text adventure!");
    GameController.displayMinimap(player);


    //test configloader
    //ConfigLoader.readConfig();
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



//----end of the game----
    p.println("\nGAME OVER\n");

    p.println("Press any button to exit:");
    SCAN.nextLine();
  }

  //input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player, String type) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
    //check if world input
    try{
      if(type.equals("world")){
        switch(inputArr[0].toLowerCase()){
          case "move":
            return GameController.translate(inputArr, player);
          case "grab_item":
            return GameController.grabItem(player);
          default:
        }
      //check if combat input
      } else if (type.equals("combat")){
        switch(inputArr[0].toLowerCase()){
          case "fight":
            return GameController.combat(player);
          case "run":
            return GameController.run(player);
          default:
        }
      }
      //readoff normal inputs
      switch(inputArr[0].toLowerCase()){
        case "scan":
          return GameController.scan(inputArr, player);
        case "interact":
          return GameController.interact(inputArr, player);
        case "minimap": //always false
          return GameController.displayMinimap(player);
        case "stats": //always false
          return GameController.displayStats(player);
        case "help": //always false
          return GameController.getHelp();
        case "end_game":
          throw new InterruptedIOException();
        default:
          return false;
      }
    } catch(IndexOutOfBoundsException e){
      p.println(e.getMessage());
      return false;
    } catch(NumberFormatException e){
      p.println("Coordinate undefined "+e.getMessage());
      return false;
    } catch(MovementOutOfBoundsException e){
      p.println(e.getMessage());
      return false;
    } catch(MovementOutOfRangeException e){
      p.println(e.getMessage());
      return false;
    } catch(NotTraversibleException e){
      p.println(e.getMessage());
      return false;
    } catch(MissingParameterException e){
      p.println(e.getMessage());
      return false;
    } catch(NullGameObjectException e){
      p.println(e.getMessage());
      return false;
    }
    
  }

  
}