package labtic.ui;

import com.jfoenix.controls.JFXButton;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import labtic.AppStarter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Data
@Controller
public class SearchPageController implements Initializable {

    private final int AMOUNT_OF_SEATS = 10;

    @FXML
    Label errorLabel;


    @FXML
    private TextField buscaNombre;

    @FXML
    private MenuButton listaBarrios = new MenuButton();

    @FXML
    private ComboBox lugares = new ComboBox();

    @FXML
    private MenuButton listaComidas;

    @Autowired
    private BackendService bs;

    Consumer consumer;

    @FXML
    private ListView<Restaurant> listaRestaurantes;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        System.out.println(consumer.getFirstName());

        //  Cargo lista de barrios, comidas a drop-down
        List<Neighbourhood> barrios = null;
        List<Food> comidas = null;
        try {
            barrios = bs.getListaBarrios();
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

//        Meto lista para elegir lugares
        ObservableList<Integer> lugaresList = FXCollections.observableArrayList();
        for(int i=1; i<AMOUNT_OF_SEATS+1; i++){
            lugaresList.add(i);
        }
        lugares.setItems(lugaresList);


//        Inicializo ListiView, le especifico Cell Factory de Restaurante

        listaRestaurantes.setCellFactory(new Callback<ListView<Restaurant>, ListCell<Restaurant>>() {
            @Override
            public ListCell<Restaurant> call(ListView<Restaurant> param) {
                return new CustomListCell();
            }
        });
    }

    @FXML
    public void cargarRestaurantes(ActionEvent event) throws RemoteException {

        String nombre = buscaNombre.getText()==null ? "" : buscaNombre.getText();

        List<Food> comidas = new ArrayList<>();
        for (MenuItem item : listaComidas.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                comidas.add((Food)deItem.getUserData());
            }
        }
        //TODO que pasa si la lista de comidas está vacía?

        List<Neighbourhood> barrios = new ArrayList<>();
        for (MenuItem item : listaBarrios.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                barrios.add((Neighbourhood)deItem.getUserData());
            }
        }
        if(barrios.size()==0){
            barrios = bs.getListaBarrios();
        }

        Integer lugaresReservados = (Integer) lugares.getSelectionModel().getSelectedItem();

        if(lugaresReservados==null){
            errorLabel.setText("Debe seleccionar cantidad de lugares");
            errorLabel.setVisible(true);
            return;
        }else{errorLabel.setVisible(false);}

        Long size = (long) comidas.size();

        List<Restaurant> restaurants = bs.filtrarRestaurants(nombre, comidas, barrios, Long.valueOf(lugaresReservados),
                size);

        listaRestaurantes.getItems().clear();
        listaRestaurantes.getItems().addAll(restaurants);

    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }


    private class CustomListCell extends ListCell<Restaurant>{
        private Restaurant restaurant;
        private HBox content;
        private Text name;
        private Text rating;
        private Text avgPrice;
        private Text workingHours;
        private JFXButton confirmReservation;

        public CustomListCell() {
            super();
            name = new Text();
            rating = new Text();
            avgPrice = new Text();
            workingHours = new Text();
            VBox vBoxName = new VBox();
            vBoxName.setAlignment(Pos.TOP_CENTER);
            vBoxName.getChildren().addAll(name, workingHours);
            VBox.setVgrow(name, Priority.ALWAYS);
            VBox.setVgrow(workingHours, Priority.ALWAYS);
            VBox vBoxData = new VBox();
            vBoxData.getChildren().addAll(rating, avgPrice);
            vBoxData.setSpacing(10);
            vBoxData.setAlignment(Pos.TOP_CENTER);
            confirmReservation = setupButton();
            content = new HBox(new Label("Imagen"), vBoxName, vBoxData,confirmReservation);
            HBox.setHgrow(vBoxName, Priority.ALWAYS);
            content.setSpacing(15);
            content.setPadding(new Insets(15, 12, 15, 12));
            content.setStyle("-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-color: grey;");


        }

        private JFXButton setupButton(){
            JFXButton confirmReservation = new JFXButton("Reservar");

            confirmReservation.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Integer lugaresReservados = (Integer) lugares.getSelectionModel().getSelectedItem();
                Reservation newReservation = new Reservation((LocalTime.now()).plusMinutes(30),restaurant,consumer,Long.valueOf(lugaresReservados),ReservationStatus.PENDING);
                try {
                    bs.saveReservation(newReservation);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            return confirmReservation;
        }

        @Override
        protected void updateItem(Restaurant item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                restaurant = item;
                name.setText(item.getName());
                rating.setText(item.getRating().toString());
                avgPrice.setText(item.getAvgPrice().toString());
                workingHours.setText(item.getOpeningHour().toString().concat(" - ").concat(item.getClosingHour().toString()));
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    public void handleEnterPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            cargarRestaurantes(null);
        }
    }
}

