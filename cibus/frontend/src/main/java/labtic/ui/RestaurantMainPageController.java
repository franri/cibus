package labtic.ui;


import entities.Reservation;
import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

    private Restaurant restaurant;

    @Autowired
    BackendService bs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreRestaurant.setText(restaurant.getName());

        List<Reservation> pendingReservations = bs.findPendResOf(restaurant);
        pendingList.getItems().addAll(pendingReservations);

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

<<<<<<< HEAD
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
=======

>>>>>>> 6d3c1f1d463e0158e4d2984cc1dc2b8234a2edd5
        }
    }
}
