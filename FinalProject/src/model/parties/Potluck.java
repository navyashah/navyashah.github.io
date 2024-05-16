package model.parties;
import java.time.LocalDate;

/**
 * This class creates a potluck party with added functionality of food description.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Potluck extends Party {
    private String foodDesc;
    public Potluck(String hostName, String name, String location, LocalDate date, String[] guests, String foodDesc) {
        super(hostName, name, location, date, guests);
        this.foodDesc = foodDesc;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    @Override
    public String toString() {
        return "Potluck: " + super.toString() +
                " Food Prompt: " + foodDesc;
    }
}
