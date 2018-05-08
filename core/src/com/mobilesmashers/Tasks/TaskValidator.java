package com.mobilesmashers.Tasks;

public class TaskValidator {

    public static boolean areTasksCorrect(Task first, Task second) {
        return first.getType().equals(second.getType());
    }
}
