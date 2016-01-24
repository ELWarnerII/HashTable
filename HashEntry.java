package SpellChecker;

/**
 * HashEntry Class
 * 
 * This class is a single entry of a hash table designed with
 * a seperate chaining data structure in mind. Each entry contains
 * the key assigned to it and a link to the next entry which will
 * hold a null value unless a collision has previously occurred.
 * 
 * @author Louis Warner
 */
public class HashEntry {
	
	//Key value and pointer to the next item in the chain
	private String key; 
	private HashEntry next; 

	/**
	 * Constructor method for HashEntry.
	 * 
	 * @param String key
	 */
	public HashEntry(String key) { 
		this.key = key;
		this.next = null; 
	} 
	
	 
	/**
	 * Getter method for key.
	 * 
	 * @return String key
	 */
	public String getKey() { 
		return key; 
	} 

	
	/**
	 * Getter method for pointer to the next item in the chain.
	 * 
	 * @return HashEntry next
	 */
	public HashEntry getNext() { 
		return next; 
	} 

	
	/**
	 * Setter method for pointer to the next item in the chain.
	 * 
	 * @param HashEntry next
	 */
	public void setNext(HashEntry next) { 
		this.next = next; 
	} 

}