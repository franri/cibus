package labtic.ui;

import entities.Admin;
import entities.Consumer;
import entities.User;
import exceptions.IncorrectPassword;
import exceptions.NoUserFound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import labtic.AppStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.IOException;

@Controller
public class LoginController{

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button confirmButton;

    @Autowired
    BackendService bs;

//    @Autowired
//    Stage stage;
    public LoginController() {
        System.out.println("Hola!");

    }

    @FXML
    void tryToLogin(ActionEvent event) throws IOException {
        if(emailField == null || "".equals(emailField.getText()) || passwordField == null || "".equals(passwordField.getText())){
            errorLabel.setText("Inserte los datos requeridos");
            errorLabel.setVisible(true);
            return;
        }

        User user = null;
        try {
            user = bs.findUser(emailField.getText());
            if(!user.getPassword().equals(passwordField.getText())){
                throw new IncorrectPassword(null);
            }
        } catch (NoUserFound noUserFound) {
            errorLabel.setText("Usuario incorrecto");
            errorLabel.setVisible(true);
            return;
        } catch (IncorrectPassword incorrectPassword) {
            errorLabel.setText("Contraseña incorrecta");
            errorLabel.setVisible(true);
            return;
        }

        //Usuario con todas las cosas ok, paso a ver que usuario es
        //Por ahora intento solo admin y comensal
        if(user instanceof Consumer){
            proceedToSearchpage((Consumer) user);
        }else if(user instanceof Admin){
            proceedToAdminPage((Admin) user);
        }

    }

    private void proceedToSearchpage(Consumer consumer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        SearchPageController controller = AppStarter.getContext().getBean(SearchPageController.class);
        controller.setConsumer(consumer);
        Parent root = loader.load(SearchPageController.class.getResourceAsStream("SearchPage.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void proceedToAdminPage(Admin admin) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        AdminPageController controller = AppStarter.getContext().getBean(AdminPageController.class);
        controller.setAdmin(admin);
        Parent root = loader.load(AdminPageController.class.getResourceAsStream("AdminPage.fxml"));
//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}