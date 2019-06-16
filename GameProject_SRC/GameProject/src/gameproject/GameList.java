package gameproject;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import java.io.*;

/**
 * Class that models a list of Game objects. 
 * <p>
 * Performs functions such as adding/deleting, searching, and reading/writing Games to file.
 * <p>
 * @author Sarah McKinnon
 * @version 1.0
 * File: GameList.java
 * Other files in this project:
 * Game.java
 * gameCSS.css
 * Main class: GameGUI_V2.java
 * Date started: March 24th, 2018
 * Course: Java 2 - PROG 24178
 */
public class GameList implements Serializable{
    
    private ArrayList<Game> list;
    private static int numberOfGames;
    
    /**
     * Default no-arg constructor that creates an empty ArrayList{@literal <Game>}
     */
    public GameList(){
        this.list = new ArrayList<>();
    }
    
    /**
     * @param gameList ArrayList{@literal <Game>}
     */
    public GameList(ArrayList<Game> gameList){
        this.list = gameList;
    }

    /**
     * Adds a new Game object to the list
     * @param newGame Game
     */
    public void addGame(Game newGame){
        list.add(newGame);
        numberOfGames++;
    }
    
    /**
     * Removes a Game object from the list
     * @param gameID String
     */
    public void deleteGame(String gameID){
        for (int i = 0; i < list.size(); i++){
            
            if (list.get(i).getGameID().equals(gameID)){
                list.remove(i);
                numberOfGames--;
            }
        }
    }
    
    /**
     * Returns the current number of Games in the list
     * @return int
     */
    public int getNumberOfGames(){
        return numberOfGames;
    }
    
    /**
     * Returns the current list of Games
     * @return ArrayList{@literal <Game>}
     */
    public ArrayList<Game> getGameList(){
        return list;
    }
    
    /**
     * Searches the titles of the list using the given keyword and
     * returns a list of results
     * @param keyword String
     * @return ArrayList{@literal <Game>}
     */
    public ArrayList<Game> searchByTitle(String keyword){
    
        ArrayList<Game> matchedList = new ArrayList<>();
        String title;
        for (int i = 0; i < list.size(); i++){
            
            title = list.get(i).getTitle().toLowerCase();
            if (title.contains(keyword.toLowerCase()))
                matchedList.add(list.get(i));
        }
        return matchedList;
    }
    
    /**
     * Reads Game data from binary file and returns the result
     * @return ArrayList{@literal <Game>} 
     * @serialData ArrayList{@literal <Game>}
     */
    public ArrayList<Game> readFromFile(){
    
        File binFile = new File("game.dat");
        
        try {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(binFile))) {
                this.list = (ArrayList<Game>)input.readObject();
            } 
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found!");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Class not found!");
        }
        catch(IOException ex){
            System.out.println("Error reading from file");
        }
        return list;
    }
    
    /**
     * Writes the current value of the list to a binary file
     * @param obsList ObservableList{@literal <Game>}
     * @serialData ArrayList{@literal <Game>}
     */
    public void writeToFile(ObservableList<Game> obsList){
        
        if(obsList.isEmpty() == false){
            list.clear();
            list.addAll(obsList);
            try {
                try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
                    output.writeObject(list);    
                }
            }
            catch(FileNotFoundException ex){
                System.out.println("File not found!");
            }
            catch(IOException ex){
                System.out.println("Error writing to file.");
            }
        }
    }
    
    /**
     * Returns a list of Game titles in the current list
     * @return String
     */
    @Override
    public String toString(){
        
        String output = "";
        for(int i = 0; i < list.size(); i++){
            output += list.get(i).getTitle() + "\n";
        }
        String replace = output.replace(",", "").replace("]", "").replace("[", "");
        return replace;
    }
}
