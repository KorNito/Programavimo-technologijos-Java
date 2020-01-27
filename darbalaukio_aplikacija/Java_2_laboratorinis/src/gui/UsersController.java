package gui;

import ds.ToDoList;
import ds.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersController implements Initializable {

    @FXML
    private TableView userTable;

    @FXML
    private TextField lLogin, lPass, lName, lSurname;

    ToDoList tdl = null;

    public void setToDoList(ToDoList t) {
        tdl = t;
        fillTable();
    }

    public void addPerson(ActionEvent asd) {
        String login = lLogin.getText();
        String pass = lPass.getText();
        String name = lName.getText();
        String surname = lSurname.getText();
        tdl.registerPerson(login, pass, name, surname);
        fillTable();
        lLogin.setText("");
        lPass.setText("");
        lName.setText("");
        lSurname.setText("");
    }

    public void fillTable() {
        if (tdl != null) {
            userTable.getItems().clear();
            userTable.getItems().addAll(tdl.getAllUsers());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableView tableView = new TableView();

        TableColumn<String, User> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, User> column2 = new TableColumn<>("Login name");
        column2.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<String, User> column3 = new TableColumn<>("Email");
        column3.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.getColumns().clear();

        userTable.getColumns().add(column1);
        userTable.getColumns().add(column2);
        userTable.getColumns().add(column3);

    }

}
