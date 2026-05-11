package Eastminster_University;

import java.util.Random;

public class Student {
    private String name;
    private int id;
    private Random random;

    public Student(int id, String name){
        this.name = name;
        this.id = id;
        this.random = new Random();
    }

    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }

    // -5% random failure rate simulates real-world issues like network timeout, server errors, etc.
    public boolean submitExam(String name) throws InterruptedException{
        int simulateTime = random.nextInt(100);
        System.out.println("Student submission is processing....");
        Thread.sleep(simulateTime);  // Each student submission takes a different amount of time

        int randomNumber = random.nextInt(100);
        if (randomNumber < 5){
            System.out.println("Student name " + name + " 's submission FAILED!");
            return false;
        } else {
            System.out.println("Student name " + name + " 's assignment submitted SUCCESSFULLY!");
            return true;
        }


    }
}
