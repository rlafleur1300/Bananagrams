# hello, this is my create_dictionary.py file

import pickle

dictionary_file = "umich_dictionary.txt"

anagram_dict = {}
for line in open("Dictionaries/" + dictionary_file, 'r'):
    line_sorted = "".join(sorted(line.strip("\n")))
    if line_sorted in anagram_dict:
        anagram_dict[line_sorted].append(line.strip('\n'))
    else:
        anagram_dict[line_sorted] = [line.strip('\n')]

output_file = open("Dictionaries/" + dictionary_file[:-4] + "_anagram.txt", 'w')

for key in anagram_dict:
    # pickle.dump()
    output_file.write(key + ": ")
    for element in anagram_dict[key]:
        output_file.write(element + " ")
    output_file.write("\n")
