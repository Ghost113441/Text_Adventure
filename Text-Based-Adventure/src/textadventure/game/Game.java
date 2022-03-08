package textadventure.game;

import java.util.Random;

public class Game {
  private Parser parser;
  private Room currentRoom;
  private Room npcRoom1;
  private Room npcRoom2;
  private Room npcRoom3;
  private Player player;
  private Room cafeteria;
  private Room launchpad;
  private Room greenhouse;
  private Room reactor;
  private Room storage;
  private Room npcNextRoom1;
  private Room npcNextRoom2;
  private Room npcNextRoom3;
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
      cafeteria = new Room( "cafeteria" , scafeteria , lcafeteria);  
      launchpad = new Room( "launchpad", slaunchpad , llaunchpad); 
      greenhouse = new Room( "greenhouse", sgreenhouse , lgreenhouse);
      reactor = new Room( "reactor" , sreactor , lreactor);
      storage = new Room( "storage" , sstorage , lstorage);
      
      cafeteria.setExit("launchpad", launchpad);
      cafeteria.setExit("greenhouse", greenhouse);
      cafeteria.setExit("reactor", reactor);
      cafeteria.setExit("storage", storage);
      
      storage.setExit("cafeteria",cafeteria);
      launchpad.setExit("cafeteria",cafeteria);
      greenhouse.setExit("cafeteria",cafeteria);
      reactor.setExit("cafeteria",cafeteria);
      
      Item note = new Item("note", "To do list: Clean the cafeteria.");
      player.setItem("note", note);

      
      currentRoom = cafeteria;
      
      npcNextRoom1 = launchpad;
      npcNextRoom1.setEnemy(false);
      npcNextRoom2 = greenhouse;
      npcNextRoom2.setEnemy(false);
      npcNextRoom3 = reactor;
      npcNextRoom3.setEnemy(false);
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
	  if(currentRoom.equals(npcRoom1)) {
		  System.out.println("You have died");
		  alive = 0;
	  }
	  if(currentRoom == cafeteria && player.getItem("note") != null) {
		  System.out.println("After cleaning the trash on the table, you read the next task on the note.");
		  Item note2 = new Item("note2", "To do list: Clean the greenhouse.");
		  player.setItem("note2", note2);
		  player.removeItem("note");
	  } 
	  if(currentRoom == greenhouse && player.getItem("note2") != null) {
		  System.out.println("After putting the trashbags in the trash chute, you notice a keycard on the ground and pick it up. ");
		  Item keycard = new Item("keycard", "The card is labeled with the name 'storage'");
		  player.setItem("keycard", keycard);
		  player.removeItem("note2");
	  } 
	  if(currentRoom == storage && player.getItem("keycard") != null) {
		  System.out.println("When you enter the storage room, you notice a dead body in the room. While you were still in shock, the power went out. There is a murderer on the loose, you need to fix the power and get out of the area");
		  Item wrench = new Item("wrench", "This tool would be useful in repairing equipment");
		  player.setItem("wrench", wrench);
		  player.removeItem("keycard");
	  } 
	  if(currentRoom == reactor && player.getItem("wrench") != null) {
		  System.out.println("You use the wrench to fix the power. You head to your ship to escape");
		  Item keys = new Item("keys", "keys to your personal spaceship");
		  player.setItem("keys", keys);
		  player.removeItem("wrench");
	  } 
	  if(currentRoom == launchpad && player.getItem("keys") != null) {
		  player.removeItem("wrench");
		  alive = 0;
		  System.out.println("You escape the murderer in your ship and report it to the space police. You win.");
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
	  		System.out.println("Input task in the correct room with the correct item in your inventory to continue with the story");
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
      String direction = command.getSecondWord();
      Room nextRoom = currentRoom.getExit(direction);
      Room[] roomList = {cafeteria, launchpad, reactor, greenhouse, storage};
      Random rand1 = new Random();
      Random rand2 = new Random();
      Random rand3 = new Random();
      int npc1 = rand1.nextInt(5);
      int npc2 = rand2.nextInt(5);
      int npc3 = rand3.nextInt(5);
      npcNextRoom1 =  roomList[npc1];
      npcNextRoom2 = roomList[npc2];
      npcNextRoom2 = roomList[npc3];
      
      if(!command.hasSecondWord()){
          System.out.println("Where do you want to go?");
          return;
      }
      else if(nextRoom == null) {
          System.out.println("You can't go there");
          return;
      }
      else{
    	  npcNextRoom1.setEnemy(false);
    	  npcNextRoom2.setEnemy(false);
    	  npcNextRoom3.setEnemy(false);
          currentRoom = nextRoom;
          npcRoom1 = npcNextRoom1;
          npcRoom2 = npcNextRoom2; 
          npcRoom3 = npcNextRoom3; 
          npcRoom1.setEnemy(true);
          npcRoom2.setEnemy(true);
          npcRoom3.setEnemy(true);
          
          if(currentRoom.equals(npcRoom1) ){        
              System.out.println("Blue is in this room"); 
          }
          else if(currentRoom.equals(npcRoom2) ){        
              System.out.println("Red is in this room"); 
          }
          else if(currentRoom.equals(npcRoom3) ){        
              System.out.println("Green is in this room"); 
          }
          else if(currentRoom.equals(npcRoom1) && currentRoom.equals(npcRoom2)) {        
              System.out.println("Blue and Red are in this room"); 
          }
          else if(currentRoom.equals(npcRoom1) && currentRoom.equals(npcRoom3)) {        
              System.out.println("Blue and Green are in this room"); 
          }
          else if(currentRoom.equals(npcRoom2) && currentRoom.equals(npcRoom3)) {        
              System.out.println("Red and Green are in this room"); 
          }
          else if(currentRoom.equals(npcRoom1) && currentRoom.equals(npcRoom2) && currentRoom.equals(npcRoom3)) {        
              System.out.println("Blue, Red, and Green are in this room"); 
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
