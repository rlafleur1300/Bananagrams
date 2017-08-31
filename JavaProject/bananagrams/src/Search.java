package bananagrams;

import bananagrams.BoardWords;
import bananagrams.Board;
import bananagrams.Counter;
import bananagrams.Heuristics;
import bananagrams.Utils;
import bananagrams.WordBank;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

class Search{
    /**
     * This does all the heavy chugging. It uses the search algorithm and
     *  backtracking to find a solution that works for the entire board
     *
     * @param board - Instance of board to solve on
     * @param wb - Instance of word bank to use for processing
     *
     * @return True if the board was fully solved
     */
    public static boolean solveBoard(Board board, WordBank wb){
        if (wb.remainingLetters.size() == 0)
            return true;
        BoardWords bw = new BoardWords();

        Counter remLettersCount = new Counter(wb.remainingLetters);
        bw = board.wordsOnBoard();

        if (bw.words.size() == 0) { // special case when board is empty
            bw.words.add("");
            bw.rows.add(Utils.numLetters-1);
            bw.cols.add(Utils.numLetters-1);
            bw.dirs.add(Utils.vert);
        }

        /*
         * Determine which words are able to be created from the dictionary using words that exist on the board and
         * at least one letter from the remainingLetters letters
         */
        Vector<char[]> viableWords = new Vector<>();
        /* Loop over each word in dictionary in word bank */
        Iterator it = wb.allWords.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String[]> pair = (Map.Entry)it.next();
            /* Loop over each word on board and remove these letters */
            for (String bWord : bw.words){
                /* Check if, with the remaining letters, the word can be formed (need to use at least one letter) */
                boolean canForm = canFormWithBoard(pair.getKey().toCharArray(), remLettersCount, bWord.toCharArray());
                /* If so, add it to the board */
                if (canForm){
                    for (String anagram : pair.getValue()){
                        viableWords.add(anagram.toCharArray());
                    }
                    break;
                }
            }
        }

        /* Now, apply heuristic and sort to choose best word */
        Collections.sort(viableWords, Comparator.comparingInt(Heuristics::getHeuristicVal).reversed());

        /* Add word to board and check if it works */
        for (char[] vWord : viableWords){
            /* Find places on the board to put it */
            for (int i = 0; i < bw.words.size(); i++){
                /* for current word, find a place where the bWord is a substring in vWord */
                String vWordAsString = String.valueOf(vWord);
                int idx = -1;
                while (true) {
                    int newLetters = 0;
                    idx = vWordAsString.indexOf(bw.words.get(i), idx+1);
                    /* no substring found! */
                    if (idx == -1){
                        break;
                    }
                    /* find where the word should be added to the board */
                    String bWord = bw.words.get(i);
                    if (bw.dirs.get(i) == Utils.vert){
                        if (canFormWithBoard(vWord, remLettersCount, bWord.toCharArray()))
                            newLetters = board.addWordToBoard(vWord, bw.rows.get(i) - idx, bw.cols.get(i), Utils.vert, wb);
                    }
                    else{
                        if (canFormWithBoard(vWord, remLettersCount, bWord.toCharArray()))
                            newLetters = board.addWordToBoard(vWord, bw.rows.get(i), bw.cols.get(i) - idx, Utils.horiz, wb);
                    }

                    /* Check if the word was successfully added to the board */
                    if (newLetters != 0){
                        boolean success = solveBoard(board, wb);
                        if (success){
                            return true;
                        }
                        else{
                            board.removeLastWord(newLetters, wb);
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Helper function to determine if word can be formed or not
     *
     * @param wordToForm Word desired to be formed
     * @param avail Available letters to form word
     * @param boardWord Word being used from board to help form wordToForm
     *
     * @return True if the word can be formed
     */
    private static boolean canFormWithBoard(char[] wordToForm, Counter avail, char[] boardWord){
        Counter w2fCount = new Counter(wordToForm);
        Counter bwCount = new Counter(boardWord);
        w2fCount = w2fCount.subtract(bwCount);
        if (w2fCount.checkAllZero())
            return false;
        return avail.canForm(w2fCount);
    }
}
