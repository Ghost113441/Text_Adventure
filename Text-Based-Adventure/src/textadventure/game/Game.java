package textadventure.game;

import java.util.Random;

//Inspect room does not work. Fix later
public class Game {
  private Parser parser;
  private Room currentRoom;
  private Room npcRoom;
  private Player player;
  private CLS cls_var;
  private Player impostor;
  private Room cafeteria;
  private Room launchpad;
  private Room greenhouse;
  private Room reactor;
  private Room storage;
  private boolean inRoom = false;
  
  public Game() {
      parser = new Parser();
      player = new Player();
      impostor = new Player();
  }
  
  public static void main(String[] args) {
      Game game = new Game();
      game.setupGame();
      game.play();
  }
  
  public void printInformation(){
      System.out.println(currentRoom.getShortDescription());
      System.out.println(currentRoom.getExitString());
      System.out.println(currentRoom.getInventoryString());
      System.out.println(player.getInventoryString());
  }
  
  public void setupGame() {
      Room cafeteria = new Room( "cafeteria" ,scafeteria , "Long description of the cafeteria", 0);  
      Room launchpad = new Room( "launchpad",slaunchpad , "Long description of the launchpad", 1); 
      Room greenhouse = new Room( "greenhouse", sgreenhouse , "Long description of the greenhouse", 2);
      Room reactor = new Room( "reactor" , sreactor , "Long description of the reactor", 3);
      Room storage = new Room( "storage" , sstorage , "Long description of the storage",4);
      
      cafeteria.setExit("launchpad", launchpad);
      cafeteria.setExit("greenhouse", greenhouse);
      cafeteria.setExit("reactor", reactor);
      cafeteria.setExit("storage", storage);
      
      storage.setExit("cafeteria",cafeteria);
      launchpad.setExit("cafeteria",cafeteria);
      greenhouse.setExit("cafeteria",cafeteria);
      reactor.setExit("cafeteria",cafeteria);
      
      Item keycard = new Item("name of item", "long description");
      Item itemExample2 = new Item("name of item", "long description");
      Item itemExample3 = new Item("name of item", "long description");
      Item itemExample4 = new Item("name of item", "long description");
      
      player.setItem("keycard", keycard);
      storage.setItem("example", itemExample2);
      greenhouse.setItem("example", itemExample3);
      reactor.setItem("example", itemExample4);
      
      
      currentRoom = cafeteria;
      npcRoom = launchpad;
      try {
              cls_var.main(); 
              }catch(Exception e) {
              System.out.println(e); 
      }
      printInformation();
      play();
  }
  
  public void play() {
      while(true) {            
          Command command = parser.getCommand(); 
          try {
              cls_var.main(); 
              }catch(Exception e) {
              System.out.println(e); 
          }
          processCommand(command);
          printInformation();   
      }
  }
  
  public void processCommand(Command command) {
      String commandWord = command.getCommandWord().toLowerCase();
      switch(commandWord) {
          case"go":
              goRoom(command);
          break;
          case"grab":
              grab(command);
          break;
          case"drop":
              drop(command);
          break;
          case"inspect" :
              inspect(command);
          break;
      }
  }

  public void inspect(Command command){
      String printString = "";
      String thingToInspect = command.getSecondWord();
      
      if(!command.hasSecondWord()){
          System.out.println("What do you want to inspect?");
          return;
      }
  
      if (thingToInspect.equals(currentRoom.getName())) {
          printString += "the room: " + currentRoom.getName() + "\n" + currentRoom.getLongDescription();
      }
      else if (currentRoom.getItem(thingToInspect) != null){
          printString += "the item: " + currentRoom.getItem(thingToInspect).getName() + "\n" + currentRoom.getItem(thingToInspect).getDescription();
      }
      else if (player.getItem(thingToInspect) != null){
          printString += "the item: " + player.getItem(thingToInspect).getName() + "\n" + player.getItem(thingToInspect).getDescription();   
      }
      else {
          printString +="\nYou can't look at that";
      }
      
      System.out.println(printString);
  }
  
  public void drop(Command command){
      String item = command.getSecondWord();
      Item itemToDrop = player.removeItem(item);
      if(!command.hasSecondWord()){
          System.out.println("What do you want to drop?");
          return;
      }
      else if(itemToDrop == null) {
          System.out.println("You can't drop that");
          return;
      }
      else{
          System.out.println("You dropped the " + item );
          currentRoom.setItem(item, itemToDrop);    
      }
  }
  
  public void grab(Command command) {
      String item = command.getSecondWord();
      Item itemToGrab = currentRoom.removeItem(item);
      if(!command.hasSecondWord()){
          System.out.println("What do you want to grab?");
          return;
      }
      else if(itemToGrab == null) {
          System.out.println("You can't grab that");
          return;
      }
      else{
          System.out.println("You grabbed the " + item );
          player.setItem(item, itemToGrab);    
      }
  }
  
  public void goRoom(Command command) {
      String direction = command.getSecondWord();
      Room nextRoom = currentRoom.getExit(direction);
      Room[] roomList = {cafeteria, launchpad, reactor, greenhouse, storage};
      Random rand = new Random();
      int n = rand.nextInt(5);
      Room npcNextRoom = roomList[n];
      if(!command.hasSecondWord()){
          System.out.println("Where do you want to go?");
          return;
      }
      else if(nextRoom == null) {
          System.out.println("You can't go there");
          return;
      }
      else{
          currentRoom = nextRoom;
          npcRoom = npcNextRoom;
          if(currentRoom == npcRoom){
              inRoom = true;   
              System.out.println("There is someone in this room"); 
          }
          else{
              inRoom = false;
              System.out.println("There is no one else in this room");    
          }
      }
  }
  
  private String scafeteria =  "You enter a typical cafeteria";
  private String lcafeteria;
  private String sreactor = "You enter the reactor.";
  private String lreactor;
  private String sgreenhouse = "You enter the greenhouse";
  private String lgreenhouse;
  private String slaunchpad= "You enter the launch pad";
  private String llaunchpad;
  private String sstorage = "You enter the storage room";
  private String lstorage;
  
}
