package view;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * UI is a utility class for ITP 265 that helps provide a friendly way to read input from
 * a user and verify that the input is correct.
 * 
 * @author Kendra Walther
 * Email: kwalther@usc.edu 
 *
 */

public class BFF implements UI {

	private Scanner sc;
	
	
	/**
	 * Constructor sets up a Scanner to be used by the class in order to read input from the standard console window (System.in)
	 */
	public BFF() {
		sc = new Scanner(System.in);
	}


	
	/**
	 * Short-cut helper method for print
	 * @param output: The String to be printed
	 */
	public void print(String output) {
		System.out.println(output);
	}

	public void print(Object o) {
		System.out.println(o.toString());
	}

	/**
	 * Short-cut helper method that prints a String with a series of stars around it.
	 * @param output: The String to be printed
	 */
	public void printPretty(String output) {
		System.out.println("***********************************************************************************************");
		System.out.println(output);
		System.out.println("***********************************************************************************************");
	}
	
	/**
	 * Short-cut helper method that prints a List with a series of stars around it.
	 * @param output: The List to be printed
	 */
	public void printPretty(List list) {
		System.out.println("***********************************************************************************************");
		for(Object o: list) {
			System.out.println(o);
		}
		System.out.println("***********************************************************************************************");
	}
	/**
	 * Prompt the user and read one line of text as a String
	 * @param prompt: the question to ask the user
	 * @return: a line of user input (including spaces, until they hit enter)
	 */
	public String input(String prompt) {
		System.out.print(prompt + "\n>");
		return sc.nextLine();
	}
	
	/**
	 * Prompt the user and read one word of text as a String
	 * @param prompt: the question to ask the user
	 * @return: a one word String - if the user enters multiple words, all other input until the return will be discarded.
	 */
	public String inputWord(String prompt) {
		System.out.print(prompt + "\n>");
		String word = sc.next();
		sc.nextLine(); // remove any "garbage" (like extra whitespace or the return key) after the one word that is read in
		return word;
	}
	
	/**
	 * Prompt the user and read one word - which must match either option1 or option2 parameters.
	 * @param prompt: the question to ask the user (should include the two valid options the user should choose from)
	 * @param option1 : One string option for the user to choose.
	 * @param option2: the other string option for the user to choose.
	 * @return: A string matching either option1 or option2
	 */
	public String inputWord(String prompt, String option1, String option2) {
		
		System.out.print(prompt + "\n>");
		String word = sc.nextLine();
		while(! (word.equalsIgnoreCase(option1) || word.equalsIgnoreCase(option2))) {
			System.out.println(word + " not recognized as " + option1 + " or " + option2);
			System.out.println(prompt);
			word = sc.nextLine();
		}
		return word;
	}
	/**
	 * Prompt the user and match text to one of the allowed matches
	 * @param prompt
	 * @param matches -- Strings that are the allowed matched
	 * @return a lowercase string that matches one of the allowed values.
	 */
	public String inputWord(String prompt, String... matches) {
		String word = input(prompt);
		String options = Arrays.toString(matches);
		boolean found = false;
		while(!found) {
			int i = 0;
			while(!found && i < matches.length){
				String match = matches[i];
				if (word.equalsIgnoreCase(match)) {
					found = true;
				}
				i++;
			}
			if(!found) { //look again
				//  System.out.printf("%s did not match the allowed choices: %s\n", word, Arrays.asList(matches));
				System.out.printf("%s did not match the allowed choices: %s\n", word, options);
				word = input(prompt);

			}
		}
		return word.toLowerCase();
	}
	/**
	 * Prompt the user and read an int, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: an int 
	 */
	public int inputInt(String prompt) {
		System.out.print(prompt + "\n>");
		// if scanner does not see an int, get rid of garbage and ask again.
		while (!sc.hasNextInt()) {
			String garbage = sc.nextLine();
			System.out.println("Didn't recognize " + garbage + " as an integer...");
			System.out.println(prompt);
		}
		int num = sc.nextInt();
		sc.nextLine(); // clear the buffer
		return num;
	}
	
