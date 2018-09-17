package exampleClassTwo;

/**
 * App to test closed address hashing.
 * 
 * @author SS1 Group 1
 */

import java.util.*;

public class HashTableApp {
	public static void main(String[] args) {
		long startTime, endTime, timeTaken, searchStartTime, searchEndTime, 
		searchTimeTaken, searchAverageTimeTaken = 0, averageTimeTaken = 0;
		
		//Hyperparameters
		int numberOfTests = 100000;	//number of tests
		int size = 30;	//number of values
		
		//Hashtable params
		int hashTableUpperBound = 9;
		int hashTableLowerBound = 0;
		
		//Generate Random numbers
		int[] keyArr = new int[size];
		
		int choice = 2; //1 for random number, 2 for multiples
		//Use these if generating random number
		if(choice == 1) {
			int upperBoundOfValues = 5000;
			int lowerBoundOfValues = 2000;
			keyArr = generateRandomNumberArray(size, lowerBoundOfValues, upperBoundOfValues); 
		}
		// Use these if generating multiples
		if(choice == 2) { 
			int multiple = 4;
			double probs = 0.75; 
			keyArr = generateRandomMultipleNumberArray(size, multiple, probs);
		}
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
			
			//Search value
			int searchValue = 3;//keyArr[5];	//Random value
			
			// Search
			searchStartTime = System.nanoTime();
			hashTable.search(searchValue);
			searchEndTime = System.nanoTime();
			searchTimeTaken = searchEndTime - searchStartTime;
			searchAverageTimeTaken += searchTimeTaken;
			
			hashTable.print(); //Print hashtable
		}
		
		averageTimeTaken /= numberOfTests;
		searchAverageTimeTaken /= numberOfTests;
		System.out.println("\nAverage time taken for hashing function: " + averageTimeTaken + "ns");
		System.out.println("\nAverage time taken for search: " + searchAverageTimeTaken + "ns");
		
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
		
		while(arrayIndex < size) {
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
	public static void modHashingFunction(int arr[], int modNumber) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] %= modNumber;
		}
	}
	
	/**
	 * Hashing function: folding using addition
	 * Only for hashtables of size 10^n
	 * 
	 * @param arr
	 * @param partitionDigit
	 */
	public static void foldingAdditionHashingFunction(int arr[], int digitPartition) {
		int temp, sum, numberSplit;
		numberSplit = (int)Math.pow(10,digitPartition + 1);
		for(int i = 0; i < arr.length; i++) {
			sum = 0;
			temp = arr[i];
			while(temp / numberSplit != 0) {
				sum += temp % numberSplit;
				temp /= numberSplit;
			}
			arr[i] = sum % numberSplit;	//Remove any additional digits
		}
	}
	
	/**
	 * Hashing Function: Mid-square method
	 * 
	 * @param arr
	 * @param digits number of digits return as key
	 */
	public static void midSquareHashingFunction(int arr[], int digits) {
		int temp, tempDigits;
		for(int i = 0; i < arr.length; i++) {
			temp = arr[i] * arr[i];	//Squaring
			tempDigits = numberOfDigits(temp);
			temp /= (tempDigits - digits)/2;	//Remove digits from right
			temp %= (int)Math.pow(10, digits);	//Extracting digits from right (Middle)
			arr[i] = temp;
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
	
	public static int numberOfDigits(int number) {
		int count = 0;
		while(number%10 != 0){
			number /= 10;
			count++;
		}
		return count;
	}
}

