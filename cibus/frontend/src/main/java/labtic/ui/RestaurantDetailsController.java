package labtic.ui;

import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import exceptions.NoRestaurantFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import rmi.BackendService;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantDetailsController {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField rutField;

    @FXML
    private JFXTextField directionField;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private Label errorLabel;

    @FXML
    private MenuButton listaComidas;

    @FXML
    private ImageView backArrow;

    @FXML
    void goBack(MouseEvent event) {

    }

    @Autowired
    private BackendService bs;


    @Override
    public void initialize(URL location, ResourceBundle resources) throws NoRestaurantFound, RemoteException {
        Restaurant restaurantToUpdateGetData = bs.findRestaurant(emailField.getText().toString());
        nameField.setText(restaurantToUpdateGetData.getName());
        emailField.setText(restaurantToUpdateGetData.getEmail());
        rutField.setText(restaurantToUpdateGetData.getRut());
        
        List<Food> comidas = null;
        try {
            comidas = bs.getListaComidas();
        } catch (RemoteException e) {
            errorLabel.setText("Error en conexión al servidor");
            errorLabel.setVisible(true);
        }

        ObservableList<CustomMenuItem> barriosToAdd = FXCollections.observableArrayList();
        barrios.forEach(item -> {
            CheckBox checkBox = new CheckBox(item.getName());
            checkBox.setUserData(item);
            CustomMenuItem customMenuItem = new CustomMenuItem(checkBox, false);
            barriosToAdd.add(customMenuItem);
        });
        listaBarrios.getItems().addAll(barriosToAdd);

        ObservableList<CustomMenuItem> comidasToAdd = FXCollections.observableArrayList();
        comidas.forEach(item -> {
            CheckBox checkBox = new CheckBox(item.getName());
            checkBox.setUserData(item);
            CustomMenuItem customMenuItem = new CustomMenuItem(checkBox, false);
            comidasToAdd.add(customMenuItem);
        });
        listaComidas.getItems().addAll(comidasToAdd);
    }

    @FXML
    void fillDataOfRestaurant(ActionEvent event) throws NoRestaurantFound, RemoteException {
        Restaurant restaurantToUpdate = bs.findRestaurant(emailField.getText().toString());
        restaurantToUpdate.setAddress(directionField.getText().toString());
        restaurantToUpdate.
    }

}