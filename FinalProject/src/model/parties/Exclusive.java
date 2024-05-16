package model.parties;
import java.time.LocalDate;
import java.util.Objects;

/**
 * This class creates an exclusive party with added functionality of an age limit.
 * Only Premium members can host these.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Exclusive extends Party implements Premium {
    private int ageLimit;

    public Exclusive(String hostName, String name, String location, LocalDate date, String[] guests, int ageLimit) {
        super(hostName, name, location, date, guests);
        this.ageLimit = ageLimit;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    @Override
    public boolean isPremium() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Exclusive exclusive = (Exclusive) o;
        return ageLimit == exclusive.ageLimit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ageLimit);
    }

    @Override
    public String toString() {
        return "Exclusive: " + super.toString() +
                " Age Limit: " + ageLimit;
    }
}
