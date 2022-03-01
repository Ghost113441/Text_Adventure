package textadventure.game;

import java.util.HashMap;
import java.util.Set;
public class Room{
    private String name;
    private String shortDescription;
    private String longDescription;
    private Player npc;
    private int ID;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> inventory;
    
    private boolean hasEnemy; 
    
    public Room(String name, String shortDescription, String longDescription, int ID) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.ID = ID;
        exits = new HashMap<>();
        inventory = new HashMap<>();
        
        hasEnemy = false; 
    }
    
    public Player getNpc() {
        return npc;             
    }
    
    public boolean getEnemy() {
    	return hasEnemy; 
    }
    
    public void setEnemy(boolean value) {
    	hasEnemy = value; 
    }
    
    public void removeNpc() {
        npc = null;
    }
        
    public String getName() {
        return name;
    }
    
    public int getID() {
        return ID;
    }
    
    public void setItem(String name, Item item) {
        inventory.put(name, item);
    }
    
    public Item getItem(String key) {
        return inventory.get(key);
    }
    
    public Item removeItem(String key) {
        return inventory.remove(key);
    }
    
    public String getShortDescription() {
        return shortDescription;
    }
    
    public String getLongDescription() {
        return longDescription;
    }
    
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    
    public Room getExit(String direction) {
        return exits.get(direction);    
    }
    
    public String getInventoryString() {
        String returnString = "Item(s) in room: ";
        
        Set<String> keys = inventory.keySet();
        for ( String item: keys) {
            returnString += " " + item;
        }
        return returnString;    
    }
    
    public String getExitString() {
        String returnString = "Exit(s) from the room: ";
        
        Set<String> keys = exits.keySet();
        for ( String exit: keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
}
