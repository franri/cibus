package labtic.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

@Controller
public class CreateOrLoginController {

    @FXML
    void handleCreateNewAccount(ActionEvent event) {
        System.out.println("Funca");
    }

    @FXML
    void handleLogin(ActionEvent event) {

    }

}