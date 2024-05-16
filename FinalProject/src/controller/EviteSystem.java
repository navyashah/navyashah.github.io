package controller;
import controller.setup.SetUp;
import model.parties.*;
import model.people.Guest;
import model.people.Host;
import model.people.User;
import view.BFF;
import java.time.LocalDate;
import java.util.*;

/**
 * This program runs the EviteSystem program
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class EviteSystem {
    private Map<String, User> userDatabase;
    private Map<PartyType, List<Party>> partyDatabase;
    private BFF helper;
    private User currentUser;
    private User tempAccount;

    public EviteSystem() {
        userDatabase = SetUp.setUpUserMap();
        partyDatabase = SetUp.setUpPartyMap();
        helper = new BFF();
        tempAccount = new User("Temp", "123");
        currentUser = tempAccount;
    }

    public static void main(String[] args) {
        EviteSystem start = new EviteSystem();
        start.run();
    }

    /**
     * Run method that displays menu options from EviteMenu Enum
     * Uses a switch method to change through the menu based on user inputted option
     */
    public void run() {
        boolean done = false;
        while (!done) {
            System.out.println(EviteMenu.getMenuString());
            int userChoice = helper.inputInt("Enter your menu choice: ", 0, EviteMenu.getMaxChoice());
            EviteMenu option = EviteMenu.getOptionNumber(userChoice);
            switch (option) {
                case CREATE_ACCOUNT:
                    createAccount();
                    userDatabase.put(currentUser.getUsername(), currentUser);
                    break;
                case LOGIN:
                    login();
                    break;
                case LOGOUT:
                    currentUser = null;
                    break;
                case CHANGE_PASSWORD:
                    if (verifyUser()) {
                        String oldPass = helper.input("Enter current password: ");
                        String newPass = helper.input("Enter a new password: ");
                        currentUser.updatePassword(oldPass, newPass);
                    }
                    break;
                case RSVP:
                   if(verifyUser()){
                       RSVPByType();
                   }
                    break;
                case UPGRADE_PREMIUM:
                    upgradePremium();
                    userDatabase.put(currentUser.getUsername(), currentUser);
                    break;
                case UPGRADE_HOST:
                    upgradeGuestToHost();
                    userDatabase.put(currentUser.getUsername(), currentUser);
                    break;
                case HOST_PARTY:
                    if (verifyUser()) {
                        //creating the party and sending guest invites
                        Party p = createParty();
                        //adding to hosting parties for host and overall party database
                        ((Host)currentUser).addParty(p);
                        System.out.println(((Host) currentUser).getPartiesHosting());
                        PartyType key = PartyType.getTypeByName(p.getClass().getSimpleName());
                        partyDatabase.get(key).add(p);
                    }
                    break;
                case UPDATE_PARTY:
                    if (verifyUser()){
                        updateParty();
                    }
                    break;
                case QUIT:
                    done = true;
                    break;
                default:
                    System.out.println("Option not yet implemented");
            }
        }
    }

    /**
     * Allows current user to create an account
     *     // method based on code in A07 written by me
     */
    public void createAccount() {
        //USERNAME INPUT
        String name = helper.input("Enter a username: ");
        if (findUser(name) != null) {
            helper.print("This user already exists. Pick other username.");
            name = helper.input("Enter a username: ");
        }
        //BDAY INPUT
        helper.print("Birthday?");
        int year = helper.inputInt("Year: ", 1900, 2023);
        int month = helper.inputInt("Month: ", 1, 12);
        int day = helper.inputInt("Day: ", 1, 31);
        LocalDate bday = LocalDate.of(year, month, day);
        boolean premium = helper.inputYesNo("Would like a Premium membership? (y/n)");
        //PASSWORD INPUT
        String password = helper.input("Enter a password: ");
        String confirmPass = helper.input("Confirm the password: ");
        // Another try to get right combination, if not then account is not created
        if (!password.equals(confirmPass)) {
            helper.print("The passwords don't match, try again.");
            password = helper.input("Enter a password: ");
            confirmPass = helper.input("Confirm the password: ");
        }
        if (!password.equals(confirmPass)) {
            helper.print("An account was not created");
            System.exit(0);
        }
        String type = helper.input("Would you like to make a Guest or Host account?");
        if (type.equalsIgnoreCase("Host")) {
            currentUser = new Host(name, password, bday, premium);
        }
        else if (type.equalsIgnoreCase("Guest")){
            currentUser = new Guest(name, password, bday, premium);
        }
        // Creates the user with input and adds to database
        SetUp.setUpUsers().add(currentUser);
        helper.print("Created new account and logged in user");
    }

    /**
     * Allows existing user to be able to log in if the password is correct
     *     // method based on code in A07 written by me
     */
    public void login(){
        String name = helper.input("Enter username: ");
        User u0 = findUser(name);
        if (u0 != null){
            String passwordAttempt = helper.input("Enter password:");
            boolean quit = false;
            while (!quit){
                if (u0.checkPassword(passwordAttempt)){
                    currentUser = u0;
                    quit = true; // if password is same then loop exits
                }
                else {
                    System.out.println("Sorry, wrong password. Try again");
                }
            }
        }
        else {
            helper.print("User does not exist, create an account.");
        }
    }

    /**
     * Finds the user by input if it matches a username in the database, returns the user.
     * @param name This is name of the user to find
     * @return user This is the user that matches that name
     */
    private User findUser(String name) {
        User user = null;
        for (User u : SetUp.setUpUsers()) {
            if (u.getUsername().equalsIgnoreCase(name)){
                user = u; //sets user to the found u from the database that matches inputted username
            }
        }
        return user;
    }

    /**
     * Verifies if the user is logged in.
     * @return boolean returns true if user is logged in
     */
    private boolean verifyUser(){
        boolean verify = true;
        if (currentUser == tempAccount){
           System.out.println("You must be logged in to complete this action");
           verify = false;
        }
        return verify;
    }

    /**
     * Displays the usernames with numbered list
     */
    private void displayUsernames() {
        int i = 0;
        for (User u : userDatabase.values()) {
            helper.print((i++) + " " + u.getUsername());
        }
    }

    /**
     * Finds the party by input if it matches a pary name in the database, returns the party.
     * @param name This is name of the party to find
     * @return party This is the party that matches that name
     */
    private Party findPartyName(String name) {
        Party party = null;
        for (Party p : SetUp.setUpParties()) {
            if (p.getName().equalsIgnoreCase(name)){
                party = p; //sets user to the found u from the database that matches inputted username
            }
        }
        return party;
    }

    /**
     * Creates a party by asking user for input than builds it up to a Party object.
     * @return party This is the party created with user input
     */
    public Party createParty() {
        Party p = new Party();
        String name = helper.input("Name your party?");
        if (findPartyName(name) != null) {
            helper.print("This party already exists. Pick another name.");
            name = helper.input("Name your party?");
        }
        String location = helper.input("Location?");
        LocalDate date = helper.inputDate("Date?");
        //inviting guests
        int i = 0;
        for (String userName : userDatabase.keySet()) {
            System.out.println(i + " : " + userName);
            i++;
        }
        boolean done = true;
        while (done) {
            String guestName = helper.input("Name of guest to invite: ");
            if (findUser(guestName) == null) {
                helper.print("There is no user named that. Try again.");
                guestName = helper.input("Name of guest to invite: ");
            }
            p.addGuestsToNewGL(userDatabase.get(guestName).getUsername());
            //((Host) currentUser).inviteGuestToParty(p, userDatabase.get(guestName).getUsername());
            done = helper.inputBoolean("Would you like to invite another guest? (true/false)");
        }
        switch (selectType()) {
            case FAVOR -> {
                String favorDesc = helper.input("What favors will you give out?");
                p = new Favor(currentUser.getUsername(), name, location, date, p.getGuests(), favorDesc);
            }
            case COSTUME -> {
                String theme = helper.input("What theme is the party?");
                p = new Costume(currentUser.getUsername(), name, location, date, p.getGuests(), theme);
            }
            case POTLUCK -> {
                String foodTheme = helper.input("A theme for the food?");
                p = new Potluck(currentUser.getUsername(), name, location, date, p.getGuests(), foodTheme);
            }
            case EXCLUSIVE -> {
                if (currentUser.isPremium()) {
                    int age = helper.inputInt("Enter an age limit:");
                    //inviting guests
                    p = new Exclusive(currentUser.getUsername(), name, location, date, p.getGuests(), age);
                } else {
                    System.out.println("Sorry! Only Premium members can host Exclusive parties.");
                    boolean choice = helper.inputBoolean("Would you like to upgrade to premium? (y/n)");
                    if (choice) {
                        currentUser.isPremium();
                    }
                }
            }
        }
        return p;
    }

    /**
     * Selects a party type from the enum PartyType
     *      // method based on code in A11 written by Prof. Walther
     * @return type This is the PartyType received from user input options
     */
    private PartyType selectType() {
        PartyType type = PartyType.UNKNOWN;
        helper.print(PartyType.makePartyTypeMenu());
        int categoryOption = helper.inputInt("Category #", 1, PartyType.values().length - 1);
        type = (PartyType.values()[categoryOption]);
        return type;
    }

    /**
     * Gets the party type and runs the RSVP method
     *      // method based on code in A11 written by Prof. Walther
     */
    private void RSVPByType() {
        PartyType type = selectType();
        RSVPPartyFromList(partyDatabase.get(type), type);
    }

    /**
     * RSVPs to parties that are in the party database
     * @param parties This is a list sorted that contains all the parties
     * @param type This is the type from the PartyType enum selected in earlier methods
     */
    private void RSVPPartyFromList(List<Party> parties, PartyType type) {
        Collections.sort(parties);
        if(parties.isEmpty()) {
            helper.print("No parties available to RSVP");
        }
        else { // if there are parties, then can RSVP
            do {
                // gets user input and makes list of parties formatted
                String options = "Select party to RSVP:\n";
                for (int i = 0; i < parties.size(); i++) {
                    Party p = parties.get(i);
                    String select = i + ": " + p.getHostName() + "'s " + p.getClass().getSimpleName() + " Party\n";
                    options += select;
                }
                // gets user input for which party
                int partyNum = helper.inputInt(options + "\nChoose party # (or -1 to not RSVP) >>> ", 0, parties.size() - 1, -1);
                if (partyNum != -1) {
                    Party p = parties.get(partyNum);
                    // checks if guest is already on guestlist
                    if(Guest.alreadyOnGuestlist(p, currentUser.getUsername())){
                        System.out.println("You are already on the guestlist!");
                    }
                    else {
                        //if not on guestlist, adds guest and prints the new list
                        String[] guests = ((Guest)currentUser).rsvpToParty(p, currentUser.getUsername());
                        System.out.println("Here is the updated guestlist:");
                        int j = 1;
                        for (String name : guests) {
                            if (name != null){
                                helper.print((j++) + " : " + name); //printing guests
                            }
                        }
                    }
                }
            } while (helper.inputYesNo("Would you like to RSVP to other " + type + " parties?(y/n)"));
        }
    }

    /**
     * Checks if currentuser is a Premium member, if they are not then upgrades them
     * Upgrade done using the interface and sets it as a Premium
     */
    public void upgradePremium() {
        if (!currentUser.isPremium()) {
            System.out.println("Looks like you are not a Premium member");
            boolean choice = helper.inputYesNo("Would you like to upgrade your account? (y/n) \n" +
                    "It would be a charge of $4.99 per month from the card on file.");
            if (choice) {
                currentUser.isPremium();
            } else {
                System.out.println("We're sorry to hear that. Redirecting you back to menu.");
            }
        }
        else{
            System.out.println("You're already a Premium member!");
        }
    }

    /**
     * Checks if currentuser is a Host, if they are not then upgrades them
     * Upgrade done by setting the current user to a Host user object
     */
    public void upgradeGuestToHost() {
        if (currentUser instanceof Guest) {
            System.out.println("Looks like you are not a Host");
            boolean choice = helper.inputYesNo("Would you like to upgrade your account to a Host? (y/n) \n" +
                    "It would be a charge of $1.99 per month from the card on file.");
            if (choice) {
                currentUser = new Host((Guest) currentUser);
            } else {
                System.out.println("We're sorry to hear that. Redirecting you back to menu.");
            }
        } else {
            System.out.println("You are already a Host!");
        }
    }

    /**
     * Adds hosted parties from the database
     * Done by matching the username of currentUser to the hostname in the party object
     */
    public void addHostingFromDB(Host h) {
        String output = null;
        List<Party> parties = new ArrayList<>();
        Collections.sort(parties);
        if(parties.isEmpty()) {
            helper.print("No parties hosting");
        }
        else{
            for (int i = 0; i < parties.size(); i++){
                if (parties.get(i).getHostName().equalsIgnoreCase(h.getUsername())){
                    h.getPartiesHosting().add(parties.get(i));
                }
            }
        }
    }

    /**
     * Updates a party created in current session by a Host user.
     * FOR V2: Include parties hosting read in from csv
     */
    public void updateParty() {
        // get the list of parties hosted by the given host
        if (((Host)currentUser).getPartiesHosting().isEmpty()) {
            System.out.println("You don't have any parties to update.");
        }
        else {
            do {
                // print out the parties formatted in a list with numbers
                for (int i = 0; i < ((Host)currentUser).getPartiesHosting().size(); i++){
                    System.out.println(i + ": " + ((Host)currentUser).getPartiesHosting().get(i).getName());
                }
                // gets user input for which party to update and gets it from hashmap
                int choiceNum = helper.inputInt( "\nChoose party # >>> ", 0, ((Host)currentUser).getPartiesHosting().size() - 1, -1);
                Party p = ((Host) currentUser).getPartiesHosting().get(choiceNum);
                // options for updating party
                System.out.println("Select an option to update:");
                System.out.println("1. Change the party name");
                System.out.println("2. Change the party location");
                System.out.println("3. Change the party date");
                System.out.println("4. Add a guest to the party");
                int option = helper.inputInt("Select an option to update: ", 1, 4);
                // switch method to update based on user choice
                switch (option) {
                    case 1:
                        String newName = helper.input("Enter the new name:");
                        p.setName(newName);
                        System.out.println("Party name updated.");
                        break;
                    case 2:
                        String newLocation = helper.input("Enter the new location:");
                        p.setLocation(newLocation);
                        System.out.println("Party location updated.");
                        break;
                    case 3:
                        String date = helper.input("Enter the new date:");
                        p.setDate(LocalDate.parse(date));
                    case 4:
                        String guestName = helper.input("Enter the name of the guest to add:");
                        ((Host)currentUser).inviteGuestToParty(p, guestName);
                        System.out.println("Guest added to the party.");
                        break;
                }
            } while (helper.inputYesNo("Would you like to update to other parties?(y/n)"));
        }
    }
}
