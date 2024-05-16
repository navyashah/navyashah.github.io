package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface UI {
	public void print(String output);
	public void print(Object o);
	public void printPretty(String output);
	public void printPretty(List output);
	public String input(String prompt);
	public String inputWord(String prompt);
	public String inputWord(String prompt, String option1, String option2);
	public String inputWord(String prompt, String... matches) ;

		public int inputInt(String prompt) ;
	
	public int inputInt(String prompt, int minValue, int maxValue) ;
	public int inputInt(String prompt, int minValue, int maxValue, int quitValue);
	public int inputPostiveInt(String prompt);
	public double inputDouble(String prompt);
	
	public boolean inputBoolean(String prompt) ;
	public boolean inputYesNo(String prompt) ;
	public LocalDate inputDate(String prompt) ;
}
