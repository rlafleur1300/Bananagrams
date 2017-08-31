package bananagrams;

import bananagrams.Board;
import bananagrams.BoardWords;
import bananagrams.Counter;
import bananagrams.Heuristics;
import bananagrams.Search;
import bananagrams.Utils;
import bananagrams.WordBank;
import java.util.Arrays;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

class Main{
    public static void main(String[] args){
//        char[] initLtrs = new char[]{'a', 'p', 'p', 'e', 'l'};
//        char[] initLtrs = new char[]{'h', 'e', 'l', 'i', 'c', 'o', 'p', 't', 'e', 'r', 'r', 'a', 'n', 'g', 'e'};
//        Vector<Character> initLtrsVec = new Vector<>();
//        for (char ltr : initLtrs){
//            initLtrsVec.add(ltr);
//        }

        Board myBoard = new Board();
        WordBank wb = new WordBank(30, null);
        wb.printRemainingLetters();
        Search.solveBoard(myBoard, wb);
//        myBoard.wordsOnBoard().print();
        myBoard.printSubsquare(Utils.numLetters-10, Utils.numLetters-10, 25);

//        wb.printRemainingLetters();
//        myBoard.addWordToBoard("helicopter".toCharArray(), 0, 0, Utils.vert, wb);
//        wb.printRemainingLetters();
//        myBoard.addWordToBoard("hello".toCharArray(), 0, 0, Utils.horiz, wb);
//        wb.printRemainingLetters();
//        myBoard.addWordToBoard("orange".toCharArray(), 0, 4, Utils.vert, wb);
//        wb.printRemainingLetters();
//
//        System.out.printf("Board is valid: %b\n", myBoard.checkIfValid(wb));
//        System.out.printf("heuristic of helicopter: %d\n", Heuristics.getHeuristicVal("helicopter".toCharArray()));
//        System.out.printf("heuristic of hello: %d\n", Heuristics.getHeuristicVal("hello".toCharArray()));
//        System.out.printf("heuristic of orange: %d\n", Heuristics.getHeuristicVal("orange".toCharArray()));
//
//        System.out.println("Initial characters: " + Arrays.toString(wb.remainingLetters.toArray()));
//
//        myBoard.printSubsquare(0, 0, 10);
//        myBoard.wordsOnBoard().print();
    }
}
