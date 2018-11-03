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
import java.util.List;
import java.util.ResourceBundle;

@Controller
@Data
public class RestaurantDetailsController implements Initializable {

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
    private TextField maxCapacity;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField tableOfTwo;

    @FXML
    private TextField tableOfFour;

    @FXML
    private MenuButton listaComidas;

    @FXML
    private ComboBox<Neighbourhood> listaBarrios;

    @FXML
    private ImageView backArrow;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @Autowired
    private BackendService bs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Restaurant restaurantToUpdateGetData = null;

        List<Neighbourhood> barrios = null;
        List<Food> comidas = null;

        try {
            restaurantToUpdateGetData = bs.findRestaurant(emailField.getText().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoRestaurantFound noRestaurantFound) {
            noRestaurantFound.printStackTrace();
        }
        nameField.setText(restaurantToUpdateGetData.getName());
        emailField.setText(restaurantToUpdateGetData.getEmail());
        rutField.setText(restaurantToUpdateGetData.getRut());

        try {
            comidas = bs.getListaComidas();
            barrios = bs.getListaBarrios();
        } catch (RemoteException e) {
            errorLabel.setText("Error en conexión al servidor");
            errorLabel.setVisible(true);
        }

        listaBarrios.setCellFactory(new Callback<ListView<Neighbourhood>,ListCell<Neighbourhood>>(){
            @Override
            public ListCell<Neighbourhood> call(ListView<Neighbourhood> l){
                return new ListCell<Neighbourhood>(){
                    @Override
                    protected void updateItem(Neighbourhood item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        });
        listaBarrios.getItems().addAll(barrios);

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
    void fillDataOfRestaurant(ActionEvent event) throws NoRestaurantFound, IOException {
        Restaurant restaurantToUpdate = bs.findRestaurant(emailField.getText().toString());
        restaurantToUpdate.setAddress(directionField.getText().toString());
        restaurantToUpdate.setMaxCapacity(Long.parseLong(maxCapacity.getText()));
        //restaurantToUpdate.setFoods(comidas);//TODO arreglar tema de las comidas
        restaurantToUpdate.setTableForFour(Long.parseLong(tableOfFour.getText()));
        restaurantToUpdate.setTableForTwo(Long.parseLong(tableOfTwo.getText()));

        Neighbourhood barrio = (Neighbourhood) listaBarrios.getSelectionModel().getSelectedItem();

        if(barrio==null){
            errorLabel.setText("Debe seleccionar cantidad de lugares");
            errorLabel.setVisible(true);
            return;
        }else{errorLabel.setVisible(false);}

        restaurantToUpdate.setNeighbourhood(barrio);

        bs.saveRestaurant(restaurantToUpdate);

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("RestaurantMainPage.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }



}