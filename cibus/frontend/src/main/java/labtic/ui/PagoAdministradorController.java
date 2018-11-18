package labtic.ui;

import entities.Reservation;
import entities.ReservationStatus;
import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class PagoAdministradorController implements Initializable {

    @FXML
    private ListView<Restaurant> listaRestaurantesQuePagan;

    @FXML
    private ImageView backArrow;

    @Autowired
    BackendService bs;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("AdminPage.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Restaurant> listaTotalDeRestaurantes = null;
        try {
            listaTotalDeRestaurantes = bs.getRestaurants();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listaRestaurantesQuePagan.setCellFactory(param -> new ListCell<Restaurant>() {
        @Override
        protected void updateItem(Restaurant item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
               setText(item.getName()+item.getCobroDeServicio().toString());
            }
        }
        });
        listaRestaurantesQuePagan.getItems().addAll(listaTotalDeRestaurantes);
    }
}

