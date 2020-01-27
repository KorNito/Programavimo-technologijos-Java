package com.example.lab6.ds;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private int id;
    private static int idCounter = 1;
    private String title;
    private ArrayList<User> members = new ArrayList();
    private ArrayList<Task> tasks = new ArrayList();

    public Project(String title, User creator) {
        this.title = title;
        members.add(creator);
        this.id = idCounter++;
    }

    public void addMember(User u) {
        members.add(u);
    }

    public void addTask(Task u) {
        tasks.add(u);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> all = new ArrayList();
        all.addAll(this.tasks);
        for (Task t : tasks) {
            all.addAll(t.getAllTasks());
        }
        return all;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
