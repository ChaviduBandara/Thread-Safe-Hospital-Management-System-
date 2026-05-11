package Eastminster_University;

import javax.sound.midi.Soundbank;

public class OldSubmissionSystem {
    private final int numOfStudents;
    private SubmissionStats stats;

    public OldSubmissionSystem(int numOfStudents){
        this.numOfStudents = numOfStudents;
        this.stats = new SubmissionStats();
    }

    public void processSubmissions(){
        System.out.println("----------------------------------");
        System.out.println("      OLD SUBMISSION SYSTEM      ");
        System.out.println("----------------------------------");
        System.out.println("Processing " + numOfStudents + " STUDENTS ONE AT A TIME:\n");

        stats.setStartTime();

        for (int i=0; i<numOfStudents;  i++){
            Student student = new Student(i+1, "Std " + (i+1));
            try{
                boolean success = student.submitExam(student.getName());
                if (success){
                    stats.increaseSuccessfulSubmission();
                }
                else {
                    stats.increaseFailedSubmission();
                }

                if ((i+1) % 1000 == 0){
                    System.out.println("Processed: " + (i+1) + "/" + numOfStudents + " students...");
                }

            } catch (InterruptedException e){
                System.out.println("ERROR: Unable to complete submission for Student " + (i + 1) + " due to an unexpected interruption");
                stats.increaseFailedSubmission();
                Thread.currentThread().interrupt();
            }
        }
        stats.setEndTime();
        System.out.println("All submissions are completed");
    }

    // Problems of the old system
    public void displayProblemsOfOldSystem(){
        long timeTaken = stats.getTotalTimeMilis();
        double minutes = timeTaken / 60000.0;

        System.out.println();
        System.out.println("\nPROBLEMS OF THE OLD SYSTEM");
        System.out.println("--------------------------------");

        System.out.println(" PROBLEM 01: Sequential Processing");
        System.out.println("    * Only one student process at a time, All other students wait in the queue");
        System.out.println("    * No concurrency, no parallelism");

        System.out.println("\n PROBLEM 02: Poor Scalability");
        System.out.println("    * Time taken: " + String.format("%.2f", minutes) + " minutes for " + numOfStudents + " students");
        // Project time for 100,000 students
        double estimatedTime = (minutes * 100000.0) / numOfStudents;
        System.out.println("    * Estimated time for 100,000 students: " + String.format("%.1f", estimatedTime) + " minutes!");
        if (estimatedTime > 20){
            System.out.println("Students would wait about 20-30 minutes");
        }

        System.out.println("\n PROBLEM 03: CPU Underutilization");
        System.out.println("    * System has " + Runtime.getRuntime().availableProcessors() + " CPU cores");
        System.out.println("    * but Old System uses only 1 core (It wastes " + (Runtime.getRuntime().availableProcessors() -1) + " cores)");

        System.out.println("\n PROBLEM 04: Limited Fault Tolerance");
        System.out.println("    * If the system crashes, all the submissions get lost");
        System.out.println("    * No retry mechanism");

        System.out.println("\n PROBLEM 05: System Timeouts");
        System.out.println("    * High processing latency causes HTTP timeouts ");
        System.out.println("    * Students fail to meet the deadlines due to system delays");

        System.out.println("\n SOLUTIONS REQUIRED: ");
        System.out.println(" ✓ Multi-threaded processing");
        System.out.println(" ✓ Thread-safe statistics");
        System.out.println(" ✓ Utilize all CPU cores");
        System.out.println(" ✓ Scalable to 100,000+ students");
        System.out.println("==================================================");
    }

    public int getNumOfStudents(){
        return numOfStudents;
    }
    public SubmissionStats getStats(){
        return stats;
    }
}
