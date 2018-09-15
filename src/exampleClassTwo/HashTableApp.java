package exampleClassTwo;

/**
 * App to test closed address hashing.
 * 
 * @author SS1 Group 1
 */


import java.lang.reflect.Array;
import java.util.*;

public class HashTableApp {
	public static void main(String[] args) {
		long startTime, endTime, timeTaken, averageTimeTaken = 0;
		int numberOfTests = 100;
		
		//Hashtable params
		int hashTableUpperBound = 9;
		int hashTableLowerBound = 0;
		
		//Generate Random numbers
		int size = 30;
		
		//Use these if generating random number
		//int upperBoundOfValues = 5000;
		//int lowerBoundOfValues = 2000;
		//int[] keyArr = generateRandomNumberArray(size, lowerBoundOfValues, upperBoundOfValues); 
		
		// Use these if generating multiples
		int multiple = 4;
		double probs = 0.75; 
		int[] keyArr = generateRandomMultipleNumberArray(size, multiple, probs);
		System.out.println(keyArr[5]);
		//Note: Original array stores values, but will be subsequently changed to keys
		
		//Print load factor
		double loadFactor = (double)size/(hashTableUpperBound - hashTableLowerBound + 1);	// Entries/Buckets
		System.out.println(loadFactor);
			
		for (int i = 0; i < numberOfTests; i++) {
			//Generate hashtable
			HashTable hashTable = new HashTable(hashTableLowerBound, hashTableUpperBound);
			
			//Generate key using hashing function
			int[] valueArr = Arrays.copyOf(keyArr, keyArr.length);
		
			startTime = System.nanoTime();	//Start timer
			HashTableApp.modHashingFunction(keyArr, 10);
			addValuePairToHashTable(keyArr, valueArr, hashTable);
			endTime = System.nanoTime();	//End timer
			timeTaken = endTime- startTime;
			averageTimeTaken += timeTaken;

			hashTable.print(); //Print hashtable
		}
		
		averageTimeTaken /= numberOfTests;
		System.out.println("\n" + averageTimeTaken);
		
	}
	
	//Data generation
	/**
	 * Used to generate an array of random integers
	 */
	public static int[] generateRandomNumberArray(int size, int lowerBound, int upperBound) {
		Random RNG = new Random();
		int[] arr = new int[size];
		for(int i = 0; i < size; i++) {
			arr[i] = lowerBound+ RNG.nextInt(upperBound-lowerBound);
		}
		return arr;
	}
	
	
	public static int[] generateRandomMultipleNumberArray(int size, int multiple, double prob) {
		int[] arr = new int[size];
		Random RNG = new Random();
		double RNGValue; 
		int number = multiple; 
		int arrayIndex = 0;
		
		while(Array.getLength(arr) < size) {
			RNGValue = RNG.nextDouble();
			if (RNGValue < prob) {
				arr[arrayIndex] = number;
				arrayIndex++;
			}
			number += multiple;
		}
		return arr;
	}

	//Hashing Functions
	/**
	 * Hashing function: Value % modNumber = key
	 * 
	 * @param arr
	 * @param modNumber
	 */
	public static void modHashingFunction(int arr[],int modNumber) {
		for(int i = 0; i < arr.length; i++) {
			arr[i]%= modNumber;
		}
	}
	
	
	
	//Utility methods
	/**
	 * Adding value-key pair to hashtable
	 * 
	 * @param keyArr
	 * @param valueArr
	 * @param hashTable
	 */
	public static void addValuePairToHashTable(int[] keyArr, int[] valueArr, HashTable hashTable){
		try {
			if (keyArr.length != valueArr.length) {
				throw new Exception("Size of keyArr and valueArr must be equals");
			}
			for(int i = 0; i < keyArr.length; i++) {
				hashTable.add(keyArr[i], valueArr[i]);
			}
		}
		catch(IllegalArgumentException iae) {System.out.println(iae);}
		catch(Exception e) {System.out.println(e);}
	}
}

