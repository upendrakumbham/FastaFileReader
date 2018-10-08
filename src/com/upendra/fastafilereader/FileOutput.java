package com.upendra.fastafilereader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ukumbham on 01/10/2018.
 */
public class FileOutput {

    private List<String> sequences;
    private int inputFileIndex;

    public FileOutput(List<String> sequences, int inputFileIndex){
        this.sequences = sequences;
        this.inputFileIndex = inputFileIndex;
    }

    public Integer getSequenceCount(){return  this.sequences.size();}

    public int getInputFileIndex(){return this.inputFileIndex;}

    public List<String> getSequences(){
        return this.sequences;
    }

    public Map<Character, Integer> getCharCount(){
        String combinedString = String.join("", this.sequences);
        return getCharCount(combinedString);
    }


    private Map<Character, Integer> getCharCount(String inputString){
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        int charCount;
        for (int i=0; i<inputString.length(); i++){
            if(map.containsKey(inputString.charAt(i))){
                charCount = map.get(inputString.charAt(i));
                charCount ++;
                map.put(inputString.charAt(i),charCount);
            }else{
                map.put(inputString.charAt(i),1);
            }
        }
        return map;
    }
}
