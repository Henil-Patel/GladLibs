import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> wordList;
    private ArrayList<String> cacheList;
    private ArrayList<String> categoriesUsed;
    private int countReplace = 0;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "datalong";
    
    public GladLibMap(){
        myRandom = new Random();
        cacheList = new ArrayList<String>();
        wordList = new HashMap<String, ArrayList<String>>();
        categoriesUsed = new ArrayList<String>();
        initializeFromSource(dataSourceDirectory);
    }
    
    public GladLibMap(String source){
        myRandom = new Random();
        cacheList = new ArrayList<String>();
        categoriesUsed = new ArrayList<String>();
        initializeFromSource(source);
    }
    
    private void initializeFromSource(String source) {
        String[] sourceList = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        for (String name : sourceList){
            ArrayList<String> list = readIt(source + "/" + name + ".txt");
            wordList.put(name, list);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {

        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (!categoriesUsed.contains(label)){
            categoriesUsed.add(label);
        }
        return randomFrom(wordList.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        
        while (cacheList.contains(sub)){
            sub = getSubstitute(w.substring(first+1,last));
        }
        if (!cacheList.contains(sub)){
            cacheList.add(sub);
        }
        String word = prefix + sub + suffix;
        countReplace = countReplace + 1;
        return word;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public int totalWordsInMap(){
        int totalCount = 0;
        for (String word: wordList.keySet()){
            ArrayList<String> resourceContainer = wordList.get(word);
            //System.out.println(resourceContainer);
            totalCount = totalCount + resourceContainer.size();
        }
        return totalCount;
    }
    
    public int totalWordsInUsedCategories(){
        int totalCount = 0;
        for (int i = 0; i < categoriesUsed.size(); i++){
            String word = categoriesUsed.get(i);
            //System.out.println("Label: (" + word + ") has: " + wordList.get(word).size());
            totalCount = totalCount + wordList.get(word).size();
        }
        return totalCount;
    }
    
    
    public void makeStory(){
        System.out.println("\n");
        cacheList.clear();
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\n" + countReplace + " words were replaced");
        int totalInMap = totalWordsInMap();
        System.out.println("Total words: " + totalInMap);
        System.out.println("Categories used: " + categoriesUsed);
        int totalUsed = totalWordsInUsedCategories();
        System.out.println("Total labels actually used: " + totalUsed);
        
    }
    


}
