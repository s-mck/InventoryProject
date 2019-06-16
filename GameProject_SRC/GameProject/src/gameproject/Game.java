package gameproject;

import java.io.Serializable;

/** 
 * Base class used to create Game objects.
 * @author Sarah McKinnon
 * @version 1.0
 * File: Game.java
 * Other Files in this Project: 
 * GameList.java
 * gameCSS.css
 * Main class: GameGUI_V2.java
 * Date Started: March 24, 2018
 * Course: Java 2 - PROG 24178
 */
public class Game implements Serializable{
    
    private String gameID;
    private String title;
    private String genre;
    private String platform;
    private int year;
    private int achievements;
    private int plays;
    
    /**
     *
     */
    public Game(){}
    
    /**
     * The gameID of the object is automatically assigned
     * @param title String
     * @param genre String
     * @param platform String
     * @param year int
     * @param achievements int
     * @param plays int
     */
    public Game(String title, String genre, String platform, int year, int achievements, int plays){
        
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.year = year;
        this.achievements = achievements;
        this.plays = plays;
        
        // Set gameID automatically
        String newGameID = "";
        for (int i = 0; i < 3; i++){
            newGameID += title.toUpperCase().charAt(i);
        }
        newGameID += year;
        this.gameID = newGameID;  
    }
    
    /**
     * Returns the gameID value of the object
     * @return String
     */
    public String getGameID(){
        return gameID;
    }
    
    /**
     * Returns the title value of the object
     * @return String
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Sets the title value of the object
     * @param newTitle String
     */
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    
    /**
     * Returns the genre value of the object
     * @return String
     */
    public String getGenre(){
        return genre;
    }
    
    /**
     * Sets the genre value of the object
     * @param newGenre String
     */
    public void setGenre(String newGenre){
        this.genre = newGenre;
    }
    
    /**
     * Returns the platform value of the object
     * @return String
     */
    public String getPlatform(){
        return platform;
    }
    
    /**
     * Sets the platform value of the object
     * @param newPlatform String
     */
    public void setPlatform(String newPlatform){
        this.platform = newPlatform;
    }
    
    /**
     * Returns the year value of the object
     * @return int
     */
    public int getYear(){
        return year;
    }
    
    /**
     * Sets the year value of the object
     * @param newYear int
     */
    public void setYear(int newYear){
            this.year = newYear;
    }
    
    /**
     * Returns the achievements value of the object
     * @return int
     */
    public int getAchievements(){
        return achievements;
    }
    
    /** 
     * Sets the achievements value of the object
     * @param newAchievements int
     */
    public void setAchievements(int newAchievements){
            this.achievements = newAchievements;
    }
    
    /**
     * Returns the playthroughs value of the object
     * @return int
     */
    public int getPlays(){
        return plays;
    }
    
    /**
     * Sets the playthroughs value of the object
     * @param newPlays int
     */
    public void setPlays(int newPlays){
            this.plays = newPlays;
    }
    
    /**
     * Returns a formatted list of all field values for the object
     * @return String
     */
    @Override
    public String toString(){
    
        String output = "Title: " + this.title + "\n";
        output += "Game ID: " + this.gameID + "\n";
        output += "Genre: " + this.genre + "\n";
        output += "Platform: " + this.platform + "\n";
        output += "Year Released: " + this.year + "\n";
        output += "Achievements: " + this.achievements + "\n";
        output += "Playthroughs: " + this.plays + "\n";
        return output + "\n";
    }
}
