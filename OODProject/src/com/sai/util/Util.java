/*
 * Copyright (c) 2013, Christos Sotiriou
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * -- Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * -- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sai.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
	/**
	 * Returns an integer between values
	 * @param min the lower limit
	 * @param max the upper limit
	 * @param rand the {@link Random} instance to generate the random values from
	 * @return an integer between the ranges given
	 */
	public static int randomRange(int min, int max, Random rand){
		int randomNum = rand.nextInt(max - min + 1) + min;
		return randomNum;
	}
	
	
	/**
	 * Prints the contents of an int array to the comnand line.
	 * @param arr
	 */
	static public void printArray(int arr[]){
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
	
	/**
	 * Shuffles a list.
	 * @param list the list to shuffle
	 */
	public static void shuffleList(List<?> list){
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
	}
	
	
	/**
	 * Copies data from one array to the other. The arrays must be of equal length and size
	 * @param source The source array
	 * @param destination The destination array;
	 */
	public static void copyTwoDimensionalArray(int [][] source, int[][] destination){
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[i].length; j++) {
				destination[i][j] = source[i][j];
			}
		}
	}
	
	
	
	/**
	 * Returns if an array is orthogonal, which means that there are no variable sizes
	 * in each column and row
	 * @param array
	 * @return
	 */
	public static boolean isOrthogonalArray(int[][] array){
		
		int firstHeight = array[0].length;
		for (int i = 0; i < array.length; i++) {
			if (array[i].length != firstHeight) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Will produce a new two dimensional array with the contents of the original. NOTE: it will
	 * only work correctly with orthogonal 2d arrays. If the source array has variable size in each
	 * line, it will not produce a correct result, and maybe an exception will be thrown.
	 *  
	 * @param source
	 * @return
	 */
	public static int[][] new2DArrayFromA2DArray(int [][] source){
		int [][] result = new int[source.length][source[0].length];
		copyTwoDimensionalArray(source, result);
		return result;
	}
}
