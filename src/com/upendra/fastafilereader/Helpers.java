package com.upendra.fastafilereader;

/**
 * Class holds common helper methods.
 * Created by ukumbham on 08/10/2018.
 */
public class Helpers {
    /**
     * Parses the String argument as an Integer.
     * Returns default value if invalid input is provided.
     * @param number
     * @param defaultVal
     * @return
     */
    public static int parseIntWithDefault(String number, int defaultVal) {
        if(number == null)
            return defaultVal;
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
