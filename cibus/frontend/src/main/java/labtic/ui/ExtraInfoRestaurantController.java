package labtic.ui;

import com.jfoenix.controls.JFXListView;
import entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

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
    void reserveRestaurant(ActionEvent event) {
        Reservation newReservation = new Reservation((LocalTime.now()).plusMinutes(30),restaurant,consumer,cantidad, ReservationStatus.PENDING);
        try {
            bs.saveReservation(newReservation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        reserveButton.setText(reserveButton.getText()+"✔");
    }


}