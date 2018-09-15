package exampleClassTwo;

/**
 * This class generates hash tables which allows chain hashing.
 * Only integer key value pair are accepted.
 * Resizing of the hash table is not implemented.
 * Similar to Java's own implementation of Hashtable.
 * 
 * @see Hashtable
 * @see LinkedList
 * @author SS1 Group 1
 */

import java.util.*;

public class HashTable {
	private int hashTableUpperBound;
	private int hashTableLowerBound;
	private int hashTableSize;
	private LinkedList<Integer>[] hashTable;
	
	/**
	 * Generate a hash table from 1 to hashTableSize
	 * 
	 * @param hashTableSize size of the hash table
	 */
	public HashTable(int hashTableSize) {
		this.hashTableSize = hashTableSize;
		this.hashTableUpperBound = hashTableSize;
		this.hashTableLowerBound = 1;
		this.hashTable = new LinkedList[this.hashTableSize];
		for(int i = 0;i < this.hashTableSize; i++) {	//Initialise linkedlist
			this.hashTable[i] = new LinkedList<Integer>();
		}
	}
	
	public HashTable(int hashTableLowerBound, int hashTableUpperBound) {
		this.hashTableUpperBound = hashTableUpperBound;
		this.hashTableLowerBound = hashTableLowerBound;
		this.hashTableSize = this.hashTableUpperBound - this.hashTableLowerBound + 1;
		this.hashTable = new LinkedList[this.hashTableSize];
		for(int i = 0;i < this.hashTableSize; i++) {
			this.hashTable[i] = new LinkedList<Integer>();
		}
	}
	
	/**
	 * Adds key-value pair to hashtable.
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public void add(int key,int value){
		if(key > this.hashTableUpperBound || key < this.hashTableLowerBound) {
			throw new IllegalArgumentException("key value out of range");
		}else {
			hashTable[key-this.hashTableLowerBound].add(value);
		}
	}
	
	/**
	 * Return an integer array of the values corresponding to the specific key
	 * 
	 * @param key
	 * @return integer array with all the corresponding values
	 */
	public int[] get(int key) {
		if(this.hashTable[key].size() == 0) {
			return null;
		}
		
		int[] arr = new int[this.hashTable[key].size()];
		for (int i =0; i < this.hashTable[key].size(); i++) {
			arr[i] = hashTable[key].get(i);
		}
		return arr;
		
	}
	
	/**
	 * Prints hash table.
	 */
	public void print() {
		System.out.print(" Key    Values");
		for (int i = 0; i < this.hashTableSize; i++) {	//Loop each key
			System.out.format("\n%4d    ", i + this.hashTableLowerBound);
			if (hashTable[i].size() == 0) {
				System.out.print("null");
			}else {
				for(int j = 0; j < this.hashTable[i].size(); j++) {
					System.out.print(" " + hashTable[i].get(j));
				}
			}
		}
		System.out.print("\n");
	}
	
}
