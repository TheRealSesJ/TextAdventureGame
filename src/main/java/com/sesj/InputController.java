//main method
package com.sesj;

//import com.sesj.Scenes.*;
import java.util.*;
import java.io.*;

public class InputController{
  
  public static final PrintStream p = new PrintStream(System.out);
  public static final Scanner SCAN = new Scanner(System.in);

//the game!!!
  
  public static void main(String[] args) {

    //playing boolean
    boolean playing = true;
    
    World.build();
    Player player = new Player();
    p.println("Welcome to text adventure!");
    GameController.displayMinimap(player);


    //game loop
    while(playing){
      
//----------------------------take turn
      try{
        while(!scanWorldInput(SCAN.nextLine(), player)){
          p.println("please enter an action");
        }
        p.println("\nNext turn:\n");

//----------------------check for combat, if yes convert to *combat turns*
        while(World.getLocation(player).getEnemy()!=null){
          p.println("\nAn enemy approaches...\n");
          p.println(World.getLocation(player).getEnemy().getStats());

          while(!scanCombatInput(SCAN.nextLine(), player)){
          p.println("please enter an action");
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


  public static boolean scanWorldInput(String input, Player player) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
    //normal inputs
    if(scanInput(input, player)) return true;
    //world inputs
    switch(inputArr[0].toLowerCase()){
      case "move":
        return GameController.translate(inputArr, player);
      case "grab_item":
        return GameController.grabItem(player);
      default:
        return false;
    }
  }

//input mappings for combat, works in similar fashion to main commands
  public static boolean scanCombatInput(String input, Player player) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
    //normal inputs
    if(scanInput(input, player)) return true;
    //combat inputs
    switch(inputArr[0].toLowerCase()){
      case "fight":
        return GameController.combat(player);
      case "run":
        return GameController.run(player);
      default:
        return false;
    }
  }


  //input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
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
        return GameController.endGame();
      default:
        return false;
    }
  }

  
}