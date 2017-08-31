package bananagrams;

import bananagrams.Counter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

class WordBank{
    public HashMap<String, String[]> allWords;
    public Vector<Character> remainingLetters;

    /**
     * Constructor for WordBank class
     *
     * @param n - Number of letters to begin with
     * @param ltrs - Which letters to begin with
     */
    WordBank(int n, Vector<Character> ltrs){
        allWords = new HashMap<>();
        Letters letters = new Letters();
        if (ltrs == null) {
            remainingLetters = letters.getInitialLettersRandomly(n);
        }
        else{
            remainingLetters = ltrs;
        }

        readInAnagrams("data/umich_dictionary_anagram.txt");
//        readInAnagrams("data/test.txt");
    }

    /**
     * Checks if word can be formed with remaining letters and letters from board
     *
     * @param word - Word under investigation
     *
     * @return Whether or not word could be created with given letters
     */
    private boolean canFormWord(char[] word, Counter avail){
        Counter queriedWordCount = new Counter(word);
        return avail.canForm(queriedWordCount);
    }

    /**
     * Function for reading in lists of anagrams created from dictionary
     *
     * @param fileName - String containing the name of the file to read from
     */
    private void readInAnagrams(String fileName){
        String line = null;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            // <sorted anagram> : <space separated words>
            Counter avail = new Counter(remainingLetters);
            while((line = br.readLine()) != null) {
                // first, split on ': '
                String[] anagramAndList = line.split(": ");
                if (!canFormWord(anagramAndList[0].toCharArray(), avail))
                    continue; // if can't form it, don't bother adding it
                // second, split on ' '
                String[] wordList = anagramAndList[1].split(" ");
                allWords.put(anagramAndList[0], wordList);
            }
            br.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to find file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Checks whether a word is in the dictionary or not
     *
     * @param word - Word under query
     *
     * @return Whether or not the word is valid
     */
    public boolean checkIfWordValid(char[] word){
        // deal with deep copy issues
        char[] copy = Arrays.copyOf(word, word.length);
        Arrays.sort(copy);

        // check if the anagram exists, then check if word itself exists
        String[] result = allWords.get(new String(copy));
        if (result != null){
            return Arrays.asList(result).contains(new String(word));
        }
        else{
            return false;
        }
    }


    /**
     * Member class for generating the letters to play with
     */
    private class Letters{
        private Vector<Character> unusedLetters;

        /**
         * Constructor for letters class
         */
        Letters(){
            unusedLetters = new Vector<>();

            for (int i = 0; i < 13; i++)
                unusedLetters.add('a');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('b');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('c');
            for (int i = 0; i < 6; i++)
                unusedLetters.add('d');
            for (int i = 0; i < 18; i++)
                unusedLetters.add('e');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('f');
            for (int i = 0; i < 4; i++)
                unusedLetters.add('g');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('h');
            for (int i = 0; i < 12; i++)
                unusedLetters.add('i');
            for (int i = 0; i < 2; i++)
                unusedLetters.add('j');
            for (int i = 0; i < 2; i++)
                unusedLetters.add('k');
            for (int i = 0; i < 5; i++)
                unusedLetters.add('l');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('m');
            for (int i = 0; i < 8; i++)
                unusedLetters.add('n');
            for (int i = 0; i < 11; i++)
                unusedLetters.add('o');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('p');
            for (int i = 0; i < 2; i++)
                unusedLetters.add('q');
            for (int i = 0; i < 9; i++)
                unusedLetters.add('r');
            for (int i = 0; i < 6; i++)
                unusedLetters.add('s');
            for (int i = 0; i < 9; i++)
                unusedLetters.add('t');
            for (int i = 0; i < 6; i++)
                unusedLetters.add('u');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('v');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('w');
            for (int i = 0; i < 2; i++)
                unusedLetters.add('x');
            for (int i = 0; i < 3; i++)
                unusedLetters.add('y');
            for (int i = 0; i < 2; i++)
                unusedLetters.add('z');
            }

        /**
         * Pulls out n letters randomly with which to solve the bananagrams puzzle
         *
         * @param n - number of letters to getWord
         *
         * @return N random letters in a character Arrays
         *
         * NOTE: This function is used for testing. In the app, the user will be
         *  able to choose between random (just for fun) and to input letters
         */
        public Vector<Character> getInitialLettersRandomly(int n){
            Collections.shuffle(unusedLetters);
            Vector<Character> ret = new Vector<>();
            for (int i = 0; i < n; i++){
                ret.add(unusedLetters.firstElement());
                unusedLetters.remove(0);
            }
            return ret;
        }
    }

    /* Debugging Functions */

    /**
     * Prints remaining letters out
     */
    public void printRemainingLetters(){
        System.out.print("Remaining letters are: ");
        for (char ltr : remainingLetters){
            System.out.printf("%c, ", ltr);
        }
        System.out.println("");
    }
}
