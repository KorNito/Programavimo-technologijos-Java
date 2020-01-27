package gui;

import ds.ToDoList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private PieChart userS;
    @FXML
    private AreaChart projectS;
    ToDoList tdl = null;

    public void setToDoList(ToDoList t) {
        tdl = t;
        showUserStat();
        showProjectsStart();
    }
    
    public void openUserManager(ActionEvent af) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("Users.fxml"));
        Parent root = load.load();
        UsersController col = load.getController();
        col.setToDoList(tdl);
        Scene scene = new Scene(root);
        
        Stage nauja = new Stage();
        nauja.setTitle("User management");
        nauja.setScene(scene);
        nauja.show();
    }
    
    public void openRegisterUser(ActionEvent af) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("Registration.fxml"));
        Parent root = load.load();
        RegistrationController col = load.getController();
        col.setToDoList(tdl);
        Scene scene = new Scene(root);
        
        Stage nauja = new Stage();
        nauja.setTitle("User registration");
        nauja.setScene(scene);
        nauja.show();
    }//openRegisterUser

    public void close(ActionEvent as) {
        Platform.exit();
    }

    public void showUserStat() {
        if (tdl != null) {
            int[] count = tdl.getUserCount();
            ObservableList<PieChart.Data> pieChartData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Persons - " + count[0], count[0]),
                            new PieChart.Data("Companies - " + count[1], count[1]));
            userS.setTitle("User statistics");
            userS.setData(pieChartData);
        }
    }

    public void showProjectsStart() {
        if (tdl != null) {
            XYChart.Series seriesMay = new XYChart.Series();
            seriesMay.setName("Tasks per project");
            int[][] m = tdl.getProjectNumbers();
            for (int[] row : m) {
                seriesMay.getData().add(new XYChart.Data(row[0], row[1]));
            }
            projectS.getData().addAll(seriesMay);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
