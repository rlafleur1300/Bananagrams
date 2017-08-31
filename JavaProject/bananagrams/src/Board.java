package bananagrams;

import bananagrams.BoardWords;
import bananagrams.Counter;
import bananagrams.Utils;
import bananagrams.WordBank;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

class Board{
  private char[][] board;
  private Stack<Character> letters;
  private Stack<Integer> rows, cols;

  /**
   * Constructor for Bananagrams board object
   */
  Board(){
    // initialize game board
    board = new char[2*Utils.numLetters][2*Utils.numLetters];
    for (char[] row : board)
      Arrays.fill(row, Utils.emptyChar);

    // intiialize stacks for problem solving
    letters = new Stack<Character>();
    rows = new Stack<Integer>();
    cols = new Stack<Integer>();
  }

  /**
   * Gets word from board in desired directions
   *
   * @param r - Row coordinate of letter under inspection
   * @param c - Col coordinate of letter under inspection
   * @param dir - Direction under inspection
   *
   * @return The word retrieved from the board
   */
  public char[] getWord(int r, int c, int dir){
      StringBuilder builder = new StringBuilder();
      while (board[r][c] != Utils.emptyChar){
          builder.append(board[r][c]);
          switch (dir) {
              case Utils.horiz:
                c++;
                break;
              case Utils.vert:
                r++;
                break;
              default:
                System.out.println("Invalid direction of query!");
                return null;
          }
      }
      return builder.toString().toCharArray();
  }

  /**
   * Gets all words on the board and their positions/directions
   *
   * @return Populated BoardWords object
   */
  public BoardWords wordsOnBoard(){
      BoardWords bw = new BoardWords();
      Stack<Integer> holdR = new Stack<>(), holdC = new Stack<>();

      while(!rows.empty()){
          // preserve the stacks
          int r = rows.pop();
          int c = cols.pop();
          holdR.push(r);
          holdC.push(c);

          // if the word is the beginning, add to BoardWords object
          int valid = 0;
          int dirs = checkIfBeginning(r, c, true);
          if ((dirs & Utils.vert) > 0){
              bw.words.add(String.valueOf(getWord(r,c, Utils.vert)));
              bw.rows.add(r);
              bw.cols.add(c);
              bw.dirs.add(Utils.vert);
          }
          if ((dirs & Utils.horiz) > 0){
              bw.words.add(String.valueOf(getWord(r,c, Utils.horiz)));
              bw.rows.add(r);
              bw.cols.add(c);
              bw.dirs.add(Utils.horiz);
          }
      }

      // restore the stacks
      while (!holdR.empty()){
          rows.push(holdR.pop());
          cols.push(holdC.pop());
      }

      return bw;
  }

  /**
   * Check whether or not a character is the beginning of a word on the board
   *
   * @param r - Row coordinate of letter under inspection
   * @param c - Col coordinate of letter under inspection
   * @parma one - Whether or not one lettered words are allowed
   *
   * @return Which directions on the board the letter is the beginning of a wordList
   *    0 - Neither directions
   *    1 - Only vertically
   *    2 - Only horizontally
   *    3 - Both directions
   */
  public int checkIfBeginning(int r, int c, boolean one){
    int ret = 0;

    // check that square is not empty
    if (board[r][c] == Utils.emptyChar) {
        return ret;
    }

    // check corner case
    if (r == 0){
        if (one)
            ret |= Utils.vert;
        else if (board[r+1][c] != Utils.emptyChar)
            ret |= Utils.vert;
    }
    // check general case
    else if (board[r-1][c] == Utils.emptyChar) {
        if (one)
            ret |= Utils.vert;
        else if (board[r + 1][c] != Utils.emptyChar)
            ret |= Utils.vert;
    }

    // check corner case
    if (c == 0){
        if (one)
            ret |= Utils.horiz;
        else if(board[r][c+1] != Utils.emptyChar)
            ret |= Utils.horiz;
    }
    // check general case
    else if (board[r][c-1] == Utils.emptyChar) {
        if (one)
            ret |= Utils.horiz;
        else if (board[r][c + 1] != Utils.emptyChar)
            ret |= Utils.horiz;
    }

    return ret;
  }

