from collections import Counter
import copy
import pdb

import bananagrams_board
import read_dictionary
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


def scrabble(word):
    word_count = Counter(word)

    ret = 0
    for letter in word_count:
        ret += word_count[letter] * word_bank.letter_weights[letter.upper()]

    return ret


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
    possible_words.sort(lambda x, y: cmp(scrabble(y[0]), scrabble(x[0])))
    prev_letters = copy.deepcopy(remaining_letters)
    for word_info in possible_words:
        new_loc = compute_loc_new_word(word_info[0], word_info[1], word_info[2], word_info[3])
        print remaining_letters
        print word_info[0]
        ok, pass_letters = curr_board.add_new_word(word_info[0], list(new_loc), word_info[3], remaining_letters)
        if (not curr_board.check_if_valid()) or (not ok):
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


dictionary = read_dictionary.read_dictionary("Dictionaries/umich_dictionary_anagram.txt")
master_wb = word_bank.WordBank()
curr_board = bananagrams_board.BananaGramsBoard(dictionary)
all_letters = "".join(sorted(master_wb.get_n_letters(35)))
curr_letters = copy.deepcopy(all_letters)
# curr_letters = "EGILMNQRRRTTUWX"

## DO IT
print curr_letters
initial_possible_words = trim_dictionary(dictionary, curr_letters)
scrabble_words = copy.deepcopy(initial_possible_words)
scrabble_words.sort(lambda x, y: cmp(scrabble(y), scrabble(x)))
print scrabble_words[0]
print "Number of workable words:", len(initial_possible_words)

for base_word in scrabble_words:
    ok, curr_letters = curr_board.add_new_word(scrabble_words[0], [143, (288 - len(scrabble_words[0])) / 2], 'h',
                                           curr_letters)
    complete, curr_board = complete_board(scrabble_words, curr_board, curr_letters)
    if complete:
        curr_board.print_board_subsquare([130, 130], 25)
        break

# # Debugging - Confirm that check_if_valid works -- it does
# print curr_board.load_board_from_file("TestBoards/test1.txt")
# curr_board.print_board_subsquare((0, 0), 20)
#
# print curr_board.load_board_from_file("TestBoards/test2.txt")
# curr_board.print_board_subsquare((0,0), 20)
#
# print curr_board.load_board_from_file("TestBoards/test3.txt")
# curr_board.print_board_subsquare((0, 0), 20)
# print curr_board.check_if_valid()
#
# curr_board.load_board_from_file("TestBoards/test4.txt")
# curr_board.print_board_subsquare((0, 0), 20)
# print curr_board.add_new_word("duck", [0, 0], 'h', "UK")
# curr_board.print_board_subsquare((0, 0), 20)
#
# curr_board.load_board_from_file("TestBoards/test5.txt")
# curr_board.print_board_subsquare((0, 0), 20)
# print curr_board.add_new_word("duck", [0, 0], 'h', "UK")
# curr_board.print_board_subsquare((0, 0), 20)