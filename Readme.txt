Project 4 - Hash Table SpellChecker
Author - Louis Warner
7/29/2015

**********************
*     Java Files     *
**********************

There are two files necessary for running this program:
HashEntry.java
HashTable.java

Store these files in the same folder and compile.
The main program for the project lies in the HashTable.java file.
To run the program, run HashTable.java.

**********************
*     Interface      *
**********************

This program begins by asking the user for two input files.

The first specified input file should be the dictionary file, stored in a
one word per line format in a .txt file. If this format is not followed,
the wrong strings will be stored in the hash table.

The second specified input file is a testing file. This is the file which
contains any text you wish to run through the spell checker. It is designed
to ignore punctuation at the beginning and end of a string, and it will delimit
words based on Java whitespace, as well as hyphens for checking compound words.

You will then be prompted for an output file. Again,
simply type in the filepath and file name you wish your output to
be stored in. If the file you specify already exists, you will be
asked to confirm or deny whether that file should be overwritten.

The output file will contain any words that were not found in the hash table,
followed by a statistical analysis of the hash table showing the number of words
in the dictionary, the number of words searched for, the number of mispelled words
returned, and the average amounts of both probes per word and probes per search.
The program will print a message to the screen once your output file has
successfully been generated.