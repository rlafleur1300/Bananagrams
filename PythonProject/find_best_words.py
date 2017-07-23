from collections import Counter
import copy
import pdb
from sys import exit

import bananagrams_board
import heuristics
from read_dictionary import read_dictionary
import word_bank


def trim_dictionary(dictionary, letters):
    possible_words = []
    letters_count = Counter(letters)

    for key in dictionary:
        key_upper = key.upper()
        add_to_dict = True
        key_count = Counter(key_upper)
        if len(key) == 1:
            add_to_dict = False
        for letter in key_count:
            if (letter not in letters_count) or (key_count[letter] > letters_count[letter]):
                add_to_dict = False
                break
        if add_to_dict:
            for word in dictionary[key]:
                possible_words.append(word)

    return possible_words


def complete_board(word_list, prev_board, remaining_letters):
    if not prev_board.check_if_valid():
        return False, None
    if len(remaining_letters) == 0:
        return True, prev_board
    curr_board = prev_board.__deepcopy__()

    possible_words = []
    rem_letters_count = Counter(remaining_letters)
    for word in word_list:
        for board_word_info in curr_board.used_words:
            board_word = board_word_info[0]
            if word == board_word:
                continue
            add_to_list = True
            if board_word not in word:
                continue
            count_needed_for_word = Counter(word)
            count_needed_for_word.subtract(Counter(board_word))
            for letter in count_needed_for_word:
                if count_needed_for_word[letter] == 0:
                    continue
                if (letter.upper() not in rem_letters_count) or (count_needed_for_word[letter] > rem_letters_count[letter.upper()]):
                    add_to_list = False
                    break
            if add_to_list:
                possible_words.append((word, board_word_info[0], board_word_info[1], board_word_info[2]))

    # return possible_words
    possible_words.sort(lambda x, y: cmp(heuristics.scrabble(y[0], word_bank), heuristics.scrabble(x[0], word_bank)))
    prev_letters = copy.deepcopy(remaining_letters)
    for word_info in possible_words:
        new_loc = compute_loc_new_word(word_info[0], word_info[1], word_info[2], word_info[3])
        print remaining_letters
        print word_info[0]
        ok, pass_letters = curr_board.add_new_word(word_info[0], list(new_loc), word_info[3])
        if (not curr_board.check_if_valid()) or (not ok):
            curr_board.print_board_subsquare([135, 135], 20)
            curr_board = prev_board.__deepcopy__()
            remaining_letters = copy.deepcopy(prev_letters)
        else:
            ret = complete_board(word_list, curr_board, pass_letters)
            if not ret[0]:
                curr_board = prev_board.__deepcopy__()
                remaining_letters = copy.deepcopy(prev_letters)
            else:
                return True, ret[1]
    return False, None


def compute_loc_new_word(new_word, base_word, base_word_loc, direction):
    idx = new_word.find(base_word)

    if direction == 'h':
        return base_word_loc[0] - idx, base_word_loc[1]
    else:
        return base_word_loc[0], base_word_loc[1] - idx


# set up variables
dictionary = read_dictionary("Dictionaries/umich_dictionary_anagram.txt")
master_wb = word_bank.WordBank()
all_letters = "".join(sorted(master_wb.get_n_letters(15)))
curr_letters = copy.deepcopy(all_letters)
#curr_letters = "AADEEEHHIMNNTWW"
curr_board = bananagrams_board.BananaGramsBoard(dictionary, Counter(curr_letters))

# DO IT
print curr_letters
initial_possible_words = trim_dictionary(dictionary, curr_letters)
scrabble_words = copy.deepcopy(initial_possible_words)
scrabble_words.sort(lambda x, y: cmp(heuristics.scrabble(y, word_bank), heuristics.scrabble(x, word_bank)))
print scrabble_words[0]
print "Number of workable words:", len(initial_possible_words)

for base_word in scrabble_words:
    ok, curr_letters = curr_board.add_new_word(scrabble_words[0], [143, (288 - len(scrabble_words[0])) / 2], 'h')
    complete, curr_board = complete_board(scrabble_words, curr_board, curr_letters)
    if complete:
        curr_board.print_board_subsquare([130, 130], 35)
        print "~Complete Board Found~"
        exit()

print "~No Complete Board Found~"
