package labtic.ui;


import entities.Reservation;
import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import labtic.AppStarter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

@Data
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

        List<Reservation> pendingReservations = null;
        try {
            pendingReservations = bs.findPendResOf(restaurant);
        } catch (RemoteException e) {
            e.printStackTrace();
            //TODO cambiar a errorLabel
        }
        pendingList.getItems().addAll(pendingReservations);

    }

    @FXML
    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();

    }


    private class CustomListCell extends ListCell<Reservation> {

        private Reservation reservation;
        private HBox content;
        private Text nombreComensal;
        private Text cantPersonas;
        private ImageView confirmarReserva;
        private ImageView rechazarReserva;

        public CustomListCell() {
            super();
            nombreComensal = new Text();
            cantPersonas = new Text();
            confirmarReserva = configureConfirmButton();
            rechazarReserva = configureDeclineButton();
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

        private ImageView configureConfirmButton() {
            File fileTick = new File("labtic/resources/labtic/ui/confirm.png");
            Image tick = new Image(fileTick.toURI().toString());

            ImageView button = new ImageView(tick);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                //TODO chequear constraints de lugares y aceptar reserva FALTA ZUFRAA
            });
            return button;
        }

        private ImageView configureDeclineButton() {
            File fileDecline = new File("labtic/resources/labtic/ui/decline.png");
            Image decline = new Image(fileDecline.toURI().toString());


            ImageView button = new ImageView(decline);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                try {
                    bs.refuseReservation(reservation);
                    refresh(); //TODO haer bien la funcion
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            return button;
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

    private void refresh() {
    }
}
