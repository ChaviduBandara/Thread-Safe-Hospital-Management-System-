package Eastminster_University;

public class OldSubmissionSystemSimulation {
    public static void main(String[] args) {
        printHeader();

        int numOfStudents = 100;

        System.out.println(" Configuration:");
        System.out.println(" Students to be process: " + numOfStudents);
        System.out.println(" Processing Mode: SEQUENTIAL (1 at a time)");
        System.out.println(" Available CPU Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println(" Cores Used: 1 (Wastes " + (Runtime.getRuntime().availableProcessors() - 1) + " cores!)");

        System.out.println("\nStarting the OLD SUBMISSION SYSTEM....\n");
        OldSubmissionSystem oldSystem = new OldSubmissionSystem(numOfStudents);
        oldSystem.processSubmissions();

        oldSystem.getStats().displayStats("Sequential (1 at a time)", numOfStudents, 0);

        oldSystem.displayProblemsOfOldSystem();
        System.out.println("\nOLD SYSTEM Simulation Completed!");
    }

    private static void printHeader(){
        System.out.println("\n===================================================");
        System.out.println("\n   EASTMINSTER UNIVERSITY - OLD SUBMISSION SYSTEM   ");
        System.out.println("   (Legacy Sequential System - Built 5 years ago)");
        System.out.println();
        System.out.println("===================================================");
    }
}
