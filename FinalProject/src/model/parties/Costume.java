/*
private String character;
    private boolean isInCostume;
 */
package model.parties;
import java.time.LocalDate;

/**
 * This class creates a costume party with added functionality of a theme
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Costume extends Party {
    private String theme;

    public Costume(String hostName, String name, String location, LocalDate date, String[] guests, String theme) {
        super(hostName, name, location, date, guests);
       this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Costume: " + super.toString() +
                " Theme: " + theme;
    }
}
