import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // Create Output object to capture log messages
        Output output = new Output();
        
        // Create Threeterson lock with the Output object
        Threeterson lock = new Threeterson(output);
        
        // Create a shared queue of Paper objects
        Queue<Paper> allPapers = new LinkedList<>();
        
        // Add some Paper objects to the queue
        for (int i = 0; i < 10; i++) {
            allPapers.add(new Paper());
        }
        
        // Create and start Marker threads
        Thread marker1 = new Marker(lock, output, allPapers);
        Thread marker2 = new Marker(lock, output, allPapers);
        Thread marker3 = new Marker(lock, output, allPapers);

        marker1.start();
        marker2.start();
        marker3.start();
        
        // Wait for all Marker threads to complete
        try {
            marker1.join();
            marker2.join();
            marker3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Create OutputValidator and validate the output
        OutputValidator validator = new OutputValidator(output);
        boolean isValid = validator.validate();
        
        // Print validation result
        System.out.println("Validation result: " + (isValid ? "Success" : "Failure"));
        
        // Optionally, print the output for manual inspection
        System.out.println("Log output:");
        System.out.println(output);
    }
}
