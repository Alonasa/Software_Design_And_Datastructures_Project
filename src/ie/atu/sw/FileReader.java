package ie.atu.sw;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * The FileReader class is responsible for processing file operations:
 * encoding, decoding, or parsing file data.
 * The class provides methods for reading input files,
 * processing content using specific logic,
 * and writing the processed data to output files.
 */
public class FileReader {
    //Filtering patterns
    private static final String BY_WORD = "\\s+";
    private static final String BY_PUNCTUATION = "[^a-z0-9]+";


    /**
     * Processes a file by reading its contents, applying encoding
     * or decoding operations, and writing the results to
     * a specified output file
     *
     * @param source the path to the source file
     */
    public Map<String, Integer> processFile(String source) {
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
                String[] words = line.toLowerCase(Locale.ROOT).replaceAll(BY_PUNCTUATION, "").split(BY_WORD);

                tokeniseWords(words, countedElements);
                System.out.println(countedElements);

                linesProcessed++;
            }
            br.close();
            System.out.println("Successfully decoded file");

        } catch (IOException e) {
            UtilMethods.printErrorMessage("I/O Error on decoding: ", e);
        }

        return countedElements;
    }

    /**
     * Words tokenisation, Concurrent hash map was used for the work with threads
     * @param words the array of the words for process
     * @return counted indices of the elements in the array
     */
    public void tokeniseWords (String[] words, Map<String, Integer> countedElements){
        for(String word: words) {
            if(!word.isEmpty()){
                countedElements.put(word, countedElements.getOrDefault(word, 0) + 1);
            }
        }
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