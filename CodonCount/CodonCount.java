import edu.duke.*;
import java.util.*;

public class CodonCount{
    // initialize hashmap
    private HashMap<String, Integer> codonCache;
    private ArrayList<String> multipleFreqCodons;
    
    // initialize constructor
    public CodonCount(){
        codonCache = new HashMap<String, Integer>();
        multipleFreqCodons = new ArrayList<String>();
    }

    /**
     * Helpers
     */
    // builder method for hashmap; start indicates 0, 1, or 2
    public void buildCodonMap(int start, String dna){
        codonCache.clear();
        int currIndex = start;
        while (true){
            int startChar = currIndex;
            int stopChar = currIndex + 3;
            if (stopChar > dna.length()){
                System.out.println(codonCache);
                break;
            }
            String codon = dna.substring(startChar, stopChar);
            if (codonCache.containsKey(codon)){
                //System.out.println("Codon already exists, updating value");
                codonCache.put(codon, codonCache.get(codon) + 1);
            }
            else{
                //System.out.println("Codon does not exist in the cache, adding...");
                codonCache.put(codon, 1);
            }
            currIndex = currIndex + 3;
        }
    }
    
    // get highest freq codon; can only be called after building the hashmap
    public String getMostCommonCodon(){
        int maxCount = 0;
        String maxCodon = "";
        multipleFreqCodons.clear();
        boolean multiple = false;
        for (String codon: codonCache.keySet()){
            int currCount = codonCache.get(codon);
            if (maxCount == 0){
                maxCount = currCount;
                maxCodon = codon;
            }
            else if (maxCount < currCount){
                maxCount = currCount;
                maxCodon = codon;
                multipleFreqCodons.clear();
                multipleFreqCodons.add(maxCodon);
                multiple = false;
            }
            
            else if (maxCount == currCount){
                maxCodon = codon;
                multipleFreqCodons.add(maxCodon);
                multiple = true;
            }
        }
        if (multiple){
            Random rn = new Random();
            int randomCodon = rn.nextInt(multipleFreqCodons.size());
            maxCodon = multipleFreqCodons.get(randomCodon);
        }
        System.out.println("Most occurring codon: " + maxCodon + " with count: " + codonCache.get(maxCodon));
        return maxCodon;
    }
    
    //print out all the codons
    public void printCodonCounts(int start, int end){
        System.out.println("Size: " + codonCache.size());
        for (String codon: codonCache.keySet()){
            int count = codonCache.get(codon);
            if (count >= start && count <= end){
                System.out.println("Codon: " + codon + " Count: " + count);
            }
        }
    }
    
    /**
     * Testers
     */
    public void testBuildCodonMap(){
        CodonCount cc = new CodonCount();
        String dna = "CGTTCAAGTTCAA";
        int startFrame = 2;
        cc.buildCodonMap(startFrame, dna);
    }
    
    public void testAll(){
        CodonCount cc = new CodonCount();
        //String dna = "CGTTCAAGTTCAA";
        FileResource fr = new FileResource();
        String dna = fr.asString();
        for (int i = 0; i < 3; i++){
            cc.buildCodonMap(i, dna);
            String maxCodon = cc.getMostCommonCodon();
            cc.printCodonCounts(0, 10);
            System.out.println("\n");
        }
    }
}