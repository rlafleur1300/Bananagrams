import random


class WordBank():
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