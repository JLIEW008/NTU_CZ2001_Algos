package exampleClassThree;

/**
 * This program is used to analyse and compare sorting algorithms.
 * 1) the time complexity of the insertion sort vs merge sort
 * 2) Combination of insertion and merge sort.
 * 
 * @author SS1 Group 1
 */

import java.util.*;

public class SortAnalysis {
	public static void main(String[] args) {
		//Hyperparameters
		int dataSize = 500;
		int dataGenerationChoice = 1;	 //1: Random, 2: ascending, 3: descending
		int sortAlgoChoice = 3; 	//1: insertion sort, 2: merge sort, 3: merge + insertion 
		
		//Generate data
		int[] dataArr = new int[dataSize];
		generateData(dataArr, dataGenerationChoice);
		
		//Sort array
		sortArray(dataArr, sortAlgoChoice);
		
		//Print sorted array + check if array is sorted correctly
		print(dataArr, 100);
		System.out.println("\n" + ascendingOrder(dataArr));
	}
	
	
	//Program flow methods
	static void generateData(int[] arr, int dataGenerationChoice) {
		switch(dataGenerationChoice) {
		case 1:
			generateDataSet(arr, arr.length, 1);
			break;
		case 2:
			generateDataSet(arr, arr.length, 2);
			break;
		case 3:
			generateDataSet(arr, arr.length, 3);
		}
	}
	
	static void sortArray(int[] arr, int choice) {
		switch(choice) {
		case 1:
			insertionSort(arr);
			break;
		case 2:
			mergeSort(arr, 0, arr.length - 1);
			break;
		case 3:
			mergeInsertionSort(arr, 0, arr.length - 1, 10);
			//merge+insertion
		}
	}
	
	//Data generation
	/**
	 * Generates a data set with random order, ascending order, and descending order.
	 * Values generated will be in the range of 1 to 'number' param.
	 * 
	 * @param arr array to input the generated values
	 * @param number of values to be generated
	 * @param choice - 1: random, 2: ascending, 3: descending
	 */
	static void generateDataSet(int[] arr, int number, int choice) {
		try {
			if(arr.length < number) {
				throw new IllegalArgumentException("The array size must be bigger than the number of data "
						+ "generated!");
			}
			if(choice < 1 || choice > 3) {
				throw new IllegalArgumentException("Choice can only be integer 1, 2, or 3.");
			}
			
			switch(choice) {
			case 1:	//Generating random int
				Random RNG = new Random();
				for(int i = 0; i < number; i++) {
					arr[i] = RNG.nextInt(number) + 1;
				}
				break;
				
			case 2:	//Ascending order
				for(int i = 0; i < number; i++) {
					arr[i] = i + 1;
				}
				break;
				
			case 3:	//Descending order
				for(int i = 0; i < number; i++) {
					arr[i] = number - i;
				}
			}
			
		} catch(IllegalArgumentException iae) {
			System.out.println(iae);
		}
		
	}
	
	
	//Sorting algorithms
	/**
	 * Insertion sort
	 * 
	 * @param arr array to sort
	 */
	static void insertionSort(int[] arr) {
		int temp;
		for(int i = 0; i < arr.length; i++) {
			for(int j = i; j > 0; j--) {
				if(arr[j] < arr[j-1]) {
					temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
				}else {break;}	//break when sorted
			}
		}
	}
	
	/**
	 * Merge sort - with auxiliary array
	 * 
	 * @param array to sort
	 * @param m lower bound of array
	 * @param n upper bound of array
	 */
	static void mergeSort(int[] arr, int m, int n) {
		if(n < m) {
			throw new IllegalArgumentException("n must be bigger than m!");
		}
		int middle = (n+m)/2;
		if(n-m> 0) {
			mergeSort(arr, m, middle);
			mergeSort(arr, middle + 1, n);
			int[] auxArray = new int[n-m + 1];
			merge(auxArray, Arrays.copyOfRange(arr, m, middle + 1), Arrays.copyOfRange(arr, middle + 1, n + 1));
			replaceRange(arr, auxArray, m);
		}else {return;}
	}
	
	static void replaceRange(int[] arr1, int[] arr2, int m) {
		for(int i = 0; i < arr2.length; i++) {
			arr1[m + i] = arr2[i];
		}
	}
	
	static void merge(int[] arr, int[] arr1, int[] arr2) {
		int j = 0, k = 0;	//j to access arr1, k to access arr2
		for(int i = 0; i < arr1.length + arr2.length; i++) {
			if(j == arr1.length) {	//if all of arr1 is transferred to auxiliary array
				arr[i] = arr2[k];	//add arr2 to auxiliary array
				k++;
			} else if(k == arr2.length) {	//if all of arr2 is transferred to auxiliary array
				arr[i] = arr1[j];
				j++;
			} else {
				if(arr1[j] < arr2[k]) {	
					arr[i] = arr1[j];
					j++;
				} else {
					arr[i] = arr2[k];
					k++;
				}
			}
		}
	}
	
	static void mergeInsertionSort(int[] arr, int first, int last, int S) {
		if(last < first) {
			throw new IllegalArgumentException("n must be bigger than m!");
		}
		if(last - first> S) {
			int middle = (last+first)/2;
			mergeSort(arr, first, middle);
			mergeSort(arr, middle + 1, last);
			int[] auxArray = new int[last-first + 1];
			merge(auxArray, Arrays.copyOfRange(arr, first, middle + 1), Arrays.copyOfRange(arr, middle + 1, last + 1));
			replaceRange(arr, auxArray, first);
		}else {
			int[] auxArray = new int[last - first + 1];
			insertionSort(auxArray);
			replaceRange(arr, auxArray,first);
		}
		
		
	}
	
	//Utility functions
	static void print(int[] arr, int width) {
		int counter = 0;
		for (int i = 0; i < arr.length; i++) {
			if(counter + digitsCounter(arr[i]) + 1> width) {
				System.out.print("\n");
				counter = 0;
			} else {
			System.out.print(arr[i] + " ");
			counter += (
				digitsCounter(arr[i]) + 1);
			}
		}
	}
	
	static int digitsCounter(int number) {
		int counter = 1;
		while(number/10 != 0) {
			number /= 10;
			counter++;
		}
		return counter;
	}
	
	static boolean ascendingOrder(int[] arr) {
		for(int i =0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) {
				return false;
			}
		}
		return true;
	}
}