  /**
   * Checks if the entire board is filled with only valid words or not
   *
   * @param wb - word bank for checking if new words are valid
   *
   * @return Whether or not the board is valid
   */
  public boolean checkIfValid(WordBank wb){
      Stack<Integer> holdR = new Stack<>(), holdC = new Stack<>();

      boolean ret = true;
      while(!rows.empty()){
          // preserve the stacks
          int r = rows.pop();
          int c = cols.pop();
          holdR.push(r);
          holdC.push(c);

          // if the word is the beginning, check if it is a valid word
          int valid = 0;
          int dirs = checkIfBeginning(r, c, false);
          if ((dirs & Utils.vert) > 0){
              if (wb.checkIfWordValid(getWord(r, c, Utils.vert)))
                valid |= Utils.vert;
              else
                ret = false;
          }
          if ((dirs & Utils.horiz) > 0){
              if (wb.checkIfWordValid(getWord(r, c, Utils.horiz)))
                valid |= Utils.horiz;
              else
                ret = false;
          }

          if (valid != dirs)
            ret = false;
      }

      // restore the stacks
      while (!holdR.empty()){
          rows.push(holdR.pop());
          cols.push(holdC.pop());
      }

      return ret;
  }

  /**
   * Attempts to add a word to the board at a desired location and orientation
   *
   * @param word - Word to be added to the board
   * @param r - Row coordinate marking the beginning place of the word
   * @param c - Col coordinate marking the beginning place of the word
   * @param dir - Direction word will be placed
   *    either Utils.vert (vertically) or Utils.horiz (horizontally)
   *
   * @return How many letters were added to the board
   */
  public int addWordToBoard(char[] word, int r, int c, int dir, WordBank wb){
//    System.out.printf("new word: %s\n", new String(word));
    Vector<Character> newLettersAdded = new Vector<>();
    boolean ok = true;

    if (dir == Utils.vert){
      for (int i = 0; i < word.length; i++){ // add new word vertically
        if (board[r+i][c] == Utils.emptyChar){
          board[r+i][c] = word[i];

//          if (!wb.remainingLetters.remove((Character)word[i])){ // could not find letter
//              ok = false;
//              break;
//          }
          letters.push(word[i]);
          rows.push(r+i);
          cols.push(c);
          newLettersAdded.add(word[i]);
        }
        else if (board[r+i][c] != word[i]){
          ok = false;
          break;
        }
      }
    }
    else if (dir == Utils.horiz){ // add new word horizontally
      for (int i = 0; i < word.length; i++){
        if (board[r][c+i] == Utils.emptyChar){
          board[r][c+i] = word[i];
//          if (!wb.remainingLetters.remove((Character)word[i])){ // could not find letter
//              ok = false;
//              break;
//          }
          letters.push(word[i]);
          rows.push(r);
          cols.push(c+i);
          newLettersAdded.add(word[i]);
        }
        else if (board[r][c+i] != word[i]){
          ok = false;
          break;
        }
      }
    }
    else{
      return 0;
    }

    if (ok) // don't waste time checking if it's not needed
        ok &= checkIfValid(wb);
    if (!ok){ //something bad happened, remove all the letters placed this turn
      for (int i = 0; i < newLettersAdded.size(); i++){
        char badLetter = letters.pop();
        int badR = rows.pop();
        int badC = cols.pop();
//        System.out.printf("badR = %d, badC = %d, badLetter =%c\n", badR, badC, badLetter);
//        wb.remainingLetters.add(badLetter);
        board[badR][badC] = Utils.emptyChar;
      }
      return 0;
    }
    for (Character ltr : newLettersAdded){
        wb.remainingLetters.remove(ltr);
    }

    return newLettersAdded.size();
  }

  /**
   * Removes the last word from the board
   *
   * @param numLetters Number of letters added to board for last word
   * @param wb WordBank object tracking game state
   */
  public void removeLastWord(int numLetters, WordBank wb){
      for (int i = 0; i < numLetters; i++){
          wb.remainingLetters.add(letters.pop());
          rows.pop();
          cols.pop();
      }
  }

  /* Debugging Functions */

  /**
   * Prints a subsquare region of the full board to the screen
   *
   * @param topLeftR - Row marking the top left of the square to print
   * @param topLeftC - Col marking the top left of the square to print
   * @param size - Length of the square to print
   */
  public void printSubsquare(int topLeftR, int topLeftC, int size){
    System.out.println(String.join("", Collections.nCopies(size*2 + 1, "-")));
    for (int r = topLeftR; r < topLeftR + size; r++){
      System.out.print("|");
      for (int c = topLeftC; c < topLeftC + size; c++)
        System.out.print(board[r][c] + "|");
      System.out.print("\n");
      System.out.println(String.join("", Collections.nCopies(size*2 + 1, "-")));
    }
  }
}
