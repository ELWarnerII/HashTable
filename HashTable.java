package SpellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * HashTable Class
 * 
 * The HashTable Class builds an array of HashEntries.
 * It contains a hashing method for inserting new entries,
 * looking up entries, and removing entries from the HashTable.
 * 
 * @author Louis Warner
 */
public class HashTable {

	//Size integer for the table and the table itself.
	public int size = 0; 
	public HashEntry[] table; 
	
	//Integers for status report in output file.
	
	public static int dictionaryWords = 0;
	public static int checkedWords = 0;
	public static int mispelledWords = 0;
	public static int probes = 0;
	public static int lookups = 0;
	

	/**
	 * Constructor method for the HashTable.
	 * 
	 * @param int size
	 */
	public HashTable(int size) { 
		this.size = size;
		table = new HashEntry[size]; 
		for (int i = 0; i < size; i++) 
			table[i] = null;
		}
	
	
	/**
	 * The hash method converts the given String key
	 * into a usable hash integer by using a version of Horner's
	 * method. Starting from 0, it multiples the current hash value
	 * by 37 and adds the value of a character in the string. It does
	 * this until the string has no more characters, and then does a mod
	 * operation based on the table size. If at this point, the hash
	 * value is negative, the method adds the table size to the value.
	 * 
	 * @param String key
	 * @param int tableSize
	 * @return int hashVal
	 */
	public int hash(String key, int tableSize){
		int hashVal = 0; //uses Horner’s method to evaluate a polynomial
		for( int i = 0; i < key.length( ); i++ ){
			hashVal = 37 * hashVal + key.charAt( i );
		}
		hashVal %= tableSize;
		if( hashVal < 0 ){
			hashVal += tableSize; //needed if hashVal is negative
		}
		return hashVal;
	}

	
	/**
	 * Returns the string if found in the hash table. If
	 * it is not found it returns null.
	 * 
	 * @param String key
	 * @return String key or null
	 */
	public String get(String key) { 
		lookups ++;
		int hash = hash(key, size); 
		if (table[hash] == null){
			return null; 
		} else { 
			HashEntry entry = table[hash]; 
			while (entry != null && !entry.getKey().equals(key)){
				probes ++;
				entry = entry.getNext(); 
			}
			if (entry == null){
				return null; 
			} else {
				probes ++;
				return entry.getKey(); 
			} 
		}
	}

	
	/**
	 * Inserts a new entry into the hash table.
	 * 
	 * @param String key
	 */
	public void put(String key) { 
		int hash = hash(key, size); 
		if (table[hash] == null){
			table[hash] = new HashEntry(key);
		} else { 
			HashEntry entry = table[hash]; 
			while (entry.getNext() != null && !entry.getKey().equals(key)){
				entry = entry.getNext();
			}
			if (!entry.getKey().equals(key)){
				entry.setNext(new HashEntry(key)); 
			} 
		} 
	}


