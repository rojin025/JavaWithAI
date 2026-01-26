import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataProcessorPerformanceTest {
    
    public static void main(String[] args) {
        System.out.println("=== DataProcessor Performance Comparison ===\n");
        
        // Test with different dataset sizes
        int[] datasetSizes = {1000, 10000, 100000, 1000000, 5000000};
        int[] percentiles = {10, 25, 50, 75, 90, 95, 99};
        
        for (int size : datasetSizes) {
            System.out.println("Testing with " + formatNumber(size) + " scores:");
            System.out.println("-".repeat(60));
            
            // Generate random test data
            List<Integer> scores = generateRandomScores(size);
            
            // Warm-up JVM
            warmUp(scores, percentiles);
            
            // Test sequential version
            long sequentialTime = measureSequential(scores, percentiles);
            
            // Test parallel version
            long parallelTime = measureParallel(scores, percentiles);
            
            // Calculate speedup
            double speedup = (double) sequentialTime / parallelTime;
            double improvement = ((double)(sequentialTime - parallelTime) / sequentialTime) * 100;
            
            // Display results
            System.out.printf("Sequential: %10s ms\n", formatNumber(sequentialTime));
            System.out.printf("Parallel:   %10s ms\n", formatNumber(parallelTime));
            System.out.printf("Speedup:    %.2fx\n", speedup);
            System.out.printf("Improvement: %.2f%%\n", improvement);
            
            // Verify results are the same
            verifyResults(scores, percentiles);
            
            System.out.println();
        }
        
        System.out.println("=== Performance Test Complete ===");
    }
    
    private static List<Integer> generateRandomScores(int size) {
        Random random = new Random(42); // Fixed seed for reproducibility
        List<Integer> scores = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scores.add(random.nextInt(1000)); // Scores between 0 and 999
        }
        return scores;
    }
    
    private static void warmUp(List<Integer> scores, int[] percentiles) {
        // Warm up JVM to get accurate measurements
        for (int i = 0; i < 3; i++) {
            DataProcessor.calculatePercentiles(new ArrayList<>(scores), percentiles);
            DataProcessor.calculatePercentilesParallel(new ArrayList<>(scores), percentiles);
        }
    }
    
    private static long measureSequential(List<Integer> scores, int[] percentiles) {
        int iterations = 10;
        long totalTime = 0;
        
        for (int i = 0; i < iterations; i++) {
            List<Integer> testScores = new ArrayList<>(scores);
            long startTime = System.nanoTime();
            DataProcessor.calculatePercentiles(testScores, percentiles);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime) / 1_000_000; // Convert to milliseconds
        }
        
        return totalTime / iterations; // Average time
    }
    
    private static long measureParallel(List<Integer> scores, int[] percentiles) {
        int iterations = 10;
        long totalTime = 0;
        
        for (int i = 0; i < iterations; i++) {
            List<Integer> testScores = new ArrayList<>(scores);
            long startTime = System.nanoTime();
            DataProcessor.calculatePercentilesParallel(testScores, percentiles);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime) / 1_000_000; // Convert to milliseconds
        }
        
        return totalTime / iterations; // Average time
    }
    
    private static void verifyResults(List<Integer> scores, int[] percentiles) {
        List<Integer> scores1 = new ArrayList<>(scores);
        List<Integer> scores2 = new ArrayList<>(scores);
        
        Map<String, Double> sequential = DataProcessor.calculatePercentiles(scores1, percentiles);
        Map<String, Double> parallel = DataProcessor.calculatePercentilesParallel(scores2, percentiles);
        
        boolean allMatch = true;
        for (int p : percentiles) {
            String key = p + "th";
            double seqValue = sequential.get(key);
            double parValue = parallel.get(key);
            
            // Allow small floating point differences
            if (Math.abs(seqValue - parValue) > 0.0001) {
                System.out.printf("WARNING: Mismatch at %s - Sequential: %.4f, Parallel: %.4f\n", 
                    key, seqValue, parValue);
                allMatch = false;
            }
        }
        
        if (allMatch) {
            System.out.println("✓ Results match between sequential and parallel versions");
        }
    }
    
    private static String formatNumber(long number) {
        return String.format("%,d", number);
    }
}
