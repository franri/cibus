package labtic.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.Admin;
import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import exceptions.NoRestaurantFound;
import exceptions.RestaurantAlreadyRegistered;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import labtic.AppStarter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Data
@Controller
public class AdminPageController implements Initializable {

    private static final int AMOUNT_OF_SEATS = 50;
    @FXML
    private Label title;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField rutField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView backArrow;

    @Autowired
    BackendService bs;

    Admin admin;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       title.setText("Administrador: ".concat(admin.getEmail()));

        List<Neighbourhood> barrios = null;
        List<Food> comidas = null;
        try {
            barrios = bs.getListaBarrios();
            comidas = bs.getListaComidas();
        } catch (RemoteException e) {
            errorLabel.setText("Error en conexión al servidor");
            errorLabel.setVisible(true);
        }
//
//        barriosMenu.setCellFactory(new Callback<ListView<Neighbourhood>,ListCell<Neighbourhood>>(){
//            @Override
//            public ListCell<Neighbourhood> call(ListView<Neighbourhood> l){
//                return new ListCell<Neighbourhood>(){
//                    @Override
//                    protected void updateItem(Neighbourhood item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item == null || empty) {
//                            setGraphic(null);
//                        } else {
//                            setText(item.getName());
//                        }
//                    }
//                } ;
//            }
//        });
    }


    @SuppressWarnings("Duplicates")
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
    void tryToRegisterRestaurant(ActionEvent event) throws RemoteException {
        if(emailField == null || "".equals(emailField.getText()) || passwordField == null || "".equals(passwordField.getText())
        || nameField == null || "".equals(nameField.getText()) || rutField == null || "".equals(rutField.getText())){
            errorLabel.setText("Inserte los datos requeridos");
            errorLabel.setVisible(true);
            return;
        }

        try {
            if(bs.findRestaurant(emailField.getText()) != null){
                errorLabel.setText("Restaurante ya ingresado con ese email");
                errorLabel.setVisible(true);
                return;
            }
        } catch (NoRestaurantFound noRestaurantFound) {
            // a gozar
        }

        if(bs.existsByRut(rutField.getText())){
            errorLabel.setText("Restaurante ya ingresado con ese RUT");
            errorLabel.setVisible(true);
            return;
        }

        Restaurant res = new Restaurant(emailField.getText(), passwordField.getText(),
                nameField.getText(), rutField.getText());
        bs.saveRestaurant(res);

        errorLabel.setText("Agregado con éxito");
        errorLabel.setVisible(true);
    }

}
