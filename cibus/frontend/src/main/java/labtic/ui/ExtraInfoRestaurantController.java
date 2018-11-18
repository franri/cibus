package labtic.ui;

import com.jfoenix.controls.JFXListView;
import entities.*;
import exceptions.NoRestaurantFound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;


@Data
@Controller
public class ExtraInfoRestaurantController implements Initializable {

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label horario;

    @FXML
    private Label maxCapacity;

    @FXML
    private Label email;

    @FXML
    private Label LabelRestaurantName;

    @FXML
    private Button reserveButton;

    @FXML
    private JFXListView<Food> foodList;

    @FXML
    private ImageView profilePicture;

    @Autowired
    private BackendService bs;

    Consumer consumer;

    private Restaurant restaurant;

    Long cantidad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phone.setText(Long.toString(restaurant.getPhoneNumber()));
        address.setText(restaurant.getAddress());
        LabelRestaurantName.setText(restaurant.getName());
        email.setText(restaurant.getEmail());
        maxCapacity.setText(Long.toString(restaurant.getMaxCapacity()));
        horario.setText(restaurant.getOpeningHour().toString().concat(" - ").concat(restaurant.getClosingHour().toString()));

        if(restaurant.getProfilePicture() != null) {
            profilePicture.setImage(
                    new Image(new ByteArrayInputStream(restaurant.getProfilePicture()), 600, 200, true, true)
            );
        }else{
            profilePicture.setImage(
            new Image(getClass().getResourceAsStream("CibusLogo.png"), 60, 60, true, true)
            );
        }

        reserveButton.setText("Reservar para " + cantidad);

        foodList.setCellFactory(param -> new ListCell<Food>() {
            @Override
            protected void updateItem(Food item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        List<Food> comidas = restaurant.getFoods();
        foodList.getItems().addAll(comidas);
    }

    @FXML
    void reserveRestaurant(ActionEvent event) throws NoRestaurantFound, RemoteException {
        if(bs.findRestaurant(restaurant.getEmail()).getFreePlaces()>=cantidad) {
            Reservation newReservation = new Reservation((LocalTime.now()).plusMinutes(30), restaurant, consumer, cantidad, ReservationStatus.PENDING);
            try {
                bs.saveReservation(newReservation);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            reserveButton.setText(reserveButton.getText() + "✔");
        }else{
            reserveButton.setText(reserveButton.getText() + "✘");
        }
    }


}