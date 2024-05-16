package controller;

/**
 * This enum class is used as a menu for the main program
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public enum EviteMenu {
    CREATE_ACCOUNT("Create Account"),
    LOGIN("Login"),
    LOGOUT("Logout"),
    CHANGE_PASSWORD("Change password"),
    RSVP("RSVP to a party"),
    UPGRADE_PREMIUM("Upgrade your account to Premium"),
    UPGRADE_HOST("Upgrade your account from Guest to Host"),
    HOST_PARTY("Host your own party"),
    UPDATE_PARTY("Update a preexisting party (ONLY ONES MADE IN THIS SESSION)"),
    QUIT("Quit");

    private final String option;

    private EviteMenu(String description) {
        this.option = description;
    }

    /**
     * Using the user input, the EviteMenu enum value is retrieved
     * @return n This is the number of menu option
     */
    public static EviteMenu getOptionNumber(int n) {
        return EviteMenu.values()[n];
    }

    /**
     * Prints out EviteMenu enum with ordinal values as the index
     * @return menu This is the String of the menu as indexed options
     */
    public static String getMenuString(){
        String menu = "*".repeat(50) + "\n";
        menu += " Welcome to Evite System! Here are the options: \n";
        for(EviteMenu op: EviteMenu.values()) {
            menu += op.ordinal() + ":" + op.option + "\n";
        }
        menu+= "*".repeat(50) + "\n";
        return menu;
    }

    /**
     * Gets the maximum choice by the returning length of the enum
     * @return int This is the length of EviteMenu enum
     */
    public static int getMaxChoice(){
        return EviteMenu.values().length -1;
    }
}
