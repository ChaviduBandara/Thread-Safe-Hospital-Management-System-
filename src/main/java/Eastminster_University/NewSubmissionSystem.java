package Eastminster_University;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NewSubmissionSystem {
    private final int numOfStudents;
    private final int poolSize;
    private SubmissionStats stats;
    private ExecutorService executor;

    // For progress tracking
    private AtomicInteger processedCount;

    // Retry mechanism tracking
    private AtomicInteger retryCount;
    private static final int max_reties = 1;
    private static final int retry_delay_ms = 50;

    public NewSubmissionSystem(int poolSize, int numOfStudents){
        this.poolSize = poolSize;
        this.stats = new SubmissionStats();
        this.numOfStudents = numOfStudents;

        // Initializing the tracking counters
        this.processedCount = new AtomicInteger(0);
        this.retryCount = new AtomicInteger(0);
    }

    public void processSubmissions(){
        System.out.println("----------------------------------");
        System.out.println("      NEW SUBMISSION SYSTEM      ");
        System.out.println("----------------------------------");
        System.out.println("Thread Pool Size: " + poolSize + " worker threads");
        System.out.println("Available CPU Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Processing " + numOfStudents + " students CONCURRENTLY...\n");

        // Creating custom thread pool with named threads for debugging
        executor = Executors.newFixedThreadPool(poolSize, new ThreadFactory() {
            private AtomicInteger threadCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "SubmissionWorker-" + threadCount.getAndIncrement());
                thread.setDaemon(false);  // Ensuring that all the threads completes before JVM exists
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            }
        });

        // Starting progress monitoring in separate thread
        ScheduledExecutorService progressMonitor = Executors.newSingleThreadScheduledExecutor();
        progressMonitor.scheduleAtFixedRate(this::displayProgress, 1, 2, TimeUnit.SECONDS);

        // Start Timing
        stats.setStartTime();

        // Each thread calls countDown() when done, main thread waits with await()
        CountDownLatch latch = new CountDownLatch(numOfStudents);

        // Submitting all the submissions as concurrent tasks to the thread pool
        for (int i=0; i<numOfStudents; i++){
            final int studentID = i+1;
            final String studentName = "Std " + studentID;
            // executor.submit() adds task to queue and multiple tasks run concurrently
            executor.submit(() -> {
                try{
                    processStudentSubmission(studentID, studentName);  // Enhanced processing with retry mechanism
                } finally {
                    latch.countDown();  // Decreasing the latch counter
                    processedCount.incrementAndGet();
                }
            });
        }

        try {
            System.out.println("Main Thread waiting for all the submissions to be complete....");
            latch.await();  // Blocking until count reaches 0
            System.out.println("All submissions completed successfully!");
        } catch (InterruptedException e){
            System.out.println("\nSystem interrupted during processing! ");
            Thread.currentThread().interrupt();
        } finally {
            stats.setEndTime();
            progressMonitor.shutdown();  // Stoping progress monitoring
        }
    }

    // Processing individual student with the retry mechanism
    private void processStudentSubmission(int studentID, String studentName){
        Student student = new Student(studentID, studentName);
        boolean success = false;
        int attempts = 0;

        // Adding retry logic for failed submissions
        while(attempts <= max_reties && !success){
            try {
                success = student.submitExam(studentName);
                if (success){
                    stats.increaseSuccessfulSubmission();
                } else{
                    attempts ++;
                    if (attempts <= max_reties){
                        retryCount.incrementAndGet();  // Tracking the retry counts
                        Thread.sleep(retry_delay_ms);
                    } else {
                        stats.increaseFailedSubmission();
                    }
                }
            } catch (InterruptedException e){
                stats.increaseFailedSubmission();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Real-time displaying the progress using a percentage value
    // Runs in separate monitoring thread and updates every 2s
    private void displayProgress(){
        int processed = processedCount.get();
        double percentage = (processed * 100.0) / numOfStudents;
        System.out.printf("\r Progress: %.1f%% completed (%d/%d students)", percentage, processed, numOfStudents);
        System.out.flush();
    }

    // Graceful shutdown with proper cleanup
    public void shutdown() throws InterruptedException{
        executor.shutdown();  // Stop accepting new Tasks
        // waiting for existing tasks to complete within 30 seconds
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)){
            System.out.println("Timeout waiting for tasks - forcing shutdown...");
            executor.shutdown();  // force shutdown

            if (!executor.awaitTermination(30, TimeUnit.SECONDS)){
                System.out.println("Thread Pool didn't terminate properly!");
            }
        }
        System.out.println("System shut down successfully!\n");
    }
    public int getNumOfStudents(){
        return numOfStudents;
    }
    public int getPoolSize(){
        return poolSize;
    }
    public SubmissionStats getStats(){
        return stats;
    }
    public int getRetryCount(){
        return retryCount.get();
    }



}
