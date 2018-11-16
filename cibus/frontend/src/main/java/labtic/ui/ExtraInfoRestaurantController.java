package labtic.ui;

import com.jfoenix.controls.JFXListView;
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
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;


@Data
@Controller
public class ExtraInfoRestaurantController implements Initializable {

    private static final int AMOUNT_OF_SEATS = 0;
    @FXML
    private Label phoneLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label openTimeLabel;

    @FXML
    private Label closingTimeLabel;

    @FXML
    private Label maxCapLabel;

    @FXML
    private Label emailLabel;


    @FXML
    private Label numberOfPeopleLabel;


    @FXML
    private Label LabelRestaurantName;

    @FXML
    private JFXListView<Food> foodListView;

    @FXML
    private Button reserveButton;

    @FXML
    private ComboBox lugares = new ComboBox();


    @FXML
    Label errorLabel;


    @Autowired
    private BackendService bs;

    Consumer consumer;

    private Restaurant restaurant;

    Long numberOfPeopleReservation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phoneLabel.setText(Long.toString(restaurant.getPhoneNumber()));
        addressLabel.setText(restaurant.getAddress());
        LabelRestaurantName.setText(restaurant.getName());
        emailLabel.setText(restaurant.getEmail());
        maxCapLabel.setText(Long.toString(restaurant.getMaxCapacity()));
        openTimeLabel.setText(restaurant.getOpeningHour().toString());
        closingTimeLabel.setText(restaurant.getClosingHour().toString());

        numberOfPeopleLabel.setText(numberOfPeopleReservation.toString());

        List<Food> comidas = restaurant.getFoods();
        foodListView.getItems().addAll(comidas);
    }

    @FXML
    void reserveRestaurant(ActionEvent event) {
        Reservation newReservation = new Reservation((LocalTime.now()).plusMinutes(30),restaurant,consumer,Long.valueOf(numberOfPeopleReservation), ReservationStatus.PENDING);
        try {
            bs.saveReservation(newReservation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}