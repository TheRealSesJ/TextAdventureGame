package Scenes;

public class Mountains extends Scene{
  
  public Mountains(){
    super("mountains");
    this.escapable = false;
  }

  public String getIcon(){ return "^"; }
  
}