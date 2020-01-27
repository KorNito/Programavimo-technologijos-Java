package java_2_laboratorinis;

import ds.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class UI {

    static Scanner keyboard = null;

    public static void main(String[] args) {
        ToDoList todo = new ToDoList();
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data.tdl")));
            todo = (ToDoList) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Failed to load data");
            todo.registerPerson("admin", "admin", "admin", "admin");
        }

        keyboard = new Scanner(System.in);
        login(todo);

        start:
        while (true) {
            printCommands();
            String input = keyboard.nextLine().trim();

            switch (input) {
                case "user":
                case "1":
                    userSubmeniu(todo);
                    break;
                case "project":

                case "2":
                    System.out.println("project");
                    break;
                case "exit":
                case "3":
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data.tdl")));
                        out.writeObject(todo);
                        out.close();
                    } catch (Exception e) {
                        System.out.println("Failed to wrtite data");
                    }
                    System.out.println("goodbye");
                    break start;
                default:
                    System.out.println("The command does not exist");
                    break;
            }
        }

    }

    public static void login(ToDoList t) {
        int numberOfAttempts = 3;
        while (numberOfAttempts > 0) {
            System.out.println("Enter login:");
            String login = keyboard.nextLine().trim();
            System.out.println("Enter password:");
            String pass = keyboard.nextLine().trim();
            try {
                t.login(login, pass);
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                numberOfAttempts--;
            }
        }
        System.exit(0);
    }

    public static void printCommands() {
        System.out.println("Pasirinkite komanda: \n"
                + "\tUser - user, 1\n"
                + "\tProjects - project, 2\n"
                + "\tExit - exit, 3");
    }

    public static void userSubmeniu(ToDoList t) {
        while (true) {
            System.out.println("Pasirinkite: \n"
                    + "\tget user list - all, 1\n"
                    + "\tadd person - addp, 2\n"
                    + "\tadd company - 3\n"
                    + "\tdeactivate - 4\n"
                    + "\tedit user - 5\n"
                    + "\timport users - 6\n"
                    + "\texit submenu - 9");

            String input = keyboard.nextLine().trim();
            switch (input) {
                case "1":
                    for (User u : t.getAllUsers()) {
                        System.out.println(u);
                    }
                    break;
                case "2":
                    readNewPerson(t);
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    importUsers(t);
                    break;
                case "9":

                    return;

            }
        }
    }

    public static void importUsers(ToDoList t) {
        Scanner file = null;
        try {
            file = new Scanner(new File("users.txt"));
            while (file.hasNext()) {
                String line = file.nextLine();
                String[] elements = line.split(";");
                t.registerPerson(elements[1], elements[2], elements[3], elements[4]);
            }
        } catch (Exception e) {
            System.out.println("Error: readint data");
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }

    public static void readNewPerson(ToDoList t) {
        String login = "";
        while (true) {
            System.out.println("enter login:");
            login = keyboard.nextLine().trim();
            if (login.equals("exit")) {
                return;
            }
            if (login.length() > 3 && t.getUserByLogin(login) == null) {
                break;
            }
        }
        System.out.println("enter pass:");
        String pass = keyboard.nextLine().trim();
        System.out.println("enter name:");
        String name = keyboard.nextLine().trim();
        System.out.println("enter surname:");
        String surname = keyboard.nextLine().trim();
        t.registerPerson(login, pass, name, surname);
    }
}
