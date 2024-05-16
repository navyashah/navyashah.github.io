package view;



import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * UI is a utility class for ITP 265 that helps provide a friendly way to read input from
 * a user and verify that the input is correct.
 * 
 * @author Kendra Walther
 * ITP 265
 * Email: kwalther@usc.edu 
 *
 */

public class UIPopUp  implements UI {


	/**
	 * Short-cut helper method for print
	 * @param output: The String to be printed
	 */
	public void print(String output) {
		JOptionPane.showMessageDialog(null, output);
	}

	public void print(Object o) {
		print(o.toString());
	}

	public void printPretty(String output) {
		JOptionPane.showMessageDialog(null, output);
	}

	@Override
	public void printPretty(List list) {
		String s = "List Contains:";
		for(Object o: list) {
			s+= o + "\n";
		}
		printPretty(s);
	}

	public String input(String prompt) {
		String s = JOptionPane.showInputDialog(null, prompt);
		return s;
	}


	public String inputWord(String prompt) {
		return input(prompt);
	}


	public String inputWord(String prompt, String option1, String option2) {
		String[] options = {option1, option2};
		String s = (String)JOptionPane.showInputDialog(null, prompt,"PopUp", JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		return s;

	}

	@Override
	public String inputWord(String prompt, String... matches) {
		//TODO
		return null;
	}

	/**
	 * Prompt the user and read an int, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: an int 
	 */
	public int inputInt(String prompt) {

		int value = 0;
		boolean gotNumber = false;
		while (!gotNumber) {
			String s = JOptionPane.showInputDialog(null, prompt);
			try {
				value = Integer.parseInt(s);
				gotNumber = true;
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, s + " was not a valid int", "Pop Up", 
						JOptionPane.ERROR_MESSAGE);

			}
		}
		return value;
	}

	/**
	 * Prompt the user and read an int between (inclusive) of minValue and maxValue, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: an int between minValue and maxValue
	 */
	public int inputInt(String prompt, int minValue, int maxValue) {
		int num = inputInt(prompt); // make sure you get a num
		while(num < minValue || num > maxValue) {
			String msg = num  + " is not in the allowed range: [" + minValue
					+ "-" + maxValue + "]";
			JOptionPane.showMessageDialog(null,  msg, "Pop Up", 
					JOptionPane.ERROR_MESSAGE);
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
			String msg = num + " is not in the allowed range: [" + minValue
					+ "-" + maxValue + "] (or " + quitValue + " to quit)";
			JOptionPane.showMessageDialog(null,  msg, "Pop Up", 
					JOptionPane.ERROR_MESSAGE);

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
			JOptionPane.showMessageDialog(null,  "Need a positive number", "Pop Up", 
					JOptionPane.ERROR_MESSAGE);
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
		double value = 0;
		boolean gotNumber = false;
		while (!gotNumber) {
			String s = JOptionPane.showInputDialog(null, prompt);
			try {
				value = Double.parseDouble(s);
				gotNumber = true;
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, s + " was not a valid double", "Pop Up", 
						JOptionPane.ERROR_MESSAGE);

			}
		}
		return value;
	}
	/**
	 * Prompt the user and read a boolean value, clearing whitespace or the enter after the number
	 * @param prompt: the question to ask the user 
	 * @return: a boolean value 
	 */
	public boolean inputBoolean(String prompt) {
		int value = JOptionPane.showConfirmDialog(null, prompt, "Pop Up", JOptionPane.YES_NO_OPTION);
		if(value == JOptionPane.YES_OPTION) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Prompt the user enter yes or no (will match y/yes and n/no any case) and return true for yes and false for no.
	 * @param prompt: the question to ask the user 
	 * @return: a boolean value 
	 */
	public boolean inputYesNo(String prompt) {
		String[] options = {"yes", "no"};
		int index = JOptionPane.showOptionDialog(null, prompt, "popup", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if(index == 0) {
			return true;
		}
		else {
			return false;
		}


	}

	@Override
	public LocalDate inputDate(String prompt) {
		int year = this.inputInt(prompt + "\tYear: ", 1900, LocalDate.now().getYear());
		int month = this.inputInt(prompt + "\tMonth (as num):", 1, 12);
		int day = this.inputInt(prompt + "\tDay:", 1, Month.of(month).maxLength());
		return LocalDate.of(year, month, day);

	}


    public Object selectFromArray(Object[] values) {
		Object choice = (Object) JOptionPane.showInputDialog(null, // frame
				"Select an option", // message"
				"Select from array", // title
				JOptionPane.QUESTION_MESSAGE,  //message Type
				null, // icon to display (null gets you duke)
				values, // selection values (an array of Objects)
				values[0]); // initial selection

		return choice;
    }


}