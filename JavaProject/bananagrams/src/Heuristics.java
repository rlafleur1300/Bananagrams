package bananagrams;

class Heuristics{
    /* Array containing values of scrabble letters */
    private static final int[] scrabbleWeights = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1,
        3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    /**
     * Heuristic evaluation of word by the sum of its scrabble letter values
     *
     * @param word - Word to evaluate heuristic for
     *
     * @return Heuristic value
     */
    private static int scrabble(char[] word){
        int val = 0;
        for (char ltr : word)
            val += scrabbleWeights[ltr-'a'];

        return val;
    }

    /**
     * By routing through this function, to change heuristics I only have to
     *  change which function is called here
     *
     * @param word - Word to evaluate heuristic for
     *
     * @return Heuristic value
     */
    public static int getHeuristicVal(char[] word){
        return scrabble(word);
    }
}
