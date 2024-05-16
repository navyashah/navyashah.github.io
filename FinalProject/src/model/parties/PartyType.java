package model.parties;

/**
 * This enum class is used as types for different party classes
 *
 * @author Navya Shah
 * Spring 2023, ITP 265, Boba
 * Email: navyasha@usc.edu
 */

public enum PartyType {
    UNKNOWN,
    FAVOR,
    POTLUCK,
    COSTUME,
    EXCLUSIVE;

    /**
     * Makes a menu by the enum values and indexes it
     * @return typeMenu is the String of the ordinals and the names of the PartyType enum
     */
    public static String makePartyTypeMenu() {
        String typeMenu = "Choose a category of the parties: ";
        for(PartyType t : PartyType.values()){
            if(t != UNKNOWN) {
                typeMenu += "\n" + (t.ordinal())
                        + ": " + t.name();
            }
        }
        return typeMenu;
    }

    /**
     * Gets the enum by the name
     * @return name is the String matched with the PartyType enum
     */
    public static PartyType getTypeByName(String name) {
        return  PartyType.valueOf(name.toUpperCase());
    }
}
