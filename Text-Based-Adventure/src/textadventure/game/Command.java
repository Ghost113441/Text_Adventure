package textadventure.game;

public class Command {
    private String commandWord;
    private String secondWord;
    private String line;
    
    public Command(String commandWord, String secondWord, String Line) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.line = line;
    }
    
    public String getCommandWord() {
        return commandWord;
    }
    
    public String getSecondWord() {
        return secondWord;
    }

    public boolean hasSecondWord() {
        return (secondWord != null);
    }
    
    public String getLine() {
        return secondWord;
    }
    
    public boolean hasLine() {
        return (secondWord != null);
    }
}
