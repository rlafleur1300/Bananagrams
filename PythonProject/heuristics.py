from collections import Counter

def scrabble(word, word_bank):
    word_count = Counter(word)

    ret = 0
    for letter in word_count:
        ret += word_count[letter] * word_bank.letter_weights[letter.upper()]

    return ret


def remaining_letters_heuristic(word, remaining_letters):
    word_count = Counter(word)
    letters_count = Counter(remaining_letters)

    ret = 0
    for letter in letters_count:
        if letter not in word_count:
            continue
        if word_count[letter.lower()] >= letters_count[letter]:
            sum += letters_count[letter]
        else:
            sum += word_count[letter.lower()]
    return ret
