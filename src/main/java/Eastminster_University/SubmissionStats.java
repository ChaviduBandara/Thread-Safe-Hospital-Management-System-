package Eastminster_University;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SubmissionStats {
    private AtomicInteger successfulSubmissions;
    private AtomicInteger failedSubmissions;
    private AtomicLong startTime;
    private AtomicLong endTime;

    public SubmissionStats(){
        this.successfulSubmissions = new AtomicInteger(0);
        this.failedSubmissions = new AtomicInteger(0);
        this.startTime = new AtomicLong(0);
        this.endTime = new AtomicLong(0);
    }

    public void reset(){
        successfulSubmissions.set(0);
        failedSubmissions.set(0);
        startTime.set(0);
        endTime.set(0);
    }
    // Increments the successful submission count
    public void increaseSuccessfulSubmission(){
        successfulSubmissions.incrementAndGet();
    }
    // Increments failed submission count
    public void increaseFailedSubmission(){
        failedSubmissions.incrementAndGet();
    }
    // Records the start time of the process
    public void setStartTime(){
        startTime.set(System.currentTimeMillis());
    }
    // Records the end time of the process
    public void setEndTime(){
        endTime.set(System.currentTimeMillis());
    }

    public int getSuccessfulSubmissions(){
        return successfulSubmissions.get();
    }
    public int getFailedSubmissions(){
        return failedSubmissions.get();
    }

    // Calculating the total time taken
    public long getTotalTimeMilis(){
        return endTime.get() - startTime.get();
    }
    // Returning the total number of submissions processed
    public int getTotalSubmissions(){
        return successfulSubmissions.get() + failedSubmissions.get();
    }
    // Calculating the success rate as a percentage
    public double getSuccessRate(){
        int total = getTotalSubmissions();
        if (total == 0){
            return 0.0;
        }
        return ((double) successfulSubmissions.get() / total) * 100;
    }
    // calculating the successful submissions per second
    public double getThroughput(){
        long totalTimeSecs = getTotalTimeMilis() / 1000;
        if (totalTimeSecs == 0){
            return 0.0;
        }
        return (double) successfulSubmissions.get() / totalTimeSecs;
    }
    // calculating the time taken
    public void displayStats(String processingMode, int numStudents, int retryCount){
        double seconds = getTotalTimeMilis() / 1000.0;
        double minutes = seconds/ 60.0;

        System.out.println("\n---------------------------------------------");
        System.out.println("         SUBMISSION STATISTICS REPORT          ");
        System.out.println("---------------------------------------------");
        System.out.println("  Total Students Processed: " + getTotalSubmissions());
        System.out.println(" ✓ Successful Submissions: " + getSuccessfulSubmissions());
        System.out.println(" ✗ Failed Submissions: " + getFailedSubmissions());

        if (retryCount > 0){
            System.out.println("  Number of students retried: " + retryCount);
        }

        System.out.println("  Time Taken: " + String.format("%.2f", seconds) + " seconds (" + String.format("%.2f", minutes) + " minutes)");
        System.out.println("  Success Rate: " + String.format("%.2f", getSuccessRate()) + "%");
        System.out.println("  Throughput " + String.format("%.2f", getThroughput()) + " submissions/sec");
        System.out.println("  Processing Mode: " + processingMode);
    }

}
