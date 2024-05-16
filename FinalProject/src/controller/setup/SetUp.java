package controller.setup;

import model.parties.*;
import model.people.Guest;
import model.people.Host;
import model.people.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * This class reads in data from files, parses it and sets up the hashmaps
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public class SetUp {
    private static final String USER_FILE = "src/controller/setup/users.csv";
    private static final String PARTY_FILE = "src/controller/setup/parties.csv";

    /**
     * Reads users into a list from the file.
     * // method based on code in A11 written by Prof. Walther
     * @return allUsers
     */
    public static List<User> setUpUsers() {
        List<User> allUsers = new ArrayList<>();
        readUserFile(USER_FILE, allUsers);
        return allUsers;
    }

    /**
     * Reads party into a list from the file.
     * // method based on code in A11 written by Prof. Walther
     * @return allParties
     */
    public static List<Party> setUpParties() {
        List<Party> allParties = new ArrayList<>();
        readPartyFile(PARTY_FILE, allParties);
        return allParties;
    }

    /**
     * Sets up a hashmap of all parties
     * @return systemParties
     */
    public static Map<PartyType, List<Party>> setUpPartyMap() {
        Map<PartyType, List<Party>> systemParties = new HashMap<>();
        for (PartyType pt : PartyType.values()) {
            ArrayList<Party> list = new ArrayList<>();
            systemParties.put(pt, list);
        }

        for (Party pEach : setUpParties()) {
            System.out.println("\t" + pEach); // seeing each product
            // figure out product type with the enum
            PartyType key = PartyType.getTypeByName(pEach.getClass().getSimpleName());
            systemParties.get(key).add(pEach);
        }
        return systemParties;
    }

    /**
     * Sets up a hashmap of all users
     * @return systemUsers
     */
    public static Map<String, User> setUpUserMap() {
        Map<String, User> systemUsers = new HashMap<>();
        for (User u : setUpUsers()) {
            //ArrayList<User> list = new ArrayList<>();
            systemUsers.put(u.getUsername(), u);
        }

        return systemUsers;
    }

    /**
     * Reads parties from CSV file and adds it to
     * @param partyFile This is the CSV file
     * @param allParties This is the list
     */
    private static void readPartyFile(String partyFile, List<Party> allParties) {
        try (FileInputStream fis = new FileInputStream(partyFile);
             Scanner scan = new Scanner(fis)) {
            if (scan.hasNext()) {
                String header = scan.nextLine();
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    Party p = parseParty(line);
                    if (p != null) {
                        allParties.add(p);
                    }
                }
            } else {
                System.err.println("File was empty: " + partyFile);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + partyFile);
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * Parse parties from reading file method
     * @param line This is the String to be scanned
     */
    private static Party parseParty(String line) {
        Party p = null;
        try {
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            String type = sc.next();
            String hostName = sc.next();
            String name = sc.next();
            String location = sc.next();
            String date = sc.next();
            //getting other data column String other = sc.nextLine();
            if (type.equalsIgnoreCase("Costume")) {
                String theme = sc.next();
                String guests = sc.next();
                String[] GL = new String[50];
                String [] guestsArray = guests.split(" ");
                for (int i = 0; i < guestsArray.length; i++){
                    GL[i] = guestsArray[i];
                }
                p = new Costume(hostName, name, location, LocalDate.parse(date), GL, theme);
            } else if (type.equalsIgnoreCase("Exclusive")) {
                int age = sc.nextInt();
                String guests = sc.next();
                String[] GL = new String[50];
                String [] guestsArray = guests.split(" ");
                for (int i = 0; i < guestsArray.length; i++){
                    GL[i] = guestsArray[i];
                }
                p = new Exclusive(hostName, name, location, LocalDate.parse(date), GL, age);
            } else if (type.equalsIgnoreCase("FavorParty")) {
                String favor = sc.next();
                String guests = sc.next();
                String[] GL = new String[50];
                String [] guestsArray = guests.split(" ");
                for (int i = 0; i < guestsArray.length; i++){
                    GL[i] = guestsArray[i];
                }
                p = new Favor(hostName, name, location, LocalDate.parse(date), GL, favor);
            } else if (type.equalsIgnoreCase("Potluck")) {
                String food = sc.next();
                String guests = sc.next();
                String[] GL = new String[50];
                String [] guestsArray = guests.split(" ");
                for (int i = 0; i < guestsArray.length; i++){
                    GL[i] = guestsArray[i];
                }
                p = new Potluck(hostName, name, location, LocalDate.parse(date), GL, food);
            }
        } catch (Exception e) {
            System.err.println("Error reading line of file: " + line + "\nerror; " + e);
        }
        return p;
    }

    /**
     * Reads users from CSV file and adds it to
     * @param userFile This is the CSV file
     * @param allUsers This is the list
     */
    private static void readUserFile(String userFile, List<User> allUsers) {
        try (FileInputStream fis = new FileInputStream(userFile);
             Scanner scan = new Scanner(fis)) {  // NOTE: resources will be closed automatically with this try block
            if (scan.hasNext()) {
                String header = scan.nextLine();
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    User u = parseUser(line);
                    if (u != null) {
                        allUsers.add(u);
                    }
                }
            } else {
                System.err.println("File was empty: " + userFile);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + userFile);
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * Parse users from reading file method
     * @param line This is the String to be scanned
     */
    private static User parseUser(String line) {
        User u = null;
        try {
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            String type = sc.next();
            String name = sc.next();
            String password = sc.next();
            String birthday = sc.next();
            boolean premium = sc.nextBoolean();
            if (type.equalsIgnoreCase("Guest")) {
                u = new Guest(name, password, LocalDate.parse(birthday), premium);
            } else if (type.equalsIgnoreCase("Host")) {
                u = new Host(name, password, LocalDate.parse(birthday), premium);
            }

        } catch (Exception e) {
            System.err.println("Error reading line of file: " + line + "\nerror; " + e);
        }
        return u;
    }
}
