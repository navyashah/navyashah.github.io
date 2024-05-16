package model.parties;
import java.time.LocalDate;

/**
 * This class creates a favor party with added functionality of favors.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Favor extends Party {
    private String favorDesc;

    public Favor(String hostName, String name, String location, LocalDate date, String[] guests, String favorDesc){
        super(hostName, name, location, date, guests);
        this.favorDesc = favorDesc;
    }

    public String getFavorDesc() {
        return favorDesc;
    }

    public void setFavorDesc(String favorDesc) {
        this.favorDesc = favorDesc;
    }

    @Override
    public String toString() {
        return "Favor Party: " + super.toString() +
                " Favors: " + favorDesc;
    }
}
