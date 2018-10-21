package labtic.ui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Consumer;
import exceptions.NoConsumerFound;
import exceptions.PasswordsDontMatch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;
import java.rmi.RemoteException;

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

        Consumer consumer = null;
        try {
            consumer = bs.findConsumer(consumer.getEmail());
            if (!passwordField.getText().equals(reEnterPasswordField.getText())) {
                throw new PasswordsDontMatch("Las contraseñas no son coinciden");
            }
        } catch (NoConsumerFound noConsumerFound) {
            errorLabel.setText("Usuario incorrecto");
            errorLabel.setVisible(true);
            return;
        } catch (PasswordsDontMatch passwordsDontMatch) {
            errorLabel.setText("Las contraseñas no coinciden");
            errorLabel.setVisible(true);
            return;
        }


        Consumer newConsumser = new Consumer(emailNewUser.getText(),passwordField.getText(),firstNameNewUser.getText(),getLastNameNewUser().getText(),longPhoneNumber);
        bs.saveNewConsumer(newConsumser);
        errorLabel.setText("Usuario creado con éxito");
        errorLabel.setVisible(true);
    }


}
