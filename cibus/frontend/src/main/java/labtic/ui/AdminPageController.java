package labtic.ui;

import entities.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;
import org.springframework.stereotype.Controller;

@Data
@Controller
public class AdminPageController {

    @FXML
    private TextField capacityField;

    @FXML
    private TextField nameField;

    @FXML
    private Button createButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private MenuButton comidasMenu;

    @FXML
    private Label title;

    @FXML
    private ComboBox<?> barriosMenu;

    @FXML
    private TextField addressField;

    @FXML
    private Label errorLabel;

    Admin user;

}
