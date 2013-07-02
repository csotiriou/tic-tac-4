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

package com.sai.model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code (french parts) taken from the Library project from
 * Software Testing and Metrics course.
 * 
 */
/**
 * Permet de lire des nombres et des chaines de caracteres
 * au clavier.
 */
public class Console {

	/**
	 * La console est un flot de caracteres bufferise.
	 */
	private static BufferedReader console =
			new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Affiche un message a l'ecran sans fin de ligne    
	 *     @param prompt Message a afficher
	 */
	public static void printPrompt(String prompt) {
		System.out.print(prompt + " ");
		System.out.flush();
	}

	/**
	 * Lecture d'une chaine de caracteres au clavier. La chaine ne
	 * contient pas le caractere 'fin de ligne'. En cas de fin de
	 * fichier ou d'erreur, la chaine retournee vaut <code>null</code>.
	 *     @return La chaine lue
	 */ 
	public static String readLine() {
		String r = "";
		try {
			r = console.readLine();
		} catch(IOException e) { r = null; }
		return r;
	}

	/**
	 * Lecture d'une chaine de caracteres au clavier avec affichage d'un
	 * prompt.
	 *     @param prompt Message a afficher
	 *     @return La chaine lue
	 *     @see #printPrompt(String)
	 *     @see #readLine()
	 */
	public static String readLine(String prompt) {
		printPrompt(prompt);
		return readLine();
	}

	/**
	 * Lecture d'un entier au clavier.
	 *     @param prompt Message a afficher
	 *     @return L'entier lu
	 *     @exception NumberFormatException en cas d'erreur
	 */
	public static int readInt(String prompt) throws NumberFormatException {
		printPrompt(prompt);
		return Integer.valueOf(readLine().trim()).intValue();
	}

	/**
	 * Lecture d'un double au clavier.
	 *     @param prompt Message a afficher
	 *     @return Le double lu
	 *     @exception NumberFormatException en cas d'erreur
	 */
	public static double readDouble(String prompt) throws NumberFormatException {
		printPrompt(prompt);
		return Double.valueOf(readLine().trim()).doubleValue();
	}


	/**
	 * Reads a point from the command line and returns the
	 * point. Throws exception in case of invalid input
	 * @return
	 * @throws Exception
	 */
	static Point getPoint(String input) throws Exception{

		Pattern p = Pattern.compile("[0-9]+\\s[0-9]+");
		Matcher m = p.matcher(input);
		String matchPattern = null;
		if (m.matches()) {
			matchPattern = m.group(0);

			int location = matchPattern.indexOf(" ");

			String number1 = matchPattern.substring(0, location);
			String number2 = matchPattern.substring(location+1);

			return new Point(Integer.valueOf(number1).intValue(), Integer.valueOf(number2).intValue());
		}else{
			throw new Exception("wrong format given");
		}
	}
	
	
	/**
	 * Read a point from the command line
	 * @param prompt
	 * @return
	 * @throws Exception
	 */
	public static Point readPoint(String prompt) throws Exception{
		printPrompt(prompt);
		
		boolean shouldExit = false;
		
		Point p = null;
		
		while (p == null && !shouldExit) {
			
			String input = null;
			input = readLine();
			if (input.equals("exit")) {
				shouldExit = true;
			}
			try {
				p = getPoint(input);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
		return p;
	}
}