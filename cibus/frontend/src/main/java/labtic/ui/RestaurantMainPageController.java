package labtic.ui;


import entities.Reservation;
import entities.ReservationStatus;
import entities.Restaurant;
import exceptions.NoRestaurantFound;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ListView<Reservation> acceptedList;

    @FXML
    private Spinner<Integer> cantLugaresDisponibles;

    @FXML
    private Spinner<Integer> mesas4Disponibles;

    @FXML
    private Spinner<Integer> mesas2Disponibles;

    @FXML
    private ImageView refreshButton;

    private Restaurant restaurant;

    @FXML
    private Label errorLabel;

    @FXML
    private Label cobroPorServicios;

    @Autowired
    BackendService bs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nombreRestaurant.setText(restaurant.getName());
        cobroPorServicios.setText(restaurant.getCobroDeServicio().toString());

        pendingList.setCellFactory(param -> new PendingCell());

        List<Reservation> pendingReservations = null;
        try {
            pendingReservations = bs.findPendResOf(restaurant);
        } catch (RemoteException e) {
            errorLabel.setText("Error de conexión");
            errorLabel.setVisible(true);
        }
        pendingList.getItems().addAll(pendingReservations);

        acceptedList.setCellFactory(param -> new AcceptedCell());

        List<Reservation> acceptedReservations = null;
        try {
            acceptedReservations = bs.findAccResOf(restaurant);
        } catch (RemoteException e) {
            errorLabel.setText("Error de conexión");
            errorLabel.setVisible(true);
        }
        acceptedList.getItems().addAll(acceptedReservations);

        //Configure Spinners:

        SpinnerValueFactory<Integer> cantLugaresFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,restaurant.getFreePlaces().intValue());
        this.cantLugaresDisponibles.setValueFactory(cantLugaresFactory);

        SpinnerValueFactory<Integer> mesas4Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,restaurant.getTableForFour().intValue());
        this.mesas4Disponibles.setValueFactory(mesas4Factory);

        SpinnerValueFactory<Integer> mesas2Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,999,restaurant.getTableForTwo().intValue());
        this.mesas2Disponibles.setValueFactory(mesas2Factory);

    }

    @FXML
    public void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @FXML
    public void goToDetails(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(RestaurantDetailsController.class.getResource("RestaurantDetails.fxml"));
        RestaurantDetailsController controller = AppStarter.getContext().getBean(RestaurantDetailsController.class);
        controller.setRestaurant(restaurant);

        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }


    private class PendingCell extends ListCell<Reservation> {

        private Reservation reservation;
        private HBox content;
        private Text nombreComensal;
        private Text cantPersonas;
        private Button confirmarReserva;
        private Button rechazarReserva;
        private TextField mesas2;
        private TextField mesas4;

        PendingCell() {
            super();
            nombreComensal = new Text();
            cantPersonas = new Text();
            confirmarReserva = configureConfirmButton();
            rechazarReserva = configureDeclineButton();

            //diseño info
            VBox infoArriba = new VBox(nombreComensal, new HBox(new Label("n°: "), cantPersonas));

            HBox infoAbajo;
            Text two = new Text("2"); two.maxWidth(20);
            mesas2 = new TextField(); mesas2.maxWidth(80);
            Text four = new Text("4"); four.maxWidth(20);
            mesas4 = new TextField(); mesas4.maxWidth(80);
            infoAbajo = new HBox(two, mesas2, four, mesas4);
            infoAbajo.maxWidth(200);

            VBox info = new VBox(infoArriba, infoAbajo); info.setMaxWidth(130);

            //diseño buttons
            VBox holderCerrar = new VBox(rechazarReserva); holderCerrar.setAlignment(Pos.TOP_RIGHT);
            VBox holderAceptar = new VBox(confirmarReserva); holderAceptar.setAlignment(Pos.CENTER);
            VBox.setVgrow(rechazarReserva, Priority.NEVER);
            VBox.setVgrow(confirmarReserva, Priority.NEVER);
            VBox buttons = new VBox(holderCerrar, holderAceptar);

            HBox.setHgrow(buttons, Priority.ALWAYS);
            content = new HBox(info, buttons);
        }

        private Button configureConfirmButton() {
            Image image = new Image(getClass().getResourceAsStream("confirm.png"), 30, 30, false, false);
            Button button = new Button();
            button.setGraphic(new ImageView(image));
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                boolean canAcceptReservation = true;
                try {
                    if (reservation.getTotalPeople() > restaurant.getFreePlaces()) {
                        errorLabel.setText("No hay capacidad");
                        errorLabel.setVisible(true);
                        return;
                    }

                    Long numeroMesas2 = Long.valueOf(mesas2.getText());
                    Long numeroMesas4 = Long.valueOf(mesas4.getText());

                    if (numeroMesas2 > restaurant.getTableForTwo() ||
                            numeroMesas4 > restaurant.getTableForFour()) {
                        errorLabel.setText("No hay mesas");
                        errorLabel.setVisible(true);
                        return;
                    }

                }catch(NumberFormatException e){
                    errorLabel.setText("Ingrese números");
                    errorLabel.setVisible(true);
                    return;
                }

                try {
                    reservation.setTableOfTwo(Long.valueOf(mesas2.getText()));
                    reservation.setTableOfFour(Long.valueOf(mesas4.getText()));
                    reservation.setReservationStatus(ReservationStatus.ACCEPTED);
                    bs.saveReservation(reservation);
                    bs.reduceFree(restaurant, reservation.getTotalPeople(), Long.valueOf(mesas2.getText()), Long.valueOf(mesas4.getText()));
                    bs.cobrar(restaurant);
                    refreshAfterConfirm();
                } catch (RemoteException e) {
                    errorLabel.setText("Error de conexión");
                    errorLabel.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return button;
        }

        private Button configureDeclineButton() {
            Image image = new Image(getClass().getResourceAsStream("decline.png"), 30, 30, false, false);

            Button button = new Button();
            button.setGraphic(new ImageView(image));
            button.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                try {
                    bs.refuseReservation(reservation);
                    refresh();
                } catch (IOException e) {
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
                cantPersonas.setText(item.getTotalPeople().toString());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    private class AcceptedCell extends ListCell<Reservation> {

        private Reservation reservation;
        private HBox content;
        private Text nombreComensal;
        private Text cantPersonas;
        private Text two, four;
        private Button darDeBaja;

        AcceptedCell() {
            super();
            nombreComensal = new Text();
            cantPersonas = new Text();
            darDeBaja = configureDarDeBajaButton();

            //diseño info
            VBox infoArriba = new VBox(nombreComensal, new HBox(new Label("n°: "), cantPersonas));

            HBox infoAbajo;
            two = new Text("2"); two.maxWidth(20); two.minWidth(20);
            four = new Text("4"); four.maxWidth(20); four.minWidth(20);
            infoAbajo = new HBox(two, four);
            infoAbajo.maxWidth(200);

            VBox info = new VBox(infoArriba, infoAbajo); info.setMaxWidth(130);

            //diseño buttons
            VBox holderDarDeBaja = new VBox(darDeBaja); holderDarDeBaja.setAlignment(Pos.CENTER);
            content = new HBox(info, holderDarDeBaja);
            content.setOpacity(1);
        }
        private Button configureDarDeBajaButton() {
            Image image = new Image(getClass().getResourceAsStream("decline.png"), 30, 30, false, false);

            Button button = new Button();
            button.setGraphic(new ImageView(image));
            button.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                try {
                    bs.completeReservation(reservation);
                    bs.reduceFree(restaurant,
                            -1*reservation.getTotalPeople(),
                            -1*reservation.getTableOfTwo(),
                            -1*reservation.getTableOfFour());
                    refresh();
                } catch (IOException e) {
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
                cantPersonas.setText(item.getTotalPeople().toString());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    private void refresh() throws IOException {
            restaurant.setFreePlaces(Long.valueOf(cantLugaresDisponibles.getValue()));
            restaurant.setTableForTwo(Long.valueOf(mesas2Disponibles.getValue()));
            restaurant.setTableForFour(Long.valueOf(mesas4Disponibles.getValue()));

            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(AppStarter.getContext()::getBean);
            loader.setLocation(RestaurantMainPageController.class.getResource("RestaurantMainPage.fxml"));
            RestaurantMainPageController controller = AppStarter.getContext().getBean(RestaurantMainPageController.class);
            try {
                controller.setRestaurant(bs.findRestaurant(restaurant.getEmail()));
            } catch (NoRestaurantFound noRestaurantFound) {
                noRestaurantFound.printStackTrace();
            }

            Parent root = loader.load();
            AppStarter.getMainStage().setScene(new Scene(root));
            AppStarter.getMainStage().show();

    }

    private void refreshAfterConfirm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(RestaurantMainPageController.class.getResource("RestaurantMainPage.fxml"));
        RestaurantMainPageController controller = AppStarter.getContext().getBean(RestaurantMainPageController.class);
        try {
            controller.setRestaurant(bs.findRestaurant(restaurant.getEmail()));
        } catch (NoRestaurantFound noRestaurantFound) {
            noRestaurantFound.printStackTrace();
        }

        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }
}

