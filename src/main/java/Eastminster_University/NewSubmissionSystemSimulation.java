package Eastminster_University;

public class NewSubmissionSystemSimulation {
    public static void main(String[] args) throws InterruptedException {
        printHeader();

        int numStudents = 100;

        int poolSize = Runtime.getRuntime().availableProcessors() * 2;

        System.out.println(" Configuration:");
        System.out.println(" Students to be process: " + numStudents);
        System.out.println(" Processing Mode: CONCURRENT (Multiple at once)");
        System.out.println(" Available CPU Cores: " + Runtime.getRuntime().availableProcessors());

        System.out.println("\nStarting the NEW SUBMISSION SYSTEM....\n");

        NewSubmissionSystem newSystem = new NewSubmissionSystem(poolSize, numStudents);
        newSystem.processSubmissions();
        newSystem.shutdown();

        newSystem.getStats().displayStats("Concurrent (" + poolSize + " threads)", numStudents, newSystem.getRetryCount());

        System.out.println("\nNEW SYSTEM Simulation Completed!");
    }

    private static void printHeader(){
        System.out.println("\n===================================================");
        System.out.println("\n   EASTMINSTER UNIVERSITY - NEW SUBMISSION SYSTEM   ");
        System.out.println("   (Modern Concurrent System - Production Ready)");
        System.out.println();
        System.out.println("===================================================");
    }
}
