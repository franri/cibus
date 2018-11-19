package labtic.ui;

import entities.Consumer;
import entities.Reservation;
import entities.ReservationStatus;
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
public class ReservasUsuarioController implements Initializable {

    @FXML
    private ListView<Reservation> listViewReservas;

    Consumer consumer;

    @Autowired
    BackendService bs;
    @FXML
    private ImageView backArrow;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("SearchPage.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

            listViewReservas.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        ReservationStatus estado = item.getReservationStatus();
                        String estadoParaMostrar = "Reserva";
                        if (estado == ReservationStatus.ACCEPTED){
                            estadoParaMostrar = "Reserva aceptada";
                        } else if (estado == ReservationStatus.DECLINED){
                            estadoParaMostrar = "Reserva declinada";
                        } else if (estado == ReservationStatus.COMPLETED){
                            estadoParaMostrar = "Reserva completada";
                        } else if(estado == ReservationStatus.PENDING){
                            estadoParaMostrar = "Reserva pendiente";
                        }
                        setText(item.getRestaurant().getName()+ " - " + estadoParaMostrar);
                    }
                }
            });
        try {
            List<Reservation> listaReservas = bs.getListOfReservationsFromConsumer(consumer);
            for(int i=listaReservas.size()-1; i>-1; i--){
                listViewReservas.getItems().add(listaReservas.get(i));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