	/**
	 * Removes an entry from the hash table.
	 * 
	 * @param String key
	 */
	public void remove(String key) { 
		int hash = hash(key, size); 
		if (table[hash] != null) { 
			HashEntry prevEntry = null; 
			HashEntry entry = table[hash]; 
			while (entry.getNext() != null && !entry.getKey().equals(key)) { 
				prevEntry = entry; 
				entry = entry.getNext(); 
			} 
			if (entry.getKey().equals(key)) { 
				if (prevEntry == null) 
					table[hash] = entry.getNext(); 
				else {
					prevEntry.setNext(entry.getNext()); 
				} 
	     	}
		}
	}
	
	
	/**
	 * Function containing spelling rules for searching
	 * the hash table for a particular word. Returns the word
	 * if found. Returns null if it is not found.
	 * 
	 * @param String word
	 * @return boolean found
	 */
	public boolean search(String word){
		boolean found = false;
		String substring = "";
		if(word.equals(get(word))){
			found = true;
		}
		if(found != true && Character.isUpperCase(word.charAt(0))){
			substring = word.toLowerCase();
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("'s")){
			substring = word.substring(0, word.length() - 2);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("s")){
			substring = word.substring(0, word.length() - 1);
			System.out.println(substring);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("es")){
			substring = word.substring(0, word.length() - 2);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("ed")){
			substring = word.substring(0, word.length() - 2);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("d")){
			substring = word.substring(0, word.length() - 1);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("er")){
			substring = word.substring(0, word.length() - 2);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("r")){
			substring = word.substring(0, word.length() - 1);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("ing")){
			substring = word.substring(0, word.length() - 3);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("ing")){
			substring = word.replace("ing", "e");
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		if(found != true && word.endsWith("ly")){
			substring = word.substring(0, word.length() - 2);
			if(substring.equals(get(substring))){
				found = true;
			}
		}
		return found;
	}
	
	/**
	 * ProcessFile takes a dictionary file input and inserts the words into a hash table.
	 * It then takes the second input file and checks if the words in that file can be found
	 * in the hash table. It produces an output file containing any of the words present in
	 * the second input file that could not be found in the hash table.
	 *
	 * @param Scanner dictionary, Scanner spellcheck, PrintStream output, HashTable hashTable
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static void processFile(Scanner dictionary, Scanner spellcheck, PrintStream output, HashTable hashTable) throws FileNotFoundException {
		String word = "";
		
		System.out.println("Building dictionary...");
		
		while(dictionary.hasNextLine()){
			word = dictionary.nextLine();
			dictionaryWords ++;
			hashTable.put(word);
		}
		
		System.out.println("Dictionary built.");
		System.out.println();
		System.out.println("Checking input file for spelling errors...");
		
		char [] punctuation = {'.', ',', '?', '!', ';', '"', '\'', '(', ')', '&'};

		while(spellcheck.hasNext()){
			word = spellcheck.next();
			
			//Create inner scanner to check for hyphenated words.
			@SuppressWarnings("resource")
			Scanner hyphen = new Scanner(word);
			hyphen.useDelimiter("-");
			
			while(hyphen.hasNext()){
				word = hyphen.next();
				System.out.println(word);
				checkedWords++;
				//Remove punctuation from beginning and end of words.
				for(int i = 0; i < punctuation.length; i++){
					if(word.charAt(0) == punctuation[i]){
						word = word.substring(1);
					}
					if(word.charAt(word.length() - 1) == punctuation[i]){
						word = word.substring(0, word.length() - 1);
					}
				}
				
				if(!hashTable.search(word)){
					output.println(word);
					mispelledWords++;
				}
			}
		}
		
		System.out.println("File successfully checked.");
		System.out.println();
		System.out.println("Words in dictionary:       " + dictionaryWords);
		System.out.println("Words in checked file:     " + checkedWords);
		System.out.println("Mispelled words:           " + mispelledWords);
		System.out.println("Number of probes:          " + probes);
		double average = (double)probes / (double)checkedWords;
		System.out.println("Average probes per word:   " + average);
		double avgLookup = (double)probes / (double)lookups;
		System.out.println("Average probes per lookup: " + avgLookup);
		
		output.println();
		output.println("Words in dictionary:       " + dictionaryWords);
		output.println("Words in checked file:     " + checkedWords);
		output.println("Mispelled words:           " + mispelledWords);
		output.println("Number of probes:          " + probes);
		output.println("Average probes per word:   " + average);
		output.println("Average probes per lookup: " + avgLookup);
	}


	/**
	 * Calls the userInterface method.
	 * 
	 * @param args An array of command line arguments
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static void main(String [] args) throws FileNotFoundException{
		userInterface();
	}

	
	/**
	 * Welcomes the user and prompts the user for two input file
     * names and an output file name.
     * 
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static void userInterface() throws FileNotFoundException{
		
	  /*50291 is prime and is larger than double the amount of words in the dictionary file (25144). It is
	   *almost double the speed of a hash table of size 25147. If the size continues to double, we can make
	   *the table much faster, but at the expense of memory. The speed gained with a size greater than 50291 
	   *is not significant enough to counter the trade off in memory being utilized. At that point our probes
	   *per word checked decreases only minimally when we increase and so I have chosen 50291 as the hash table size.*/
		HashTable hashTable = new HashTable(50291);
		
		//Create an input Scanner to interact with user
		Scanner input = new Scanner(System.in);
	      
		System.out.println();
		System.out.println();
		System.out.println("Welcome to Spell Checker!");
		System.out.println();
		System.out.println("This program is designed to first create a hash table from a given");
		System.out.println("dictionary file. It will then perform a spell check on another specified");
		System.out.println("text file and will output a text file containing any words that this program");
		System.out.println("does not find in the dictionary file.");
		System.out.println();
		  
		//Get a Scanner that will read the dictionary
		Scanner dictionary = getInputScanner(input);

		//Get a Scanner that will read the file to be checked
		Scanner fileReader = getInputScanner(input);
		
		//Create a PrintStream based on the valid file
		PrintStream fileWriter = getOutputPrintStream(input);

		processFile(dictionary, fileReader, fileWriter, hashTable);
		System.out.println("Output file successfully generated.");
		System.out.println("Goodbye!");
	}

	
	/**
	 * This method prompts the user for a file name,
	 * and re-prompts the user until a valid file name is entered.
	 *
	 * @param Scanner console
	 * @return fileScanner a new scanner associated with the file
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static Scanner getInputScanner(Scanner console) throws FileNotFoundException{
		Scanner fileScanner = null;
		while (fileScanner == null) {
			System.out.print("Please enter the location and name of the file to be processed: ");
			String name = console.nextLine();
			try {
				fileScanner = new Scanner(new File(name));
				}
			catch (FileNotFoundException e) {
				System.out.println();
				System.out.println("File: " + name + " not found. Try again.");
			}
		}
		return fileScanner;
	}

	
	/**
	 * Returns a PrintStream for the specified file.  If the
	 * file specified by the user already exists, it will ask
	 * the user whether or not it can overwrite the file.
	 * 
	 * @param Scanner console to process user's input
	 * @return a PrintStream to print to the file.
     * @throws FileNotFoundException if file doesn't exist
	 */
	public static PrintStream getOutputPrintStream(Scanner console) throws FileNotFoundException{
		PrintStream output = null;
		while (output == null) {
			System.out.println();
			System.out.print("Please enter the location and name of the output file: ");
			String outputName = console.nextLine();
			try {
				File fileName = new File(outputName);
				if (fileName.exists()) {
					Scanner verify = new Scanner(System.in);
					System.out.print("Okay to overwrite file? (y/n): ");
					if (verify.next().charAt(0) == 'y') {
						output = new PrintStream(fileName);
					} else {
						System.out.println();
						System.out.println("You have elected not to overwrite the file. Please try again.");
					}
					verify.close();
				} else {
					output = new PrintStream(fileName);
				}
			} catch (FileNotFoundException e) {
				System.out.println();
				System.out.println("Problem creating file:" + e + " Please try again.");
			}
		}
		return output;
	}
	
}