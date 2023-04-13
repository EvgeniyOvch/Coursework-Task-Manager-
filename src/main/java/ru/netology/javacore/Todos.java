package ru.netology.javacore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Todos {

    private Set<String> todos = new HashSet<>();
    private final int MAX_SIZE = 7;

    public void addTask(String task) {
        if (todos.size() < MAX_SIZE) {
            todos.add(task);
        }
    }

    public void removeTask(String task) {
        todos.remove(task);
    }

    public String getAllTasks() {
        return todos.stream()
                .sorted()
                .collect(Collectors.joining(" "));
    }
}



