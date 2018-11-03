package labtic.ui;


import entities.Reservation;
import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import javax.naming.spi.ResolveResult;
import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class RestaurantMainPageController implements Initializable {

    @FXML
    private Label nombreRestaurant;

    @FXML
    private ImageView settings;
    @FXML
    private ListView<Reservation> pendingList;

    @FXML
    private ListView<Reservation> acceptedLIst;

    @FXML
    private Spinner<Integer> cantLugaresDisponibles;

    @FXML
    private Spinner<Integer> mesas4Disponibles;

    @FXML
    private Spinner<Integer> mesas2Disponibles;

    @FXML
    private ImageView refreshButton;

    private Restaurant restaurant;

    @Autowired
    BackendService bs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreRestaurant.setText(restaurant.getName());

        List<Reservation> pendingReservations = null;
        try {
            pendingReservations = bs.findPendResOf(restaurant);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        pendingList.getItems().addAll(pendingReservations);

        //Configure Spinners:

        SpinnerValueFactory<Integer> cantLugaresFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0);
        this.cantLugaresDisponibles.setValueFactory(cantLugaresFactory);

        SpinnerValueFactory<Integer> mesas4Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0);
        this.mesas4Disponibles.setValueFactory(cantLugaresFactory);

        SpinnerValueFactory<Integer> mesas2Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,0);
        this.mesas2Disponibles.setValueFactory(cantLugaresFactory);

    }


    private class CustomListCell extends ListCell<Reservation> {

        File file = new File("labtic/resources/labtic/ui/confirm.png");
        Image image = new Image(file.toURI().toString());

        private Reservation reservation;
        private HBox content;
        private Text nombreComensal;
        private Text cantPersonas;
        private ImageView confirmarReserva;

        public CustomListCell() {
            super();
            nombreComensal = new Text();
            cantPersonas = new Text();
            confirmarReserva = new ImageView(image);
            VBox vBoxName = new VBox();
            vBoxName.setAlignment(Pos.TOP_CENTER);
            vBoxName.getChildren().addAll(nombreComensal);
            VBox.setVgrow(nombreComensal, Priority.ALWAYS);
            VBox vBoxData = new VBox();
            vBoxData.getChildren().addAll(cantPersonas);
            vBoxData.setSpacing(10);
            vBoxData.setAlignment(Pos.TOP_CENTER);
            content = new HBox(vBoxName, vBoxData, confirmarReserva);
            HBox.setHgrow(vBoxName, Priority.ALWAYS);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(Reservation item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                reservation = item;
                nombreComensal.setText(item.getConsumer().getFirstName().concat(" ").concat(item.getConsumer().getLastName()));
                cantPersonas.setText(item.getGroupSize().toString());
                setGraphic(content);
            } else {
                setGraphic(null);
            }

        }
    }

}

