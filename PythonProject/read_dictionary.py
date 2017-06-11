def read_dictionary(dictionary_file):
    ret = {}
    for line in open(dictionary_file, 'r'):
        key, anagrams = line.split(': ')
        ret[key] = anagrams.split()

    return ret