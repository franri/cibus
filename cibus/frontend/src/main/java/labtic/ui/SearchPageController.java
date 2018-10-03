package labtic.ui;

import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
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

    User user;

    @FXML
    private ListView<Restaurant> listaRestaurantes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        barrios.forEach(item -> barriosToAdd.add(new CustomMenuItem(new CheckBox(item.getName()), false)));
        listaBarrios.getItems().addAll(barriosToAdd);


        ObservableList<CustomMenuItem> comidasToAdd = FXCollections.observableArrayList();
        comidas.forEach(item -> comidasToAdd.add(new CustomMenuItem(new CheckBox(item.getFoodName()), false)));
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
                return new ListCell<Restaurant>(){
                    @Override
                    protected void updateItem(Restaurant item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                        }
                    }
                };
            }
        });

//        Restaurant res = new Restaurant();
//        res.setName("Pepe");
//        res.setRUT("44");
//        res.setAddress("Av. Brasil");
//        res.setPhoneNumber(4478L);
//        res.setMaxCapacity(10);
//        listaRestaurantes.getItems().add(res);
    }

    @FXML
    public void cargarRestaurantes(ActionEvent event) throws RemoteException {

        String nombre = buscaNombre.getText()==null ? "" : buscaNombre.getText();

        List<Food> comidas = new ArrayList<>();
        for (MenuItem item : listaComidas.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                comidas.add(new Food(deItem.getText()));
            }
        }

        List<Neighbourhood> barrios = new ArrayList<>();
        for (MenuItem item : listaBarrios.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                barrios.add(new Neighbourhood(deItem.getText()));
            }
        }

        Integer lugaresReservados = (Integer)lugares.getSelectionModel().getSelectedItem();

        if(lugaresReservados==null){
            errorLabel.setText("Debe seleccionar cantidad de lugares");
            errorLabel.setVisible(true);
            return;
        }

        List<Restaurant> restaurants = bs.filtrarRestaurants(nombre, comidas, barrios, lugaresReservados);

        listaRestaurantes.getItems().addAll(restaurants);

    }

//        ObservableList<CustomMenuItem> barrios = FXCollections.observableArrayList();
//        barrios.add(new CustomMenuItem(new CheckBox("Pocitos"), false));
//        barrios.add(new CustomMenuItem(new CheckBox("Centro"), false));
//        barrios.add(new CustomMenuItem(new CheckBox("Punta Carretas"), false));
//
//        for (CustomMenuItem item : barrios) {
//            listaBarrios.getItems().add(item);
//        }
//        CheckBox hola = (CheckBox) ((CustomMenuItem) listaBarrios.getItems().get(1)).getContent();
//        String hallo = hola.getText();
//        hola.setSelected(true);
//        boolean checked = hola.isSelected();
//        System.out.println(checked+hallo);
//
//
//        ObservableList<Integer> lugaresList = FXCollections.observableArrayList();
//        for(int i=1; i<AMOUNT_OF_SEATS+1; i++){
//            lugaresList.add(i);
//        }
//
//            lugares.setItems(lugaresList);
//
//            Integer halla = (Integer)lugares.getSelectionModel().getSelectedItem();
//            System.out.println(halla);
//
////        boolean halla = lugares.get
////        System.out.println(halla);
//        }
//
//        public void printalgo(){
//            System.out.println(lugares.getSelectionModel().getSelectedItem());
//            System.out.println(lugares.getSelectionModel().isEmpty());

}

