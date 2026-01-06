package ie.atu.sw;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ThreadsProcessor class designed to process
 * and compare of 2 files for similarity
 * Class includes a main method for starting the file processing
 * and generates a similarity report. Similarity made with Sørensen–Dice
 *
 * @author Alona Skrypnyk
 * @version 1.0
 */
public class ThreadsProcessor {
    public static void main(String[] args) {
        String filePath1 = args[0];
        String filePath2 = args[1];
        String reportFileLocation = args[2];

        try {
            new ThreadsProcessor().go(filePath1, filePath2, reportFileLocation);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Virtual-thread run failed", e);
        }
    }

    /**
     * The threads processor method. Take 2 files and work with them simultaneously
     * @param filePath1 path to query file
     * @param filePath2 path to subject file
     * @param reportFileLocation location of the report
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void go(String filePath1, String filePath2, String reportFileLocation)
            throws ExecutionException, InterruptedException {

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Callable<Map<String, Integer>> task1 = () -> FilesProcessor.processFile(filePath1);
            Callable<Map<String, Integer>> task2 = () -> FilesProcessor.processFile(filePath2);

            var future1 = executor.submit(task1);
            var future2 = executor.submit(task2);

            Map<String, Integer> output1 = future1.get();
            Map<String, Integer> output2 = future2.get();

            int intersectionSize = SimilarityProcessor.intersectionFinder(output1, output2);

            double similarity = SimilarityProcessor.similarityFinder(
                    intersectionSize,
                    output1.size(),
                    output2.size()
            );

            String report = FilesProcessor.createReport(
                    filePath1,
                    filePath2,
                    output1.size(),
                    output2.size(),
                    intersectionSize,
                    similarity
            );

            FilesProcessor.writeReport(reportFileLocation, report);
        }
    }
}
