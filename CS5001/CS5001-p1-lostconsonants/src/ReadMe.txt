This program accepts two command line arguments (and exactly two). The first argument is a dictionary, whereas the second is a sentence.
I first split the words up with a space as a delimiter. Then, I check if the standalone words are in the dictionary already without removal.
If they are in the dictionary, I flag an index in a parallel boolean array to the words array. I then remove consonant by consonant checking 
after each consonant if the word is in the dictionary and printing the rest of the words that were flagged as in the dictionary.