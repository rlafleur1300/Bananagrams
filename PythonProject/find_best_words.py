from collections import Counter
import pdb

import read_dictionary
import word_bank


def trim_dictionary(dictionary, letters):
    possible_words = []
    letters_count = Counter(letters)

    for key in dictionary:
        key_upper = key.upper()
        add_to_dict = True
        key_count = Counter(key_upper)
        for letter in key_count:
            if (letter not in letters_count) or (key_count[letter] > letters_count[letter]):
                add_to_dict = False
                break
        if add_to_dict:
            try:
                for word in dictionary[key]:
                    possible_words.append(word)
            except:
                pdb.set_trace()

    return possible_words

master_wb = word_bank.WordBank()
curr_letters = "".join(sorted(master_wb.get_n_letters(15)))
dictionary = read_dictionary.read_dictionary("Dictionaries/umich_dictionary_anagram.txt")

print curr_letters
possible_words = trim_dictionary(dictionary, curr_letters)
possible_words.sort(lambda x,y: cmp(len(y), len(x)))
print possible_words[0]