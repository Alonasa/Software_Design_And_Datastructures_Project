package ie.atu.sw;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The FileReader class is responsible for processing file operations:
 * <ul>
 *   <li>Buffered file reading with a progress indicator</li>
 *   <li>Lowercased word tokenisation without punctuation</li>
 *   <li>Similarity report writer</li>
 * </ul>
 * Thread-safety: builds maps using {@code ConcurrentHashMap}.
 */
public class FilesProcessor {
    //Filtering patterns
    private static final String BY_WORD = "\\s+";
    private static final String BY_PUNCTUATION = "[^a-z0-9]+";


    /**
     * Processes a file by reading its contents and building its words map
     * for future processing
     *
     * @param source the path to the source file
     */
    public static Map<String, Integer> processFile(String source) {
        Map<String, Integer> countedElements = new ConcurrentHashMap<>();

        try {
            BufferedReader br = getBufferedReader(source);
            int amountOfLines = getAmountOfLines(source);
            int linesProcessed = 0;

            String line;


            while ((line = br.readLine()) != null) {
                System.out.print(ConsoleColour.YELLOW);//Change the colour of the console text
                ProgressBar.printProgress(linesProcessed + 1, amountOfLines);
                //Generate a mini array of words from a line element
                String[] words = line.toLowerCase(Locale.ROOT).replaceAll(BY_PUNCTUATION, " ").split(BY_WORD);

                tokeniseWords(words, countedElements);

                linesProcessed++;
            }
            br.close();
            System.out.println("Successfully processed file");

        } catch (IOException e) {
            UtilMethods.printErrorMessage("I/O Error on processing: ", e);
        }

        return countedElements;
    }

    /**
     * Words tokenisation, Concurrent hash map was used for the work with threads
     * @param words the array of the words for process
     */
    public static void tokeniseWords (String[] words, Map<String, Integer> countedElements){
        for(String word: words) {
            if(!word.isEmpty()){
                countedElements.put(word, countedElements.getOrDefault(word, 0) + 1);
            }
        }
    }

    /**
     * Generate a report to the file defined by the user
     * @param outputFile output file location
     */
    public static void writeReport(String outputFile, String report) {
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile), 8192);
            out.write(report);
            out.flush();
            out.close();
            System.out.println("Report created");

        } catch (IOException e) {
            UtilMethods.printErrorMessage("I/O Error on processing: ", e);
        }

    }

    /**
     *
     * @param subjectFileLocation subject file
     * @param queryFileLocation query file
     * @param subjectTokens subject tokens amount
     * @param queryTokens query tokens amount
     * @param intersection intersection size
     * @param percent percentage similarity
     * @return string for .txt generation
     */
    public static String createReport(String subjectFileLocation,
                                      String queryFileLocation,
                                      Integer subjectTokens,
                                      Integer queryTokens,
                                      Integer intersection,
                                      Double percent) {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format(
                "************************************************************%n" +
                        "                    REPORT  (%s)%n                       %n" +
                        "************************************************************%n" +
                        "Subject File: %s%n %n" +
                        "Query File:   %s%n %n" +
                        "Subject Unique Tokens        %d%n                         %n" +
                        "Query Unique Tokens          %d%n                         %n" +
                        "************************************************************%n" +
                        "*Intersection Size:           %d                           %n" +
                        "                                                          %n" +
                        "*Similarity:                  %.2f%%%n                      %n" +
                        "************************************************************%n",
                timestamp,
                subjectFileLocation,
                queryFileLocation,
                subjectTokens,
                queryTokens,
                intersection,
                percent * 100
        );
    }


    /**
     * Optimised File I/O Operations by adding extended buffer reader size
     * @param source - address of the source to process
     * @return BufferedReader or null
     */
    public static BufferedReader getBufferedReader(String source) {
        // Creates a BufferedReader for the specified source file
        BufferedReader result = null;

        try {
            result = new BufferedReader(new InputStreamReader(
                    new FileInputStream(source), StandardCharsets.UTF_8), 8192);
        } catch (FileNotFoundException e) {
            String errorMessage = UtilMethods.buildString("File not found: ", source);
            UtilMethods.printErrorMessage(errorMessage, e);
        }
        return result;
    }

    /**
     * Get a number of lines in the processing file.
     * Used for a progress meter
     * @param source the file location
     * @return number of lines
     */
    public static int getAmountOfLines(String source) {
        int amountOfLines = 0;
        try (BufferedReader br = getBufferedReader(source)) {
            while (br.readLine() != null) {
                amountOfLines++;
            }
            return amountOfLines;
        } catch (IOException e) {
            UtilMethods.printErrorMessage("Error: ", e);
        }
        return amountOfLines;
    }

}