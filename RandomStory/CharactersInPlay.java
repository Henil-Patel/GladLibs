import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;
import java.util.*;
public class CharactersInPlay
{
    private ArrayList<String> characterNames;
    private ArrayList<Integer> characterCounts;
    
    public CharactersInPlay(){
        characterNames = new ArrayList<String>();
        characterCounts = new ArrayList<Integer>();
    }
    
    public void update(String person){
        int index = characterNames.indexOf(person);
        if (index == -1){
            characterNames.add(person);
            characterCounts.add(1);
        }
        else{
            int count = characterCounts.get(index);
            characterCounts.set(index, count + 1);
        }
    }
    
    public void findAllCharacters(){
        FileResource fr = new FileResource();
        String person = new String();
        for (String line: fr.lines()){
            int periodIdx = line.indexOf(".");
            if (periodIdx != -1){
                person = line.substring(0, periodIdx);
                update(person);
            }
        }
    }

    public void charactersWithNumParts(int num1, int num2){
        for (int i = 0; i < characterCounts.size(); i++){
            if (characterCounts.get(i) >= num1 && characterCounts.get(i) <= num2){
                System.out.println(characterNames.get(i) + " " + characterCounts.get(i));
            }
        }
    }
    
    public String mainCharacter(){
        int maxVal = 0;
        String name = new String();
        for (int i = 0; i < characterCounts.size(); i++){
            if (maxVal == 0){
                maxVal = characterCounts.get(i);
                name = characterNames.get(i);
            }
            if (maxVal < characterCounts.get(i)){
                maxVal = characterCounts.get(i);
                name = characterNames.get(i);
            }
        }
        System.out.println("Max value is: " + maxVal);
        return name;
    }
    
    public void tester(){
        characterNames.clear();
        characterCounts.clear();
        findAllCharacters();
        
        for (int i = 0; i < characterNames.size(); i++){
            if (characterCounts.get(i) >= 2){
                System.out.println(characterNames.get(i) + " " + characterCounts.get(i));
            }
        }
        
        String mainChar = mainCharacter();
        System.out.println("Main Character is: " + mainChar + " with ");
        charactersWithNumParts(10, 15);
    }
}
