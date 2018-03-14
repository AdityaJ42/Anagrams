/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    ArrayList<String> word_list = new ArrayList<String>();
    HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    HashSet<String> wordSet = new HashSet<String>();
    private Random random = new Random();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            word_list.add(word);
            wordSet.add(word);
            if(lettersToWord.containsKey(sortLetters(word))){
                ArrayList<String> anagrams = lettersToWord.get(sortLetters(word));
                anagrams.add(word);
                lettersToWord.put(sortLetters(word),anagrams);
            }
            else
            {
                ArrayList<String> anagrams = new ArrayList<String>();
                anagrams.add(word);
                lettersToWord.put(sortLetters(word),anagrams);
            }
        }

    }

    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word))
            if(!word.contains(base))
                return true;
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedTargetWord = sortLetters(targetWord);
        for(int i=0;i<word_list.size();i++)
        {
            if(word_list.get(i).length()==sortedTargetWord.length())
            {
                if(sortedTargetWord.equalsIgnoreCase(sortLetters(word_list.get(i))))
                    result.add(targetWord);
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for(char letter='a';letter<='z';letter++){
            if(lettersToWord.containsKey(sortLetters(word+letter))){
                ArrayList<String> listAnagrams =lettersToWord.get(sortLetters(word+letter));
                for(int i=0;i<listAnagrams.size();i++){
                    if(isGoodWord(word,listAnagrams.get(i))){
                        result.add(listAnagrams.get(i));
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int selector;
        selector = random.nextInt(word_list.size());
        if(word_list.get(selector).length()<=5)
            return word_list.get(selector);
        else
            return pickGoodStarterWord();
    }
    
    public String sortLetters(String word){
        char arr[] = word.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
}
