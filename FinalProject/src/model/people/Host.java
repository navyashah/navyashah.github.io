package model.people;
import model.parties.Party;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a host user with added functionality of hosting and inviting guests to parties.
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class Host extends Guest{
    private List<Party> partiesHosting;

    public Host(String username, String password, LocalDate date, boolean premium) {
        super(username, password, date, premium);
        this.partiesHosting = new ArrayList<>();
    }

    public Host(Guest g){
        super(g);
        this.partiesHosting = new ArrayList<>();
    }

    public List<Party> getPartiesHosting() {
        return partiesHosting;
    }

    /**
     * Adds parties to partiesHosting list
     */
    public void addParty(Party p){
      partiesHosting.add(p);
    }

    /**
     * Adds guest to the guest list of a certain party by the host invited
     * @param party This is the party for which the guestlist is for
     * @param guestName This is the guestName invited
     */
    public void inviteGuestToParty(Party party, String guestName) {
        party.addGuests(guestName);
        System.out.println(getUsername() + " has invited " + guestName + " to their " + party.getName() + " party.");
    }
}