	/**
	 * Prompt the user and read an int between (inclusive) of minValue and maxValue, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: an int between minValue and maxValue
	 */
	public int inputInt(String prompt, int minValue, int maxValue) {
		int num = inputInt(prompt); // make sure you get a num
		while(num < minValue || num > maxValue) {
			System.out.println(num + " is not in the allowed range: [" + minValue
					+ "-" + maxValue + "]");
			num = inputInt(prompt); // make sure you get a num

		}
		return num;
	}
	

	/**
	 * Prompt the user and read an int between (inclusive) of minValue and maxValue, 
	 * (or sentinel quitValue) clearing whitespace or the enter after the number
	 * @param prompt the question to ask the user
	 * @param minValue
	 * @param maxValue
	 * @param quitValue
	 * @return an int between minValue and maxValue (or quit sentinel value)
	 */
	public int inputInt(String prompt, int minValue, int maxValue, int quitValue) {
		int num = inputInt(prompt); // make sure you get a num
		while(num != quitValue && (num < minValue || num > maxValue)) {
			System.out.println(num + " is not in the allowed range: [" + minValue
					+ "-" + maxValue + "] (or " + quitValue + " to quit)");
			num = inputInt(prompt); // make sure you get a num
		}
		return num;
	}
	/**
	 * Prompt the user and read a postive (>=0) int, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: an int >= 0
	 */
	public int inputPostiveInt(String prompt) {
		// use the fact inputInt works....
		int num = inputInt(prompt); // off-loaded work
		while(num < 0) { //not positive....
			System.out.println("Need a positive number, try again");
			num = inputInt(prompt);
		}
		return num;
		
	}
	/**
	 * Prompt the user and read a floating point number, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: a double value 
	 */
	public double inputDouble(String prompt) {
		System.out.print(prompt + "\n>");
		// if scanner does not see a double, get rid of garbage and ask again.
		while (!sc.hasNextDouble()) {
			String garbage = sc.nextLine();
			System.out.println("Didn't recognize " + garbage + " as a double.");
			System.out.println(prompt);
		}
		double num = sc.nextDouble();
		sc.nextLine(); // clear the buffer
		return num;
	}
	/**
	 * Prompt the user and read a boolean value, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: a boolean value 
	 */
	public boolean inputBoolean(String prompt) {
		System.out.print(prompt + "\n>");
		// if scanner does not see a boolean, get rid of garbage and ask again.
		while (!sc.hasNextBoolean()) {
			String garbage = sc.nextLine();
			System.out.println("Didn't recognize " + garbage + " as a boolean value. Must enter: "
					+ "\"true\" or \"false\"");
			System.out.println(prompt);
		}
		boolean value = sc.nextBoolean();
		sc.nextLine(); // clear the buffer
		return value;
	}
	
	/**
	 * Prompt the user enter yes or no (will match y/yes and n/no any case) and return true for yes and false for no.
	 * @param prompt: the question to ask the user 
	 * @return: a boolean value 
	 */
	public boolean inputYesNo(String prompt) {
		System.out.print(prompt + "\n>");
		// if scanner is seeing BAD input... loop to get good input
		String answer = sc.nextLine().toLowerCase();
		while ( ! (answer.equals("y") || answer.equals("yes") 
					|| answer.equals("n") || answer.equals("no") )) {
		
			System.out.println("Didn't recognize " + answer + " as yes or no...");
			System.out.println(prompt);
			answer = sc.nextLine().toLowerCase();
		}
		//end of loop = good input
		
		if(answer.equals("y") || answer.equals("yes")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public LocalDate inputDate(String prompt) {

		System.out.println(prompt); // what kind of date is the program looking for
		int year = inputInt("Year ", 1900, 2555); // arbitrary future date.
		int month = inputInt("Month ", 1, 12);
		int numDays = Month.of(month).length(Year.isLeap(year)); // find out the number of actual days in the month.
		int day = inputInt("Day ", 1, numDays);

		// create LocalDate object
		return LocalDate.of(year, month, day);

	}


}