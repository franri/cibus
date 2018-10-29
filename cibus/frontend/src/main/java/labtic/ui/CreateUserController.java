package labtic.ui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Consumer;
import exceptions.NoConsumerFound;
import exceptions.PasswordsDontMatch;
import exceptions.UserAlreadyRegistered;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import labtic.AppStarter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.IOException;
import java.rmi.RemoteException;

@Data
@Controller
public class CreateUserController {
    @FXML
    private JFXTextField emailNewUser;
    @FXML
    private JFXTextField lastNameNewUser;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXPasswordField reEnterPasswordField;
    @FXML
    private JFXTextField phoneNewUser;
    @FXML
    private JFXTextField firstNameNewUser;
    @FXML
    private ImageView backArrow;

    @FXML
    private Label errorLabel;

    @Autowired
    private BackendService bs;


    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @FXML
    void registerNewConsumer(ActionEvent event) throws RemoteException{
        Long longPhoneNumber = null;
        if(reEnterPasswordField == null || "".equals(reEnterPasswordField.getText())|| passwordField == null || "".equals(passwordField.getText()) || emailNewUser == null || "".equals(emailNewUser.getText()) || phoneNewUser == null || "".equals(phoneNewUser.getText())
        ){
            errorLabel.setText("Inserte los datos requeridos");
            errorLabel.setVisible(true);
            return;
        } else {
            try {
                longPhoneNumber = Long.parseLong(phoneNewUser.getText());
            } catch(NumberFormatException e) {
                errorLabel.setText("Inserte correctamente el número de telefono");
                errorLabel.setVisible(true);
                return;
            }
        }

        Consumer consumer = null;

        try{

            if(bs.existsConsumerByEmail(emailNewUser.getText())){
                throw new UserAlreadyRegistered("Usuario ya registrado");
            }

            if (!passwordField.getText().equals(reEnterPasswordField.getText())) {
                throw new PasswordsDontMatch("Las contraseñas no son coinciden");
            }

        } catch (UserAlreadyRegistered | PasswordsDontMatch error) {

            errorLabel.setText(error.getMessage());
            errorLabel.setVisible(true);
            return;

        }

        Consumer newConsumser = new Consumer(emailNewUser.getText(),passwordField.getText(),firstNameNewUser.getText(),lastNameNewUser.getText(),longPhoneNumber);
        bs.saveNewConsumer(newConsumser);
        errorLabel.setText("Usuario creado con éxito");
        errorLabel.setVisible(true);
    }


}