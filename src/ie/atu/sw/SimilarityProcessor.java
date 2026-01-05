package ie.atu.sw;

import java.util.Map;

/**
 * Class which make a similarity calculation, such as Intersection finder and similarity calculation
 */
public class SimilarityProcessor {
    /**
     * Count the amount of the intersected items in the dictionaries
     * @param subjectTokens Dictionary of subject tokens
     * @param queryTokens Dictionary of query tokens
     * @return The amount of intersected items in both Lists
     */
    public static int intersectionFinder(Map<String, Integer> subjectTokens, Map<String, Integer> queryTokens) {
        int intersectionCounter = 0;

        for(String word: subjectTokens.keySet()){
            if(queryTokens.containsKey(word)){
                intersectionCounter++;
            }
        }
        return intersectionCounter;
    }

    /**
     * Similarity calculation method
     * @param intersectionSize size of the intersection
     * @param subjectSize amount of unique elements in subject map
     * @param querySize amount of unique elements in query map
     * @return similarity size
     */
    public static double similarityFinder(Integer intersectionSize, Integer subjectSize, Integer querySize){
        return (double) (2 * intersectionSize) / (subjectSize + querySize);
    }
}
