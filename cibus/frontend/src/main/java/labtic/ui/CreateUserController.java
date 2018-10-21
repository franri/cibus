package labtic.ui;


import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.Data;
import org.springframework.stereotype.Controller;



@Data
@Controller
public class CreateUserController {




    @FXML
    public JFXTextField nameText;

    @FXML
    void guardarDatos(ActionEvent event) {
        System.out.println("holi");
        System.out.println(nameText.getText());



    }


}
