package model.people;
import model.parties.Party;
import java.time.LocalDate;

/**
 * This class creates a guest user with added functionality of RSVPing to parties.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Guest extends User{
    //Map<String, Party> partiesInvited;

    public Guest(String username, String password, LocalDate date, boolean premium) {
        super(username, password, date, premium);
        //this.partiesInvited = new HashMap<>();
    }
    public Guest(Guest g){
        super(g.getUsername(), g.getPassword(), g.getDate(), g.isPremium());
    }

    /**
     * Adds guest to the guest list of a certain party by the guest RSVPing
     * @param party This is the party for which the guestlist is for
     * @param guestName This is the guestName RSVPing
     */
    public String[] rsvpToParty(Party party, String guestName) {
        party.addGuests(guestName);
        System.out.println(getUsername() + " has RSVP'd to " + party.getHostName() + "'s Party.");
       return party.getGuests();
    }

    /**
     * Checks if guest is already on the guestlist
     * @param party This is the party for which the guestlist is for
     * @param guestName This is the guestName invited
     * @return boolean This is a boolean for whether they are on the guestlist or not
     */
    public static boolean alreadyOnGuestlist(Party party, String guestName) {
        String[] checkGuest = party.getGuests();
        for (String name : checkGuest) {
            if(name != null){
                if (name.equals(guestName)) {
                    return true;
                }
            }
        }
        return false;
    }
}

