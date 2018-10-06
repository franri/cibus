package labtic.ui;

import entities.Food;
import entities.Neighbourhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class adminCreateRestaurantController implements Initializable {

    @FXML
    private MenuButton listaBarrios;

    @FXML
    private MenuButton listaComidas;

    @FXML
    private TextField restaurantName;

    @FXML
    private TextField restaurantTablesForTwo;

    @FXML
    private Button saveRestaurant;

    @FXML
    private TextField restaurantTelephoneNumber;

    @FXML
    private TextField restaurantMaxCapacity;

    @FXML
    private TextField restaurantEmail;

    @FXML
    private TextField restaurantDirection;

    @FXML
    private TextField restaurantTablesForFour;

    @FXML
    private TextField restaurantRUT;

    @FXML
    void obtainDataOfRestaurant(ActionEvent event) {

       String rName= restaurantName.getText();
       String rDirection=restaurantDirection.getText();
       String rRUT=restaurantRUT.getText();
       Long rPhone=Long.parseLong(restaurantTelephoneNumber.getText());
       String rEmail=restaurantEmail.getText();
       int rMaxCapacity= Integer.parseInt(restaurantMaxCapacity.getText());
       int rTableForFour= Integer.parseInt(restaurantTablesForFour.getText());
       int rTableForTwo= Integer.parseInt(restaurantTablesForTwo.getText());






    }



    @Autowired
    private BackendService bs;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
            //  Cargo lista de barrios, comidas a drop-down
            List<Neighbourhood> barrios = null;
            List<Food> comidas = null;
            try {
                barrios = bs.getListaBarrios();
                comidas = bs.getListaComidas();
            } catch (RemoteException e) {
                //errorLabel.setText("Error en conexión al servidor");
               // errorLabel.setVisible(true);
            }

            ObservableList<CustomMenuItem> barriosToAdd = FXCollections.observableArrayList();
            barrios.forEach(item -> barriosToAdd.add(new CustomMenuItem(new CheckBox(item.getName()), false)));
            listaBarrios.getItems().addAll(barriosToAdd);

            ObservableList<CustomMenuItem> comidasToAdd = FXCollections.observableArrayList();
            comidas.forEach(item -> comidasToAdd.add(new CustomMenuItem(new CheckBox(item.getFoodName()), false)));
            listaComidas.getItems().addAll(comidasToAdd);
        }

    }
