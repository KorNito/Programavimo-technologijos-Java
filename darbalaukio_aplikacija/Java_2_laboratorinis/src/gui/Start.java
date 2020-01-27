package gui;

import ds.ToDoList;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
ToDoList todo = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        todo = new ToDoList();
        todo.connectToDB();
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data.tdl")));
            todo = (ToDoList) in.readObject();
            in.close();
        } catch (Exception e) {
            todo.registerPerson("admin", "admin", "admin", "admin");
            todo.registerPerson("admin2", "admin", "admin", "admin");
            todo.registerPerson("admin3", "admin", "admin", "admin");
            todo.login("admin", "admin");
            todo.addProject("Project1");
            todo.addProject("Project2");
            todo.addProject("Project3");
            todo.addTaskToProject(1, "task1");
            todo.addTaskToProject(1, "task2");
            todo.addTaskToProject(3, "task1");
        }
        

        FXMLLoader load = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = load.load();
        DashboardController col = load.getController();
        col.setToDoList(todo);
        Scene scene = new Scene(root);
        primaryStage.setTitle("ToDoList admin interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        todo.disconect();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
