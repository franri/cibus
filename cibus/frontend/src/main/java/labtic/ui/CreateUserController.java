package labtic.ui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Admin;
import entities.Consumer;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

@Data
@Controller
public class CreateUserController {


    @FXML
    private JFXTextField emailNewUser;

    @FXML
    private JFXTextField LastNameNewUser;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField reEnterPasswordField;

    @FXML
    private JFXTextField phoneNewUser;

    @FXML
    private JFXTextField firstNameNewUser;
    @FXML
    private Label errorLabel;

    @Autowired
    private BackendService bs;

    @FXML
    void registerNewConsumer(ActionEvent event) throws RemoteException{
        Long longPhoneNumber = null;
        if(passwordField == null || "".equals(passwordField.getText()) || emailNewUser == null || "".equals(emailNewUser.getText()) || phoneNewUser == null || "".equals(phoneNewUser.getText())
                ){
            errorLabel.setText("Inserte los datos requeridos");
            errorLabel.setVisible(true);
            return;
        } else {
          try {
              longPhoneNumber = Long.parseLong(phoneNewUser.getText());
          } catch(NumberFormatException e) {
              errorLabel.setText("Inserte correctamente el numero de telefono");
              errorLabel.setVisible(true);
              return;
            }
        }
        
        Consumer newConsumser = new Consumer(emailNewUser.getText(),passwordField.getText(),firstNameNewUser.getText(),getLastNameNewUser().getText(),longPhoneNumber);
        bs.saveNewConsumer(newConsumser);
        errorLabel.setText("Usuario creado con éxito");
        errorLabel.setVisible(true);
    }


}
