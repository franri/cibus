package labtic.ui;

import entities.*;
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

    Consumer consumer;

    @FXML
    private ListView<Restaurant> listaRestaurantes;

    @SuppressWarnings("Duplicates")
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
        //TODO que pasa si la lista de comidas está vacía?

        List<Neighbourhood> barrios = new ArrayList<>();
        for (MenuItem item : listaBarrios.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                barrios.add(new Neighbourhood(deItem.getText()));
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
        }

        Long size = (long) comidas.size();

        List<Restaurant> restaurants = bs.filtrarRestaurants(nombre, comidas, barrios, Long.valueOf(lugaresReservados),
                size);

        listaRestaurantes.getItems().addAll(restaurants);

    }

}

