package fel.cvut.jordaji1.rpg_engine.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Save and Load class is responsible for saving and loading game boards into json file.
*/
public class SaveAndLoadMap {
    private static ArrayList<String> dataToRead;
    
    /**
    * Saves game that is done in editor or it can be also saved game during playing. Game is saved in project folder in src/main/resources/Maps.
    * 
    * @param rect takes ArrayList of data that is saved piece by piece
    */
    public static void save(ArrayList<String> gameData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/main/resources/Maps/gameMap.json"), gameData);
    }
    
    /**
    * Loads map from json file located in roject folder in src/main/resources/Maps.
    */
    public static void load(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File directory = new File("src/main/resources/Maps/gameMap.json");
            dataToRead = objectMapper.readValue(new File("src/main/resources/Maps/gameMap.json"), new TypeReference<ArrayList<String>>(){});
        } catch (IOException ex) {
            Logger.getLogger(SaveAndLoadMap.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

    public ArrayList<String> getDataToRead() {
        return dataToRead;
    }   
}
