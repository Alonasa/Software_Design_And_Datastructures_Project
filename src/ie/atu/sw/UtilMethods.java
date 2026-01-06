package ie.atu.sw;


/**
 * Utility class providing helper methods for string manipulation, error message handling,
 * and conversion of strings to numbers. This class is designed for general-purpose use
 * within other classes and contains static methods that can be accessed directly.
 * @author Alona Skrypnyk
 * @version 1.0
 */
public class UtilMethods {
    private UtilMethods() {
    }

    /**
     * Constructs a single string from provided arguments.
     *
     * @param args one or more objects
     * @return single string value
     */
    public static String buildString(Object... args){
        StringBuilder messageString = new StringBuilder(4);
        for (Object arg: args){
            messageString.append(" ").append(arg);
        }
        return messageString.toString().trim();
    }


    /**
     * Create a custom error message to optimise rewriting
     * @param format initial value of the string
     * @param args the rest of parameters for future message
     */
    public static void printErrorMessage(String format, Object... args) {
        StringBuilder message = new StringBuilder(format);

        for (Object arg : args) {
            if (arg instanceof Throwable throwable) {
                String errorMessage = throwable.getMessage();
                // Appended properly spaced error message
                message.append(" ").append(errorMessage);
            } else {
                message.append(" ").append(arg);
            }
        }

        String messageToPrint = message.toString().trim();
        // Use System.err for errors to better distinguish error messages
        System.err.println(messageToPrint);
    }


    /**
     * Used as helper function in convertion string to numbers
     * @param element which we will try to convert to integer
     * @return integer if convertion was successful or 0
     */
    public static int convertToNumber(String element) {
        int defaultValue = 0;
        if (element == null || element.isEmpty()) {
            printErrorMessage("Invalid input: null or empty string. Returning default value 0.");
            return defaultValue; // Default fallback value
        }

        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException e) {
            printErrorMessage("Unable to parse '%s' as an integer. Returning default value 0.", element);
            return defaultValue; // Default fallback value
        }
    }
}
