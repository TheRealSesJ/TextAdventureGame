package Scenes;

public class Lake extends Scene{
  public Lake(){
    super("lake");
    this.traversible = false;
  }

  public String getIcon(){ return "~"; }
  
}