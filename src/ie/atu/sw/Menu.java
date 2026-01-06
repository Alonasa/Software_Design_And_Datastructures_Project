package ie.atu.sw;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static java.lang.System.in;
import static java.lang.System.out;

public class Menu {
    private final Scanner scanner;
    private static final String DEFAULT_FILE_LOCATION = "./out.txt";

    private static String queryFileLocation = "";
    private static String subjectFileLocation = "";
    private static String outputFileLocation = "";


    public Menu() {
        scanner = new Scanner(in, StandardCharsets.UTF_8);
    }

    /**
     * @param keepRunning identify if the user still wants to work with the program
     * @return while return true, we see the menu and got an offer to make an input
     */
    public boolean processMenuInput(boolean keepRunning) {

        try {
            String next = scanner.next();
            int menuItem = UtilMethods.convertToNumber(next);
            ThreadsProcessor threadsProcessor = new ThreadsProcessor();

            switch (menuItem) {
                case 1 -> {
                    queryFileLocation = getFileLocationFromUser("Enter Query File",
                            "Query");
                }
                case 2 -> {
                    subjectFileLocation = getFileLocationFromUser("Enter Subject File",
                            "Subject");
                }
                case 3 -> {
                    outputFileLocation = getFileLocationFromUser("Please enter the location of the output file",
                            "Output");
                }
                case 4 -> {
                    isEmptyFileLocations(queryFileLocation, subjectFileLocation);
                    if (!queryFileLocation.isEmpty() && !subjectFileLocation.isEmpty()) {
                        out.println("Begin Analysis");
                        if (outputFileLocation.isEmpty()) {
                            threadsProcessor.go(queryFileLocation, subjectFileLocation, DEFAULT_FILE_LOCATION);
                        } else {
                            threadsProcessor.go(queryFileLocation, subjectFileLocation, outputFileLocation);
                        }
                    } else {
                        UtilMethods.printErrorMessage("Query file location or Subject file location is empty");
                    }
                }
                case 5 -> {
                    out.println("Goodbye! 5");
                    keepRunning = false;
                }
                default -> {
                    out.println("Invalid Option Selected");
                }
            }
        } catch (NumberFormatException e) {
            UtilMethods.printErrorMessage("Invalid array Index: ", e);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return keepRunning;
    }


    /**
     * @param fileType Type of file for
     * @return The file location got from the user
     */
    private String getFileLocationFromUser(String promptMessage, String fileType) {
        out.println(promptMessage);
        scanner.nextLine(); // Consume the newline character
        String fileLocation = scanner.nextLine();
        String logMessage = UtilMethods.buildString(fileType, " file location:", fileLocation);
        out.println(logMessage);
        return fileLocation;
    }


    /**
     * Checks if passing empty file location and print message
     *
     * @param queryFileLocation  location of queryFile
     * @param subjectFileLocation location of the subject file
     */
    private static void isEmptyFileLocations(String queryFileLocation, String subjectFileLocation) {
        String logMessage;
        if (queryFileLocation .isEmpty()) {
            logMessage = UtilMethods.buildString("No file specified");
            out.println(logMessage);
        }
        if (subjectFileLocation.isEmpty()) {
            out.println("No input file specified");
        }
    }


    public void renderMenu() {
        //You should put the following code into a menu or Menu class
        System.out.println(ConsoleColour.WHITE);
        System.out.println("************************************************************");
        System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
        System.out.println("*                                                          *");
        System.out.println("*      Comparing Text Documents with Virtual Threads       *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("(1) Enter Query File");
        System.out.println("(2) Enter Subject File");
        System.out.println("(3) Specify Output File (default: ./out.txt)");
        System.out.println("(4) Execute, Analyse and Report");
        System.out.println("(5) Quit");

        //Output a menu of options and solicit text from the user
        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
        System.out.print("Select Option [1-4]>");
        System.out.println();

    }

}
