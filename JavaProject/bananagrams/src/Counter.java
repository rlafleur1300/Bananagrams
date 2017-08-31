package bananagrams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class Counter{
    public HashMap<Character, Integer> counts;

    /**
     * Construtor for Counter Class
     *
     * @param str Character array of word to represent as Count object
     */
    Counter(char[] str){
        counts = new HashMap<>();
        if (str == null)
            return;

        for (char chr : str){
            if (!counts.containsKey(chr)) // if character is not in hash map, # of occurrences = 1
                counts.put(chr, 1);
            else // if it is in the hash map , increment
                counts.put(chr, counts.get(chr)+1);
        }
    }

    /**
     * Construtor for Counter Class
     *
     * @param vec Vector of letters to represent as Count object
     */
    Counter(Vector<Character> vec) {
        counts = new HashMap<>();
        if (vec == null)
            return;

        for (char chr : vec) {
            if (!counts.containsKey(chr)) // if character is not in hash map, # of occurrences = 1
                counts.put(chr, 1);
            else // if it is in the hash map , increment
                counts.put(chr, counts.get(chr) + 1);
        }
    }

    /**
     *  Constructor for Counter Classs
     *
     *  @param other HashMap to be deep copied into Count Object
     */
    Counter(HashMap<Character, Integer> other){
        counts = new HashMap<>();
        Iterator it = other.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
            counts.put(pair.getKey(), pair.getValue());
        }
    }

    /**
     * Subtract off counts from input Counter object and return new Counter object
     *
     * @param other - Other Counter object to subtract counts by
     *
     * @return New Counter object contaning difference between 2 Counter objects
     *
     * NOTE: returns null if any values in difference would be negative
     */
    public Counter subtract(Counter other){
        Counter diff = new Counter(this.counts);
        Iterator it = other.counts.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
            char chr = pair.getKey();
            Integer val = counts.get(chr);
            int otherVal = pair.getValue();
            if (val == null) { // this instance does not have that letter
                diff.counts.put(chr, -otherVal);
            }
            else { // else diff
                diff.counts.put(chr, val - otherVal);
            }
        }

        return diff;
    }

    /**
     * Check if all counts are non-negative
     *
     * @return True if all are non-negative, False otherwise
     */
    public boolean checkAllNonNeg(){
        Iterator it = counts.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
            if (pair.getValue() < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if all counts are non-positive
     *
     * @return True if all are non-positive, False otherwise
     */
    public boolean checkAllNonPos(){
        Iterator it = counts.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
            if (pair.getValue() > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if all counts are zero
     *
     * @return True if all are zero, False otherwise
     */
    public boolean checkAllZero(){
        Iterator it = counts.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
            if (pair.getValue() != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if passed in Counter has all required letters to form word
     *
     * @param toForm - Available letters to use
     *
     * @return True if can form, false otherwise
     */
    public boolean canForm(Counter toForm){
        Counter diff = subtract(toForm);
//        System.out.println("Here's what diff in canForm() looks like");
//        diff.print();
        return diff.checkAllNonNeg();
    }

    /* Debugging Functions */

    /**
     * Prints out the counts of each letter
     */
     public void print(){
         Iterator it = counts.entrySet().iterator();

         while(it.hasNext()){
             Map.Entry<Character, Integer> pair = (Map.Entry)it.next();
             char chr = pair.getKey();
             int val = pair.getValue();
             System.out.printf("%c: %d\n", chr, val);
         }
     }
}
