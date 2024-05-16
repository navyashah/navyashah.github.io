package model.parties;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is a parent class to the parties.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Party implements Comparable<Party> {
    private String hostName;
    private String name;
    private String location;
    private LocalDate date;
    private String[] guests;


    public Party(String hostName, String name, String location, LocalDate date, String[] guests) {
        this.hostName = hostName;
        this.name = name;
        this.location = location;
        this.date = date;
        this.guests = guests;
    }

    public Party(String hostName, String name, String location, LocalDate date) {
        this.hostName = hostName;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public Party() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String[] getGuests() {
        return this.guests;
    }

    public String[] getNewGL(){
        String[] newGL;
        return newGL = new String[50];
    }

    public String getHostName() {
        return hostName;
    }

    /**
     * Adds guest to the guest list of a certain party
     * @return guests This is an array of all the guests invited and RSVP'd
     */
    public String[] addGuests(String guestName){
        int count = 0;
        for(int i = 0; i < guests.length; i++) {
            if (count == 0) {
                if (guests[i] == null) {
                    guests[i] = guestName;
                    count++;
                }
            }
        }
        return this.guests;
    }

    /**
     * Adds guest to the guest list of a new party
     * This is because the previous guestlist is initialized when reading data in SetUp
     * New guestlist created and then assigned to the old one
     * @return guests This is an array of all the guests invited and RSVP'd for a new party
     */
    public String[] addGuestsToNewGL(String guestName){
        int count = 0;
        for(int i = 0; i < getNewGL().length; i++) {
            if (count == 0) {
                if (getNewGL()[i] == null) {
                    getNewGL()[i] = guestName;
                    count++;
                }
            }
            guests = getNewGL();
        }
        return guests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return name.equals(party.name) && location.equals(party.location) && date.equals(party.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, date);
    }

    @Override
    public String toString() {
        return hostName + " is hosting " + name + " at " + location + " on " + date;
    }

    @Override
    public int compareTo(Party other) {
        int value = -1 * this.date.compareTo(other.date);
        if (value == 0){
            value = this.name.compareTo(other.name);
        }
        return value;
    }
}
