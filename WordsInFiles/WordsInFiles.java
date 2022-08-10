import edu.duke.*;
import java.util.*;
import java.io.*;
public class WordsInFiles{
    // instance variables
    private HashMap<String, ArrayList<String>> wordsCache;

    // constructor
    public WordsInFiles(){
        wordsCache = new HashMap<String, ArrayList<String>>();
    }
    
    /**
     * Helpers
     */
    
    // hashmap builder; note File f is path to file
    private void addWordsFromFile(File f){
        FileResource fr = new FileResource(f);
        for (String word : fr.words()){
            String fileName = f.getName();
            if (wordsCache.containsKey(word)){
                ArrayList foundFiles = wordsCache.get(word);
                if (!foundFiles.contains(fileName)){
                    foundFiles.add(fileName);
                }
            }
            else if (!wordsCache.containsKey(word)){
                ArrayList<String> foundFiles = new ArrayList<String>();
                foundFiles.add(fileName);
                wordsCache.put(word, foundFiles);
            }
        }
    }
    
    // call to builder
    public void buildWordFileMap(){
        wordsCache.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            addWordsFromFile(f);
        }
        //System.out.println(wordsCache);
    }
    
    // More descriptor methods
    public int maxNumber(){
        int maxVal = 0;
        for (String word: wordsCache.keySet()){
            ArrayList<String> fileNames = wordsCache.get(word);
            if (maxVal == 0){
                maxVal = fileNames.size();
            }
            else if (maxVal < fileNames.size()){
                maxVal = fileNames.size();
            }
        }
        return maxVal;
    }
    
    //...even more descriptor methods
    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> wordsByNum = new ArrayList<String>();
        for (String word: wordsCache.keySet()){
            ArrayList<String> fileList = wordsCache.get(word);
            int numOfFiles = fileList.size();
            if (numOfFiles == number){
                wordsByNum.add(word);
            }
        }
        return wordsByNum;
    }
    
    // even more descriptor methods
    public void printFilesIn(String word){
        for (String key : wordsCache.keySet()){
            if (key.equals(word)){
                ArrayList<String> files = wordsCache.get(word);
                for (int i = 0; i < files.size(); i++){
                    System.out.println(files.get(i));
                }
                break;
            }
        }
    }
    /**
     * Testers
     */
    public void testMaxNumber(){
        buildWordFileMap();
        int maxVal = maxNumber();
        System.out.println(maxVal);
    }
    
    public void testWordsInNumFile(){
        buildWordFileMap();
        ArrayList<String> wordsList = wordsInNumFiles(7);
        //System.out.println(wordsList);
        System.out.println(wordsList.size());
    }
    
    public void testPrintFilesIn(){
        buildWordFileMap();
        printFilesIn("laid");
    }
    
    public void testAll(){
        buildWordFileMap();
        int maxVal = maxNumber();
        ArrayList<String> wordList = wordsInNumFiles(maxVal);
        for (int i = 0; i < wordList.size(); i++){
            String word = wordList.get(i);
            //System.out.println(word);
            printFilesIn(word);
        }
        //System.out.println(wordsCache);
    }
    
}
