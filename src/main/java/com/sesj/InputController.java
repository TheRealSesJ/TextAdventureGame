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
      
      //take turn
      try{
        while(!scanInput(SCAN.nextLine(), player)){
          p.println("please enter an action");
        }
        p.println("\nNext turn:\n");
      }
//----------------handles game end
      catch(InterruptedIOException e){
        playing = false;
        break;
      }
      

      //check for combat, if yes convert to *combat turns*
      while(World.getLocation(player).getEnemy()!=null){
        p.println("\nan enemy approaches...\n");
        p.println("enemy is: "+ World.getLocation(player).getEnemy().toString());
        p.println("Hp: "+World.getLocation(player).getEnemy().getHp());
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

//-----check for game over condition
      if(player.getHp()<=0){
        playing = false;
        break;
      }

    }
//----end of the game----
    p.println("\nGAME OVER\n");
    try{
      Thread.sleep(2000);
    }
    catch(InterruptedException e){}
  }

//input mappings for normal inputs, uses up one turn if a correct input is executed
  //methods here can return false in order to not advance the player turn, namely the help function
  public static boolean scanInput(String input, Player player) throws InterruptedIOException{
    String[] inputArr = input.split(" ");
    switch(inputArr[0].toLowerCase()){
      case "scan":
        return GameController.scan(inputArr, player);
      case "move":
        return GameController.translate(inputArr, player);
      case "interact":
        return GameController.interact(inputArr, player);
      case "minimap": //always false
        return GameController.displayMinimap(player);
      case "stats": //always false
        return GameController.displayStats(player);
      case "help": //always false
        return GameController.getHelp();
      case "grab_item":
        return GameController.grabItem(player);
      case "end_game":
        return GameController.endGame();
      default:
        return false;
    }
  }


//input mappings for combat, works in similar fashion to main commands
  public static boolean scanCombatInput(String input, Player player){
    String[] inputArr = input.split(" ");
    switch(inputArr[0].toLowerCase()){
      case "fight":
        return GameController.combat(player);
      case "scan": 
        return GameController.scan(inputArr, player);
      case "run":
        return GameController.run(player);
      case "minimap": //always false
        return GameController.displayMinimap(player);
      case "stats": //always false
        return GameController.displayStats(player);
      case "interact":
        return GameController.interact(inputArr, player);
      case "help": //always false
        return GameController.getHelp();
      default:
        return false;
    }
  }


//end game
public static boolean scanContinue(String input, Player player){
  String[] inputArr = input.split(" ");
  switch(inputArr[0].toLowerCase()){
    case "end_game":
      return true; //does not need to be evaluated
    default:
      return false; //game continues
  }
}
  

  
}