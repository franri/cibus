package labtic.ui;

import entities.Admin;
import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import exceptions.NoRestaurantFound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

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
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField rutField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField addressField;

    @FXML
    private Button confirmButton;

    @FXML
    private MenuButton comidasMenu;

    @FXML
    private ComboBox barriosMenu;

    @FXML
    private ComboBox capacidadMenu;

    @FXML
    private Label errorLabel;

    @Autowired
    BackendService bs;

    Admin admin;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        title.setText(title.getText().concat(" ").concat(admin.getUsername()));

        List<Neighbourhood> barrios = null;
        List<Food> comidas = null;
        try {
            barrios = bs.getListaBarrios();
            comidas = bs.getListaComidas();
        } catch (RemoteException e) {
            errorLabel.setText("Error en conexión al servidor");
            errorLabel.setVisible(true);
        }

        ObservableList<String> barriosToAdd = FXCollections.observableArrayList();
        barrios.forEach(item -> barriosToAdd.add(item.getName()));
        barriosMenu.setItems(barriosToAdd);


        ObservableList<CustomMenuItem> comidasToAdd = FXCollections.observableArrayList();
        comidas.forEach(item -> comidasToAdd.add(new CustomMenuItem(new CheckBox(item.getFoodName()), false)));
        comidasMenu.getItems().addAll(comidasToAdd);

//        Meto lista para elegir lugares
        ObservableList<Integer> lugaresList = FXCollections.observableArrayList();
        for(int i=1; i<AMOUNT_OF_SEATS+1; i++){
            lugaresList.add(i);
        }
        capacidadMenu.setItems(lugaresList);
    }

    @FXML
    void tryToRegisterRestaurant(ActionEvent event) throws RemoteException {
        if(emailField == null || "".equals(emailField.getText()) || passwordField == null || "".equals(passwordField.getText())
        || nameField == null || "".equals(nameField.getText()) || addressField == null || "".equals(addressField.getText())
        || rutField == null || "".equals(rutField.getText())){
            errorLabel.setText("Inserte los datos requeridos");
            errorLabel.setVisible(true);
            return;
        }

        String barrioString = (String)barriosMenu.getSelectionModel().getSelectedItem();
        if(barrioString==null){
            errorLabel.setText("Debe seleccionar un barrio");
            errorLabel.setVisible(true);
            return;
        }
        Neighbourhood neighbourhood = new Neighbourhood(barrioString);

        Integer capacidad = (Integer)capacidadMenu.getSelectionModel().getSelectedItem();

        if(capacidad==null){
            errorLabel.setText("Debe seleccionar cantidad de lugares");
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

        List<Food> comidas = new ArrayList<>();
        for (MenuItem item : comidasMenu.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                comidas.add(new Food(deItem.getText()));
            }
        }
        if (comidas.isEmpty()){
            errorLabel.setText("Debe seleccionar comidas");
            errorLabel.setVisible(true);
            return;
        }

        Restaurant res = new Restaurant(emailField.getText(), nameField.getText(), passwordField.getText(),
                nameField.getText(), rutField.getText(), Long.valueOf(capacidad), neighbourhood);
        res.getFoods().addAll(comidas);
        bs.saveRestaurant(res);

        errorLabel.setText("Agregado con éxito");
        errorLabel.setVisible(true);
    }

}
