package model.people;
import model.parties.Premium;
import java.time.LocalDate;
import java.util.Objects;

/**
 * This class is a parent class to the users.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class User implements Premium {
    private String username;
    private String password;
    private LocalDate date;
    private boolean premium;


    public User(String username, String password, LocalDate date, boolean premium) {
        this.username = username;
        this.password = password;
        this.date = date;
        this.premium = premium;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password) && date.equals(user.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, date);
    }

    public boolean checkPassword(String passwordAttempt) {
        boolean checkPassword = false;
        return passwordAttempt.equalsIgnoreCase(password);
    }

    public boolean updatePassword(String password, String newPassword){
        if (checkPassword(password)){
            this.password = newPassword;
            return true;
        } else return false;
    }

    @Override
    public boolean isPremium() {
        return true;
    }
}
