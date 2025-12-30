package ie.atu.sw;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Menu {
//    private final ArraysProcessor ag = new ArraysProcessor();
    private final Scanner scanner;
//    private static final ArrayMode ENCODE_MODE = ArrayMode.ENCODE;
//    private static final ArrayMode DECODE_MODE = ArrayMode.DECODE;
    private static final String DEFAULT_FILE_LOCATION = "./out.txt";

    private static String mapFileLocation = "";
    private static String inputFileLocation = "";
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

            Object[][] mapItems;
            switch (menuItem) {
                case 1 -> {
//                    mapFileLocation = getFileLocationFromUser("Please enter the location of the mapping file",
//                            "Mapping");
                }
                case 2 -> {
//                    inputFileLocation = getFileLocationFromUser("Please enter the location of the input file",
//                            "Input");
                }
                case 3 -> {
//                    outputFileLocation = getFileLocationFromUser("Please enter the location of the output file",
//                            "Output");
                }
                case 4 -> {
                    isEmptyFileLocations(mapFileLocation, inputFileLocation);
//                    mapItems = ArraysProcessor.getArray(mapFileLocation);
                    if (!mapFileLocation.isEmpty() && !inputFileLocation.isEmpty()) {
                        out.println("Begin Encoding");
                        if (outputFileLocation.isEmpty()) {
//                            CipherProcessor.decode(inputFileLocation, mapItems, ENCODE_MODE);
                        } else {
//                            FileReader.processFile(inputFileLocation, outputFileLocation, mapItems, ENCODE_MODE);
                        }
                    } else {
                        UtilMethods.printErrorMessage("Map file location or input file location isn't specified");
                    }
                }
                case 5 -> {
//                    out.println("Begin Decoding");
//
//                    mapItems = ArraysProcessor.getArray(mapFileLocation);
//                    CipherProcessor.decode(DEFAULT_FILE_LOCATION, mapItems, DECODE_MODE);
                }
                case 6 -> {
                    out.println("Goodbye! 6");
                    keepRunning = false;
                }
                default -> {
                    out.println("Invalid Option Selected");
                }
            }
        } catch (NumberFormatException e) {
            UtilMethods.printErrorMessage("Invalid array Index: ", e);
        }
        return keepRunning;
    }


    /**
     * @param fileType Type of file for
     * @return The file location got from the user
     */
    private String getRequestFromUser(String promptMessage, String fileType) {
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
     * @param mapFileLocation   location of mapFile
     * @param inputFileLocation location of an input file
     * @return true if something isn't specified
     */
    private static boolean isEmptyFileLocations(String mapFileLocation, String inputFileLocation) {
        boolean isNoFile = false;
        String logMessage;
        if (mapFileLocation.isEmpty()) {
            logMessage = UtilMethods.buildString("No mapping file specified");
            out.println(logMessage);
            isNoFile = true;
        }
        if (inputFileLocation.isEmpty()) {
            out.println("No input file specified");
            isNoFile = true;
        }

        return isNoFile;
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
        System.out.println("(5) Optional Extras...");
        System.out.println("(?) Quit");

        //Output a menu of options and solicit text from the user
        System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
        System.out.print("Select Option [1-4]>");
        System.out.println();

    }

}
