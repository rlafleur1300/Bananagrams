package bananagrams;

import java.util.Vector;

public class BoardWords{
    public Vector<String> words;
    public Vector<Integer> rows;
    public Vector<Integer> cols;
    public Vector<Integer> dirs;

    /**
     * Constructor for AllWords class
     */
     public BoardWords(){
         words = new Vector<>();
         rows = new Vector<>();
         cols = new Vector<>();
         dirs = new Vector<>();
     }

     /* Debugging Functions! */

    /**
     * Prints out all words found on the board
     */
    public void print(){
        for (String word: words){
            System.out.printf("Word found!: %s\n", word);
        }
    }
}
