import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;
import java.util.*;

public class WordFrequencies
{
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;

    public WordFrequencies()
    {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public void findUnique()
    {
        if (myWords.size() != 0){
            myWords.clear();
        }
        if (myFreqs.size() != 0){
            myFreqs.clear();
        }
        
        FileResource fr = new FileResource();
        for (String word : fr.words()){
            word = word.toLowerCase();
            int index = myWords.indexOf(word);
            if (index == -1){
                myWords.add(word);
                myFreqs.add(1);
            }
            else{
                int value = myFreqs.get(index);
                myFreqs.set(index, value + 1);
            }
        }
    }
    
    public void tester(){
        findUnique();
        System.out.println("Number of unique words: " + myWords.size());
        /**
        for (int i = 0; i < myWords.size(); i++){
            System.out.println("Frequency: " + myFreqs.get(i) + ", Word: " + myWords.get(i)); 
        }
        */
        int maxIdx = findIndexOfMax();
        System.out.println("The word that occurs most often and its count are: " + myWords.get(maxIdx) + " " + myFreqs.get(maxIdx));
    }
    
    public int findIndexOfMax(){
        int maxVal = 0;
        int maxIdx = 0;
        for (int i = 0; i < myFreqs.size(); i++){
            if (maxVal == 0){
                maxVal = myFreqs.get(i);
                maxIdx = i;
            }
            if (maxVal < myFreqs.get(i)){
                maxVal = myFreqs.get(i);
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}
