package textadventure.game;

import java.util.Random;

//WHAT TO DO:
//Task method if else that checks if you are in the correct room with correct item for the specific task
//Item's names and descriptions

//What I COULD DO:
//More NPCs

public class Game {
  private Parser parser;
  private Room currentRoom;
  private Room npcRoom;
  private Player player;
  private CLS cls_var;
  private Room cafeteria;
  private Room launchpad;
  private Room greenhouse;
  private Room reactor;
  private Room storage;
  private Room npcNextRoom; 
  private int alive = 1;
  
  public Game() {
      parser = new Parser();
      player = new Player();
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
      cafeteria = new Room( "cafeteria" , scafeteria , lcafeteria, 0);  
      launchpad = new Room( "launchpad", slaunchpad , llaunchpad, 1); 
      greenhouse = new Room( "greenhouse", sgreenhouse , lgreenhouse, 2);
      reactor = new Room( "reactor" , sreactor , lreactor, 3);
      storage = new Room( "storage" , sstorage , lstorage,4);
      
      cafeteria.setExit("launchpad", launchpad);
      cafeteria.setExit("greenhouse", greenhouse);
      cafeteria.setExit("reactor", reactor);
      cafeteria.setExit("storage", storage);
      
      storage.setExit("cafeteria",cafeteria);
      launchpad.setExit("cafeteria",cafeteria);
      greenhouse.setExit("cafeteria",cafeteria);
      reactor.setExit("cafeteria",cafeteria);
      
      Item note = new Item("note", "To do list: Clean storage, greenhouse, and cafeteria.");
      Item itemExample2 = new Item("name of item", "long description");
      Item itemExample3 = new Item("name of item", "long description");
      Item itemExample4 = new Item("name of item", "long description");
      
      player.setItem("note", note);
      storage.setItem("example", itemExample2);
      greenhouse.setItem("example", itemExample3);
      reactor.setItem("example", itemExample4);
      
      
      currentRoom = cafeteria;
      npcNextRoom = launchpad;
      npcNextRoom.setEnemy(false);
      try {
              //cls_var.main(); 
              }catch(Exception e) {
              System.out.println(e); 
      }
      printInformation();
      play();
  }
  
  public void play() {
      while(true && alive == 1) {            
          Command command = parser.getCommand(); 
          try {
              //cls_var.main(); 
              }catch(Exception e) {
              System.out.println(e); 
          }
          processCommand(command);
          if(alive > 0) {
        	  printInformation();
          }
           
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
          case"help" :
        	  help(command);
          break;
          case"task" :
        	  task(command);
          break;
      }
  }
  
  public void task(Command command) {
	  // if currentRoom == "whateverRoomName" && player.getItem("note") != null 
	  // // do something
	  // // Item newItem = create an item 
	  // // player.setItem("this key", newItem)
	  
	  
	  
	  
	  if(currentRoom.equals(npcRoom)) {
		  System.out.println("You have died");
		  alive = 0;

		  
	  }
  }
  
  public void help(Command command) {
	  if(!command.hasSecondWord()){
          System.out.println("Possible commands are: go, grab, drop, inspect, task, and help");
          System.out.println("Input help (command) to recieve more specific help");
          return;
      }
	  else {
		 switch(command.getSecondWord()) {
	  	case"go":
	  		System.out.println("Input 'go' and one of the exits to go to that room");
	   break;
	  	case"grab":
	  		System.out.println("Input 'grab' and one of the items in the room to grap the item ");
      break;
	  	case"drop":
	  		System.out.println("Input 'drop' and one of the items in your inventory to drop the item in the room");
      break;
	  	case"inspect" :
	  		System.out.println("Input 'inspect' and an item/room to get additional information about it");
      break;
	  	case"task" :
	  		System.out.println("lmao");
	  break;
		}
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
	  // check if there is line()
	  
	
      String direction = command.getSecondWord();
      Room nextRoom = currentRoom.getExit(direction);
      Room[] roomList = {cafeteria, launchpad, reactor, greenhouse, storage};
      Random rand = new Random();
      int n = rand.nextInt(2);
      
       npcNextRoom =  roomList[n];
      if(!command.hasSecondWord()){
          System.out.println("Where do you want to go?");
          return;
      }
      else if(nextRoom == null) {
          System.out.println("You can't go there");
          return;
      }
      else{
    	  npcNextRoom.setEnemy(false);
          currentRoom = nextRoom;
          npcRoom = npcNextRoom;
          npcRoom.setEnemy(true);
                   
          if(currentRoom.equals(npcRoom) ){        
              System.out.println("There is someone in this room"); 
          }
          else{
              System.out.println("There is no one else in this room");    
          }
      }
  }
  
  private String scafeteria =  "It's a typical cafeteria";
  private String lcafeteria = "Upon further inspection, there are trash on the tables that could be cleaned up.";
  private String sreactor = "You enter the reactor.";
  private String lreactor = "Upon further inspection, there are some wires that could be fixed in the fuse box";
  private String sgreenhouse = "You enter the greenhouse";
  private String lgreenhouse = "Upon further inspection, there are trash bags next that could be thrown in the trash chute next to them";
  private String slaunchpad= "You enter the launch pad";
  private String llaunchpad = "There is a rocket to leave the facility, but you need the launch password";
  private String sstorage = "You enter the storage room";
  private String lstorage = "Upon further inspection, there is a messy room that could be organized";
  
}
