import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

public class DataProcessor {

    //calculate percentiles for list of integers scores returns a map String and double


    /**
     * Calculates specified percentiles from a list of integer scores, handling edge cases such as
     * null/empty input and duplicate scores. Uses the nearest-rank method with linear interpolation.
     * @param scores List of integer scores.
     * @param percentilesToCalculate Array of percentiles to calculate (e.g., {10, 25, 50, 75, 90}).
     * @return Map from percentile label (e.g., "10th") to score value at that percentile.
     */
    public static Map<String, Double> calculatePercentiles(List<Integer> scores, int[] percentilesToCalculate) {
        if (scores == null || scores.isEmpty()) {
            throw new IllegalArgumentException("Scores list must not be null or empty.");
        }

        // Defensive copy and sort
        List<Integer> sortedScores = new ArrayList<>(scores);
        Collections.sort(sortedScores);
        int n = sortedScores.size();

        Map<String, Double> result = new HashMap<>();
        for (int p : percentilesToCalculate) {
            if (p < 0 || p > 100) {
                throw new IllegalArgumentException("Percentile must be between 0 and 100 inclusive.");
            }
            if (n == 1) {
                result.put(p + "th", (double) sortedScores.get(0));
                continue;
            }
            // Nearest-rank with linear interpolation
            double pos = p / 100.0 * (n - 1);
            int lowerIndex = (int) Math.floor(pos);
            int upperIndex = (int) Math.ceil(pos);

            double value;
            if (upperIndex == lowerIndex) {
                value = sortedScores.get(lowerIndex);
            } else {
                double lowerValue = sortedScores.get(lowerIndex);
                double upperValue = sortedScores.get(upperIndex);
                value = lowerValue + (upperValue - lowerValue) * (pos - lowerIndex);
            }
            result.put(p + "th", value);
        }
        return result;
    }

    /**
     * Parallel version of calculatePercentiles that uses parallel processing for better performance
     * on large datasets. Uses parallel sorting and parallel percentile calculations.
     * @param scores List of integer scores.
     * @param percentilesToCalculate Array of percentiles to calculate (e.g., {10, 25, 50, 75, 90}).
     * @return Map from percentile label (e.g., "10th") to score value at that percentile.
     */
    public static Map<String, Double> calculatePercentilesParallel(List<Integer> scores, int[] percentilesToCalculate) {
        if (scores == null || scores.isEmpty()) {
            throw new IllegalArgumentException("Scores list must not be null or empty.");
        }

        // Parallel sort using parallel streams
        List<Integer> sortedScores = scores.parallelStream()
                .sorted()
                .collect(Collectors.toList());
        int n = sortedScores.size();

        // Use ConcurrentHashMap for thread-safe parallel writes
        Map<String, Double> result = new ConcurrentHashMap<>();
        
        // Parallel processing of percentile calculations
        Arrays.stream(percentilesToCalculate)
                .parallel()
                .forEach(p -> {
                    if (p < 0 || p > 100) {
                        throw new IllegalArgumentException("Percentile must be between 0 and 100 inclusive.");
                    }
                    if (n == 1) {
                        result.put(p + "th", (double) sortedScores.get(0));
                        return;
                    }
                    // Nearest-rank with linear interpolation
                    double pos = p / 100.0 * (n - 1);
                    int lowerIndex = (int) Math.floor(pos);
                    int upperIndex = (int) Math.ceil(pos);

                    double value;
                    if (upperIndex == lowerIndex) {
                        value = sortedScores.get(lowerIndex);
                    } else {
                        double lowerValue = sortedScores.get(lowerIndex);
                        double upperValue = sortedScores.get(upperIndex);
                        value = lowerValue + (upperValue - lowerValue) * (pos - lowerIndex);
                    }
                    result.put(p + "th", value);
                });

        return new HashMap<>(result); // Convert back to regular HashMap for consistency
    }
}