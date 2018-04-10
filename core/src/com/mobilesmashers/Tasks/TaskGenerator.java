package com.mobilesmashers.Tasks;

import com.mobilesmashers.HelpClasses.Strings;
import java.util.Random;

public class TaskGenerator {
    private String previousTask, twoTaskBefore;
    private int amountDifferentTasks = 3;

    //if you want to use this class
    //TaskGenerator name = new TaskGenerator();
    //then for each ball -> generateTask();

    //to add task you have to:
    //amountDifferentTasks+1
    //create tasksMethod that returns Task
    //add this method in generateFirstTask
    //add this method in generateSecondTask

    public Task generateTask() {
        if (previousTask != null && twoTaskBefore != null) {
            if (previousTask.equals(twoTaskBefore)) {
                clearTasks();
            }
        }
        if (previousTask == null) {
            return generateFirstTask();
        }
        if (previousTask != null) {
            return generateSecondTask();
        }
        return null;
    }

    private Task generateFirstTask() {
        final int random = new Random().nextInt(amountDifferentTasks);
        if (random == 0) {
            switchTasks(Strings.EVEN_TASK);
            return generateEvenNumberLessThanTen();
        } else if (random == 1) {
            switchTasks(Strings.PRIME_TASK);
            return generatePrimeNumber();
        } else if (random == 2) {
            switchTasks(Strings.MULTIPLIED_BY_TEN_TASK);
            return generateMultipliedByTen();
        }
        return null;
    }

    private Task generateSecondTask() {
        if (previousTask.equals(Strings.EVEN_TASK)) {
            switchTasks(Strings.EVEN_TASK);
            return generateEvenNumberLessThanTen();
        } else if (previousTask.equals(Strings.PRIME_TASK)) {
            switchTasks(Strings.PRIME_TASK);
            return generatePrimeNumber();
        } else if (previousTask.equals(Strings.MULTIPLIED_BY_TEN_TASK)) {
            switchTasks(Strings.MULTIPLIED_BY_TEN_TASK);
            return generateMultipliedByTen();
        }
        return null;
    }

    private Task generateEvenNumberLessThanTen() {
        int random = new Random().nextInt(10 / 2) * 2;
        return new Task(random, Strings.EVEN_TASK);
    }

    private Task generatePrimeNumber() {
        int[] primeNumbers = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        int random = new Random().nextInt(primeNumbers.length);
        int value = primeNumbers[random];
        return new Task(value, Strings.PRIME_TASK);
    }

    private Task generateMultipliedByTen() {
        int random = new Random().nextInt(9) + 1;
        return new Task(random * 10, Strings.MULTIPLIED_BY_TEN_TASK);
    }

    private void switchTasks(String task) {
        twoTaskBefore = previousTask;
        previousTask = task;
    }

    private void clearTasks() {
        previousTask = null;
        twoTaskBefore = null;
    }
}
