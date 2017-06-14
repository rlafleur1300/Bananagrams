import random

letter_weights = {'A': 1,
                  'B': 3,
                  'C': 3,
                  'D': 2,
                  'E': 1,
                  'F': 4,
                  'G': 2,
                  'H': 4,
                  'I': 1,
                  'J': 8,
                  'K': 5,
                  'L': 1,
                  'M': 3,
                  'N': 1,
                  'O': 1,
                  'P': 3,
                  'Q': 10,
                  'R': 1,
                  'S': 1,
                  'T': 1,
                  'U': 1,
                  'V': 4,
                  'W': 4,
                  'X': 8,
                  'Y': 4,
                  'Z': 10}


class WordBank:
    def __init__(self):
        self.letters = ['A'] * 13 + \
                       ['B'] * 3 + \
                       ['C'] * 3 + \
                       ['D'] * 6 + \
                       ['E'] * 18 + \
                       ['F'] * 3 + \
                       ['G'] * 4 + \
                       ['H'] * 3 + \
                       ['I'] * 12 + \
                       ['J'] * 2 + \
                       ['K'] * 2 + \
                       ['L'] * 5 + \
                       ['M'] * 3 + \
                       ['N'] * 8 + \
                       ['O'] * 11 + \
                       ['P'] * 3 + \
                       ['Q'] * 2 + \
                       ['R'] * 9 + \
                       ['S'] * 6 + \
                       ['T'] * 9 + \
                       ['U'] * 6 + \
                       ['V'] * 3 + \
                       ['W'] * 3 + \
                       ['X'] * 2 + \
                       ['Y'] * 3 + \
                       ['Z'] * 2

    def get_n_letters(self, n):
        if n > len(self.letters):
            return -1

        random.shuffle(self.letters)
        ret = self.letters[0:n]
        self.letters = self.letters[n:]

        return ret
