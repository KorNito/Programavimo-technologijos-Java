package gui;

import ds.ToDoList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RegistrationController implements Initializable {

    @FXML
    TextField nameField, lastnameField, emailField, loginField, passwordField, confirmField;

    @FXML
    RadioButton companyButton, personButton;

    private ToDoList todo = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setToDoList(ToDoList tdl) {
        todo = tdl;
    }
    
    public void regsterUser(ActionEvent af){
        String name = nameField.getText();
        String lastname = lastnameField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        
        if(password.equals(confirm)){
            if(name.length()>0 && login.length()>0){
                if(todo.isLoginOcupied(login)){
                    if(personButton.isSelected()){
                        if(email.length()>0 && lastname.length()>0)
                            todo.registerPerson(login, password, name, lastname);
                    }
                    else{
                        todo.registerCompany(login, password, name);
                    }
                }
            }
        }
    }

}
